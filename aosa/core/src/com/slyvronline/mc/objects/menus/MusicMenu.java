package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.AudioTrack;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.GameConstants;

public class MusicMenu extends Menu {
	
	public String nextSong;
	public boolean transitioning;
	public long transitionTicker;
	public long transitionTickerAmt;
	public float volumeFader;
	
	public boolean displayingInfo;
	public long displayTicker;
	public long displayTickerAmt;
	
	public boolean paused;
	
	public MusicMenu(){
		volumeFader = 0.01f;
		transitionTickerAmt = 1500;
		displayTickerAmt = 3000;
	}
	
	public void update(){
		/*
		updateVolume();
		updateButtonClick();
		updateMusicTransition();
		displayMusicInfo();
		*/
	}
	
	public void updateVolume(){
		AudioTrack current = Aosa.getGlobal().getCurrentTrack();
		if (current != null && !transitioning){
			current.getMusic().setVolume(Aosa.getGlobal().getOptions().getAudioVolume());
		}
	}
	
	public void updateMusicTransition(){
		if (transitioning){
			if (!Aosa.getGlobal().getCurrentTrack().getMusic().isPlaying()){
				Aosa.getGlobal().setCurrentTrack(Aosa.getGlobal().getTrackByName(nextSong));
				Aosa.getGlobal().getCurrentTrack().getMusic().play();
				transitioning = false;
				
				displayTicker = System.currentTimeMillis()+displayTickerAmt;
				displayingInfo = true;
			}
			else{
				if (transitionTicker==0){
					transitionTicker = System.currentTimeMillis();
				}
				if (System.currentTimeMillis() < transitionTicker){
					//Fade volume until zero
					if (Aosa.getGlobal().getCurrentTrack().getMusic().getVolume() >= volumeFader)
						Aosa.getGlobal().getCurrentTrack().getMusic().setVolume(Aosa.getGlobal().getCurrentTrack().getMusic().getVolume() - volumeFader);
				}
				else{
					//Song change, reset volume
					Aosa.getGlobal().getCurrentTrack().getMusic().setVolume(Aosa.getGlobal().getOptions().getAudioVolume());
					Aosa.getGlobal().getCurrentTrack().getMusic().stop();
					Aosa.getGlobal().setCurrentTrack(Aosa.getGlobal().getTrackByName(nextSong));
					Aosa.getGlobal().getCurrentTrack().getMusic().play();
					transitioning = false;
					
					displayTicker = System.currentTimeMillis()+displayTickerAmt;
					displayingInfo = true;
				}
			}
		}
		
		if (!Aosa.getGlobal().getCurrentTrack().getMusic().isPlaying() && !paused){
			nextSong();
		}
	}
	
	public void updateButtonClick(){
		if (Gdx.input.justTouched()){
			Rectangle mousePos = new Rectangle(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),1,1);
			for (Ent e : this.getEnts()){
				if (e.getName().contains("music")){
					Rectangle ePos = new Rectangle(e.getPosBox().getX(),e.getPosBox().getY(),36,36);
					if (mousePos.overlaps(ePos)){
						if (e.getName().equals("musicprev")){
							prevSong();
						}
						else if (e.getName().equals("musicplay")){
							if (Aosa.getGlobal().getCurrentTrack().getMusic().isPlaying()){
								Aosa.getGlobal().getCurrentTrack().getMusic().pause();
								e.setImg(Aosa.getGlobal().getImgByName("music_play"));
								paused = true;
							}
							else{
								Aosa.getGlobal().getCurrentTrack().getMusic().play();
								e.setImg(Aosa.getGlobal().getImgByName("music_pause"));
								paused = false;
							}
						}
						else if (e.getName().equals("musicnext")){
							nextSong();
						}
						else if (e.getName().equals("musicinfo") && !displayingInfo){
							displayingInfo = true;
							displayTicker = System.currentTimeMillis()+displayTickerAmt;
						}
					}
				}
			}
		}
	}
	
	public void prevSong(){
		transitionTicker = System.currentTimeMillis()+transitionTickerAmt;
		AudioTrack current = Aosa.getGlobal().getCurrentTrack();
		AudioTrack prev = Aosa.getGlobal().getTracks().get(Aosa.getGlobal().getTracks().size()-1);
		for(AudioTrack track : Aosa.getGlobal().getTracks()){
			if (track.getName().equals(current.getName())){
				nextSong = prev.getName();
				break;
			}
			prev = track;
		}
		transitioning = true;
	}
	
	public void nextSong(){
		transitionTicker = System.currentTimeMillis()+transitionTickerAmt;
		AudioTrack current = Aosa.getGlobal().getCurrentTrack();
		boolean nextIsIt = false;
		for(int i=0; i<Aosa.getGlobal().getTracks().size(); i++){
			AudioTrack track = Aosa.getGlobal().getTracks().get(i);
			if (nextIsIt){
				nextSong = track.getName();
				break;
			}
			if (track.getName().equals(current.getName())){
				if (i != Aosa.getGlobal().getTracks().size()-1)
					nextIsIt = true;
			}
		}
		if (!nextIsIt){
			nextSong = Aosa.getGlobal().getTracks().get(0).getName();
		}
		transitioning = true;
	}
	
	public void displayMusicInfo(){
		for(int i = this.getEnts().size()-1; i>=0; i--){
			Ent ent = this.getEnts().get(i);
			if (ent.getName().equals("fntInfo") || ent.getName().equals("whitecover"))
				this.getEnts().remove(ent);
		}
		
		if (displayingInfo){
			if (displayTicker==0){
				displayTicker = System.currentTimeMillis();
			}
			if (System.currentTimeMillis() < displayTicker){
				//Do logic
				Ent musicbg = this.getEntByName("bg");
				
				Ent whitecover = new Ent();
				whitecover.setName("whitecover");
				whitecover.setImg(Aosa.getGlobal().getImgByName("whitecover"));
				whitecover.setPosBox(new Rectangle(musicbg.getPosBox().getX() - 50,
						musicbg.getPosBox().getY() + musicbg.getPosBox().getHeight(),
						musicbg.getPosBox().getWidth()*2,
						96));
				this.getEnts().add(whitecover);
				
				AudioTrack current = Aosa.getGlobal().getCurrentTrack();
				
				Ent fntInfo = new Ent();
				fntInfo.setName("fntInfo");
				fntInfo.setFont(Aosa.getGlobal().getFontByName("Calibri20"));
				fntInfo.setPosBox(new Rectangle(whitecover.getPosBox().getX() + 5,
						whitecover.getPosBox().getY() + whitecover.getPosBox().getHeight() - 5,
						0,0));
				fntInfo.setText(current.getTrackName()+"\nBy "+current.getArtistName()+"\n"+current.getContactText());
				fntInfo.setColor(Color.BLACK);
				this.getEnts().add(fntInfo);
			}
			else{
				displayingInfo = false;
			}
		}
	}
}
