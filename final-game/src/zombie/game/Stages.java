package zombie.game;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import zombie.game.objects.ZombieEnum;

public class Stages {
	
	private int stageCounter; //stage counter for iteration purposes
	
	private int waitCounter = 0; //wait counter for iteration purposes
	
	private int stage; //stores the stage number
	
	private int spawnVar; //iteration number affecting frequency of zombie respawns
		
	private boolean wait; //determines whether or not to wait until next stage 
	
	private boolean newStage;//determines when to go to next stage 
	
	private Random random = new Random(); //random variable used to create random numbers
	
	Map<Tuple,Double> probMap = new TreeMap<Tuple,Double>();//stores scores to obtain high scores
	
	private int stageLength = 1000; //iteration number affecting length of stage
	
	private int waitLength = 200; //iteration number affecting length of wait time 
	
	private boolean end = false; //determines if game ended 

	//stage constructors that sets necessary fields 
	public Stages() {
		
		stage = 1;
		stageCounter = 0;
		waitCounter = 0;
		spawnVar = 100;
		wait = false;
		newStage = false;
		
		/*---------------------------------------------------------
		 *  Probabilities of different types of Zombies spawning 
		 *  based on stages of the game
		 *---------------------------------------------------------*/
		
		//stage 1
		probMap.put(new Tuple(ZombieEnum.NORMAL, 1),1.0);
		probMap.put(new Tuple(ZombieEnum.FAST, 1),0.0);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 1),0.0);

		//stage 2
		probMap.put(new Tuple(ZombieEnum.NORMAL, 2),0.8);
		probMap.put(new Tuple(ZombieEnum.FAST, 2),0.2);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 2),0.0);
		
		//stage 3
		probMap.put(new Tuple(ZombieEnum.NORMAL, 3),0.7);
		probMap.put(new Tuple(ZombieEnum.FAST, 3),0.3);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 3),0.0);
		
		//stage 4
		probMap.put(new Tuple(ZombieEnum.NORMAL, 4),0.5);
		probMap.put(new Tuple(ZombieEnum.FAST, 4),0.2);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 4),0.3);
		
		//stage 5
		probMap.put(new Tuple(ZombieEnum.NORMAL, 5),0.5);
		probMap.put(new Tuple(ZombieEnum.FAST, 5),0.4);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 5),0.1);
		
		//stage 6
		probMap.put(new Tuple(ZombieEnum.NORMAL, 6),0.4);
		probMap.put(new Tuple(ZombieEnum.FAST, 6),0.35);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 6),0.25);

		//stage 7
		probMap.put(new Tuple(ZombieEnum.NORMAL, 7),0.3);
		probMap.put(new Tuple(ZombieEnum.FAST, 7),0.45);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 7),0.25);
		
		//stage 8
		probMap.put(new Tuple(ZombieEnum.NORMAL, 8),0.2);
		probMap.put(new Tuple(ZombieEnum.FAST, 8),0.50);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 8),0.3);
		
		//stage 9
		probMap.put(new Tuple(ZombieEnum.NORMAL, 9),0.2);
		probMap.put(new Tuple(ZombieEnum.FAST, 9),0.45);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 9),0.35);

		//stage 10
		probMap.put(new Tuple(ZombieEnum.NORMAL, 10),0.1);
		probMap.put(new Tuple(ZombieEnum.FAST, 10),0.50);
		//probMap.put(new Tuple(ZombieEnum.ARMORED, 10),0.40);
		
		//stage 11
		probMap.put(new Tuple(ZombieEnum.NORMAL, 11),0.1);
		probMap.put(new Tuple(ZombieEnum.FAST, 11),0.30);
		
		//stage 12
		probMap.put(new Tuple(ZombieEnum.NORMAL, 12),0.0);
		probMap.put(new Tuple(ZombieEnum.FAST, 12),0.30);
		
		//stage 13
		probMap.put(new Tuple(ZombieEnum.NORMAL, 13),0.2);
		probMap.put(new Tuple(ZombieEnum.FAST, 13),0.30);
	}
		
	//updates continuously in the Game class
	public void update() {
		
		//determines if it's ready to change stage
		if(stageCounter > stageLength) {
			stageCounter = 0;
			newStage = true;
			wait = true;
		} 
		/*
		 * if ready, start waiting till zombies are killed, otherwise increment stage 
		 * counter until it's ready
		 */
		else { 
			stageCounter++;
			if(newStage && !Game.areZombiesLeft()) {
				waitCounter++;
				if(stage == 13) {
					waitCounter += 10000;
				}
			}
			//wait for specified time 
			if(waitCounter > waitLength) {
				wait = !wait;
				waitCounter = 0;
				newStage = false;
				if(stage < 13) {
					stage++;
				} else {
					end = true;
				}
			}
		}
		
		//change spawnVar depending on stage of the game 
		switch (stage) {
		
		case 1: spawnVar = 95; break;
		case 2: spawnVar = 70; break;
		case 3: spawnVar = 55; break;
		case 4: spawnVar = 40; break;
		case 5: spawnVar = 30; break;
		case 6: spawnVar = 25; break;
		case 7: spawnVar = 20; break;
		case 8: spawnVar = 15; break;
		case 9: spawnVar = 13; break;
		case 10: spawnVar = 11; break;
		case 11: spawnVar = 10; break;
		case 12: spawnVar = 9;
		default: spawnVar = 8; break;
		
		}
	}
		
	//determines whether or not to wait 
	public boolean boolWait() {
		if (stage != 13) {
			return wait;
		} else {
			return false;
		}
		
	}
	
	public boolean isEnd() {
		return end;
	}
	
    /*------------------------------------------
     *  self-explanatory getter methods 
     *------------------------------------------*/
	public int getStage() {return stage;}
	
	public int getSpawnVar() {return spawnVar;}
	
	public int getType(int stage) {
		Tuple tupleNormal = new Tuple(ZombieEnum.NORMAL, stage);
		Tuple tupleFast = new Tuple(ZombieEnum.FAST, stage);
		//Tuple tupleArmored = new Tuple(ZombieEnum.ARMORED, stage);
		
		double normalProb = probMap.get(tupleNormal);
		double fastProb = probMap.get(tupleFast);
		//double armoredProb = probMap.get(tupleArmored);
		
		double rand = random.nextDouble();
		if (rand < normalProb) {
		  return 0;
		} else if (rand < normalProb + fastProb) {
		  return 1;
		} else {
		  return 2;
		}
	}
	
}
