package com.zainchen.game2048.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zainchen.game2048.Game2048;

/**
 *
 * @author Zain Chen
 * @date 2021/4/12 13:24
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 1280;
		new LwjglApplication(new Game2048(), config);
	}
}
