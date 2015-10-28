package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;

public class Tower extends Building{

	public Tower(){
		super();
		this.setBlockSize(2);
		this.setName("Tower");
		this.setCanBeAssigned(true);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(Block b) {
		
	}
}
