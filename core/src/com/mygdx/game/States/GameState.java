package com.mygdx.game.States;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RogueMonster;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.CharacterState;

public class GameState implements Screen {
    // game attributes
    final RogueMonster game;
    Player player;

    OrthographicCamera camera;
    float enlapsedTime;

    //game methods
    public GameState(final RogueMonster game){
        this.game = game;
        player = new Player(500, 500);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000,1000);



    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

  
    @Override
    public void render(float delta) {
       ScreenUtils.clear(0.2f,0,0.2f,1);
       enlapsedTime += delta;
       

       camera.update();

       game.batch.setProjectionMatrix(camera.combined);

       game.batch.begin();
       //player.draw(game.batch);
       game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
       game.batch.end();


       //player movement system
       if(Gdx.input.isKeyPressed(Input.Keys.S)){//down
            player.movement(0, -1, CharacterState.SOUTH);
       }else if(Gdx.input.isKeyPressed(Input.Keys.A)){//left
            player.movement(-1,0,CharacterState.WEST);
       }else if(Gdx.input.isKeyPressed(Input.Keys.D)){//right
            player.movement(1,0,CharacterState.EAST);
       }else if(Gdx.input.isKeyPressed(Input.Keys.W)){//up
            player.movement(0,1,CharacterState.NORTH);
       }else{
            player.movement(0,0, CharacterState.STANDING);
            
       }
        
    }

    @Override
    public void resize(int width, int height) {
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        

        
    }
    
}
