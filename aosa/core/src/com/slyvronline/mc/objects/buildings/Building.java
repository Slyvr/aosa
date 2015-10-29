package com.slyvronline.mc.objects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.characters.Worker;

public abstract class Building extends Ent{

	private int blockSize;
	private int level;
	private int mineralCost;
	private int gasCost;
	private int buildProgress;
	private int actionProgress;
	private int actionMineralCost;
	private int actionGasCost;
	
	public Building(){
		super();
	}
	
	public abstract void update();
	
	public abstract void updateWorkerActivity(BlockGroup grp);
	
	public abstract void updateBuildingAction(BlockGroup grp);
	
	public void updateActionStart(){
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)){
			//If starting action when player is on this building, execute action
			BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
			if (grp.getBuilding() != null && grp.getWorker() == null){
				if (grp.getBuilding().equals(this) && !this.getName().equals("Tower")){
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
	
	public void updateBuildProgress(BlockGroup grp){
		if (buildProgress > 0){
			buildProgress--;
		}
		else{
			if (this.getName().equals("Mine")){
				this.setImg(Aosa.getGlobal().getImgByName("mine"));
			}
			else if (this.getName().equals("Refinery")){
				this.setImg(Aosa.getGlobal().getImgByName("refinery"));
			}
			else if (this.getName().equals("Tower")){
				this.setImg(Aosa.getGlobal().getImgByName("tower"));
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(grp.getWorker());
				grp.setWorker(null);
			}
			else if (this.getName().equals("Base")){
				this.setImg(Aosa.getGlobal().getImgByName("base"));
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(grp.getWorker());
				grp.setWorker(null);
			}
			else if (this.getName().equals("Barracks")){
				this.setImg(Aosa.getGlobal().getImgByName("barracks"));
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(grp.getWorker());
				grp.setWorker(null);
			}
		}
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMineralCost() {
		return mineralCost;
	}

	public void setMineralCost(int mineralCost) {
		this.mineralCost = mineralCost;
	}

	public int getGasCost() {
		return gasCost;
	}

	public void setGasCost(int gasCost) {
		this.gasCost = gasCost;
	}

	public int getBuildProgress() {
		return buildProgress;
	}

	public void setBuildProgress(int buildProgress) {
		this.buildProgress = buildProgress;
	}

	public int getActionProgress() {
		return actionProgress;
	}

	public void setActionProgress(int actionProgress) {
		this.actionProgress = actionProgress;
	}

	public int getActionMineralCost() {
		return actionMineralCost;
	}

	public void setActionMineralCost(int actionMineralCost) {
		this.actionMineralCost = actionMineralCost;
	}

	public int getActionGasCost() {
		return actionGasCost;
	}

	public void setActionGasCost(int actionGasCost) {
		this.actionGasCost = actionGasCost;
	}
	
}
