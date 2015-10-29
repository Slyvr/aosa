package com.slyvronline.mc.objects.buildings;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.utils.GameConstants;

public class Mine extends Building {

	public Mine(){
		super();
		this.setBlockSize(2);
		this.setName("Mine");
		this.setImg(Aosa.getGlobal().getImgByName("mine"));
		this.setMineralCost(100);
		this.setBuildProgress(1000);
	}

	@Override
	public void update() {
		updateActionStart();
	}

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
	public void updateWorkerActivity(BlockGroup grp) {
		Random rand = new Random();
		if (rand.nextInt(100) > GameConstants.RESOURCE_GATHER_PERCENT){
			Aosa.getGlobal().getGame().setMineralsCollected(Aosa.getGlobal().getGame().getMineralsCollected() + 1);
			grp.setMineralAmt(grp.getMineralAmt() - 1);
			if (grp.getMineralAmt() <= 0){
				for(Block b : grp.getBlocks()){
					if (b.getOverlandImg() != null){
						if (b.getOverlandImg().getName().equals("minerals")){
							b.setOverlandImg(null);
						}
					}
				}
				Aosa.getGlobal().getGame().getWorld().getWorkers().add(grp.getWorker());
				grp.setWorker(null);
				grp.setBuilding(null);
			}
		}
	}

	@Override
	public void updateBuildingAction(BlockGroup grp) {
		
	}
}
