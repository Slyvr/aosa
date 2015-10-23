package com.slyvronline.mc.load;

import com.slyvronline.aosa.Aosa;
import com.slyvronline.mc.objects.Ent;
import com.slyvronline.mc.objects.games.SandboxMode;

public class LoadGameInstance {

	public static void load(String gamemode){
		if (gamemode.equals("sandbox")){
			SandboxMode game = new SandboxMode();
			game.setGamemode(gamemode);
			Aosa.getGlobal().setGame(game);
		}
	}
}
