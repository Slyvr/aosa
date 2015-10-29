package com.slyvronline.mc.objects.abilities;

public abstract class Ability {

	private String name;
	private int atkPower;
	private int defPower;
	private int atkModifier;
	private int defModifier;
	
	public Ability(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAtkPower() {
		return atkPower;
	}

	public void setAtkPower(int atkPower) {
		this.atkPower = atkPower;
	}

	public int getDefPower() {
		return defPower;
	}

	public void setDefPower(int defPower) {
		this.defPower = defPower;
	}

	public int getAtkModifier() {
		return atkModifier;
	}

	public void setAtkModifier(int atkModifier) {
		this.atkModifier = atkModifier;
	}

	public int getDefModifier() {
		return defModifier;
	}

	public void setDefModifier(int defModifier) {
		this.defModifier = defModifier;
	}
	
	
}
