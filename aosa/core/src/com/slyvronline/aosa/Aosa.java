package com.slyvronline.aosa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.mc.load.LoadFonts;
import com.slyvronline.mc.load.LoadImgs;
import com.slyvronline.mc.load.LoadMenus;
import com.slyvronline.mc.load.LoadMusic;
import com.slyvronline.mc.load.LoadSounds;
import com.slyvronline.mc.objects.Global;
import com.slyvronline.mc.render.Renderer;
import com.slyvronline.mc.update.Updater;

public class Aosa extends ApplicationAdapter {
	public static final String TITLE = "Aosa";
	
	public static final float STEP = 1/60f;
	private float accum;
	
	private static Global global;
	
	@Override
	public void create () {
		global = new Global();
		global.setDemoMode(true);
		LoadImgs.load();
		LoadFonts.load();
		LoadSounds.load();
		LoadMusic.load();
		LoadMenus.load();
	}

	@Override
	public void render () {
		
		//Run game at 60 fps
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			Updater.update();
			Renderer.render();
		}
		
	}
	
	@Override
	public void dispose(){
		
	}
	
	public static Global getGlobal(){
		return global;
	}
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
}
