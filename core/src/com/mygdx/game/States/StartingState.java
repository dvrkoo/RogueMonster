package com.mygdx.game.States;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.oak;
import com.mygdx.game.Command.Command;
import com.mygdx.game.Command.DownCommand;
import com.mygdx.game.Command.LeftCommand;
import com.mygdx.game.Command.RightCommand;
import com.mygdx.game.Command.StandCommand;
import com.mygdx.game.Command.UpCommand;
import com.mygdx.game.Controller.DialogueController;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;

import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Observers.ChangeSateteNotifier;
import com.mygdx.game.Observers.Observer;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.Enums.Pokemon;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.ui.OptionBox;

public class StartingState implements Screen {
    private InputMultiplexer multiplexer;
    public DialogueController dialogueController;
    private Dialogue dialogue;
    public DialogueBox dialogueBox;
    private OptionBox optionBox;
    static Table root;

    final int GAME_RUNNING = 1;
    final int GAME_PAUSED = 0;

    static Stage uiStage = new Stage(new ScreenViewport());

    String starter;
    final RogueMonster game;
    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Viewport viewport;
    public Player player;
    public ArrayList<Character> characters;
    float enlapsedTime;
    public PokemonFactory pkmFactory;
    Collision collisions = new Collision();
    public static ArrayList<Rectangle> rectangleArray = new ArrayList<Rectangle>();
    public static ArrayList<Rectangle> dialogRectangles = new ArrayList<Rectangle>();
    oak oak = new oak();
    private List<Observer> observers = new ArrayList<>();
    public static Rectangle Mudkip;
    public static Rectangle Bulbasaur;
    public static Rectangle Charmander;

    //Command Pattern invoker
    Command bagCommand;
    Command leftCommand;
    Command rightCommand;
    Command upCommand;
    Command downCommand;
    Command standCommand;

    public StartingState(final RogueMonster game, Player player) {
        this.player = player;
        this.game = game;

        pkmFactory = new PokemonFactory();
        characters = new ArrayList<Character>();

        characters.add(oak);

        addObserver(new ChangeSateteNotifier(player));

        //command init
        upCommand = new UpCommand(player);
        downCommand = new DownCommand(player);
        leftCommand = new LeftCommand(player);
        rightCommand = new RightCommand(player);
        standCommand = new StandCommand(player);

    }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Maps/lab2.tmx");

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions");
        TiledMapTileLayer secondCollisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions2");
        TiledMapTileLayer starter = (TiledMapTileLayer) map.getLayers().get("pokemon1");
        TiledMapTileLayer starter2 = (TiledMapTileLayer) map.getLayers().get("pokemon2");
        TiledMapTileLayer starter3 = (TiledMapTileLayer) map.getLayers().get("pokemon3");
        Mudkip = getDialogueCollisions(starter);
        Charmander = getDialogueCollisions(starter2);
        Bulbasaur = getDialogueCollisions(starter3);
        getCollisionArray(collisionObjectLayer);
        getCollisionArray(secondCollisionObjectLayer);
        renderer = new OrthogonalTiledMapRenderer(map, 2);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);

        viewport = new FitViewport(1000, 1000, camera);

        initUI();
        multiplexer = new InputMultiplexer();
        dialogueController = new DialogueController(dialogueBox, optionBox);
        multiplexer.addProcessor(0, dialogueController);
        dialogue = new Dialogue();

        DialogueNode node1 = new DialogueNode("No time for questions\nPick a Pokemon!", 0);

        dialogue.addNode(node1);

        dialogueController.startDialogue(dialogue);
        Gdx.input.setInputProcessor(multiplexer);

    }

    public void update(float delta) {
        dialogueController.update(delta);
        uiStage.act(delta);

    }

    @Override
    public void render(float delta) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.BLUE);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawCharacters();
        game.batch.end();
        commandHandle();
        shapeRenderer.begin(ShapeType.Line);

        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (Rectangle rec : rectangleArray) {
            shapeRenderer.rect(rec.getX(), rec.getY(), 35, 35);
        }
        shapeRenderer.rect(oak.getX(), oak.getY(), oak.getWidth(), oak.getHeight());
        shapeRenderer.end();

        moveCamera();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player.addPokemon(pkmFactory.getPokemon(Pokemon.MUDKIP));
            updateChangeToGameState(true);
            game.setScreen(new GameState(game, player));
        }

        uiStage.draw();

        update(delta);
        if (player.starter != null) {
            generateDialogue(player.starter);
            starter = player.starter;
            player.starter = null;
        }
        if (dialogueController.hasChosen == true) {
            addPkmn(starter);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();

        // TODO Auto-generated method stub

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
        dispose();
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        // TODO Auto-generated method stub

    }

    public void drawCharacters() {
        game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
        game.batch.draw(oak.getAnimation().getKeyFrame(enlapsedTime, true), oak.getX(), oak.getY());
    }

    public Player getPlayer() {
        return player;
    }

    public void moveCamera() {
        Vector3 pos3 = new Vector3();
        pos3.x = player.x;
        pos3.y = player.y;
        camera.position.lerp(pos3, .1f);
    }

    public Rectangle getDialogueCollisions(TiledMapTileLayer collisionObjectLayer) {

        Rectangle rectangle = new Rectangle();
        for (int x = 0; x < collisionObjectLayer.getWidth(); x++) {
            for (int y = 0; y < collisionObjectLayer.getHeight(); y++) {
                Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                    rectangle.x = x * 32;
                    rectangle.y = y * 32;
                }
            }
        }
        return rectangle;
    }

    public void getCollisionArray(TiledMapTileLayer collisionObjectLayer) {
        for (int x = 0; x < collisionObjectLayer.getWidth(); x++) {
            for (int y = 0; y < collisionObjectLayer.getHeight(); y++) {
                Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.x = x * 32;
                    rectangle.y = y * 32;
                    rectangleArray.add(rectangle);
                }
            }
        }
        rectangleArray.add(oak);
    }

    public void initUI() {

        uiStage.getViewport().update(700, 700, true);
        // uiStage.setDebugAll(true);

        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);

        dialogueBox = new DialogueBox(RogueMonster.getSkin());
        dialogueBox.setVisible(false);

        optionBox = new OptionBox(RogueMonster.getSkin());
        optionBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(optionBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogTable.add(dialogueBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();

        root.add(dialogTable).expand().align(Align.bottom);
    }

    void addObserver(Observer o) {
        observers.add(o);
    }

    void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void updateChangeToGameState(boolean isChanged) {
        for (Observer o : observers) {
            o.update(isChanged);
        }
    }

    public void addPkmn(String pkmn) {
        if (pkmn == "Mudkip") {
            updateChangeToGameState(true);
            player.addPokemon(pkmFactory.getPokemon(Pokemon.MUDKIP));
            game.setScreen(new GameState(game, player));
        } else if (pkmn == "Charmander") {
            updateChangeToGameState(true);
            player.addPokemon(pkmFactory.getPokemon(Pokemon.CHARMANDER));
            game.setScreen(new GameState(game, player));

        } else if (pkmn == "Bulbasaur") {
            updateChangeToGameState(true);
            player.addPokemon(pkmFactory.getPokemon(Pokemon.BULBASAUR));
            game.setScreen(new GameState(game, player));

        }
    }

    public void generateDialogue(String pkmn) {
        Dialogue dialogue = new Dialogue();

        DialogueNode node1 = new DialogueNode("Are you sure you want to pick " + pkmn + "?", 0);
        DialogueNode node2 = new DialogueNode("You won't be able to choose again", 1);

        DialogueNode node3 = new DialogueNode("Good luck !", 2);
        DialogueNode node4 = new DialogueNode("Alright then", 3);

        node1.makeLinear(node2.getID());
        node2.addChoice("Yes", 2);
        node2.addChoice("No", 3);

        dialogue.addNode(node1);
        dialogue.addNode(node2);

        dialogue.addNode(node3);
        dialogue.addNode(node4);
        dialogueController.pkmn = pkmn;
        dialogueController.startDialogue(dialogue);

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
        } else {
            standCommand.execute();

        }

    }
}
