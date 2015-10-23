package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;

public class GameWinMenu extends Menu {

	public GameWinMenu(){
		
	}
	
	public void update(){
		super.updateButtonHover();
		updateButtonClick();
	}
	
	public void updateButtonClick(){
		if (Gdx.input.justTouched()){
			Rectangle mousePos = new Rectangle(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),1,1);
			
			for(Ent e : getEnts()){
				if (e.getName().contains("btn") && mousePos.overlaps(e.getPosBox())){
					if (e.getName().equals("btnMainMenu")){
						Aosa.getGlobal().getSoundByName("click").getSound().play();
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
						Aosa.getGlobal().setCurrentMenu(Aosa.getGlobal().getMenuByName("main"));
						Aosa.getGlobal().setGame(null);
					}
				}
			}
		}
	}
}
