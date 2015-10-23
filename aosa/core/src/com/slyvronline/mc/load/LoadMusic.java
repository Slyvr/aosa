package com.slyvronline.mc.load;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.AudioTrack;
import com.slyvronline.mc.objects.SoundFx;

public class LoadMusic {

	public static void load(){
		ArrayList<AudioTrack> tracks = new ArrayList<AudioTrack>();
		
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Call to Adventure.mp3")), "music1", "Call to Adventure", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Monkeys Spinning Monkeys.mp3")), "music2", "Monkeys Spinning Monkeys", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Pamgaea.mp3")), "music3", "Pamgaea", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/The Builder.mp3")), "music4", "The Builder", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Winner Winner.mp3")), "music5", "Winner Winner", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Fluffing a Duck.mp3")), "music6", "Fluffing a Duck", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/One-eyed Maestro.mp3")), "music7", "One-eyed Maestro", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/New Friendly.mp3")), "music8", "New Friendly", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		tracks.add(new AudioTrack(Gdx.audio.newMusic(Gdx.files.internal("data/music/Machinations.mp3")), "music9", "Machinations", "Kevin MacLeod (incompetech.com) ", "Licensed under Creative Commons: \nBy Attribution 3.0"));
		
		
		Aosa.getGlobal().setTracks(tracks);
		Aosa.getGlobal().setCurrentTrack(tracks.get(new Random().nextInt(tracks.size()-1)));
		Aosa.getGlobal().getCurrentTrack().getMusic().play();
	}
}
