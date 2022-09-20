package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Maps.Media;
import com.mygdx.game.States.GameOver;
import com.mygdx.game.States.MainMenu;
import com.mygdx.game.Utils.SkinGenerator;

public class RogueMonster extends Game {
	private static AssetManager assetManager;
	public SpriteBatch batch;
	public BitmapFont font;
	public static Skin skin;

	@Override
	public void create() {

		assetManager = new AssetManager();
		assetManager.load("assets/ui/uipack.atlas", TextureAtlas.class);
		assetManager.finishLoading();
		skin = SkinGenerator.generateSkin(assetManager);

		Media.load_assets();
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new GameOver(this, 0));

	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public static Skin getSkin() {
		return skin;
	}

}
