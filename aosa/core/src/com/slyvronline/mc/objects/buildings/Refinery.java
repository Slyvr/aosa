package com.slyvronline.mc.objects.buildings;

import java.util.Random;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.utils.GameConstants;

public class Refinery extends Building {

	public Refinery(){
		super();
		this.setBlockSize(2);
		this.setName("Refinery");
		this.setCanBeAssigned(true);
		this.setImg(Aosa.getGlobal().getImgByName("refinery"));
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		Random rand = new Random();
		if (rand.nextInt(100) > GameConstants.RESOURCE_GATHER_PERCENT){
			Aosa.getGlobal().getGame().setGasCollected(Aosa.getGlobal().getGame().getGasCollected() + 1);
			grp.setGasAmt(grp.getGasAmt() - 1);
			if (grp.getGasAmt() <= 0){
				this.setCanBeAssigned(false);
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(grp.getWorker());
				grp.setWorker(null);
				grp.setBuilding(null);
			}
		}
	}
}
