package com.slyvronline.mc.objects.characters;

import com.slyvronline.aosa.Aosa;

public class Worker extends Character {
	
	private Grunt targetGrunt;
	
	public Worker(){
		super();
		this.setAtk(0.3f);
	}

	public void update(){
		super.update();
		
		if (currentState == STATE.ATTACKING){
			attackEnemy();
		}
		else{
			checkForEnemies();
		}
		
		if (currentState == STATE.DEFENDING){
			
		}
	}
	
	public void checkForEnemies(){
		boolean foundSomethingToAttack = false;
		for(Grunt g : Aosa.getGlobal().getGame().getWorld().getGrunts()){
			if (this.getPosBox().getX() >= g.getPosBox().getX() - this.getSightDistance() &&
					this.getPosBox().getX() <= g.getPosBox().getX() + this.getSightDistance()){
				foundSomethingToAttack = true;
				this.setPrevState(currentState);
				this.setCurrentState(STATE.ATTACKING);
				targetGrunt = g;
				break;
			}
		}
	}
	
	public void attackEnemy(){
		if (targetGrunt != null){
			float speed = this.getJogSpeed();
			if (this.getPosBox().getX() < (targetGrunt.getPosBox().getX()-this.getAttackDistance())){
				this.getPosBox().setX(this.getPosBox().getX() + speed);
			}
			else if (this.getPosBox().getX() > (targetGrunt.getPosBox().getX()+this.getAttackDistance())){
				this.getPosBox().setX(this.getPosBox().getX() - speed);
			}
			else{
				//Within attacking distance
				if (targetGrunt != null){
					targetGrunt.setHp(targetGrunt.getHp() - this.getAtk());
				}
				if (targetGrunt.getHp() <= 0){
					targetGrunt = null;
					STATE state = currentState;
					currentState = prevState;
					prevState = state;
				}
			}
		}
		else{
			STATE state = currentState;
			currentState = prevState;
			prevState = state;
		}
	}
	
}
