package com.slyvronline.mc.update;

import com.slyvronline.aosa.Aosa;

public class Updater {

	public static void update(){
		Aosa.getGlobal().getCurrentMenu().update();
		if (Aosa.getGlobal().getGame() != null && Aosa.getGlobal().getCurrentMenu().getCurrentSubMenu() == null)
			Aosa.getGlobal().getGame().update();
		
		Aosa.getGlobal().getMusicMenu().update();
	}
}
