package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.Ent;

public abstract class Building extends Ent{

	private int blockSize;
	private int level;
	private boolean canBeAssigned;
	
	public Building(){
		super();
	}
	
	public abstract void update();
	
	public abstract void updateWorkerActivity(BlockGroup grp);

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
