package com.slyvronline.mc.objects;

public class GameOptions {

	private boolean fullscreen;
	private Float audioVolume;
	
	public GameOptions(){
		fullscreen=false;
		audioVolume=1f;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public Float getAudioVolume() {
		return audioVolume;
	}

	public void setAudioVolume(Float audioVolume) {
		this.audioVolume = audioVolume;
	}
	
}
