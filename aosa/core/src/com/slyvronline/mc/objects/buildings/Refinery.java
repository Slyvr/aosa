package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;

public class Refinery extends Building {

	public Refinery(){
		super();
		this.setBlockSize(2);
		this.setName("Refinery");
		this.setCanBeAssigned(true);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(Block b) {
		
	}
}
