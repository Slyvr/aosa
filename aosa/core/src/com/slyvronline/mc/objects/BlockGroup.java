package com.slyvronline.mc.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.buildings.Building;
import com.slyvronline.mc.objects.characters.Soldier;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.objects.characters.Character.STATE;

public class BlockGroup {

	private ArrayList<Block> blocks;
	private int id;
	private boolean buildable;
	private Building building;
	private int dismantleAmt;
	private int mineralAmt;
	private int gasAmt;
	private Img overlandImg;
	private Worker worker;
	private ArrayList<Soldier> soldiers;
	
	public BlockGroup(){
		blocks = new ArrayList<Block>();
		soldiers = new ArrayList<Soldier>();
	}
	
	public void update(){
		for(Block b : blocks){
			b.update();
		}
		
		updateWorker();
		updateSoldier();
		
		if (building != null)
			building.update();
	}
	
	public void render(SpriteBatch batch){
		if (building!=null)
			building.render(batch);
		
		for(Block b : blocks){
			b.render(batch);
		}
		
		if (worker != null)
			worker.render(batch);
		
		for (Soldier s : soldiers){
			s.render(batch);
		}
		
		if (this.worker != null){
			//Draw assigned worker image
			batch.draw(Aosa.getGlobal().getImgByName("assigned").getTex(),
					this.blocks.get(4).getPosBox().getX(),
					this.blocks.get(4).getPosBox().getY()+128,
					Aosa.getGlobal().getImgByName("assigned").getTex().getWidth(),
					Aosa.getGlobal().getImgByName("assigned").getTex().getHeight());
		}
		if (this.soldiers.size() > 0){
			float x = this.blocks.get(4).getPosBox().getX();
			float y = this.blocks.get(4).getPosBox().getY()+256;
			int soldierCounter = 0;
			for(int i=0; i<soldiers.size(); i++){
				batch.draw(Aosa.getGlobal().getImgByName("assigned").getTex(),
						x,
						y,
						Aosa.getGlobal().getImgByName("assigned").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("assigned").getTex().getHeight());
				soldierCounter++;
				y += Aosa.getGlobal().getImgByName("assigned").getTex().getHeight();
				if (soldierCounter >= 20){
					x += Aosa.getGlobal().getImgByName("assigned").getTex().getWidth();
					y = this.blocks.get(4).getPosBox().getY()+256;
				}
			}
		}
	}
	
	public void updateWorker(){
		float centerX = this.blocks.get(4).getPosBox().getX();
		if (worker != null && worker.getCurrentState() != STATE.ATTACKING){
			if (worker.getPosBox().getWidth() > 1){
				if (worker.getPosBox().getX() < centerX){
					worker.getPosBox().setX(worker.getPosBox().getX() + worker.getJogSpeed());
				}
				if (worker.getPosBox().getX() > centerX){
					worker.getPosBox().setX(worker.getPosBox().getX() - worker.getJogSpeed());
				}
				if ((worker.getPosBox().getX() <= centerX+10) &&
						(worker.getPosBox().getX() >= centerX-10)){
					if (dismantleAmt > 0){
						dismantleAmt--;
						if (dismantleAmt<=0){
							Aosa.getGlobal().getGame().getWorld().getWorkers().add(worker);
							worker = null;
							this.overlandImg = null;
						}
					}
					else if (building != null){
						if (building.getImg().getName().equals("construct")){
							building.updateBuildProgress(this);
						}
						else if (building.getActionProgress() > 0){
							building.updateBuildingAction(this);
						}
						else{
							building.updateWorkerActivity(this);
						}
					}
				}
			}
		}
		
		if (worker != null)
			worker.update();
	}
	
	public void updateSoldier(){
		float centerX = this.blocks.get(4).getPosBox().getX();
		if (soldiers.size() > 0){
			for(Soldier soldier : soldiers){
				if (soldier.getCurrentState() != STATE.ATTACKING){
					if (soldier.getPosBox().getX() < centerX){
						soldier.getPosBox().setX(soldier.getPosBox().getX() + soldier.getWalkSpeed());
					}
					if (soldier.getPosBox().getX() > centerX){
						soldier.getPosBox().setX(soldier.getPosBox().getX() - soldier.getWalkSpeed());
					}
					if ((soldier.getPosBox().getX() <= centerX+10) &&
							(soldier.getPosBox().getX() >= centerX-10)){
						float x = soldiers.get(0).getPosBox().getX();
						for(Soldier s : soldiers){
							if ((s.getPosBox().getX() <= centerX+10) &&
									(s.getPosBox().getX() >= centerX-10)){
								s.getPosBox().setX(x);
								x+=5;
							}
						}
						if (soldiers.get(0).getPosBox().getX() <= centerX+10 && soldiers.get(0).getPosBox().getX() >= centerX-10)
							soldiers.get(0).getPosBox().setY(80+32+85);
					}
				}
				
				soldier.update();
			}
		}
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean isBuildable() {
		return buildable;
	}

	public void setBuildable(boolean buildable) {
		this.buildable = buildable;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public int getDismantleAmt() {
		return dismantleAmt;
	}

	public void setDismantleAmt(int dismantleAmt) {
		this.dismantleAmt = dismantleAmt;
	}

	public int getMineralAmt() {
		return mineralAmt;
	}

	public void setMineralAmt(int mineralAmt) {
		this.mineralAmt = mineralAmt;
	}

	public int getGasAmt() {
		return gasAmt;
	}

	public void setGasAmt(int gasAmt) {
		this.gasAmt = gasAmt;
	}

	public Img getOverlandImg() {
		return overlandImg;
	}

	public void setOverlandImg(Img overlandImg) {
		this.overlandImg = overlandImg;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public ArrayList<Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(ArrayList<Soldier> soldiers) {
		this.soldiers = soldiers;
	}
	
	
}
