package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

public class Barracks extends Building {

	public Barracks(){
		super();
		this.setBlockSize(4);
		this.setName("Barracks");
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}
}
