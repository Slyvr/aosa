package com.slyvronline.aosa.runnable;

public class LoadingScreenThread extends Thread{

	private Thread t;
	private String threadName;
	
	public LoadingScreenThread(String name){
		this.threadName = name;
	}
	
	public void run(){
		
	}
	
	public void start(){
		if(t == null){
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
