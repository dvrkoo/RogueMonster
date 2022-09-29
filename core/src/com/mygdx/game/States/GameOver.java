package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RogueMonster;

public class GameOver implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    RogueMonster game;
    int score, highscore;

    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOver(RogueMonster game, int score) {
        this.game = game;
        this.score = score;

        // Get highscore from save file
        Preferences prefs = Gdx.app.getPreferences("RogueMonsters");
        this.highscore = prefs.getInteger("highscore", 0);

        // check if highscore beats score
        if (score > highscore) {
            // save it to file
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        // Load textures and font
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("assets/font/score.fnt"));

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "Highscore: \n" + highscore, Color.WHITE, 0,
                Align.left,
                false);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highScoreLayout, Gdx.graphics.getWidth() / 2 - highScoreLayout.width / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2;
        float tryAgainY = Gdx.graphics.getWidth() / 2 - tryAgainLayout.height / 2;
        float mainMenuX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2;
        float mainMenuY = Gdx.graphics.getWidth() / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // condition to determine if the buttons are being pressed
        if (Gdx.input.isTouched()) {
            // try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width
                    && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenu(game));
                return;
            }

            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width
                    && touchY > mainMenuY - mainMenuLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenu(game));
                return;
            }
        }

        // Draw buttons
        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

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
