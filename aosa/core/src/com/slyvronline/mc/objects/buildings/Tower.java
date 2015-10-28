package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

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
	public void updateWorkerActivity(BlockGroup grp) {
		
	}
}
