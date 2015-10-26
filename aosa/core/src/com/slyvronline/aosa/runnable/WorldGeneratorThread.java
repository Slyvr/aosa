package com.slyvronline.aosa.runnable;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.GameInstance;

public class WorldGeneratorThread extends Thread{
	
	private Thread t;
	private String threadName;
	private boolean dispose;
	
	public WorldGeneratorThread(String name){
		this.threadName = name;
		dispose = false;
	}
	
	public void run(){
		GameInstance game = Aosa.getGlobal().getGame();
		while(game==null){
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (game!=null){
			game.getWorld().setupWorld();
		}
		
		System.out.println("Ending World Generator Thread");
		Aosa.getGlobal().setCurrentMenu(Aosa.getGlobal().getMenuByName("game"));
		Aosa.getGlobal().setBackMenu(Aosa.getGlobal().getMenuByName("skybox"));
		Ent skybox1 = Aosa.getGlobal().getBackMenu().getEntByName("skybox1");
		Ent skybox2 = Aosa.getGlobal().getBackMenu().getEntByName("skybox2");
		skybox1.getPosBox().setX(0);
		skybox2.getPosBox().setX(-skybox2.getImg().getTex().getWidth());
		dispose = true;
	}
	
	public void start(){
		if(t == null){
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	public boolean getDispose(){
		return dispose;
	}
}