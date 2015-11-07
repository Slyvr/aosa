package com.slyvronline.mc.objects.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Img;
import com.slyvronline.mc.objects.abilities.Ability;

public abstract class Character extends Ent {

	public enum STATE {FOLLOWING,IDLE,CHARGING,DEFENDING,ATTACKING,WORKING};
	protected STATE currentState;
	protected STATE prevState;
	
	private float baseHp;
	private float hp;
	private float reviveAmt;
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
	
	private long timeOfLastAttack;
	private long reviveTimer;
	
	private Integer blockGroupIndex;
	private Integer blockIndex;
	
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
		reviveAmt = 2;
	}
	
	public void update(){
		reviveOverTime();
		checkCurrentBlocks();
	}
	
	public void checkCurrentBlocks(){
		if (blockGroupIndex != null && blockIndex != null){
			BlockGroup currentGroup = Aosa.getGlobal().getGame().getWorld().getBlockGroups().get(blockGroupIndex);
			Block currentBlock = currentGroup.getBlocks().get(blockIndex);
			Block nextBlock = null;
			BlockGroup nextGroup = null;
			Integer nextBlockIndex = null;
			Integer nextGroupIndex = null;
			boolean movingRight = false;
			boolean movingLeft = false;
			if (this.getPosBox().getX() > currentBlock.getPosBox().getX()+currentBlock.getPosBox().getWidth()){
				//If character is to the right of the current block
				try{
					nextBlock = currentGroup.getBlocks().get(blockIndex+1);
					nextBlockIndex = blockIndex + 1;
				}catch(ArrayIndexOutOfBoundsException ex){
					
				}
					movingRight = true;
			}
			else if (this.getPosBox().getX() < currentBlock.getPosBox().getX()){
				//If chracter is to the left of the current block
				try{
					nextBlock = currentGroup.getBlocks().get(blockIndex-1);
					nextBlockIndex = blockIndex - 1;
				}catch(ArrayIndexOutOfBoundsException ex){
					
				}
				movingLeft = true;
			}
			
			if (nextBlock == null){
				//Next block not in current group, select next group
				if (movingRight){
					nextGroup = Aosa.getGlobal().getGame().getWorld().getBlockGroups().get(blockGroupIndex + 1);
					nextGroupIndex = blockGroupIndex + 1;
				}
				else if (movingLeft){
					nextGroup = Aosa.getGlobal().getGame().getWorld().getBlockGroups().get(blockGroupIndex - 1);
					nextGroupIndex = blockGroupIndex - 1;
				}
			}
			else{
				blockIndex = nextBlockIndex;
			}
			
			if (nextGroup != null){
				blockGroupIndex = nextGroupIndex;
			}
		}
	}
	
	public void reviveOverTime(){
		if (hp < baseHp && System.currentTimeMillis() > timeOfLastAttack + 3000){
			if (System.currentTimeMillis() > reviveTimer + 1000){
				hp = hp + reviveAmt;
				if (hp > baseHp){
					hp = baseHp;
				}
				reviveTimer = System.currentTimeMillis();
			}
		}
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
		if (hp < this.hp){
			timeOfLastAttack = System.currentTimeMillis();
		}
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

	public float getReviveAmt() {
		return reviveAmt;
	}

	public void setReviveAmt(float reviveAmt) {
		this.reviveAmt = reviveAmt;
	}

	public long getTimeOfLastAttack() {
		return timeOfLastAttack;
	}

	public void setTimeOfLastAttack(long timeOfLastAttack) {
		this.timeOfLastAttack = timeOfLastAttack;
	}

	public long getReviveTimer() {
		return reviveTimer;
	}

	public void setReviveTimer(long reviveTimer) {
		this.reviveTimer = reviveTimer;
	}

	public Integer getBlockGroupIndex() {
		return blockGroupIndex;
	}

	public void setBlockGroupIndex(Integer blockGroupIndex) {
		this.blockGroupIndex = blockGroupIndex;
	}

	public Integer getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(Integer blockIndex) {
		this.blockIndex = blockIndex;
	}
	
}
