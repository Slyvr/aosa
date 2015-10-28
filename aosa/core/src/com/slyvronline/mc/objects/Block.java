package com.slyvronline.mc.objects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.buildings.Building;
import com.slyvronline.mc.objects.characters.Worker;

public class Block extends Ent {

	private String blockName;
	private boolean buildable;
	private Building building;
	private int dismantleAmt;
	private int mineralAmt;
	private int gasAmt;
	private Img overlandImg;
	private Worker worker;
	
	public void update(){
		updateWorker();
	}
	
	public void updateWorker(){
		if (worker != null){
			if (worker.getPosBox().getWidth() > 1){
				if (worker.getPosBox().getX() < this.getPosBox().getX()){
					worker.getPosBox().setX(worker.getPosBox().getX() + worker.getWalkSpeed());
				}
				if (worker.getPosBox().getX() > this.getPosBox().getX()){
					worker.getPosBox().setX(worker.getPosBox().getX() - worker.getWalkSpeed());
				}
				if ((worker.getPosBox().getX() <= this.getPosBox().getX()+10) &&
						(worker.getPosBox().getX() >= this.getPosBox().getX()-10)){
					if (dismantleAmt > 0){
						dismantleAmt--;
						if (dismantleAmt<=0){
							Aosa.getGlobal().getGame().getWorld().getWorkers().add(worker);
							worker = null;
							this.overlandImg = null;
						}
					}
					else if (building != null){
						if (building.isCanBeAssigned()){
							building.updateWorkerActivity(this);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch){
		if (this.isDisplay()){
			if (this.getColor()!=null){
				batch.setColor(this.getColor());
			}
			else{
				batch.setColor(Color.WHITE);
			}
			if (this.getImg()!=null){
				if (this.getImg().getTexRegs() != null){
					this.getImg().setCurrentFrame(this.getImg().getAnim().getKeyFrame(Aosa.getGlobal().getStateTime(), true));
					batch.draw(this.getImg().getCurrentFrame(),
							this.getPosBox().getX(),
							this.getPosBox().getY(),
							this.getPosBox().getWidth(),
							this.getPosBox().getHeight());
				}
				else if (this.getImg().getTex() != null){
					batch.draw(this.getImg().getTex(),
							this.getPosBox().getX(),
							this.getPosBox().getY(),
							this.getPosBox().getWidth(),
							this.getPosBox().getHeight());
				}
			}
			if (this.getOverlandImg()!=null){
				if (this.getOverlandImg().getTexRegs() != null){
					this.getOverlandImg().setCurrentFrame(this.getOverlandImg().getAnim().getKeyFrame(Aosa.getGlobal().getStateTime(), true));
					batch.draw(this.getOverlandImg().getCurrentFrame(),
							this.getPosBox().getX(),
							this.getPosBox().getY()+this.getImg().getTex().getHeight(),
							this.getOverlandImg().getTex().getWidth(),
							this.getOverlandImg().getTex().getHeight());
				}
				else if (this.getOverlandImg().getTex() != null){
					batch.draw(this.getOverlandImg().getTex(),
							this.getPosBox().getX(),
							this.getPosBox().getY()+this.getImg().getTex().getHeight(),
							this.getOverlandImg().getTex().getWidth(),
							this.getOverlandImg().getTex().getHeight());
				}
			}
			if (this.worker != null){
				//Draw assigned worker image
				batch.draw(Aosa.getGlobal().getImgByName("assigned").getTex(),
						this.getPosBox().getX(),
						this.getPosBox().getY()+128,
						Aosa.getGlobal().getImgByName("assigned").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("assigned").getTex().getHeight());
				this.worker.render(batch);
			}
			if (this.getFont()!=null){
				this.getFont().getFont().setColor(this.getColor());
				this.getFont().getFont().draw(batch, this.getText(), this.getPosBox().getX(), this.getPosBox().getY());
			}
		}
	}
	
	public void setBlockBuildable(){
		this.buildable = true;
		this.blockName = "Dirt";
		this.setImg(Aosa.getGlobal().getImgByName("dirt"));
	}
	
	public void setMineral(){
		this.overlandImg = Aosa.getGlobal().getImgByName("minerals");
		this.setMineralAmt(1000);
	}
	
	public void setGas(){
		this.overlandImg = Aosa.getGlobal().getImgByName("gas");
		this.setGasAmt(1000);
	}
	
	public void setTree(){
		this.overlandImg = Aosa.getGlobal().getImgByName("tree");
		this.dismantleAmt = 1000;
	}
	
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
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
	public Img getOverlandImg() {
		return overlandImg;
	}
	public void setOverlandImg(Img overlandImg) {
		this.overlandImg = overlandImg;
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
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
}
