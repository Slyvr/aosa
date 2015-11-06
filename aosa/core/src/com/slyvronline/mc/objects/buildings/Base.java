package com.slyvronline.mc.objects.buildings;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.objects.characters.Character.STATE;

public class Base extends Building{

	public Base(){
		super();
		this.setBlockSize(8);
		this.setName("Base");
		this.setImg(Aosa.getGlobal().getImgByName("base"));
		this.setMineralCost(500);
		this.setGasCost(200);
		this.setBuildProgress(1000);
		this.setActionMineralCost(100);
	}

	@Override
	public void update() {
		updateActionStart();
	}

	@Override
	public void updateBuildingAction(BlockGroup grp) {
		this.setActionProgress(this.getActionProgress() - 1);
		
		if (this.getActionProgress() <= 0){
			//Base Action executes
			ArrayList<Worker> workers = Aosa.getGlobal().getGame().getWorld().getWorkers();
			Worker w = new Worker();
			w.setName("Worker");
			w.setImg(Aosa.getGlobal().getImgByName("worker"));
			w.setPosBox(new Rectangle(this.getPosBox().getX(),
					80+32,
					w.getImg().getTex().getWidth(),
					w.getImg().getTex().getHeight()));
			w.setCurrentState(STATE.IDLE);
			workers.add(w);
			grp.getWorker().setCurrentState(STATE.IDLE);
			workers.add(grp.getWorker());
			grp.setWorker(null);
			Aosa.getGlobal().getGame().getWorld().setWorkers(workers);
		}
	}
	
	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}

	
}
