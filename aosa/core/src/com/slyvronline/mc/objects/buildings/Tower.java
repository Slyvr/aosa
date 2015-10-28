package com.slyvronline.mc.objects.buildings;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

public class Tower extends Building{

	public Tower(){
		super();
		this.setBlockSize(2);
		this.setName("Tower");
		this.setImg(Aosa.getGlobal().getImgByName("tower"));
		this.setCanBeAssigned(true);
		this.setMineralCost(100);
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
