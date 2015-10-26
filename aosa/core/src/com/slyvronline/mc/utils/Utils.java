package com.slyvronline.mc.utils;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;

public class Utils {
	
	public static double calcLineDistance(Rectangle pos1, Rectangle pos2){
		float diffX = pos1.getX()-pos2.getX();
		float diffY = pos1.getY()-pos2.getY();
		double distance = Math.sqrt((diffX*diffX)+(diffY*diffY));
		return distance;
	}
	
	public static Rectangle getMousePos(){
		Vector3 mousePos = Aosa.getGlobal().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		return new Rectangle(mousePos.x, mousePos.y,1,1);
	}
}
