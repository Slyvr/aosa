package com.slyvronline.mc.objects.characters;

import com.slyvronline.mc.objects.Ent;

public abstract class Character extends Ent {

	private int walkSpeed;
	private int jogSpeed;
	private int runSpeed;
	
	public Character(){
		walkSpeed = 3;
		jogSpeed = 5;
		runSpeed = 10;
	}
	
	public void update(){
		
	}

	public int getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(int walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public int getJogSpeed() {
		return jogSpeed;
	}

	public void setJogSpeed(int jogSpeed) {
		this.jogSpeed = jogSpeed;
	}

	public int getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(int runSpeed) {
		this.runSpeed = runSpeed;
	}
	
}
