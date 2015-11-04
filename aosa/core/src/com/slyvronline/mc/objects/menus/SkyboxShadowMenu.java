package com.slyvronline.mc.objects.menus;

import com.badlogic.gdx.Gdx;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.utils.GameConstants;

public class SkyboxShadowMenu extends Menu {

	public SkyboxShadowMenu(){
		
	}
	
	private boolean initiatingMorning;
	private boolean initiatingNight;
	
	private long timer;
	
	public void update(){
		Ent skybox_shadow = this.getEntByName("skybox_shadow");
		boolean startDay = Aosa.getGlobal().getGame().getWorld().isStartDay();
		boolean startNight = Aosa.getGlobal().getGame().getWorld().isStartNight();
		
		if (!initiatingMorning && startDay){
			initiatingMorning = startDay;
			timer = System.currentTimeMillis();
		}
		if (!initiatingNight && startNight){
			initiatingNight = startNight;
			timer = System.currentTimeMillis();
		}
		
		if (initiatingMorning && skybox_shadow.getColor().a > 0.1f){
			if (System.currentTimeMillis() > timer + 100){
				skybox_shadow.getColor().a = skybox_shadow.getColor().a - 0.01f;
				timer = System.currentTimeMillis();
			}
		}
		else if (initiatingMorning && skybox_shadow.getColor().a <= 0.1f){
			initiatingMorning = false;
		}
		
		if (initiatingNight && skybox_shadow.getColor().a < 0.8f){
			if (System.currentTimeMillis() > timer + 100){
				skybox_shadow.getColor().a = skybox_shadow.getColor().a + 0.01f;
				timer = System.currentTimeMillis();
			}
		}
		else if (initiatingNight && skybox_shadow.getColor().a >= 0.8f){
			initiatingNight = false;
		}
		
		System.out.println(skybox_shadow.getColor().a);
	}
}
