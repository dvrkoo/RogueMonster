package com.mygdx.game.States;

import org.w3c.dom.Text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;

public class MainMenu implements Screen {
    final RogueMonster game;
    OrthographicCamera camera;
    private Viewport viewport;
    Texture playButton;
    Texture quitButton;
    int QUIT_WIDTH = 172;
    int QUIT_HEIGHT = 60;
    int PLAY_WIDTH = 158;
    int PLAY_HEIGHT = 66;

    public MainMenu(final RogueMonster game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new ScreenViewport(camera);
        playButton = new Texture("play.png");
        quitButton = new Texture("quit.png");

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        int x = Gdx.graphics.getWidth() / 2 - PLAY_WIDTH / 2;
        game.batch.draw(playButton, x, 500, PLAY_WIDTH, PLAY_HEIGHT);

        x = Gdx.graphics.getWidth() / 2 - QUIT_WIDTH / 2;

        game.batch.draw(quitButton, x, 400, QUIT_WIDTH, QUIT_HEIGHT);

        game.batch.end();

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // condition to determine if the buttons are being pressed
        float playX = Gdx.graphics.getWidth() / 2 - PLAY_WIDTH / 2;
        float playY = Gdx.graphics.getWidth() / 2 - PLAY_HEIGHT / 2;
        float quitX = Gdx.graphics.getWidth() / 2 - QUIT_WIDTH / 2;
        float quitY = Gdx.graphics.getWidth() / 2 - QUIT_HEIGHT / 2 + QUIT_HEIGHT + 5;
        if (Gdx.input.isTouched()) {
            // try again
            if (touchX > playX && touchX < playX + PLAY_WIDTH
                    && touchY > playY - PLAY_HEIGHT && touchY < playY) {
                this.dispose();
                Gdx.app.exit();
            }

            if (touchX > quitX && touchX < quitX + QUIT_WIDTH
                    && touchY > quitY - QUIT_HEIGHT && touchY < quitY) {

                game.setScreen(new StartingState(game, new Player(200, 180)));

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
        Gdx.input.setInputProcessor(null);
    }

}
