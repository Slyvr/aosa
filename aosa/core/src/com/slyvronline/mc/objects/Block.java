package com.slyvronline.mc.objects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.buildings.Building;

public class Block extends Ent {

	private String blockName;
	private boolean buildable;
	private Building building;
	private boolean dismantlable;
	private int mineralAmt;
	private int gasAmt;
	private Img overlandImg;
	
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
	public boolean isDismantlable() {
		return dismantlable;
	}
	public void setDismantlable(boolean dismantlable) {
		this.dismantlable = dismantlable;
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
	
}
