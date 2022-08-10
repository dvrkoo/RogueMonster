package com.mygdx.game.States;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.Maps.Tile;
import com.mygdx.game.Utils.Enums.CharacterState;


public class GameState implements Screen {
    // game attributes
    final RogueMonster game;
    Player player;
    Island island;

    Viewport viewport;
    OrthographicCamera camera;
    float enlapsedTime;
    PokemonFactory pkmFactory;
    Array<Character> pokemon;

    void spawnEnemy() {
        pokemon.add(pkmFactory.getPokemon("Mudkip", 0, 0));
        pokemon.add(pkmFactory.getPokemon("Charmander", 0, 64));
        pokemon.add(pkmFactory.getPokemon("Mew", 0, 128));
        pokemon.add(pkmFactory.getPokemon("Bulbasaur", 0, 192));
        pokemon.add(pkmFactory.getPokemon("Pikachu", 0, 256));

    }

    // game methods
    public GameState(final RogueMonster game) {
        this.game = game;
        player = new Player(500, 500);

        island = new Island();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000, 1000, camera);

        pkmFactory = new PokemonFactory();
        pokemon = new Array<Character>();
        spawnEnemy();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0.2f, 1);
        enlapsedTime += delta;

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // map draw
        for (ArrayList<Tile> row : island.chunk.tiles) {
            for (Tile tile : row) {
                game.batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                if (tile.secondary_texture != null)
                    game.batch.draw(tile.secondary_texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
            }
        }
        // player.draw(game.batch);
        game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
        for (Character iter : pokemon) {
            game.batch.draw(iter.getAnimation().getKeyFrame(enlapsedTime, true), iter.getX(), iter.getY());
        }
        game.batch.end();

        // player movement system
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {// down
            player.movement(0, -1, CharacterState.SOUTH);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {// left
            player.movement(-1, 0, CharacterState.WEST);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {// right
            player.movement(1, 0, CharacterState.EAST);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {// up
            player.movement(0, 1, CharacterState.NORTH);
        } else {
            player.movement(0, 0, CharacterState.STANDING);
        }

        // update pokemon movement
        for (Character iter : pokemon) {
            iter.update();

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

    }

}
