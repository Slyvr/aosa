package com.slyvronline.mc.objects;

import com.badlogic.gdx.audio.Music;

/**
 * This object is used to contain music track information
 * @author Matt Schrum - slyvr89
 * @date 10/25/2013
 */
public class AudioTrack {

	private String name;
	private Music music;
	private String trackName;
	private String artistName;
	private String contactText;
	
	public AudioTrack(Music music, String name){
		this.music=music;
		this.name=name;
	}
	public AudioTrack(Music music, String name, String trackName, String artistName, String contactText){
		this.music=music;
		this.name=name;
		this.trackName=trackName;
		this.artistName=artistName;
		this.contactText=contactText;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getContactText() {
		return contactText;
	}
	public void setContactText(String contactText) {
		this.contactText = contactText;
	}
	
	
}
