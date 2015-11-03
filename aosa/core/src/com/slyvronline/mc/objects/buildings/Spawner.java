package com.slyvronline.mc.objects.buildings;

import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.mc.objects.BlockGroup;

public class Spawner extends Building{

	private Rectangle spawnPos;
	
	public Spawner(){
		super();
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

	public Rectangle getSpawnPos() {
		return spawnPos;
	}

	public void setSpawnPos(Rectangle spawnPos) {
		this.spawnPos = spawnPos;
	}

}
