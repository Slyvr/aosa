package com.slyvronline.mc.objects.buildings;

import com.slyvronline.mc.objects.Ent;

public abstract class Building extends Ent{

	private int blockSize;
	private int level;
	
	public Building(){
		
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
	
	
}
