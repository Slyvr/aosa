package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.SoundFx;

public class LoadSounds {

	public static void load(){
		ArrayList<SoundFx> sounds = new ArrayList<SoundFx>();
		
		//sounds.add(new SoundFx(Gdx.audio.newSound(Gdx.files.internal("sounds/Menu Selection Click.wav")),"click"));
		//sounds.add(new SoundFx(Gdx.audio.newSound(Gdx.files.internal("sounds/zipclick.wav")),"click"));
		sounds.add(new SoundFx(Gdx.audio.newSound(Gdx.files.internal("data/sounds/click3.wav")),"click"));
		sounds.add(new SoundFx(Gdx.audio.newSound(Gdx.files.internal("data/sounds/error.wav")),"error"));
		sounds.add(new SoundFx(Gdx.audio.newSound(Gdx.files.internal("data/sounds/whistle.wav")),"whistle"));
		
		Aosa.getGlobal().setSounds(sounds);
	}
}
