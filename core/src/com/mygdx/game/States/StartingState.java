package com.mygdx.game.States;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.Enums.Pokemon;

public class StartingState implements Screen {

    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 0;

    private int gamestatus = GAME_RUNNING;
    final RogueMonster game;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Viewport viewport;
    public static Player player;
    public static ArrayList<Character> pokemon;
    float enlapsedTime;
    PokemonFactory pkmFactory;
    Collision collisions = new Collision();

    public StartingState(final RogueMonster game) {
        this.game = game;
        player = new Player(200, 200);

        pkmFactory = new PokemonFactory();
        pokemon = new ArrayList<Character>();

        player.addPokemon(pkmFactory.getPokemon(Pokemon.MUDKIP));
        player.addPokemon(pkmFactory.getPokemon(Pokemon.CHARMANDER));

    }

    @Override
    public void show() {
        gamestatus = GAME_RUNNING;
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("Maps/lab2.tmx");

        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get("Collisions");
        System.out.print(map.getLayers().size());
        System.out.print(collisionObjectLayer.getObjects().getCount());
        MapObjects objects = collisionObjectLayer.getObjects();
        System.out.print(objects.getCount());

        renderer = new OrthogonalTiledMapRenderer(map, 2);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);
        // TODO Auto-generated method stub

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

        shapeRenderer.end();

        moveCamera();

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

    public void getMapCollisions() {
        MapLayer CollisionLayer = map.getLayers().get("Collisions");

    }

}
