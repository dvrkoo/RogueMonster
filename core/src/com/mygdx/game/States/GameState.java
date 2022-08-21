package com.mygdx.game.States;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Factory.PokemonFactory;
import com.mygdx.game.Maps.Entity;
import com.mygdx.game.Maps.Island;
import com.mygdx.game.Maps.Tile;
import com.mygdx.game.Utils.Collision;
import com.mygdx.game.Utils.RandomUtils;
import com.mygdx.game.Utils.Enums.Pokemon;



public class GameState implements Screen {
    // game attributes
    final RogueMonster game;

    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 0;

    private int gamestatus = GAME_RUNNING;

    //attributes
    Player player;
    

    Island island;

    Viewport viewport;
    OrthographicCamera camera;
    float enlapsedTime;
    PokemonFactory pkmFactory;
    RandomUtils random = new RandomUtils();
    Array<Character> pokemon;

    void spawnEnemy() {

        
        Vector2 position = new Vector2();
        
        for (int i = 0; i < 10; i++) {
            pokemon.add(pkmFactory.getPokemon(Pokemon.randomPokemon()));
        }
        for (Character iter : pokemon) {
            Collision collision = new Collision();
            position = random.getRandomPos(island.minMaxX, island.minMaxY);
            while(collision.getCollision(position)){
                position = random.getRandomPos(island.minMaxX, island.minMaxY); 
            }
            iter.setPosition(position);
            
        }
    }
    
    public void drawEntities() {
        for(Entity e: island.entities){
            e.draw(game.batch);
        }
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

        player.addPokemon(pkmFactory.getPokemon(Pokemon.MUDKIP));
        player.addPokemon(pkmFactory.getPokemon(Pokemon.CHARMANDER));
        spawnEnemy();
    }

    @Override
    public void show() {
        
        gamestatus = GAME_RUNNING;
    }
   
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0.2f, 1);
        enlapsedTime += delta;

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        drawMap();

        

        drawCharacters();
      
        drawEntities();
        game.batch.end();

        player.commandMovement();
        moveCamera();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			pauseGame();
		}

		if (gamestatus == GAME_PAUSED) {
			// draw pause screenù
			pause();
			
		}

    }
    public void pauseGame() {
        gamestatus = GAME_PAUSED;
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
            iter.update();
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        game.setScreen(new BattleState(game, this));

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

    //getter setter
    public Player getPlayer() {
        return player;
    }

}
