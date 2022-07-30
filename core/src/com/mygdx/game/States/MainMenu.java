package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RogueMonster;

public class MainMenu implements Screen{
    final RogueMonster game;
    OrthographicCamera camera;

    public MainMenu(final RogueMonster game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,1000,1000);
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch,"MAIN MENU", 500, 500);
        game.batch.end();
        
        //change to Game state logic
        if(Gdx.input.isTouched()){
            game.setScreen(new GameState(game));
        }
        
    }

    @Override
    public void resize(int width, int height) {
        //TODO add viewport to handle the resizing problems

        
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
