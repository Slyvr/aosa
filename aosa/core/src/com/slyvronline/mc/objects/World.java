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
import com.slyvronline.mc.objects.characters.Grunt;
import com.slyvronline.mc.objects.characters.MainCharacter;
import com.slyvronline.mc.objects.characters.Worker;
import com.slyvronline.mc.utils.GameConstants;

public class World {
	
	private ArrayList<Block> bottomBlocks;
	private ArrayList<BlockGroup> blockGroups;
	private ArrayList<Block> baseBlocks;
	private ArrayList<Integer> buildableIndexes;
	private ArrayList<Integer> mineralIndexes;
	private ArrayList<Integer> gasIndexes;
	private ArrayList<Integer> dismantleIndexes;
	
	private Block selectedBlock;
	
	private ArrayList<Worker> workers;
	private ArrayList<Grunt> grunts;
	private MainCharacter mainChar;
	private Ent summon;
	
	public World(){
		bottomBlocks = new ArrayList<Block>();
		baseBlocks = new ArrayList<Block>();
		buildableIndexes = new ArrayList<Integer>();
		mineralIndexes = new ArrayList<Integer>();
		gasIndexes = new ArrayList<Integer>();
		dismantleIndexes = new ArrayList<Integer>();
		
		summon = new Ent();
		summon.setName("summon");
		summon.setImg(Aosa.getGlobal().getImgByName("summon"));
		summon.setPosBox(new Rectangle());
		
		setupCharacters();
		WorldGeneratorThread wgt = new WorldGeneratorThread("thread1");
		Aosa.getGlobal().setThread(wgt);
		wgt.start();
	}
	
	public void update(){
		updateFollowers();
		
		for(Worker w : workers){
			w.update();
		}
		
		for(Block b : baseBlocks){
			b.update();
		}
	}
	
	public void render(SpriteBatch batch){
		for(Block block : bottomBlocks){
			block.render(batch);
		}
		for(Block block : baseBlocks){
			block.render(batch);
		}
		
		for(Worker w : workers){
			w.render(batch);
		}
		for(Grunt g : grunts){
			g.render(batch);
		}
		mainChar.render(batch);
		summon.render(batch);
	}
	
	public void updateFollowers(){
		float mainCharX = (mainChar.getPosBox().getX()-(mainChar.getImg().getTex().getWidth()/2));
		int numLeft=0;
		int numRight=0;
		for(Worker w : workers){
			if (w.isSelected()){
				if (w.getPosBox().getX() < mainCharX) numLeft++;
				if (w.getPosBox().getX() > mainCharX) numRight++;
				
				if (w.getPosBox().getX() < (mainCharX-64)-(numLeft*32)){
					w.getPosBox().setX(w.getPosBox().getX() + w.getWalkSpeed());
				}
				if (w.getPosBox().getX() > (mainCharX+64)+(numRight*32)){
					w.getPosBox().setX(w.getPosBox().getX() - w.getWalkSpeed());
				}
			}
		}
	}
	
	public void setupCharacters(){
		mainChar = new MainCharacter();
		mainChar.setName("Main");
		mainChar.setImg(Aosa.getGlobal().getImgByName("mainchar"));
		mainChar.setPosBox(new Rectangle(32*(1000)+64,
				80+32,
				mainChar.getImg().getTex().getWidth(),
				mainChar.getImg().getTex().getHeight()));
		
		workers = new ArrayList<Worker>();
		grunts = new ArrayList<Grunt>();
		
		for(int i=1; i<4; i++){
			Worker w = new Worker();
			w.setName("Worker");
			w.setId(i);
			w.setImg(Aosa.getGlobal().getImgByName("worker"));
			w.setPosBox(new Rectangle(32*(1000)+64 + 32 + (i*w.getImg().getTex().getWidth()),
					80+32,
					w.getImg().getTex().getWidth(),
					w.getImg().getTex().getHeight()));
			workers.add(w);
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
						block.setGasAmt(1000);
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
						block.setMineralAmt(1000);
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
			if (b.getDismantleAmt() > 0){
				dismantleIndexes.add(i);
			}
		}
		
		Aosa.getGlobal().getCamera().position.x = 32*(baseBlocks.size()/2)+64;
		//End of map
		//Aosa.getGlobal().getCamera().translate((32*(baseBlocks.size())), 0, 0);
	}
	
	public void setSelectedBlock(){
		Rectangle charPos = new Rectangle(mainChar.getPosBox().getX()+(mainChar.getImg().getTex().getWidth()/2),80+16,1,1);
		for(Block b : baseBlocks){
			if (b.getPosBox().overlaps(charPos)){
				selectedBlock = b;
				break;
			}
		}
	}
	
	public ArrayList<Block> getBaseBlocks() {
		return baseBlocks;
	}
	public void setBaseBlocks(ArrayList<Block> baseBlocks) {
		this.baseBlocks = baseBlocks;
	}

	public ArrayList<Block> getBottomBlocks() {
		return bottomBlocks;
	}

	public void setBottomBlocks(ArrayList<Block> bottomBlocks) {
		this.bottomBlocks = bottomBlocks;
	}

	public ArrayList<Integer> getBuildableIndexes() {
		return buildableIndexes;
	}

	public void setBuildableIndexes(ArrayList<Integer> buildableIndexes) {
		this.buildableIndexes = buildableIndexes;
	}

	public ArrayList<Integer> getMineralIndexes() {
		return mineralIndexes;
	}

	public void setMineralIndexes(ArrayList<Integer> mineralIndexes) {
		this.mineralIndexes = mineralIndexes;
	}

	public ArrayList<Integer> getGasIndexes() {
		return gasIndexes;
	}

	public void setGasIndexes(ArrayList<Integer> gasIndexes) {
		this.gasIndexes = gasIndexes;
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

	public ArrayList<Integer> getDismantleIndexes() {
		return dismantleIndexes;
	}

	public void setDismantleIndexes(ArrayList<Integer> dismantleIndexes) {
		this.dismantleIndexes = dismantleIndexes;
	}
	
}
