package com.slyvronline.mc.objects.characters;

import java.util.ArrayList;

import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.abilities.Ability;

public abstract class Character extends Ent {

	private int hp;
	private int energy;
	private int atk;
	private int def;
	private ArrayList<Ability> abilities;
	private int walkSpeed;
	private int jogSpeed;
	private int runSpeed;
	private float sightDistance;
	private float attackDistance;
	
	public Character(){
		walkSpeed = 5;
		jogSpeed = 8;
		runSpeed = 10;
		hp = 1000;
		energy = 1000;
		atk = 1;
		def = 1;
		abilities = new ArrayList<Ability>();
		attackDistance = 45;
		sightDistance = 300;
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

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(ArrayList<Ability> abilities) {
		this.abilities = abilities;
	}

	public float getSightDistance() {
		return sightDistance;
	}

	public void setSightDistance(float sightDistance) {
		this.sightDistance = sightDistance;
	}

	public float getAttackDistance() {
		return attackDistance;
	}

	public void setAttackDistance(float attackDistance) {
		this.attackDistance = attackDistance;
	}
	
}
