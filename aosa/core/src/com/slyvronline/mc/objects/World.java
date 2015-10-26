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
import com.slyvronline.mc.utils.GameConstants;

public class World {
	
	private ArrayList<Block> bottomBlocks;
	private ArrayList<Block> baseBlocks;
	private ArrayList<Integer> buildableIndexes;
	private ArrayList<Integer> mineralIndexes;
	private ArrayList<Integer> gasIndexes;
	private ArrayList<Ent> buildings;
	private ArrayList<Ent> workers;
	private ArrayList<Ent> enemies;
	private Ent mainChar;
	
	public World(){
		bottomBlocks = new ArrayList<Block>();
		baseBlocks = new ArrayList<Block>();
		buildableIndexes = new ArrayList<Integer>();
		mineralIndexes = new ArrayList<Integer>();
		gasIndexes = new ArrayList<Integer>();
		buildings = new ArrayList<Ent>();
		workers = new ArrayList<Ent>();
		enemies = new ArrayList<Ent>();
		mainChar = new Ent();
		WorldGeneratorThread wgt = new WorldGeneratorThread("thread1");
		Aosa.getGlobal().setThread(wgt);
		wgt.start();
	}
	
	public void update(){
		
	}
	
	public void render(SpriteBatch batch){
		for(Block block : bottomBlocks){
			block.render(batch);
		}
		for(Block block : baseBlocks){
			block.render(batch);
		}
	}
	
	public void setupWorld(){
		int worldSize = 2000;
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
		Mine mine = new Mine();
		Refinery refinery = new Refinery();
		
		//Build baseBlocks
		for(int x=0; x<worldSize; x+=8){
			//Determine if we are at the main base location
			boolean mainBase=false;
			boolean mainGas = false;
			boolean mainMineral = false;
			if (x==worldSize/2){
				mainBase = true;
			}
			if (x==(worldSize/2)-8){
				mainGas=true;
			}
			if (x==(worldSize/2)+8){
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
			if (randInt > 40){
				buildable = true;
				if (rand.nextBoolean())	buildSize = 2;
				else if (rand.nextBoolean()) buildSize = 4;
				else buildSize = 8;
				if (buildSize == 2){
					if(rand.nextBoolean())
						mineral = true;
					else
						gas = true;
				}
			}
			
			int buildSizeCounter = 0;
			boolean buildingSet = false;
			for(int i=0; i<8; i++){
				Block block = new Block();
				block.setName("block");
				block.setType("base");
				block.setBlockName("Grass");
				block.setBuildable(false);
				block.setBuilding(null);
				block.setDismantlable(false);
				block.setOverlandImg(null);
				block.setImg(Aosa.getGlobal().getImgByName("grass"));
				block.setPosBox(new Rectangle((x+i)*block.getImg().getTex().getWidth(),
						80,
						block.getImg().getTex().getWidth(),
						block.getImg().getTex().getHeight()));
				baseBlocks.add(block);
				if (mainBase){
					block.setBlockBuildable();
					block.setBuilding(base);
					if (!buildingSet){
						block.setOverlandImg(Aosa.getGlobal().getImgByName("base"));
						buildingSet = true;
					}
				}
				else if (mainGas){
					if (buildSizeCounter < 2){
						block.setBlockBuildable();
						block.setBuilding(refinery);
						buildSizeCounter++;
					}
					if (!buildingSet){
						block.setOverlandImg(Aosa.getGlobal().getImgByName("refinery"));
						buildingSet = true;
					}
				}
				else if (mainMineral){
					if (buildSizeCounter >= 6){
						block.setBlockBuildable();
						block.setBuilding(mine);
						if (!buildingSet){
							block.setOverlandImg(Aosa.getGlobal().getImgByName("mine"));
							buildingSet = true;
						}
					}
					buildSizeCounter++;
				}
				else if (buildable){
					if (buildSizeCounter<buildSize){
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
			}
		}
		
		for(int i=0; i<baseBlocks.size(); i++){
			Block b = baseBlocks.get(i);
			if (b.isBuildable()){
				buildableIndexes.add(i);
			}
			if (b.getMineralAmt() > 0){
				mineralIndexes.add(i);
			}
			if (b.getGasAmt() > 0){
				gasIndexes.add(i);
			}
		}
		
		Aosa.getGlobal().getCamera().position.x = 32*(baseBlocks.size()/2)+64;
		//End of map
		//Aosa.getGlobal().getCamera().translate((32*(baseBlocks.size())), 0, 0);
	}
	
	public ArrayList<Block> getBaseBlocks() {
		return baseBlocks;
	}

	public void setBaseBlocks(ArrayList<Block> baseBlocks) {
		this.baseBlocks = baseBlocks;
	}

	public ArrayList<Ent> getBuildings() {
		return buildings;
	}

	public void setBuildings(ArrayList<Ent> buildings) {
		this.buildings = buildings;
	}

	public ArrayList<Ent> getWorkers() {
		return workers;
	}

	public void setWorkers(ArrayList<Ent> workers) {
		this.workers = workers;
	}

	public ArrayList<Ent> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Ent> enemies) {
		this.enemies = enemies;
	}

	public Ent getMainChar() {
		return mainChar;
	}

	public void setMainChar(Ent mainChar) {
		this.mainChar = mainChar;
	}
}
