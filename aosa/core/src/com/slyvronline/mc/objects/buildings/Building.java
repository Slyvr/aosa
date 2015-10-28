package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.characters.Worker;

public abstract class Building extends Ent{

	private int blockSize;
	private int level;
	private boolean canBeAssigned;
	
	public Building(){
		
	}
	
	public abstract void update();
	
	public abstract void updateWorkerActivity(Block b);

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

	public boolean isCanBeAssigned() {
		return canBeAssigned;
	}

	public void setCanBeAssigned(boolean canBeAssigned) {
		this.canBeAssigned = canBeAssigned;
	}
	
}
