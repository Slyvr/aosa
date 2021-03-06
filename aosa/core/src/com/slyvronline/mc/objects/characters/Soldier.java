package com.slyvronline.mc.objects.characters;

import com.slyvronline.aosa.Aosa;

public class Soldier extends Character {
	
	private Grunt targetGrunt;
	
	public Soldier(){
		super();
		this.setBaseHp(150);
		this.setHp(this.getBaseHp());
		this.setAtk(0.8f);
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
				currentState = STATE.ATTACKING;
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
				this.getPosBox().setY(80+32);
			}
			else if (this.getPosBox().getX() > (targetGrunt.getPosBox().getX()+this.getAttackDistance())){
				this.getPosBox().setX(this.getPosBox().getX() - speed);
				this.getPosBox().setY(80+32);
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
