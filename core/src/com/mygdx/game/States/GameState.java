package com.mygdx.game.States;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Character;

public class GameState implements Screen {
    // game attributes
    final RogueMonster game;
    Character player;

    OrthographicCamera camera;

    //game methods
    public GameState(final RogueMonster game){
        this.game = game;
        player = new Character(500, 500);

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

       camera.update();

       game.batch.setProjectionMatrix(camera.combined);

       game.batch.begin();
       game.batch.draw(player.getTexture(), player.getBoundingRectangle().x,player.getBoundingRectangle().y,player.getBoundingRectangle().width,player.getBoundingRectangle().height);
       game.batch.end();

        
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
