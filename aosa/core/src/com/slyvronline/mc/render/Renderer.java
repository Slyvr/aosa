package com.slyvronline.mc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;

public class Renderer {

	public static void render(){
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		Aosa.getGlobal().setStateTime(Aosa.getGlobal().getStateTime() + Gdx.graphics.getDeltaTime());
		
		SpriteBatch batch = Aosa.getGlobal().getBatch();
		batch.begin();
		
		Aosa.getGlobal().getCurrentMenu().render(batch);
		
		if (!Aosa.getGlobal().getCurrentMenu().getName().equals("splash"))
			Aosa.getGlobal().getMusicMenu().render(batch);
		
		batch.end();
	}
}
