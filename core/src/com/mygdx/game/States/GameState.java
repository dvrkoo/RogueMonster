package com.mygdx.game.States;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Maps.Entity;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.Maps.Tile;
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

    BagScreen bagScreen;
    TeamScreen teamScreen;
    Item choosenItem = null;

    // GameState constructor
    public GameState(final RogueMonster game, Player player) {
        this.game = game;
        this.player = player;
        posReset.set(800, 800);

        for (int i = 0; i < 6; i++) {
            if (player.getPokemon(i) != null) {
                System.out.print(i);
            } else {
                System.out.print("cazzo");
            }
        }
        island = new Island();
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

        spawnEnemy();

    }

    @Override
    public void show() {

        gamestatus = GAME_RUNNING;
        System.out.println(posReset.x + " " + posReset.y);
        player.setPosition(posReset);
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
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.begin(ShapeType.Line);

        for (Rectangle collision : Island.collisionRectangle) {
            shapeRenderer.rect(collision.getX(), collision.getY(), collision.getWidth(), collision.getHeight());
        }
        for (Character pkmn : pokemon) {
            shapeRenderer.rect(pkmn.getX(), pkmn.getY(), pkmn.getWidth(), pkmn.getHeight());
        }
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        shapeRenderer.end();

        game.batch.setProjectionMatrix(cameraScreen.combined);
        game.batch.begin();

        drawScreen();

        game.batch.end();

        player.commandMovement();
        moveCamera();
        checkBattle();
        checkGameOver();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            gamestatus = GAME_BAG;
            bagScreen.isVisible = true;
        }

        switch (gamestatus) {
            case GAME_RUNNING: {
                player.commandMovement();
                moveCamera();
                checkBattle();
                for (Character iter : pokemon)
                    iter.update();
                break;
            }
            case GAME_BATTLE: {
                // draw pause screenù
                posReset.set(player.x, player.y);
                System.out.println(posReset.x + " " + posReset.y);
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
            // add cases, example bag state ecc
        }

    }

    void spawnEnemy() {

        Vector2 position = new Vector2();

        for (int i = 0; i < 10; i++) {
            pokemon.add(pkmFactory.getPokemon(Pokemon.randomPokemon()));
        }
        for (Character iter : pokemon) {
            Collision collision = new Collision();
            position = random.getRandomPos(island.minMaxX, island.minMaxY);
            iter.setPosition(position);
            while (collision.getCollision(iter) || collision.getPkmnCollision(iter)) {
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
            game.setScreen(new GameOver(game, score));
            this.dispose();
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
            // System.out.println(character.isOpponent);
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
