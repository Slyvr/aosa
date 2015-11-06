package com.slyvronline.mc.objects.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Img;
import com.slyvronline.mc.objects.abilities.Ability;

public abstract class Character extends Ent {

	public enum STATE {FOLLOWING,IDLE,CHARGING,DEFENDING,ATTACKING,WORKING};
	protected STATE currentState;
	protected STATE prevState;
	
	private float baseHp;
	private float hp;
	private float baseEnergy;
	private float energy;
	private float atk;
	private float def;
	private ArrayList<Ability> abilities;
	private float walkSpeed;
	private float jogSpeed;
	private float runSpeed;
	private float sightDistance;
	private float attackDistance;
	
	public Character(){
		walkSpeed = 5;
		jogSpeed = 8;
		runSpeed = 10;
		baseHp = 100;
		hp = baseHp;
		baseEnergy = 100;
		energy = baseEnergy;
		abilities = new ArrayList<Ability>();
		attackDistance = 45;
		sightDistance = 300;
	}
	
	public void update(){
		
	}
	
	public void render(SpriteBatch batch){
		super.render(batch);
		
		if (this.getHp() < this.getBaseHp()){
			float hpDifference = this.getBaseHp()-this.getHp();
			float hpPercent = hpDifference / this.getBaseHp();
			
			Img hpUnderBar = Aosa.getGlobal().getImgByName("characterBar");
			Img hpTopBar = Aosa.getGlobal().getImgByName("characterBar");
			
			float topBarWidth = hpTopBar.getTex().getWidth()-(hpTopBar.getTex().getWidth()*hpPercent);
			if (topBarWidth < 0) topBarWidth = 0;
			
			batch.setColor(Color.RED);
			batch.draw(hpUnderBar.getTex(),
					this.getPosBox().getX(),
					this.getPosBox().getY() + this.getPosBox().getHeight() + 16,
					hpUnderBar.getTex().getWidth(),
					hpUnderBar.getTex().getHeight());
			
			batch.setColor(Color.GREEN);
			batch.draw(hpTopBar.getTex(),
					this.getPosBox().getX(),
					this.getPosBox().getY() + this.getPosBox().getHeight() + 16,
					topBarWidth,
					hpTopBar.getTex().getHeight());
			
			batch.setColor(Color.WHITE);
		}
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public float getAtk() {
		return atk;
	}

	public void setAtk(float atk) {
		this.atk = atk;
	}

	public float getDef() {
		return def;
	}

	public void setDef(float def) {
		this.def = def;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(float walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public float getJogSpeed() {
		return jogSpeed;
	}

	public void setJogSpeed(float jogSpeed) {
		this.jogSpeed = jogSpeed;
	}

	public float getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(float runSpeed) {
		this.runSpeed = runSpeed;
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

	public STATE getCurrentState() {
		return currentState;
	}

	public void setCurrentState(STATE currentState) {
		this.currentState = currentState;
	}

	public float getBaseHp() {
		return baseHp;
	}

	public void setBaseHp(float baseHp) {
		this.baseHp = baseHp;
	}

	public float getBaseEnergy() {
		return baseEnergy;
	}

	public void setBaseEnergy(float baseEnergy) {
		this.baseEnergy = baseEnergy;
	}

	public STATE getPrevState() {
		return prevState;
	}

	public void setPrevState(STATE prevState) {
		this.prevState = prevState;
	}
	
}
