package com.slyvronline.mc.render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.aosa.runnable.WorldGeneratorThread;

public class Renderer {

	public static void render(){
		SpriteBatch batch = Aosa.getGlobal().getBatch();
		
		
		
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		Aosa.getGlobal().setStateTime(Aosa.getGlobal().getStateTime() + Gdx.graphics.getDeltaTime());
		
		batch.begin();
		
		WorldGeneratorThread thread = Aosa.getGlobal().getThread();
		
		if (thread == null){
			if (Aosa.getGlobal().getBackMenu()!=null){
				batch.setProjectionMatrix(Aosa.getGlobal().getBackCam().combined);
				Aosa.getGlobal().getBackMenu().render(batch);
			}
			
			batch.setProjectionMatrix(Aosa.getGlobal().getCamera().combined);
			
			if (Aosa.getGlobal().getGame() != null){
				Aosa.getGlobal().getGame().render(batch);
			}
		}
		
		if (Aosa.getGlobal().getOverlapMenu()!=null){
			batch.setProjectionMatrix(Aosa.getGlobal().getBackCam().combined);
			Aosa.getGlobal().getOverlapMenu().render(batch);
		}
		
		batch.setProjectionMatrix(Aosa.getGlobal().getHudCam().combined);
		
		Aosa.getGlobal().getCurrentMenu().render(batch);
		
		if (!Aosa.getGlobal().getCurrentMenu().getName().equals("splash"))
			Aosa.getGlobal().getMusicMenu().render(batch);
		
		
		batch.end();
	}
}
