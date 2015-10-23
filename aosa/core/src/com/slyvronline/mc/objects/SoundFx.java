package com.slyvronline.mc.objects;

import com.badlogic.gdx.audio.Sound;

public class SoundFx {

	private String name;
	private Sound sound;
	
	public SoundFx(){
		
	}
	
	public SoundFx(Sound sound, String name){
		this.sound=sound;
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sound getSound() {
		return sound;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	
}
