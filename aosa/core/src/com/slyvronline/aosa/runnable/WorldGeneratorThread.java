package com.slyvronline.aosa.runnable;

import com.slyvronline.aosa.Aosa;
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