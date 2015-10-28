package com.slyvronline.mc.objects.buildings;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

public class Base extends Building{

	public Base(){
		super();
		this.setBlockSize(8);
		this.setName("Base");
		this.setImg(Aosa.getGlobal().getImgByName("base"));
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateWorkerActivity(BlockGroup grp) {
		
	}
}
