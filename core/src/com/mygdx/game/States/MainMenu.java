package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;

public class MainMenu implements Screen {
    final RogueMonster game;
    OrthographicCamera camera;
    private Viewport viewport;

    public MainMenu(final RogueMonster game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new ScreenViewport(camera);
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
        game.font.draw(game.batch, "MAIN MENU", 500, 500);
        game.batch.end();

        // change to Game state logic
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameState(game));
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
