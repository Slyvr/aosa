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
			
			if (Aosa.getGlobal().getGame() != null && Aosa.getGlobal().getCurrentMenu().getName().equals("game")){
				if (Aosa.getGlobal().getBackMenu()!=null)
					Aosa.getGlobal().getBackMenu().update();
				
				if (Aosa.getGlobal().getOverlapMenu()!=null)
					Aosa.getGlobal().getOverlapMenu().update();
			}
			
			Aosa.getGlobal().getCamera().update();
			Aosa.getGlobal().getHudCam().update();
			Aosa.getGlobal().getBackCam().update();
		}
	}
}
