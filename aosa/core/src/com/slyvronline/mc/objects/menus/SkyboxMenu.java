package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.GameConstants;

public class SkyboxMenu extends Menu {

	public SkyboxMenu(){
		
	}
	
	public void update(){
		Ent skybox1 = this.getEntByName("skybox1");
		Ent skybox2 = this.getEntByName("skybox2");
		skybox1.getPosBox().setX(skybox1.getPosBox().getX() - GameConstants.SKYBOX_SPEED);
		skybox2.getPosBox().setX(skybox2.getPosBox().getX() - GameConstants.SKYBOX_SPEED);
		
		//Determine start of day or night
		if (skybox1.getPosBox().getX() <= -(skybox1.getImg().getTex().getWidth())){
			Aosa.getGlobal().getGame().getWorld().setStartNight(true);
		}
		if (skybox2.getPosBox().getX() <= -(skybox2.getImg().getTex().getWidth())){
			Aosa.getGlobal().getGame().getWorld().setStartDay(true);
		}
		
		if (skybox1.getPosBox().getX() <= -skybox1.getImg().getTex().getWidth()){
			skybox1.getPosBox().setX(skybox2.getPosBox().getX()+skybox1.getImg().getTex().getWidth());
		}
		if (skybox2.getPosBox().getX() <= -skybox2.getImg().getTex().getWidth()){
			skybox2.getPosBox().setX(skybox1.getPosBox().getX()+skybox2.getImg().getTex().getWidth());
		}
	}
}
