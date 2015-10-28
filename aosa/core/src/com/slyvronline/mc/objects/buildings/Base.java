package com.slyvronline.mc.objects.buildings;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.characters.Worker;

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
	
	public void updateActionStart(){
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)){
			//If starting action when player is on this building, execute action
			BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
			if (grp.getBuilding() != null){
				if (grp.getBuilding().equals(this)){
					//Execute action of Base
					Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
					if (worker != null){
						int mineralsCollected = Aosa.getGlobal().getGame().getMineralsCollected();
						int gasCollected = Aosa.getGlobal().getGame().getGasCollected();
						if (mineralsCollected >= this.getActionMineralCost() &&
								gasCollected >= this.getActionGasCost()){
							Aosa.getGlobal().getGame().setGasCollected(gasCollected - this.getActionGasCost());
							Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - this.getActionMineralCost());
							Aosa.getGlobal().getSoundByName("click").getSound().play();
							grp.setWorker(worker);
							Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
							this.setActionProgress(1000);
						}
						else{
							Aosa.getGlobal().getSoundByName("error").getSound().play();
						}
					}
				}
			}
		}
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
			w.setSelected(false);
			workers.add(w);
			grp.getWorker().setSelected(false);
			workers.add(grp.getWorker());
			grp.setWorker(null);
			Aosa.getGlobal().getGame().getWorld().setWorkers(workers);
		}
	}
	
	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}

	
}
