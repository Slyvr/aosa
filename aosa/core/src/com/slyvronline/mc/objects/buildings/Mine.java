package com.slyvronline.mc.objects.buildings;

import java.util.Random;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;

public class Mine extends Building {

	public Mine(){
		super();
		this.setBlockSize(2);
		this.setName("Mine");
		this.setCanBeAssigned(true);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(Block b) {
		Random rand = new Random();
		if (rand.nextInt(100) > 80){
			Aosa.getGlobal().getGame().setMineralsCollected(Aosa.getGlobal().getGame().getMineralsCollected() + 1);
			b.setMineralAmt(b.getMineralAmt() - 1);
			if (b.getMineralAmt() <= 0){
				this.setCanBeAssigned(false);
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(b.getWorker());
				b.setWorker(null);
				b.setOverlandImg(null);
			}
		}
	}
}
