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
    public static DialogueController dialogueController;
    private Dialogue dialogue;
    public DialogueBox dialogueBox;
    private OptionBox optionBox;
    Table root;

    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 0;

    Stage uiStage = new Stage(new ScreenViewport());

    public static RogueMonster game;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Viewport viewport;
    public static Player player;
    public static ArrayList<Character> characters;
    float enlapsedTime;
    public static PokemonFactory pkmFactory;
    Collision collisions = new Collision();
    public static ArrayList<Rectangle> rectangleArray = new ArrayList<Rectangle>();
    public static ArrayList<Rectangle> dialogRectangles = new ArrayList<Rectangle>();
    oak oak = new oak();
    private static List<Observer> observers = new ArrayList<>();
    public static Rectangle Mudkip;
    public static Rectangle Bulbasaur;
    public static Rectangle Charmander;

    public StartingState(final RogueMonster game) {
        this.game = game;
        player = new Player(200, 180);

        pkmFactory = new PokemonFactory();
        characters = new ArrayList<Character>();

        characters.add(oak);

        addObserver(new ChangeSateteNotifier(player));

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
        player.commandMovement();
        shapeRenderer.begin(ShapeType.Line);

        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (Rectangle rec : rectangleArray) {
            shapeRenderer.rect(rec.getX(), rec.getY(), 35, 35);
        }
        shapeRenderer.rect(oak.getX(), oak.getY(), oak.getWidth(), oak.getHeight());
        shapeRenderer.end();

        moveCamera();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            updateChangeToGameState(true);
            game.setScreen(new GameState(game, player));
        }

        uiStage.draw();

        update(delta);

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

    private void initUI() {

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
        this.observers.add(o);
    }

    void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public static void updateChangeToGameState(boolean isChanged) {
        for (Observer o : observers) {
            o.update(isChanged);
        }
    }

}
