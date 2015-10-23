package com.slyvronline.mc.objects.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.TextInput;
import com.slyvronline.mc.utils.Utils;

public class GameMenu extends Menu {

	public GameMenu(){
		
	}
	
	public void render(SpriteBatch batch){
		for(Ent e : this.getEnts()){
			e.render(batch);
		}
		
		if (this.getCurrentSubMenu() != null){
			this.getCurrentSubMenu().render(batch);
		}
	}
	
	public void update(){
		if (getCurrentSubMenu() != null){
			getCurrentSubMenu().update();
		}
		else{
			if (Aosa.getGlobal().getGame().isPaused()){
				if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Keys.P)){
					Aosa.getGlobal().getGame().setPaused(false);
				}
			}
			else{
				if (Gdx.input.isKeyJustPressed(Keys.P) || Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
					Aosa.getGlobal().getGame().setPaused(true);
					this.setCurrentSubMenu(this.getSubMenuByName("pause"));
				}
				
				//Game logic here
			}
		}
	}
	
}
