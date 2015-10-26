package com.slyvronline.mc.update;

import java.util.ArrayList;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.aosa.runnable.WorldGeneratorThread;

public class Updater {

	public static void update(){
		WorldGeneratorThread thread = Aosa.getGlobal().getThread();
		
		if (thread != null){
			Aosa.getGlobal().getCurrentMenu().update();
			if (thread.getDispose()){
				Aosa.getGlobal().setThread(null);
			}
		}
		else{
			Aosa.getGlobal().getCurrentMenu().update();
			if (Aosa.getGlobal().getGame() != null && Aosa.getGlobal().getCurrentMenu().getCurrentSubMenu() == null)
				Aosa.getGlobal().getGame().update();
			
			Aosa.getGlobal().getMusicMenu().update();
			
			Aosa.getGlobal().getCamera().update();
			Aosa.getGlobal().getHudCamera().update();
		}
	}
}
