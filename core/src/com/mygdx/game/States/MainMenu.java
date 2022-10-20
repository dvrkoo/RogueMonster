package com.mygdx.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;

public class MainMenu implements Screen {
    final RogueMonster game;
    OrthographicCamera camera;
    private Viewport viewport;
    Rectangle play = new Rectangle();
    Rectangle quit= new Rectangle();
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
        setButton();

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
        game.batch.draw(playButton, play.x, play.y, play.width, play.height);
        game.batch.draw(quitButton, quit.x, quit.y, quit.width, quit.height);

        game.batch.end();

        
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Math.abs(Gdx.input.getY() - 1000);
            // try again
            if (quit.contains(x, y)) {
                this.dispose();
                Gdx.app.exit();
            }
            if (play.contains(x, y)) {
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
        playButton.dispose();
        quitButton.dispose();
    }

    void setButton(){
        int xp = Gdx.graphics.getWidth() / 2 - PLAY_WIDTH / 2;
        int xq = Gdx.graphics.getWidth() / 2 - QUIT_WIDTH / 2;
        play.setPosition(xp, 500);
        quit.setPosition(xq,400);
        play.setHeight(PLAY_HEIGHT);
        play.setWidth(PLAY_WIDTH);
        quit.setHeight(QUIT_HEIGHT);
        quit.setWidth(QUIT_WIDTH);

    }

}
