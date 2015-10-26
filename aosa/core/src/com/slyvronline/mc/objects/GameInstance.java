package com.slyvronline.mc.objects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
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
		moveMap();
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
		boolean overlapping = false;
		if (Aosa.getGlobal().getCurrentMenu().getName().equals("game")){
			Ent blockTooltip = Aosa.getGlobal().getCurrentMenu().getEntByName("blockTooltip");
			for(Block block : this.world.getBaseBlocks()){
				Rectangle mousePos = Utils.getMousePos();
				//System.out.println(mousePos.x+" , "+mousePos.y);
				if (mousePos.overlaps(block.getPosBox())){
					String tooltipText = block.getBlockName();
					if (block.getOverlandImg() != null) tooltipText += "\n"+block.getOverlandImg().getName();
					if (block.getBuilding() != null) tooltipText += "\nBuilding: "+block.getBuilding().getName();
					if (block.getMineralAmt() > 0) tooltipText += "\nMinerals: "+block.getMineralAmt();
					if (block.getGasAmt() > 0) tooltipText += "\nGas: "+block.getGasAmt();
					blockTooltip.setText(tooltipText);
					overlapping=true;
				}
			}
			if (!overlapping){
				blockTooltip.setText("");
			}
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
