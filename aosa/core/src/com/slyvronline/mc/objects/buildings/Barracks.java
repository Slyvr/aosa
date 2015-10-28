package com.slyvronline.mc.objects.buildings;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.characters.Worker;

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
		updateActionStart();
	}

	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}

	@Override
	public void updateBuildingAction(BlockGroup grp) {
		this.setActionProgress(this.getActionProgress() - 1);
		if (this.getActionProgress() <= 0){
			//Action executes
			ArrayList<Worker> workers = Aosa.getGlobal().getGame().getWorld().getWorkers();
			grp.getWorker().setSelected(false);
			workers.add(grp.getWorker());
			grp.setWorker(null);
			Aosa.getGlobal().getGame().getWorld().setWorkers(workers);
		}
	}
}
