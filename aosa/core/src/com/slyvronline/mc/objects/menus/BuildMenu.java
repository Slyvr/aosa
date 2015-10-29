package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Block;
import com.slyvronline.mc.objects.BlockGroup;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.objects.buildings.Barracks;
import com.slyvronline.mc.objects.buildings.Base;
import com.slyvronline.mc.objects.buildings.Mine;
import com.slyvronline.mc.objects.buildings.Refinery;
import com.slyvronline.mc.objects.buildings.Tower;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.utils.Utils;

public class BuildMenu extends Menu {

	public BuildMenu(){
		
	}
	
	public void update(){
		if (Gdx.input.justTouched()){
			Rectangle mousePos = Utils.getMenuMousePos();
			boolean clickedOnItem = false;
			for(Ent e : this.getEnts()){
				if (mousePos.overlaps(e.getPosBox()) && !e.getName().equals("whitecover")){
					clickedOnItem = true;
					if (e.getName().contains("mine")){
						buildBuilding("mine");
					}
					else if (e.getName().contains("refinery")){
						buildBuilding("refinery");
					}
					else if (e.getName().contains("tower")){
						buildBuilding("tower");
					}
					else if (e.getName().contains("barracks")){
						buildBuilding("barracks");
					}
					else if (e.getName().contains("base")){
						buildBuilding("base");
					}
				}
			}
			
			if (!clickedOnItem){
				//Clicked off menu, close submenu
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
		}
	}
	
	public void buildBuilding(String type){
		int mineralsCollected = Aosa.getGlobal().getGame().getMineralsCollected();
		int gasCollected = Aosa.getGlobal().getGame().getGasCollected();
		if (type.equals("mine")){
			Mine mine = new Mine();
			mine.setImg(Aosa.getGlobal().getImgByName("construct"));
			Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
			if (mineralsCollected >= mine.getMineralCost() && gasCollected >= mine.getGasCost() && worker != null){
				Aosa.getGlobal().getSoundByName("click").getSound().play();
				Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - mine.getMineralCost());
				Aosa.getGlobal().getGame().setGasCollected(gasCollected - mine.getGasCost());
				BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
				float buildX = 0;
				for(Block b : grp.getBlocks()){
					if (b.isBuildable()){
						if (buildX == 0)
						buildX = b.getPosBox().getX();
					}
					if (b.getOverlandImg() != null && b.getOverlandImg().getName().equals("minerals")){
						b.setOverlandImg(null);
					}
				}
				mine.setPosBox(new Rectangle(
						buildX,
						80+32,
						Aosa.getGlobal().getImgByName("mine").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("mine").getTex().getHeight()));
				grp.setBuilding(mine);
				worker.setSelected(false);
				grp.setWorker(worker);
				Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
			else{
				Aosa.getGlobal().getSoundByName("error").getSound().play();
			}
		}
		else if (type.equals("refinery")){
			Refinery refinery = new Refinery();
			refinery.setImg(Aosa.getGlobal().getImgByName("construct"));
			Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
			if (mineralsCollected >= refinery.getMineralCost() && gasCollected >= refinery.getGasCost() && worker != null){
				Aosa.getGlobal().getSoundByName("click").getSound().play();
				Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - refinery.getMineralCost());
				Aosa.getGlobal().getGame().setGasCollected(gasCollected - refinery.getGasCost());
				BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
				float buildX = 0;
				for(Block b : grp.getBlocks()){
					if (b.isBuildable()){
						if (buildX == 0)
						buildX = b.getPosBox().getX();
					}
					if (b.getOverlandImg() != null && b.getOverlandImg().getName().equals("gas")){
						b.setOverlandImg(null);
					}
				}
				refinery.setPosBox(new Rectangle(
						buildX,
						80+32,
						Aosa.getGlobal().getImgByName("refinery").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("refinery").getTex().getHeight()));
				grp.setBuilding(refinery);
				worker.setSelected(false);
				grp.setWorker(worker);
				Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
			else{
				Aosa.getGlobal().getSoundByName("error").getSound().play();
			}
		}
		else if (type.equals("tower")){
			Tower tower = new Tower();
			tower.setImg(Aosa.getGlobal().getImgByName("construct"));
			Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
			if (mineralsCollected >= tower.getMineralCost() && gasCollected >= tower.getGasCost() && worker != null){
				Aosa.getGlobal().getSoundByName("click").getSound().play();
				Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - tower.getMineralCost());
				Aosa.getGlobal().getGame().setGasCollected(gasCollected - tower.getGasCost());
				BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
				float buildX = 0;
				for(Block b : grp.getBlocks()){
					if (b.isBuildable()){
						buildX = b.getPosBox().getX();
						break;
					}
				}
				tower.setPosBox(new Rectangle(
						buildX,
						80+32,
						Aosa.getGlobal().getImgByName("tower").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("tower").getTex().getHeight()));
				grp.setBuilding(tower);
				worker.setSelected(false);
				grp.setWorker(worker);
				Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
			else{
				Aosa.getGlobal().getSoundByName("error").getSound().play();
			}
		}
		else if (type.equals("barracks")){
			Barracks barracks = new Barracks();
			barracks.setImg(Aosa.getGlobal().getImgByName("construct"));
			Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
			if (mineralsCollected >= barracks.getMineralCost() && gasCollected >= barracks.getGasCost() && worker != null){
				Aosa.getGlobal().getSoundByName("click").getSound().play();
				Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - barracks.getMineralCost());
				Aosa.getGlobal().getGame().setGasCollected(gasCollected - barracks.getGasCost());
				BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
				float buildX = 0;
				for(Block b : grp.getBlocks()){
					if (b.isBuildable()){
						buildX = b.getPosBox().getX();
						break;
					}
				}
				barracks.setPosBox(new Rectangle(
						buildX,
						80+32,
						Aosa.getGlobal().getImgByName("barracks").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("barracks").getTex().getHeight()));
				grp.setBuilding(barracks);
				worker.setSelected(false);
				grp.setWorker(worker);
				Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
			else{
				Aosa.getGlobal().getSoundByName("error").getSound().play();
			}
		}
		else if (type.equals("base")){
			Base base = new Base();
			base.setImg(Aosa.getGlobal().getImgByName("construct"));
			Worker worker = Aosa.getGlobal().getGame().getWorld().getAvailableWorker();
			if (mineralsCollected >= base.getMineralCost() && gasCollected >= base.getGasCost() && worker != null){
				Aosa.getGlobal().getSoundByName("click").getSound().play();
				Aosa.getGlobal().getGame().setMineralsCollected(mineralsCollected - base.getMineralCost());
				Aosa.getGlobal().getGame().setGasCollected(gasCollected - base.getGasCost());
				BlockGroup grp = Aosa.getGlobal().getGame().getWorld().getSelectedBlockGroup();
				float buildX = 0;
				for(Block b : grp.getBlocks()){
					if (b.isBuildable()){
						buildX = b.getPosBox().getX();
						break;
					}
				}
				base.setPosBox(new Rectangle(
						buildX,
						80+32,
						Aosa.getGlobal().getImgByName("base").getTex().getWidth(),
						Aosa.getGlobal().getImgByName("base").getTex().getHeight()));
				grp.setBuilding(base);
				worker.setSelected(false);
				grp.setWorker(worker);
				Aosa.getGlobal().getGame().getWorld().getWorkers().remove(worker);
				Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(null);
			}
			else{
				Aosa.getGlobal().getSoundByName("error").getSound().play();
			}
		}
	}
}
