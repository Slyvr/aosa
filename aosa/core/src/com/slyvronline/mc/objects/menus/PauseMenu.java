package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;

public class PauseMenu extends Menu {

	public PauseMenu(){
		
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
					Aosa.getGlobal().getSoundByName("click").getSound().play();
					if (e.getName().equals("btnMainMenu")){
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
						Aosa.getGlobal().setCurrentMenu(Aosa.getGlobal().getMenuByName("main"));
						Aosa.getGlobal().setGame(null);
					}
					if (e.getName().equals("btnReturn")){
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
						Aosa.getGlobal().getGame().setPaused(false);
					}
					if (e.getName().equals("btnOptions")){
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(Aosa.getGlobal().getCurrentMenu().getSubMenuByName("options"));
					}
				}
			}
		}
	}
}
