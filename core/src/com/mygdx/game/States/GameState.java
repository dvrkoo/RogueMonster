package com.mygdx.game.States;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RogueMonster;

public class GameState implements Screen {
    // game attributes
    final RogueMonster game;

    OrthographicCamera camera;

    //game methods
    public GameState(final RogueMonster game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 100);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CHARTREUSE);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
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
        // TODO Auto-generated method stub
        
    }
    
}
