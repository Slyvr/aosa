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
	}
	
	public void update(){
		world.update();
		gameOverCheck();
		//moveMap();
		moveMainChar();
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
	
	public void updateAssignFollowers(){
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)){
			//Get a worker to assign
			Worker worker = null;
			for(Worker w : world.getWorkers()){
				if (w.isSelected()){
					worker = w;
					break;
				}
			}
			//Determine what the assignment is on the block
			if (worker != null){
				Block selected = world.getSelectedBlock();
				if (selected.getBuilding() != null){
					if (selected.getBuilding().isCanBeAssigned()){
						selected.setWorker(worker);
						worker.setSelected(false);
						world.getWorkers().remove(worker);
					}
				}
				else if (selected.getDismantleAmt() > 0){
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
		for(Block b : world.getBaseBlocks()){
			if (b.getWorker() != null){
				if (b.getWorker().getPosBox().overlaps(world.getSummon().getPosBox())){
					Worker w = b.getWorker();
					world.getWorkers().add(w);
					b.setWorker(null);
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
			float leftblockX = world.getBaseBlocks().get(0).getPosBox().getX();
			float rightblockX = ((this.world.getBaseBlocks().size() * 32)-650);
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
			float leftblockX = world.getBaseBlocks().get(0).getPosBox().getX();
			float rightblockX = ((this.world.getBaseBlocks().size() * 32)-650);
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
			Block block = world.getBaseBlocks().get(0);
			if (block.getPosBox().getX()+650 <= Aosa.getGlobal().getCamera().position.x)
				Aosa.getGlobal().getCamera().translate(-50, 0, 0);
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			//Get rightmost block
			Block block = world.getBaseBlocks().get(world.getBaseBlocks().size()-1);
			if (Aosa.getGlobal().getCamera().position.x < ((this.world.getBaseBlocks().size() * 32)-650)){
				Aosa.getGlobal().getCamera().translate(50, 0, 0);
			}
		}
	}
	
	public void updateDebugTooltip(){
		if (Aosa.getGlobal().getCurrentMenu().getName().equals("game")){
			Ent debugTooltip = Aosa.getGlobal().getCurrentMenu().getEntByName("debugTooltip");
			String debugText = "FPS: "+Gdx.graphics.getFramesPerSecond();
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
				if (world.getSelectedBlock().getOverlandImg() != null) tooltipText += "\n"+world.getSelectedBlock().getOverlandImg().getName();
				if (world.getSelectedBlock().getBuilding() != null) tooltipText += "\nBuilding: "+world.getSelectedBlock().getBuilding().getName();
				if (world.getSelectedBlock().getMineralAmt() > 0) tooltipText += "\nMinerals: "+world.getSelectedBlock().getMineralAmt();
				if (world.getSelectedBlock().getGasAmt() > 0) tooltipText += "\nGas: "+world.getSelectedBlock().getGasAmt();
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
