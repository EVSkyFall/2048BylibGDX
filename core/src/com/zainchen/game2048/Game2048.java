package com.zainchen.game2048;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.zainchen.game2048.screen.GameScreen;

/**
 * Game2048
 *
 * @author Zain Chen
 * @date 2021/4/6 11:06
 */
public class Game2048 extends Game {

	/**
	 * 世界宽度
	 */
	private float worldWidth;
	/**
	 * 世界高度
	 */
	private float worldHeight;

	/**
	 * 资源管理器
	 */
	AssetManager assetManager;

	/**
	 * 纹理图集
	 */
	private TextureAtlas atlas;

	/**
	 * 位图字体
	 */
	private BitmapFont bitmapFont;

	/**
	 * 主游戏场景
	 */
	private com.zainchen.game2048.screen.GameScreen gameScreen;

	@Override
	public void create() {

		//世界宽度720
		worldWidth = 720;
		//根据屏幕比例计算世界高度
		worldHeight = Gdx.graphics.getHeight() * worldWidth / Gdx.graphics.getWidth();

		// 创建资源管理器
		assetManager = new AssetManager();

		// 加载资源
		assetManager.load("atlas/game.atlas", TextureAtlas.class);
		assetManager.load("font/bitmap_font.fnt", BitmapFont.class);
		assetManager.load("audio/move.mp3", Sound.class);
		assetManager.load("audio/merge.wav", Sound.class);

		// 等待资源加载完毕
		assetManager.finishLoading();

		// 获取资源
		atlas = assetManager.get("atlas/game.atlas", TextureAtlas.class);
		bitmapFont = assetManager.get("font/bitmap_font.fnt", BitmapFont.class);

		// 创建主游戏场景
		gameScreen = new com.zainchen.game2048.screen.GameScreen(this);

		// 设置当前场景
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		// 应用退出时, 需要手动销毁场景
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		// 应用退出时释放资源
		if (assetManager != null) {
			assetManager.dispose();
		}
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public BitmapFont getBitmapFont() {
		return bitmapFont;
	}
}
