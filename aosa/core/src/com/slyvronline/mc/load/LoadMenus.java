package com.slyvronline.mc.load;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.Menu;
import com.slyvronline.mc.objects.menus.*;
import com.slyvronline.mc.utils.Utils;

public class LoadMenus {

	public static void load(){
		ArrayList<Menu> menus = new ArrayList<Menu>();
		
		menus.add(loadSplashMenu());
		menus.add(loadMainMenu());
		menus.add(loadGameMenu());
		menus.add(loadMusicMenu());
		
		Aosa.getGlobal().setMenus(menus);
		Aosa.getGlobal().setCurrentMenu(menus.get(0));
		Aosa.getGlobal().setMusicMenu(Aosa.getGlobal().getMenuByName("music"));
	}
	
	public static Menu loadSplashMenu(){
		SplashMenu menu = new SplashMenu();
		
		menu.setName("splash");
		
		ArrayList<Ent> ents = new ArrayList<Ent>();

		Ent logo = new Ent();
		logo.setName("logo");
		logo.setImg(Aosa.getGlobal().getImgByName("Logo2_white"));
		logo.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(logo.getImg().getTex().getWidth()/2),
				(Gdx.graphics.getHeight()/2)-(logo.getImg().getTex().getHeight()/2),
				logo.getImg().getTex().getWidth(),
				logo.getImg().getTex().getHeight()));
		ents.add(logo);
		
		menu.setEnts(ents);
		
		return menu;
	}
	
	public static Menu loadMainMenu(){
		MainMenu menu = new MainMenu();
		menu.setName("main");
		
		ArrayList<Ent> ents = new ArrayList<Ent>();
		
		Ent logo = new Ent();
		logo.setName("logo");
		logo.setImg(Aosa.getGlobal().getImgByName("Logo2_white"));
		logo.setPosBox(new Rectangle(80,
				Gdx.graphics.getHeight()-logo.getImg().getTex().getHeight()-100,
				logo.getImg().getTex().getWidth(),
				logo.getImg().getTex().getHeight()));
		ents.add(logo);
		
		menu.setEnts(ents);
		
		//Setup sub menus
		ArrayList<Menu> subMenus = new ArrayList<Menu>();
		subMenus.add(loadOptionsMenu());
		subMenus.add(loadCreditsMenu());
		menu.setSubMenus(subMenus);
		
		return menu;
	}
	
	public static Menu loadGameMenu(){
		GameMenu menu = new GameMenu();
		menu.setName("game");
		
		ArrayList<Ent> ents = new ArrayList<Ent>();
		
		menu.setEnts(ents);
		
		ArrayList<Menu> subMenus = new ArrayList<Menu>();
		
		subMenus.add(loadPauseMenu());
		subMenus.add(loadOptionsMenu());
		subMenus.add(loadCreditsMenu());
		subMenus.add(loadGameOverMenu());
		subMenus.add(loadGameWinMenu());
		
		menu.setSubMenus(subMenus);
		
		return menu;
	}
	
	public static Menu loadMusicMenu(){
		MusicMenu menu = new MusicMenu();
		menu.setName("music");
		
		ArrayList<Ent> ents = new ArrayList<Ent>();
		
		Ent musicbg = new Ent();
		musicbg.setName("bg");
		musicbg.setImg(Aosa.getGlobal().getImgByName("music_bg"));
		musicbg.setPosBox(new Rectangle(Gdx.graphics.getWidth() - musicbg.getImg().getTex().getWidth(),
				0,
				musicbg.getImg().getTex().getWidth(),
				musicbg.getImg().getTex().getHeight()));
		ents.add(musicbg);
		
		float commonY=14;
		float commonXDiff = 44;
		
		Ent musicprev = new Ent();
		musicprev.setName("musicprev");
		musicprev.setImg(Aosa.getGlobal().getImgByName("music_prev"));
		musicprev.setPosBox(new Rectangle(musicbg.getPosBox().getX() + commonXDiff,
				commonY,
				musicprev.getImg().getTex().getWidth(),
				musicprev.getImg().getTex().getHeight()));
		ents.add(musicprev);
		
		Ent musicplay = new Ent();
		musicplay.setName("musicplay");
		musicplay.setImg(Aosa.getGlobal().getImgByName("music_pause"));
		musicplay.setPosBox(new Rectangle(musicprev.getPosBox().getX() + commonXDiff,
				commonY,
				musicplay.getImg().getTex().getWidth(),
				musicplay.getImg().getTex().getHeight()));
		ents.add(musicplay);
		
		Ent musicnext = new Ent();
		musicnext.setName("musicnext");
		musicnext.setImg(Aosa.getGlobal().getImgByName("music_next"));
		musicnext.setPosBox(new Rectangle(musicplay.getPosBox().getX() + commonXDiff,
				commonY,
				musicnext.getImg().getTex().getWidth(),
				musicnext.getImg().getTex().getHeight()));
		ents.add(musicnext);
		
		Ent musicinfo = new Ent();
		musicinfo.setName("musicinfo");
		musicinfo.setImg(Aosa.getGlobal().getImgByName("music_info"));
		musicinfo.setPosBox(new Rectangle(musicnext.getPosBox().getX() + commonXDiff,
				commonY,
				musicinfo.getImg().getTex().getWidth(),
				musicinfo.getImg().getTex().getHeight()));
		ents.add(musicinfo);
		
		menu.setEnts(ents);
		
		return menu;
	}
	
	public static Menu loadOptionsMenu(){
		OptionsMenu options = new OptionsMenu();
		options.setName("options");
		
		ArrayList<Ent> subEnts = new ArrayList<Ent>();
		
		Ent whitecover = new Ent();
		whitecover.setName("whitecover");
		whitecover.setImg(Aosa.getGlobal().getImgByName("black"));
		whitecover.setPosBox(new Rectangle(0,0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		subEnts.add(whitecover);
		
		options.setEnts(subEnts);
		
		return options;
	}
	
	public static Menu loadCreditsMenu(){
		CreditsMenu menu = new CreditsMenu();
		menu.setName("credits");
		
		ArrayList<Ent> subEnts = new ArrayList<Ent>();
		
		Ent whitecover = new Ent();
		whitecover.setName("whitecover");
		whitecover.setImg(Aosa.getGlobal().getImgByName("black"));
		whitecover.setPosBox(new Rectangle(0,0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		subEnts.add(whitecover);
		
		Ent fntCredits = new Ent();
		fntCredits.setName("fntCredits");
		fntCredits.setFont(Aosa.getGlobal().getFontByName("Calibri20"));
		fntCredits.setPosBox(new Rectangle(64,
				Gdx.graphics.getHeight() - 64,
				0,
				0));
		fntCredits.setText("Game Created by Matthew Schrum (indie-dollar.com)");
		subEnts.add(fntCredits);
		
		Ent btnReturn = new Ent();
		btnReturn.setName("btnReturn");
		btnReturn.setImg(Aosa.getGlobal().getImgByName("btnReturn"));
		btnReturn.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnReturn.getImg().getTex().getWidth()/2),
				btnReturn.getImg().getTex().getHeight() + 10,
				btnReturn.getImg().getTex().getWidth(),
				btnReturn.getImg().getTex().getHeight()));
		subEnts.add(btnReturn);
		
		menu.setEnts(subEnts);
		
		return menu;
	}
	
	public static Menu loadPauseMenu(){
		PauseMenu pause = new PauseMenu();
		pause.setName("pause");
		
		ArrayList<Ent> subEnts = new ArrayList<Ent>();
		
		Ent whitecover = new Ent();
		whitecover.setName("whitecover");
		whitecover.setImg(Aosa.getGlobal().getImgByName("whitecover"));
		whitecover.setPosBox(new Rectangle(0,0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		subEnts.add(whitecover);
		
		Ent btnReturn = new Ent();
		btnReturn.setName("btnReturn");
		btnReturn.setImg(Aosa.getGlobal().getImgByName("btnReturn"));
		btnReturn.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnReturn.getImg().getTex().getWidth()/2),
				(Gdx.graphics.getHeight()/2)-(btnReturn.getImg().getTex().getHeight()/2),
				btnReturn.getImg().getTex().getWidth(),
				btnReturn.getImg().getTex().getHeight()));
		subEnts.add(btnReturn);
		
		Ent btnOptions = new Ent();
		btnOptions.setName("btnOptions");
		btnOptions.setImg(Aosa.getGlobal().getImgByName("btnOptions"));
		btnOptions.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnOptions.getImg().getTex().getWidth()/2),
				btnReturn.getPosBox().getY() - btnOptions.getImg().getTex().getHeight() - 10,
				btnOptions.getImg().getTex().getWidth(),
				btnOptions.getImg().getTex().getHeight()));
		subEnts.add(btnOptions);
		
		Ent btnMainMenu = new Ent();
		btnMainMenu.setName("btnMainMenu");
		btnMainMenu.setImg(Aosa.getGlobal().getImgByName("btnMainMenu"));
		btnMainMenu.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnMainMenu.getImg().getTex().getWidth()/2),
				btnOptions.getPosBox().getY() - btnMainMenu.getImg().getTex().getHeight() - 10,
				btnMainMenu.getImg().getTex().getWidth(),
				btnMainMenu.getImg().getTex().getHeight()));
		subEnts.add(btnMainMenu);
		
		pause.setEnts(subEnts);
		
		return pause;
	}
	
	public static Menu loadGameOverMenu(){
		GameOverMenu menu = new GameOverMenu();
		menu.setName("gameover");
		
		ArrayList<Ent> subEnts = new ArrayList<Ent>();
		
		Ent whitecover = new Ent();
		whitecover.setName("whitecover");
		whitecover.setImg(Aosa.getGlobal().getImgByName("whitecover"));
		whitecover.setPosBox(new Rectangle(0,0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		subEnts.add(whitecover);
		
		Ent fntIntro = new Ent();
		fntIntro.setName("fntIntro");
		fntIntro.setFont(Aosa.getGlobal().getFontByName("Calibri32"));
		fntIntro.setPosBox(new Rectangle(400,
				630,
				0,
				0));
		fntIntro.setText("GAME OVER");
		fntIntro.setColor(Color.BLACK);
		subEnts.add(fntIntro);
		
		Ent btnMainMenu = new Ent();
		btnMainMenu.setName("btnMainMenu");
		btnMainMenu.setImg(Aosa.getGlobal().getImgByName("btnMainMenu"));
		btnMainMenu.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnMainMenu.getImg().getTex().getWidth()/2),
				btnMainMenu.getImg().getTex().getHeight() + 10,
				btnMainMenu.getImg().getTex().getWidth(),
				btnMainMenu.getImg().getTex().getHeight()));
		subEnts.add(btnMainMenu);
		
		menu.setEnts(subEnts);
		
		return menu;
	}
	
	public static Menu loadGameWinMenu(){
		GameWinMenu menu = new GameWinMenu();
		menu.setName("gamewin");
		
		ArrayList<Ent> subEnts = new ArrayList<Ent>();
		
		Ent whitecover = new Ent();
		whitecover.setName("whitecover");
		whitecover.setImg(Aosa.getGlobal().getImgByName("whitecover"));
		whitecover.setPosBox(new Rectangle(0,0,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		subEnts.add(whitecover);
		
		Ent fntIntro = new Ent();
		fntIntro.setName("fntIntro");
		fntIntro.setFont(Aosa.getGlobal().getFontByName("Calibri32"));
		fntIntro.setPosBox(new Rectangle(400,
				630,
				0,
				0));
		fntIntro.setText("WINNER!");
		fntIntro.setColor(Color.BLACK);
		subEnts.add(fntIntro);
		
		Ent btnMainMenu = new Ent();
		btnMainMenu.setName("btnMainMenu");
		btnMainMenu.setImg(Aosa.getGlobal().getImgByName("btnMainMenu"));
		btnMainMenu.setPosBox(new Rectangle((Gdx.graphics.getWidth()/2)-(btnMainMenu.getImg().getTex().getWidth()/2),
				btnMainMenu.getImg().getTex().getHeight() + 10,
				btnMainMenu.getImg().getTex().getWidth(),
				btnMainMenu.getImg().getTex().getHeight()));
		subEnts.add(btnMainMenu);
		
		menu.setEnts(subEnts);
		
		return menu;
	}
}
