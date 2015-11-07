package com.slyvronline.mc.objects.characters;

import com.slyvronline.aosa.Aosa;

public class MainCharacter extends Character {

	public MainCharacter(){
		super();
		this.setRunSpeed(15);
	}

	@Override
	public void update() {
		super.update();
		
		if (this.getHp() <= 0)
			updateDeath();
	}
	
	public void updateDeath(){
		Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
		Aosa.getGlobal().setCurrentMenu(Aosa.getGlobal().getMenuByName("main"));
		Aosa.getGlobal().setBackMenu(null);
		Aosa.getGlobal().setGame(null);
	}
}
