package com.slyvronline.mc.objects.characters;

import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;

public class Grunt extends Character{

	private enum STATE {IDLE,CHARGING,ATTACKING};
	private STATE currentState;
	
	private Worker targetWorker;
	private Soldier targetSoldier;
	private MainCharacter targetChar;
	private BlockGroup targetBuilding;
	
	private boolean chargingRight;
	
	public Grunt(){
		super();
		this.setImg(Aosa.getGlobal().getImgByName("grunt"));
		currentState = STATE.CHARGING;
	}

	@Override
	public void update() {
		
		checkAttack();
		
		if (currentState == STATE.ATTACKING){
			updateAttack();
		}
		
		if (currentState == STATE.CHARGING){
			updateMovement();
		}
	}
	
	public void checkAttack(){
		boolean foundSomethingToAttack = false;
		
		if (!foundSomethingToAttack){
			boolean targetSet = false;
			for(BlockGroup bg : Aosa.getGlobal().getGame().getWorld().getBlockGroups()){
				if (bg.getSoldiers().size() > 0){
					for(Soldier s : bg.getSoldiers()){
						if (this.getPosBox().getX() >= s.getPosBox().getX() - this.getSightDistance() &&
								this.getPosBox().getX() <= s.getPosBox().getX() + this.getSightDistance()){
							foundSomethingToAttack = true;
							currentState = STATE.ATTACKING;
							targetSoldier = s;
							targetSet = true;
							break;
						}
					}
				}
				if (targetSet) break;
			}
		}
		
		//Check if soldiers are nearby
		if (!foundSomethingToAttack)
		for(Soldier s : Aosa.getGlobal().getGame().getWorld().getSoldiers()){
			if (this.getPosBox().getX() >= s.getPosBox().getX() - this.getSightDistance() &&
					this.getPosBox().getX() <= s.getPosBox().getX() + this.getSightDistance()){
				foundSomethingToAttack = true;
				currentState = STATE.ATTACKING;
				targetSoldier = s;
				break;
			}
		}
		
		//Check if workers are nearby
		for(Worker w : Aosa.getGlobal().getGame().getWorld().getWorkers()){
			if (this.getPosBox().getX() >= w.getPosBox().getX() - this.getSightDistance() &&
					this.getPosBox().getX() <= w.getPosBox().getX() + this.getSightDistance()){
				foundSomethingToAttack = true;
				currentState = STATE.ATTACKING;
				targetWorker = w;
				break;
			}
		}
		
		//Check if buildings are nearby
		if (!foundSomethingToAttack){
			boolean targetSet = false;
			for(BlockGroup bg : Aosa.getGlobal().getGame().getWorld().getBlockGroups()){
				if (bg.getWorker() != null){
					if (this.getPosBox().getX() >= bg.getWorker().getPosBox().getX() - this.getSightDistance() &&
							this.getPosBox().getX() <= bg.getWorker().getPosBox().getX() + this.getSightDistance()){
						foundSomethingToAttack = true;
						currentState = STATE.ATTACKING;
						targetWorker = bg.getWorker();
						targetSet = true;
					}
				}
				else if (bg.getBuilding() != null && (this.getPosBox().getX() >= bg.getBuilding().getPosBox().getX() - this.getSightDistance() &&
						this.getPosBox().getX() <= bg.getBuilding().getPosBox().getX() + this.getSightDistance())){
					foundSomethingToAttack = true;
					currentState = STATE.ATTACKING;
					targetBuilding = bg;
					targetSet = true;
				}
				else{
					for(Block b : bg.getBlocks()){
						if (b.getWorker() != null){
							if (this.getPosBox().getX() >= b.getWorker().getPosBox().getX() - this.getSightDistance() &&
									this.getPosBox().getX() <= b.getWorker().getPosBox().getX() + this.getSightDistance()){
								foundSomethingToAttack = true;
								currentState = STATE.ATTACKING;
								targetWorker = b.getWorker();
								targetSet = true;
							}
						}
					}
				}
				if (targetSet){
					break;
				}
			}
		}
		
		//Check if player is nearby
		if (!foundSomethingToAttack){
			MainCharacter charac = Aosa.getGlobal().getGame().getWorld().getMainChar();
			if (this.getPosBox().getX() >= charac.getPosBox().getX() - this.getSightDistance() &&
					this.getPosBox().getX() <= charac.getPosBox().getX() + this.getSightDistance()){
				foundSomethingToAttack = true;
				currentState = STATE.ATTACKING;
				targetChar = charac;
			}
		}
		
		if (!foundSomethingToAttack){
			currentState = STATE.CHARGING;
			targetWorker = null;
			targetSoldier = null;
			targetChar = null;
			targetBuilding = null;
		}
	}
	
	public void updateAttack(){
		Rectangle targetPos = null;
		if (targetWorker != null) targetPos = targetWorker.getPosBox();
		if (targetSoldier != null) targetPos = targetSoldier.getPosBox();
		if (targetChar != null) targetPos = targetChar.getPosBox();
		if (targetBuilding != null && targetBuilding.getBuilding() != null) targetPos = targetBuilding.getBuilding().getPosBox();
		
		int speed = this.getJogSpeed();
		
		if (targetPos != null){
			if (this.getPosBox().getX() < (targetPos.getX()-this.getAttackDistance())){
				this.getPosBox().setX(this.getPosBox().getX() + speed);
			}
			else if (this.getPosBox().getX() > (targetPos.getX()+this.getAttackDistance())){
				this.getPosBox().setX(this.getPosBox().getX() - speed);
			}
			else{
				//Within attacking distance
				if (targetWorker != null){
					targetWorker.setHp(targetWorker.getHp() - this.getAtk());
				}
				if (targetSoldier != null){
					targetSoldier.setHp(targetSoldier.getHp() - this.getAtk());
				}
				if (targetChar != null){
					targetChar.setHp(targetChar.getHp() - this.getAtk());
				}
				if (targetBuilding != null && targetBuilding.getBuilding() != null){
					targetBuilding.getBuilding().setHp(targetBuilding.getBuilding().getHp() - this.getAtk());
				}
			}
		}
	}
	
	public void updateMovement(){
		int speed = this.getWalkSpeed();
		
		if (chargingRight){
			this.getPosBox().setX(this.getPosBox().getX() + speed);
		}
		else{
			this.getPosBox().setX(this.getPosBox().getX() - speed);
		}
	}

	public STATE getCurrentState() {
		return currentState;
	}

	public void setCurrentState(STATE currentState) {
		this.currentState = currentState;
	}

	public Worker getTargetWorker() {
		return targetWorker;
	}

	public void setTargetWorker(Worker targetWorker) {
		this.targetWorker = targetWorker;
	}

	public Soldier getTargetSoldier() {
		return targetSoldier;
	}

	public void setTargetSoldier(Soldier targetSoldier) {
		this.targetSoldier = targetSoldier;
	}

	public MainCharacter getTargetChar() {
		return targetChar;
	}

	public void setTargetChar(MainCharacter targetChar) {
		this.targetChar = targetChar;
	}

	public BlockGroup getTargetBuilding() {
		return targetBuilding;
	}

	public void setTargetBuilding(BlockGroup targetBuilding) {
		this.targetBuilding = targetBuilding;
	}

	public boolean isChargingRight() {
		return chargingRight;
	}

	public void setChargingRight(boolean chargingRight) {
		this.chargingRight = chargingRight;
	}
	
	
}
