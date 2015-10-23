package com.slyvronline.mc.objects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.mc.objects.menus.GameMenu;

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
	
	public GameInstance(){
		paused = false;
		startGameMillis = System.currentTimeMillis();
	}
	
	public void update(){
		gameOverCheck();
	}
	
	public void gameOverCheck(){
		boolean gameOver = false;
		boolean gameWin = false;
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
	
}
