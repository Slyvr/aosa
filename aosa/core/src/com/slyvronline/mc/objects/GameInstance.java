package com.slyvronline.mc.objects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.characters.MainCharacter;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.objects.menus.GameMenu;
import com.slyvronline.mc.utils.Utils;

/**
 * This class stores the data of a particular game instance, whether new or saved.
 * The galaxy can be used to dig down and find specific locations to travel to.
 * All other location variables are used to store which places the player in this
 * instance is currently located in.
 * 
 * The currentCollection determines the specific place that should currently be rendered
 * to the screen.
 * @author Matt Schrum - slyvr89
 * @date 10/25/2013
 */
public class GameInstance {
	
	private boolean paused;
	private String gamemode;
	private long startGameMillis;
	private World world;
	
	private int mineralsCollected;
	private int gasCollected;
	
	public GameInstance(){
		paused = false;
		startGameMillis = System.currentTimeMillis();
		world = new World();
		
		//TESTING
		mineralsCollected = 1000;
		gasCollected = 1000;
	}
	
	public void update(){
		world.update();
		gameOverCheck();
		//moveMap();
		moveMainChar();
		updateBuild();
		updateAssignFollowers();
		updateSummonFollowers();
		updateBlockTooltip();
		updateDebugTooltip();
	}
	
	public void render(SpriteBatch batch){
		world.render(batch);
	}
	
	public void gameOverCheck(){
		boolean gameOver = false;
		boolean gameWin = false;
	}
	
	public void updateBuild(){
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)){
			if (world.getSelectedBlockGroup().isBuildable() && world.getSelectedBlockGroup().getBuilding() == null && world.getWorkers().size() > 0){
				boolean okToBuild = true;
				for(Block b : world.getSelectedBlockGroup().getBlocks()){
					if (b.isBuildable() && b.getDismantleAmt() > 0){
						okToBuild = false;
					}
				}
				if (okToBuild){
					Aosa.getGlobal().getCurrentMenu().setCurrentSubMenu(Aosa.getGlobal().getCurrentMenu().getSubMenuByName("build"));
					//Reset the build menu
					Menu buildMenu = Aosa.getGlobal().getCurrentMenu().getCurrentSubMenu();
					for(int i=buildMenu.getEnts().size()-1; i>0; i--){
						Ent e = buildMenu.getEnts().get(i);
						if (e.getName().contains("building")){
							buildMenu.getEnts().remove(e);
						}
					}
					
					int buildSize=0;
					for(Block b : world.getSelectedBlockGroup().getBlocks()){
						if (b.isBuildable()){
							buildSize++;
						}
					}
					//Determine which buildings to allow to be built here
					float maxHeight=0;
					ArrayList<Ent> buildings = new ArrayList<Ent>();
					if (buildSize == 2){
						if (world.getSelectedBlockGroup().getMineralAmt() > 0){
							Ent mine = new Ent();
							mine.setName("building-mine");
							mine.setImg(Aosa.getGlobal().getImgByName("mine"));
							if (mine.getImg().getTex().getHeight() > maxHeight) maxHeight = mine.getImg().getTex().getHeight();
							buildings.add(mine);
						}
						else if (world.getSelectedBlockGroup().getGasAmt() > 0){
							Ent refinery = new Ent();
							refinery.setName("building-refinery");
							refinery.setImg(Aosa.getGlobal().getImgByName("refinery"));
							if (refinery.getImg().getTex().getHeight() > maxHeight) maxHeight = refinery.getImg().getTex().getHeight();
							buildings.add(refinery);
						}
						else{
							Ent tower = new Ent();
							tower.setName("building-tower");
							tower.setImg(Aosa.getGlobal().getImgByName("tower"));
							if (tower.getImg().getTex().getHeight() > maxHeight) maxHeight = tower.getImg().getTex().getHeight();
							buildings.add(tower);
						}
					}
					else if (buildSize == 4){
						Ent barracks = new Ent();
						barracks.setName("building-barracks");
						barracks.setImg(Aosa.getGlobal().getImgByName("barracks"));
						if (barracks.getImg().getTex().getHeight() > maxHeight) maxHeight = barracks.getImg().getTex().getHeight();
						buildings.add(barracks);
					}
					else if (buildSize == 8){
						Ent base = new Ent();
						base.setName("building-base");
						base.setImg(Aosa.getGlobal().getImgByName("base"));
						if (base.getImg().getTex().getHeight() > maxHeight) maxHeight = base.getImg().getTex().getHeight();
						buildings.add(base);
					}
					float blackWidth = buildings.size()*32*buildSize + (16*buildings.size()) + 16;
					Ent blackcover = buildMenu.getEntByName("blackcover");
					blackcover.setPosBox(new Rectangle(
							(Gdx.graphics.getWidth()/2)-(blackWidth/2),
							256,
							blackWidth,
							maxHeight+32));
					float x=blackcover.getPosBox().getX()+16;
					for(Ent building : buildings){
						building.setPosBox(new Rectangle(
								x,
								256 + 16,
								building.getImg().getTex().getWidth(),
								building.getImg().getTex().getHeight()));
						buildMenu.getEnts().add(building);
						x+= building.getImg().getTex().getWidth()+16;
					}
				}
			}
		}
	}
	
	public void updateAssignFollowers(){
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)){
			//Get a worker to assign
			Worker worker = world.getAvailableWorker();
			//Determine what the assignment is on the block
			if (worker != null){
				BlockGroup grp = world.getSelectedBlockGroup();
				Block selected = world.getSelectedBlock();
				if (grp.getBuilding() != null && grp.getWorker() == null && grp.getBuilding().isCanBeAssigned()){
					grp.setWorker(worker);
					worker.setSelected(false);
					world.getWorkers().remove(worker);
				}
				else if (selected.getDismantleAmt() > 0 && selected.getWorker() == null){
					selected.setWorker(worker);
					worker.setSelected(false);
					world.getWorkers().remove(worker);
				}
			}
		}
	}
	
	float elapsedSummonTime;
	float elapsedTotalTime;
	public void updateSummonFollowers(){
		if ((Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) && elapsedTotalTime < 80){
			world.getSummon().setPosBox(new Rectangle(
					(world.getMainChar().getPosBox().getX() + world.getMainChar().getImg().getTex().getWidth()/2) - (elapsedSummonTime/2),
					80+32,
					elapsedSummonTime,
					32));
			if (elapsedSummonTime <= 128){
				elapsedSummonTime+=10;
			}
			elapsedTotalTime++;
		}
		else if ((Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) && elapsedTotalTime >= 80){
			elapsedSummonTime=0;
			world.getSummon().setPosBox(new Rectangle());
			elapsedTotalTime++;
		}
		else{
			elapsedSummonTime=0;
			elapsedTotalTime=0;
			world.getSummon().setPosBox(new Rectangle());
		}
		
		for(Worker w : world.getWorkers()){
			if (w.getPosBox().overlaps(world.getSummon().getPosBox())){
				w.setSelected(true);
			}
		}
		for(BlockGroup grp : world.getBlockGroups()){
			for(Block b : grp.getBlocks()){
				if (b.getWorker() != null){
					if (b.getWorker().getPosBox().overlaps(world.getSummon().getPosBox())){
						Worker w = b.getWorker();
						world.getWorkers().add(w);
						b.setWorker(null);
						w.setSelected(true);
					}
				}
			}
			if (grp.getWorker() != null){
				if (grp.getWorker().getPosBox().overlaps(world.getSummon().getPosBox())){
					Worker w = grp.getWorker();
					world.getWorkers().add(w);
					grp.setWorker(null);
					w.setSelected(true);
				}
			}
		}
	}
	
	public void moveMainChar(){
		MainCharacter main = world.getMainChar();
		int speed = 1;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			speed = main.getRunSpeed();
		}
		else{
			speed = main.getJogSpeed();
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)){
			float leftblockX = world.getBlockGroups().get(0).getBlocks().get(0).getPosBox().getX();
			float rightblockX = (((this.world.getBlockGroups().size() * this.world.getBlockGroups().get(0).getBlocks().size()) * 32)-650);
			if (leftblockX <= main.getPosBox().getX()){
				main.getPosBox().setX(main.getPosBox().getX() - speed);
				world.setSelectedBlock();
				if (leftblockX+650 <= Aosa.getGlobal().getCamera().position.x){
					if (main.getPosBox().getX() < rightblockX-650){
						Aosa.getGlobal().getCamera().position.x = main.getPosBox().getX();
					}
				}
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
			float leftblockX = world.getBlockGroups().get(0).getBlocks().get(0).getPosBox().getX();
			float rightblockX = (((this.world.getBlockGroups().size() * this.world.getBlockGroups().get(0).getBlocks().size()) * 32)-650);
			if (main.getPosBox().getX() < rightblockX-32){
				main.getPosBox().setX(main.getPosBox().getX() + speed);
				world.setSelectedBlock();
				if (Aosa.getGlobal().getCamera().position.x < rightblockX-650){
					if (main.getPosBox().getX() > leftblockX+650){
						Aosa.getGlobal().getCamera().position.x = main.getPosBox().getX();
					}
				}
			}
		}
	}
	
	public void moveMap(){
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			//Get leftmost block
			Block block = world.getBlockGroups().get(0).getBlocks().get(0);
			if (block.getPosBox().getX()+650 <= Aosa.getGlobal().getCamera().position.x)
				Aosa.getGlobal().getCamera().translate(-50, 0, 0);
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			//Get rightmost block
			BlockGroup lastGrp = world.getBlockGroups().get(world.getBlockGroups().size()-1);
			Block block = lastGrp.getBlocks().get(lastGrp.getBlocks().size()-1);
			if (Aosa.getGlobal().getCamera().position.x < (((this.world.getBlockGroups().size() * this.world.getBlockGroups().get(0).getBlocks().size()) * 32)-650)){
				Aosa.getGlobal().getCamera().translate(50, 0, 0);
			}
		}
	}
	
	public void updateDebugTooltip(){
		if (Aosa.getGlobal().getCurrentMenu().getName().equals("game")){
			Ent debugTooltip = Aosa.getGlobal().getCurrentMenu().getEntByName("debugTooltip");
			String debugText = "";
			debugText += "Minerals: "+Aosa.getGlobal().getGame().getMineralsCollected();
			debugText += "\nGas: "+Aosa.getGlobal().getGame().getGasCollected();
			debugText += "\nFPS: "+Gdx.graphics.getFramesPerSecond();
			debugText += "\nCamX: "+Aosa.getGlobal().getCamera().position.x/32;
			debugTooltip.setText(debugText);
		}
	}
	
	public void updateBlockTooltip(){
		if (Aosa.getGlobal().getCurrentMenu().getName().equals("game")){
			Ent blockTooltip = Aosa.getGlobal().getCurrentMenu().getEntByName("blockTooltip");
			String tooltipText = "";
			if (world.getSelectedBlock() != null) {
				tooltipText += world.getSelectedBlock().getBlockName();
				if (world.getSelectedBlock().getOverlandImg() != null)
					tooltipText += "\n"+world.getSelectedBlock().getOverlandImg().getName();
				if (world.getSelectedBlockGroup().getBuilding() != null)
					tooltipText += "\nBuilding: "+world.getSelectedBlockGroup().getBuilding().getName();
				if (world.getSelectedBlockGroup().getMineralAmt() > 0)
					tooltipText += "\nMinerals: "+world.getSelectedBlockGroup().getMineralAmt();
				if (world.getSelectedBlockGroup().getGasAmt() > 0)
					tooltipText += "\nGas: "+world.getSelectedBlockGroup().getGasAmt();
				if (world.getSelectedBlockGroup().getBuilding() != null 
						&& world.getSelectedBlockGroup().getBuilding().getBuildProgress() > 0)
					tooltipText += "\nBuildProgress: "+world.getSelectedBlockGroup().getBuilding().getBuildProgress();
				if (world.getSelectedBlock().getDismantleAmt() > 0)
					tooltipText += "\nDismantleAmount: "+world.getSelectedBlock().getDismantleAmt();
				if (world.getSelectedBlockGroup().getBuilding() != null &&
						world.getSelectedBlockGroup().getBuilding().getActionProgress() > 0)
					tooltipText += "\nActionProgress: "+world.getSelectedBlockGroup().getBuilding().getActionProgress();
			}
			blockTooltip.setText(tooltipText);
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public String getGamemode() {
		return gamemode;
	}

	public void setGamemode(String gamemode) {
		this.gamemode = gamemode;
	}

	public long getStartGameMillis() {
		return startGameMillis;
	}

	public void setStartGameMillis(long startGameMillis) {
		this.startGameMillis = startGameMillis;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getMineralsCollected() {
		return mineralsCollected;
	}

	public void setMineralsCollected(int mineralsCollected) {
		this.mineralsCollected = mineralsCollected;
	}

	public int getGasCollected() {
		return gasCollected;
	}

	public void setGasCollected(int gasCollected) {
		this.gasCollected = gasCollected;
	}
	
}
