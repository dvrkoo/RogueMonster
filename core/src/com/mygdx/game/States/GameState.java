package com.mygdx.game.States;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Command.BagCommand;
import com.mygdx.game.Command.Command;
import com.mygdx.game.Command.DownCommand;
import com.mygdx.game.Command.LeftCommand;
import com.mygdx.game.Command.RightCommand;
import com.mygdx.game.Command.StandCommand;
import com.mygdx.game.Command.UpCommand;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Maps.Entity;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.Maps.Tile;
import com.mygdx.game.Observers.ChangeSateteNotifier;
import com.mygdx.game.Observers.Observer;
import com.mygdx.game.States.BattleState.BagScreen;
import com.mygdx.game.States.BattleState.BattleState;
import com.mygdx.game.States.BattleState.TeamScreen;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.RandomUtils;
import com.mygdx.game.Utils.Enums.ItemType;
import com.mygdx.game.Utils.Enums.Pokemon;

public class GameState implements Screen {
    // game attributes
    final RogueMonster game;
    public int score = 0;
    public static final int GAME_RUNNING = 0;
    public static final int GAME_BATTLE = 1;
    public static final int GAME_BAG = 2;
    public static final int GAME_OVER = 3;
    public static final int GAME_SWITCH = 4;

    private int gamestatus = GAME_RUNNING;

    // attributes
    public Player player;
    Vector2 posReset = new Vector2();

    Island island;

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Viewport viewport;
    OrthographicCamera camera;
    OrthographicCamera cameraScreen;
    float enlapsedTime;
    PokemonFactory pkmFactory;
    RandomUtils random = new RandomUtils();
    public static ArrayList<Character> pokemon;
    List<Observer> observers = new ArrayList<>();

    BagScreen bagScreen;
    TeamScreen teamScreen;
    Item choosenItem = null;

    // Command Pattern invoker
    Command bagCommand;
    Command leftCommand;
    Command rightCommand;
    Command upCommand;
    Command downCommand;
    Command standCommand;

    // GameState constructor
    public GameState(final RogueMonster game, Player player) {
        this.game = game;
        this.player = player;

        island = new Island();

        posReset.set(island.spawnX * 40, island.spawnY * 40);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        this.cameraScreen = new OrthographicCamera();
        this.cameraScreen.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);

        pkmFactory = new PokemonFactory();
        pokemon = new ArrayList<Character>();

        player.addItem(new Item(ItemType.POTION));
        player.addItem(new Item(ItemType.HYPERPOTION));
        player.addItem(new Item(ItemType.HYPERPOTION));
        player.addItem(new Item(ItemType.HYPERPOTION));
        player.addItem(new Item(ItemType.POTION));
        this.bagScreen = new BagScreen(player);
        this.teamScreen = new TeamScreen(player);

        addObserver(new ChangeSateteNotifier(player));

        // command init
        bagCommand = new BagCommand(bagScreen);
        upCommand = new UpCommand(player);
        downCommand = new DownCommand(player);
        leftCommand = new LeftCommand(player);
        rightCommand = new RightCommand(player);
        standCommand = new StandCommand(player);

        spawnEnemy();
    }

    void addObserver(Observer o) {
        observers.add(o);
    }

    void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void updateChangeToStartingState(boolean isChanged) {
        for (Observer o : observers) {
            o.update(isChanged);
        }
    }

    public void newLevel() {
        if (pokemon.size() == 0) {
            player.setPosition(200, 180);
            updateChangeToStartingState(false);
            player.levelCount++;
            gamestatus = GAME_SWITCH;

        }
    }

    @Override
    public void show() {

        gamestatus = GAME_RUNNING;
        player.setPosition(posReset);
        newLevel();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0.2f, 1);
        enlapsedTime += delta;

        camera.update();
        cameraScreen.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        drawMap();

        drawCharacters();

        drawEntities();
        game.batch.end();
        /*
         * shapeRenderer.setProjectionMatrix(camera.combined);
         * shapeRenderer.setColor(Color.BLUE);
         * shapeRenderer.begin(ShapeType.Line);
         * 
         * for (Rectangle collision : Island.collisionRectangle) {
         * shapeRenderer.rect(collision.getX(), collision.getY(), collision.getWidth(),
         * collision.getHeight());
         * }
         * for (Character pkmn : pokemon) {
         * shapeRenderer.rect(pkmn.getX(), pkmn.getY(), pkmn.getWidth(),
         * pkmn.getHeight());
         * }
         * shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(),
         * player.getHeight());
         * 
         * shapeRenderer.end();
         */

        game.batch.setProjectionMatrix(cameraScreen.combined);
        game.batch.begin();

        drawScreen();

        game.batch.end();

        checkGameOver();

        switch (gamestatus) {
            case GAME_RUNNING: {
                commandHandle();
                moveCamera();
                checkBattle();
                for (Character iter : pokemon)
                    iter.update();
                break;
            }
            case GAME_BATTLE: {
                // draw pause screenù
                posReset.set(player.x, player.y);
                game.setScreen(new BattleState(game, this));
                score += 100;
                break;

            }
            case GAME_BAG: {

                if (Gdx.input.justTouched() && bagScreen.isVisible) {
                    choosenItem = chooseItem(Gdx.input.getX(), Gdx.input.getY());
                } else if (Gdx.input.justTouched() && teamScreen.isVisible)
                    choosePokemon(Gdx.input.getX(), Gdx.input.getY(), choosenItem);
                break;
            }
            case GAME_OVER: {
                game.setScreen(new GameOver(game, score));
                this.dispose();
                break;
            }
            case GAME_SWITCH: {
                game.setScreen(new StartingState(game, player));
                dispose();
                break;
            }
            // add cases, example bag state ecc
        }

    }

    void spawnEnemy() {

        Vector2 position = new Vector2();
        if (player.levelCount == 1)
            for (int i = 0; i < 5; i++) {
                pokemon.add(pkmFactory.getPokemon(Pokemon.randomPokemon1()));
            }
        else {
            for (int i = 0; i < 5; i++) {
                pokemon.add(pkmFactory.getPokemon(Pokemon.randomPokemon2()));
            }
        }
        for (Character iter : pokemon) {
            Collision collision = new Collision();
            position = random.getRandomPos(island.minMaxX, island.minMaxY);

            iter.setPosition(position);
            while (collision.getCollision(iter) || collision.getPkmnCollision(iter)
                    || iter.y / 40 > island.chunk.numberRows / 2 - 4 && iter.y / 40 < island.chunk.numberRows / 2 + 4
                            && iter.x / 40 > island.chunk.numberCols / 2 - 4
                            && iter.x / 40 < island.chunk.numberCols / 2 + 4) {
                System.out.print(island.chunk.numberRows / 2 - 3 + "\n");
                System.out.print(island.chunk.numberCols / 2 - 3 + "\n");
                System.out.print(iter.y / 40 + "\n");
                System.out.print(iter.x / 40 + "\n");
                iter.setPosition(position);
                position = random.getRandomPos(island.minMaxX, island.minMaxY);
            }

        }
    }

    public void checkGameOver() {
        boolean found = false;
        for (int i = 0; i < 6; i++) {
            if (player.getPokemon(i) != null) {
                found = true;
            }
        }
        if (!found) {
            gamestatus = GAME_OVER;
        }
    }

    public void drawEntities() {
        for (Entity e : island.entities) {
            e.draw(game.batch);
        }
    }

    void battle() {
        gamestatus = GAME_BATTLE;
    }

    public void moveCamera() {
        Vector3 pos3 = new Vector3();
        pos3.x = player.x;
        pos3.y = player.y;
        camera.position.lerp(pos3, .1f);
    }

    public void drawCharacters() {
        game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
        for (Character iter : pokemon) {
            game.batch.draw(iter.getAnimation().getKeyFrame(enlapsedTime, true), iter.getX(), iter.getY());

        }

    }

    public void drawMap() {
        for (ArrayList<Tile> row : island.chunk.tiles) {
            for (Tile tile : row) {
                game.batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                if (tile.secondaryTexture != null)
                    game.batch.draw(tile.secondaryTexture, tile.pos.x, tile.pos.y, tile.size, tile.size);
            }
        }
    }

    public void checkBattle() {
        for (Character character : pokemon) {
            if (character.isOpponent) {
                battle();

            }
        }
    }

    void drawScreen() {
        if (bagScreen.isVisible)
            bagScreen.drawBagScreen(game);
        else if (teamScreen.isVisible)
            teamScreen.draw(game);

    }

    Item chooseItem(float x, float y) {
        y = Math.abs(y - 1000);
        Item itemChosen = null;
        boolean choose = false;
        for (int i = 0; i < bagScreen.buttons.size(); i++) {
            if (bagScreen.buttons.get(i).contains(x, y) && !player.getBag().getBag().isEmpty()) {
                teamScreen.isVisible = true;
                itemChosen = player.getBag().getBag().get(i).get(0);
                choose = true;
            }
        }
        if (choose) {
            bagScreen.isVisible = false;
        }

        return itemChosen;
    }

    void choosePokemon(float x, float y, Item item) {
        y = Math.abs(y - 1000);
        boolean isChoosen = false;

        for (int i = 0; i < teamScreen.buttons.length; i++) {
            if (teamScreen.buttons[i].contains(x, y) && player.getPokemon(i) != null && item != null) {
                item.useItem(player.getPokemon(i));
                for (final ArrayList<Item> iter : player.getBag().getBag()) {
                    if (iter.get(0).getType() == item.getType())
                        player.getBag().remove(item);
                }

                isChoosen = true;
            }
        }
        if (isChoosen) {
            teamScreen.isVisible = false;
            gamestatus = GAME_RUNNING;
        }

    }

    void commandHandle() {

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {// down
            downCommand.execute();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {// left
            leftCommand.execute();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {// right
            rightCommand.execute();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {// up
            upCommand.execute();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            gamestatus = GAME_BAG;
            bagCommand.execute();
        } else {
            standCommand.execute();

        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();

    }

    // getter setter
    public Player getPlayer() {
        return player;
    }

}
