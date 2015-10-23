package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;

public class OptionsMenu extends Menu {

	public OptionsMenu(){
		
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
					if (e.getName().equals("btnReturn")){
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
						if (Aosa.getGlobal().getGame() != null)
							Aosa.getGlobal().getGame().setPaused(false);
					}
					if (e.getName().equals("btnCredits")){
						Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(Aosa.getGlobal().getCurrentMenu().getSubMenuByName("credits"));
					}
					if (e.getName().equals("btnFullscreen")){
						if(Gdx.graphics.isFullscreen()){
							Gdx.graphics.setDisplayMode(Aosa.getGlobal().getDefaultScreenWidth(), Aosa.getGlobal().getDefaultScreenHeight(), false);
						}
						else{
							Gdx.graphics.setDisplayMode(Aosa.getGlobal().getDefaultScreenWidth(), Aosa.getGlobal().getDefaultScreenHeight(), true);
						}
					}
					if (e.getName().equals("btnVolumeDown")){
						if (Aosa.getGlobal().getOptions().getAudioVolume() >= 0.08f){
							Aosa.getGlobal().getOptions().setAudioVolume(Aosa.getGlobal().getOptions().getAudioVolume() - 0.08f);
						}
					}
					if (e.getName().equals("btnVolumeUp")){
						if (Aosa.getGlobal().getOptions().getAudioVolume() <= 0.92f){
							Aosa.getGlobal().getOptions().setAudioVolume(Aosa.getGlobal().getOptions().getAudioVolume() + 0.08f);
						}
					}
				}
			}
		}
	}
}
