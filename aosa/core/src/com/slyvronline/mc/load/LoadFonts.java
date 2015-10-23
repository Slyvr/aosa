package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Font;

/**
 * @author Matt Schrum - slyvr89
 * @date 10/25/2013
 */
public class LoadFonts {

	public static void load(){
		ArrayList<Font> fonts = new ArrayList<Font>();
		
		fonts.add(new Font("Calibri32",32,new BitmapFont(Gdx.files.internal("data/fonts/Calibri32.fnt"),false)));
		fonts.add(new Font("Calibri24",24,new BitmapFont(Gdx.files.internal("data/fonts/Calibri24.fnt"),false)));
		fonts.add(new Font("Calibri20",20,new BitmapFont(Gdx.files.internal("data/fonts/Calibri20.fnt"),false)));
		fonts.add(new Font("Calibri16",16,new BitmapFont(Gdx.files.internal("data/fonts/Calibri16.fnt"),false)));
		fonts.add(new Font("Calibri16b",16,new BitmapFont(Gdx.files.internal("data/fonts/Calibri16b.fnt"),false)));
		
		Aosa.getGlobal().setFonts(fonts);
	}
}
