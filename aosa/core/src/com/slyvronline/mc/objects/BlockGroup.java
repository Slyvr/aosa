package com.slyvronline.mc.objects;

import java.util.ArrayList;

public class BlockGroup {

	private ArrayList<Block> blocks;
	private int id;
	
	public BlockGroup(){
		
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
