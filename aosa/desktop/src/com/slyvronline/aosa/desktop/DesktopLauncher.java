package com.slyvronline.aosa.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.utils.GameConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Aosa.TITLE;
		config.width = GameConstants.DEFAULT_WIDTH;
		config.height = GameConstants.DEFAULT_HEIGHT;
		new LwjglApplication(new Aosa(), config);
	}
}
