package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.GameConstants;

public class LoadMenu extends Menu {

	private long startMillis;
	
	public LoadMenu(){
		
	}
	
	boolean rollingLeft;
	
	public void update(){
		Ent spinwheel = this.getEntByName("spinwheel");
		if (rollingLeft){
			spinwheel.getPosBox().setX(spinwheel.getPosBox().getX() - 1);
		}
		else if (!rollingLeft){
			spinwheel.getPosBox().setX(spinwheel.getPosBox().getX() + 1);
		}
		
		if (spinwheel.getPosBox().getX() < (Gdx.graphics.getWidth()/2)-(spinwheel.getImg().getTex().getWidth()/2) - 30){
			rollingLeft = false;
		}
		if (spinwheel.getPosBox().getX() > (Gdx.graphics.getWidth()/2)-(spinwheel.getImg().getTex().getWidth()/2) + 30){
			rollingLeft = true;
		}
	}
}
