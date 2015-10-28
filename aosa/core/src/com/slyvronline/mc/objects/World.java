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
	
	private Block selectedBlock;
	private BlockGroup selectedBlockGroup;
	
	private ArrayList<Worker> workers;
	private ArrayList<Grunt> grunts;
	private MainCharacter mainChar;
	private Ent summon;
	
	public World(){
		bottomBlocks = new ArrayList<Block>();
		blockGroups = new ArrayList<BlockGroup>();
		
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
		
		for(BlockGroup grp : blockGroups){
			grp.update();
		}
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
		Mine mine = new Mine();
		Refinery refinery = new Refinery();
		
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
			if (randInt > 20){
				buildable = true;
				int randInt2 = rand.nextInt(100);
				if (randInt2 > 60)	buildSize = 2;
				else if (randInt2 > 10) buildSize = 4;
				else buildSize = 8;
				if (buildSize == 2){
					if (rand.nextInt(100) > 50){
						if(rand.nextBoolean())
							mineral = true;
						else
							gas = true;
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
			if (w.isSelected()){
				return w;
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
	
}
