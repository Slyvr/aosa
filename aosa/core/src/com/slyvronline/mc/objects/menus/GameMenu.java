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
import com.slyvronline.mc.objects.characters.Grunt;
import com.slyvronline.mc.objects.characters.MainCharacter;
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
				
				updateArrows();
			}
		}
	}
	
	public void updateArrows(){
		if (Aosa.getGlobal().getGame().getWorld().getGrunts().size() > 0){
			MainCharacter charac = Aosa.getGlobal().getGame().getWorld().getMainChar();
			boolean leftDisplay = false;
			boolean rightDisplay = false;
			for(Grunt g : Aosa.getGlobal().getGame().getWorld().getGrunts()){
				if (g.getPosBox().getX() >= charac.getPosBox().getX() + charac.getSightDistance() ||
						g.getPosBox().getX() <= charac.getPosBox().getX() - charac.getSightDistance()){
					if (g.getPosBox().getX() < charac.getPosBox().getX()){
						leftDisplay = true;
					}
					if (g.getPosBox().getX() > charac.getPosBox().getX()){
						rightDisplay = true;
					}
				}
			}
			if (leftDisplay){
				this.getEntByName("leftArrow").setDisplay(true);
			}
			else{
				this.getEntByName("leftArrow").setDisplay(false);
			}
			if (rightDisplay){
				this.getEntByName("rightArrow").setDisplay(true);
			}
			else{
				this.getEntByName("rightArrow").setDisplay(false);
			}
		}
		else{
			this.getEntByName("leftArrow").setDisplay(false);
			this.getEntByName("rightArrow").setDisplay(false);
		}
	}
	
}
