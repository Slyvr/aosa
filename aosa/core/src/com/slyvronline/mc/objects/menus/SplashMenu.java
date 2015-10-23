package com.slyvronline.mc.objects.menus;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.GameConstants;

public class SplashMenu extends Menu {

	private long startMillis;
	
	public SplashMenu(){
		
	}
	
	public void update(){
		if (startMillis==0) startMillis = System.currentTimeMillis();
		else{
			if (startMillis+GameConstants.SPLASH_MENU_WAIT<=System.currentTimeMillis()){
				Aosa.getGlobal().setCurrentMenu(Aosa.getGlobal().getMenuByName("main"));
			}
		}
	}
}
