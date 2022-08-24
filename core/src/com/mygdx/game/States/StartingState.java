package com.mygdx.game.States;

import java.util.ArrayList;

import javax.sound.sampled.Line;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.oak;
import com.mygdx.game.Controller.DialogueController;
import com.mygdx.game.Dialogue.Dialogue;
import com.mygdx.game.Dialogue.DialogueNode;
import com.mygdx.game.Dialogue.LinearDialogueNode;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.Enums.Pokemon;
import com.mygdx.game.ui.DialogueBox;
import com.mygdx.game.ui.OptionBox;

public class StartingState implements Screen {

    private DialogueController dialogueController;
    private Dialogue dialogue;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;

    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 0;

    final RogueMonster game;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Viewport viewport;
    public static Player player;
    public static ArrayList<Character> characters;
    float enlapsedTime;
    PokemonFactory pkmFactory;
    Collision collisions = new Collision();
    public static ArrayList<Rectangle> rectangleArray = new ArrayList<Rectangle>();
    oak oak = new oak();

    public StartingState(final RogueMonster game) {
        this.game = game;
        player = new Player(200, 180);

        pkmFactory = new PokemonFactory();
        characters = new ArrayList<Character>();

        characters.add(oak);

    }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Maps/lab2.tmx");

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions");
        TiledMapTileLayer secondCollisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions2");
        getCollisionArray(collisionObjectLayer);
        getCollisionArray(secondCollisionObjectLayer);
        renderer = new OrthogonalTiledMapRenderer(map, 2);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);

        dialogueController = new DialogueController(dialogueBox, optionBox);

        dialogue = new Dialogue();
        // dialogueBox = new DialogueBox(RogueMonster.getSkin());
        LinearDialogueNode node1 = new LinearDialogueNode("Hey nice to meet you !", 0);
        dialogue.addNode(node1);

        // dialogueController.startDialogue(dialogue);

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
            game.setScreen(new GameState(game));
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

    public void getCollisionArray(TiledMapTileLayer collisionObjectLayer) {
        for (int x = 0; x < collisionObjectLayer.getWidth(); x++) {
            for (int y = 0; y < collisionObjectLayer.getHeight(); y++) {
                Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                    System.out.print("test");
                    Rectangle rectangle = new Rectangle();
                    rectangle.x = x * 32;
                    rectangle.y = y * 32;
                    rectangleArray.add(rectangle);
                }
            }
        }
        rectangleArray.add(oak);

    }

}
