package com.slyvronline.mc.objects;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.slyvronline.aosa.Aosa;
import com.slyvronline.aosa.runnable.WorldGeneratorThread;
import com.slyvronline.mc.objects.buildings.Base;
import com.slyvronline.mc.objects.buildings.Mine;
import com.slyvronline.mc.objects.buildings.Refinery;
import com.slyvronline.mc.objects.buildings.Spawner;
import com.slyvronline.mc.objects.characters.Grunt;
import com.slyvronline.mc.objects.characters.MainCharacter;
import com.slyvronline.mc.objects.characters.Soldier;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.objects.characters.Character.STATE;
import com.slyvronline.mc.utils.GameConstants;
import com.slyvronline.mc.utils.Utils;

public class World {
	
	private ArrayList<Block> bottomBlocks;
	private ArrayList<BlockGroup> blockGroups;
	
	private Block selectedBlock;
	private BlockGroup selectedBlockGroup;
	
	private ArrayList<Worker> workers;
	private ArrayList<Soldier> soldiers;
	private ArrayList<Grunt> grunts;
	private MainCharacter mainChar;
	private Ent summon;
	private Spawner enemySpawn1;
	private Spawner enemySpawn2;
	private int worldSize = 1000;
	private int dayCounter;
	private boolean startDay;
	private long startDayMilli;
	private boolean startNight;
	private long startNightMilli;
	
	public World(){
		bottomBlocks = new ArrayList<Block>();
		blockGroups = new ArrayList<BlockGroup>();
		
		summon = new Ent();
		summon.setName("summon");
		summon.setImg(Aosa.getGlobal().getImgByName("summon"));
		summon.setPosBox(new Rectangle());
		
		enemySpawn1 = new Spawner();
		enemySpawn1.setName("enemySpawn1");
		enemySpawn1.setImg(Aosa.getGlobal().getImgByName("enemyspawn1"));
		enemySpawn1.setPosBox(new Rectangle(0,0,
				enemySpawn1.getImg().getTex().getWidth(),
				enemySpawn1.getImg().getTex().getHeight()));
		enemySpawn1.setEnemy(true);
		enemySpawn1.setSpawnPos(new Rectangle(enemySpawn1.getImg().getTex().getWidth(),
				80+32,
				enemySpawn1.getImg().getTex().getWidth(),
				enemySpawn1.getImg().getTex().getHeight()));
		
		enemySpawn2 = new Spawner();
		enemySpawn2.setName("enemySpawn2");
		enemySpawn2.setImg(Aosa.getGlobal().getImgByName("enemyspawn2"));
		float rightblockX = (worldSize * 32 - 650 );
		enemySpawn2.setPosBox(new Rectangle(rightblockX-enemySpawn2.getImg().getTex().getWidth(),
				0,
				enemySpawn2.getImg().getTex().getWidth(),
				enemySpawn2.getImg().getTex().getHeight()));
		enemySpawn2.setEnemy(true);
		enemySpawn2.setSpawnPos(new Rectangle(rightblockX-enemySpawn2.getImg().getTex().getWidth() - 32,
				80+32,
				enemySpawn2.getImg().getTex().getWidth(),
				enemySpawn2.getImg().getTex().getHeight()));
		
		setupCharacters();
		WorldGeneratorThread wgt = new WorldGeneratorThread("thread1");
		Aosa.getGlobal().setThread(wgt);
		wgt.start();
		
		startDayMilli = System.currentTimeMillis();
	}
	
	public void update(){
		updateFollowers();
		
		for(Worker w : workers){
			w.update();
		}
		
		for(Grunt g : grunts){
			g.update();
		}
		
		for(BlockGroup grp : blockGroups){
			grp.update();
		}
		
		for(Soldier s : soldiers){
			s.update();
		}
		
		enemySpawn1.update();
		enemySpawn2.update();
		
		if (startDay){
			updateStartDay();
		}
		if (startNight){
			updateStartNight();
		}
		//if spawner isn't set yet, make sure we do first day init
		if (spawner == null){
			startDay = true;
			updateStartDay();
		}
		
		spawnEnemies();
		
		updateDeaths();
	}
	
	public void render(SpriteBatch batch){
		for(Block block : bottomBlocks){
			block.render(batch);
		}
		for(BlockGroup grp : blockGroups){
			grp.render(batch);
		}
		
		for(Worker w : workers){
			w.render(batch);
		}
		for(Soldier s : soldiers){
			s.render(batch);
		}
		for(Grunt g : grunts){
			g.render(batch);
		}
		mainChar.render(batch);
		enemySpawn1.render(batch);
		enemySpawn2.render(batch);
		summon.render(batch);
	}
	
	
	private int spawnCounter;
	private Spawner spawner;
	private boolean spawnedEnemies;
	
	public void updateStartDay(){
		startDay = false;
		startDayMilli = System.currentTimeMillis();
		dayCounter++;
		spawnCounter = 0;

		if (new Random().nextBoolean()){
			spawner = enemySpawn1;
		}
		else{
			spawner = enemySpawn2;
		}
		
		spawnedEnemies = false;
		
		//spawnEnemies();
	}
	
	public void updateStartNight(){
		startNight = false;
		startNightMilli = System.currentTimeMillis();
	}
	
	public void spawnEnemies(){
		//Spawn enemies a few seconds after the day starts
		if (!spawnedEnemies && System.currentTimeMillis() > startDayMilli+10000){
			spawnCounter = dayCounter;
			for(int i=0; i<spawnCounter; i++){
				Grunt g = new Grunt();
				g.setName("Grunt");
				g.setPosBox(new Rectangle(spawner.getSpawnPos().getX() + (i*16),
						spawner.getSpawnPos().getY(),
						g.getImg().getTex().getWidth(),
						g.getImg().getTex().getHeight()));
				if (spawner.getName().contains("1")) g.setChargingRight(true);
				else g.setChargingRight(false);
				this.grunts.add(g);
			}
			System.out.println("Spawned Enemies "+spawner.getName());
			spawnedEnemies = true;
		}
	}
	
	public void updateDeaths(){
		//Check worker hp
		for(int i=workers.size()-1; i>=0; i--){
			Worker w = workers.get(i);
			if (w.getHp() <= 0){
				for(Grunt g : grunts){
					if (w.equals(g.getTargetWorker())){
						g.setTargetWorker(null);
					}
				}
				workers.remove(w);
			}
		}
		
		//Check soldier hp
		for(int i=soldiers.size()-1; i>=0; i--){
			Soldier s = soldiers.get(i);
			if (s.getHp() <= 0){
				for(Grunt g : grunts){
					if (s.equals(g.getTargetSoldier())){
						g.setTargetSoldier(null);
					}
				}
				soldiers.remove(s);
			}
		}
		
		//Check player hp
		
		//Check building worker, soldier, and building hp
		for(BlockGroup bg : blockGroups){
			if (bg.getSoldiers().size() > 0){
				for(int i=bg.getSoldiers().size()-1; i>=0; i--){
					Soldier s = bg.getSoldiers().get(i);
					if (s.getHp() <= 0){
						for(Grunt g : grunts){
							if (s.equals(g.getTargetSoldier())){
								g.setTargetSoldier(null);
							}
						}
						bg.getSoldiers().remove(s);
					}
				}
			}
			if (bg.getWorker() != null){
				if (bg.getWorker().getHp() <= 0){
					for(Grunt g : grunts){
						if (bg.getWorker().equals(g.getTargetWorker())){
							g.setTargetWorker(null);
						}
					}
					bg.setWorker(null);
				}
			}
			if (bg.getBuilding() != null){
				if (bg.getBuilding().getHp() <= 0){
					for(Grunt g : grunts){
						if (bg.equals(g.getTargetBuilding())){
							g.setTargetBuilding(null);
						}
					}
					bg.setBuilding(null);
				}
			}
			for(Block b : bg.getBlocks()){
				if (b.getWorker() != null){
					if (b.getWorker().getHp() <= 0){
						for(Grunt g : grunts){
							if (b.getWorker().equals(g.getTargetWorker())){
								g.setTargetWorker(null);
							}
						}
						b.setWorker(null);
					}
				}
			}
		}
		
		//Check grunts hp
		for(int i=grunts.size()-1; i>=0; i--){
			Grunt g = grunts.get(i);
			if (g.getHp() <= 0) grunts.remove(g);
		}
	}
	
	public void updateFollowers(){
		float mainCharX = (mainChar.getPosBox().getX()-(mainChar.getImg().getTex().getWidth()/2));
		int numLeft=0;
		int numRight=0;
		for(Soldier s : soldiers){
			if (s.getCurrentState() == STATE.FOLLOWING){
				if (s.getPosBox().getX() < mainCharX) numLeft++;
				if (s.getPosBox().getX() > mainCharX) numRight++;
				
				float speed = s.getWalkSpeed();
				
				if (s.getPosBox().getX() < (mainCharX-64)-(numLeft*32)){
					s.getPosBox().setX(s.getPosBox().getX() + speed);
				}
				if (s.getPosBox().getX() > (mainCharX+64)+(numRight*32)){
					s.getPosBox().setX(s.getPosBox().getX() - speed);
				}
			}
		}
		for(Worker w : workers){
			if (w.getCurrentState() == STATE.FOLLOWING){
				if (w.getPosBox().getX() < mainCharX) numLeft++;
				if (w.getPosBox().getX() > mainCharX) numRight++;
				
				float speed = w.getWalkSpeed();
				
				if (w.getPosBox().getX() < (mainCharX-64)-(numLeft*32)){
					w.getPosBox().setX(w.getPosBox().getX() + speed);
				}
				if (w.getPosBox().getX() > (mainCharX+64)+(numRight*32)){
					w.getPosBox().setX(w.getPosBox().getX() - speed);
				}
			}
		}
	}
	
	public void setupCharacters(){
		mainChar = new MainCharacter();
		mainChar.setName("Main");
		mainChar.setImg(Aosa.getGlobal().getImgByName("mainchar"));
		mainChar.setPosBox(new Rectangle(32*(worldSize/2)+64,
				80+32,
				mainChar.getImg().getTex().getWidth(),
				mainChar.getImg().getTex().getHeight()));
		
		workers = new ArrayList<Worker>();
		soldiers = new ArrayList<Soldier>();
		grunts = new ArrayList<Grunt>();
		
		for(int i=1; i<4; i++){
			Worker w = new Worker();
			w.setName("Worker");
			w.setId(i);
			w.setImg(Aosa.getGlobal().getImgByName("worker"));
			w.setPosBox(new Rectangle(32*(worldSize/2)+64 + 32 + (i*w.getImg().getTex().getWidth()),
					80+32,
					w.getImg().getTex().getWidth(),
					w.getImg().getTex().getHeight()));
			workers.add(w);
		}
	}
	
	public void setupWorld(){
		procedurallyGenerateWorld();
	}
	
	public void procedurallyGenerateWorld(){
		int blockGrpSize = 10;
		//Setup fulldirt baseblocks
		for(int y=1; y<4; y++){
			for(int x=0; x<worldSize; x++){
				Block block = new Block();
				block.setName("block");
				block.setType("bottom");
				block.setBlockName("Dirt");
				block.setImg(Aosa.getGlobal().getImgByName("fulldirt"));
				block.setPosBox(new Rectangle(x*block.getImg().getTex().getWidth(),
						80-(block.getImg().getTex().getHeight()*y),
						block.getImg().getTex().getWidth(),
						block.getImg().getTex().getHeight()));
				bottomBlocks.add(block);
			}
		}
		
		Base base = new Base();
		base.setBuildProgress(0);
		Mine mine = new Mine();
		mine.setBuildProgress(0);
		Refinery refinery = new Refinery();
		refinery.setBuildProgress(0);
		
		//Build baseBlocks
		int groupIdCounter=0;
		for(int x=0; x<worldSize; x+=blockGrpSize){
			//Determine if we are at the main base location
			boolean mainBase=false;
			boolean mainGas = false;
			boolean mainMineral = false;
			if (x==worldSize/2){
				mainBase = true;
			}
			if (x==(worldSize/2)-blockGrpSize){
				mainGas=true;
			}
			if (x==(worldSize/2)+blockGrpSize){
				mainMineral=true;
			}
			//Determine what type this set of blocks is going to be
			boolean buildable = false;
			int buildSize = 0;
			boolean mineral = false;
			boolean gas = false;
			Random rand = new Random();
			int randInt = rand.nextInt(100);
			//Buildable
			if (randInt > 5){
				buildable = true;
				int randInt2 = rand.nextInt(100);
				if (randInt2 > 50)	buildSize = 2;
				else if (randInt2 > 10) buildSize = 4;
				else buildSize = 8;
				if (buildSize == 2){
					if (rand.nextInt(100) > 35){
						if(rand.nextBoolean() && rand.nextBoolean())
							gas = true;
						else
							mineral = true;
					}
				}
			}
			//Setup the block group data
			BlockGroup grp = new BlockGroup();
			grp.setId(groupIdCounter);
			groupIdCounter++;
			if (mainBase){
				grp.setBuildable(true);
				grp.setBuilding(base);
				buildSize = 8;
			}
			else if (mainGas){
				grp.setBuildable(true);
				grp.setGasAmt(1000);
				grp.setBuilding(refinery);
				buildSize = 2;
			}
			else if (mainMineral){
				grp.setBuildable(true);
				grp.setMineralAmt(1000);
				grp.setBuilding(mine);
				buildSize = 2;
			}
			else if (mineral){
				grp.setMineralAmt(1000);
				grp.setBuildable(true);
				//block.setMineral();
			}
			else if (gas){
				grp.setGasAmt(1000);
				grp.setBuildable(true);
				//block.setGas();
			}
			else if (buildable){
				grp.setBuildable(true);
			}
			
			//Populate block group with blocks
			int buildSizeCounter = 0;
			float buildSizeStartX = 0;
			if (grp.isBuildable()){
				float blockGroupStartX = (x*32);
				float buildSizeAddition = (((10-buildSize)*32)/2);
				buildSizeStartX = blockGroupStartX + buildSizeAddition;
			}
			for(int i=0; i<blockGrpSize; i++){
				Block block = new Block();
				block.setName("block");
				block.setId(groupIdCounter);
				block.setType("base");
				block.setBlockName("Grass");
				block.setImg(Aosa.getGlobal().getImgByName("grass"));
				block.setPosBox(new Rectangle((x+i)*block.getImg().getTex().getWidth(),
						80,
						block.getImg().getTex().getWidth(),
						block.getImg().getTex().getHeight()));
				if(mainBase){
					if (buildSizeCounter<buildSize && block.getPosBox().getX() >= buildSizeStartX){
						if (grp.getBuilding().getPosBox().getX()==0){
							grp.getBuilding().setPosBox(new Rectangle(
									block.getPosBox().getX(),
									80+32,
									grp.getBuilding().getImg().getTex().getWidth(),
									grp.getBuilding().getImg().getTex().getHeight()));
						}
						block.setBlockBuildable();
						buildSizeCounter++;
						if (block.getPosBox().getX() >= 32*(worldSize/2)+64 && selectedBlock == null){
							selectedBlock = block;
						}
					}
				}
				else if (mainGas){
					if (buildSizeCounter<buildSize  && block.getPosBox().getX() >= buildSizeStartX){
						if (grp.getBuilding().getPosBox().getX()==0){
							grp.getBuilding().setPosBox(new Rectangle(
									block.getPosBox().getX(),
									80+32,
									grp.getBuilding().getImg().getTex().getWidth(),
									grp.getBuilding().getImg().getTex().getHeight()));
						}
						block.setBlockBuildable();
						block.setGas();
						block.setOverlandImg(null);
						buildSizeCounter++;
					}
				}
				else if (mainMineral){
					if (buildSizeCounter<buildSize  && block.getPosBox().getX() >= buildSizeStartX){
						if (grp.getBuilding().getPosBox().getX()==0){
							grp.getBuilding().setPosBox(new Rectangle(
									block.getPosBox().getX(),
									80+32,
									grp.getBuilding().getImg().getTex().getWidth(),
									grp.getBuilding().getImg().getTex().getHeight()));
						}
						block.setBlockBuildable();
						block.setMineral();
						block.setOverlandImg(null);
						buildSizeCounter++;
					}
				}
				else if (buildable){
					if (buildSizeCounter<buildSize  && block.getPosBox().getX() >= buildSizeStartX){
						block.setBlockBuildable();
						if (mineral) block.setMineral();
						if (gas) block.setGas();
						buildSizeCounter++;
					}
					if (rand.nextBoolean() && !mineral && !gas){
						block.setTree();
					}
				}
				else{
					if (rand.nextBoolean() && !mineral && !gas){
						block.setTree();
					}
				}
				
				grp.getBlocks().add(block);
			}
			selectedBlockGroup = grp;
			blockGroups.add(grp);
		}
		
		Aosa.getGlobal().getCamera().position.x = 32*(worldSize/2)+64;
		//End of map
		//Aosa.getGlobal().getCamera().translate((32*(baseBlocks.size())), 0, 0);
	}
	
	public void setSelectedBlock(){
		Rectangle charPos = new Rectangle(mainChar.getPosBox().getX()+(mainChar.getImg().getTex().getWidth()/2),80+16,1,1);
		boolean foundSelected = false;
		for(BlockGroup grp : blockGroups){
			for (Block b : grp.getBlocks()){
				if (b.getPosBox().overlaps(charPos)){
					selectedBlock = b;
					foundSelected = true;
					break;
				}
			}
			if (foundSelected){
				selectedBlockGroup = grp;
				break;
			}
		}
	}
	
	public Worker getAvailableWorker(){
		for(Worker w : workers){
			if (w.getCurrentState() == STATE.FOLLOWING){
				return w;
			}
		}
		return null;
	}
	
	public Soldier getAvailableSoldier(){
		for(Soldier s : soldiers){
			if (s.getCurrentState() == STATE.FOLLOWING){
				return s;
			}
		}
		return null;
	}

	public ArrayList<Block> getBottomBlocks() {
		return bottomBlocks;
	}

	public void setBottomBlocks(ArrayList<Block> bottomBlocks) {
		this.bottomBlocks = bottomBlocks;
	}
	
	public ArrayList<BlockGroup> getBlockGroups() {
		return blockGroups;
	}

	public void setBlockGroups(ArrayList<BlockGroup> blockGroups) {
		this.blockGroups = blockGroups;
	}

	public ArrayList<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(ArrayList<Worker> workers) {
		this.workers = workers;
	}

	public ArrayList<Grunt> getGrunts() {
		return grunts;
	}

	public void setGrunts(ArrayList<Grunt> grunts) {
		this.grunts = grunts;
	}

	public MainCharacter getMainChar() {
		return mainChar;
	}

	public void setMainChar(MainCharacter mainChar) {
		this.mainChar = mainChar;
	}

	public Ent getSummon() {
		return summon;
	}

	public void setSummon(Ent summon) {
		this.summon = summon;
	}

	public Block getSelectedBlock() {
		return selectedBlock;
	}

	public void setSelectedBlock(Block selectedBlock) {
		this.selectedBlock = selectedBlock;
	}

	public BlockGroup getSelectedBlockGroup() {
		return selectedBlockGroup;
	}

	public void setSelectedBlockGroup(BlockGroup selectedBlockGroup) {
		this.selectedBlockGroup = selectedBlockGroup;
	}

	public ArrayList<Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(ArrayList<Soldier> soldiers) {
		this.soldiers = soldiers;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}

	public Spawner getEnemySpawn1() {
		return enemySpawn1;
	}

	public void setEnemySpawn1(Spawner enemySpawn1) {
		this.enemySpawn1 = enemySpawn1;
	}

	public Spawner getEnemySpawn2() {
		return enemySpawn2;
	}

	public void setEnemySpawn2(Spawner enemySpawn2) {
		this.enemySpawn2 = enemySpawn2;
	}

	public int getDayCounter() {
		return dayCounter;
	}

	public void setDayCounter(int dayCounter) {
		this.dayCounter = dayCounter;
	}

	public boolean isStartDay() {
		return startDay;
	}

	public void setStartDay(boolean startDay) {
		this.startDay = startDay;
	}

	public boolean isStartNight() {
		return startNight;
	}

	public void setStartNight(boolean startNight) {
		this.startNight = startNight;
	}

	public long getStartDayMilli() {
		return startDayMilli;
	}

	public void setStartDayMilli(long startDayMilli) {
		this.startDayMilli = startDayMilli;
	}

	public long getStartNightMilli() {
		return startNightMilli;
	}

	public void setStartNightMilli(long startNightMilli) {
		this.startNightMilli = startNightMilli;
	}
	
}
