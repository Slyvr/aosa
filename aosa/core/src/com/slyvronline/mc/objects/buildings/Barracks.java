package com.slyvronline.mc.objects.buildings;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

public class Barracks extends Building {

	public Barracks(){
		super();
		this.setBlockSize(4);
		this.setName("Barracks");
		this.setImg(Aosa.getGlobal().getImgByName("barracks"));
		this.setMineralCost(200);
		this.setGasCost(50);
		this.setBuildProgress(1000);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}

	@Override
	public void updateBuildingAction(BlockGroup grp) {
		
	}
}
