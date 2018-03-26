package zombie.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import zombie.game.input.KeyInput;
import zombie.game.objects.ArmoredZombie;
import zombie.game.objects.Arrow;
import zombie.game.objects.Barricade;
import zombie.game.objects.BarricadeSeparator;
import zombie.game.objects.FastZombie;
import zombie.game.objects.NormalZombie;
import zombie.game.objects.Player;
import zombie.game.objects.Weapon;
import zombie.game.objects.WeaponEnum;
import zombie.game.objects.Zombie;
import zombie.game.objects.ZombieEnum;

public class Game extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private static int frameHeight; //height of game screen
	private static int frameWidth; //width of game screen
	

/*---------------------------------------------------------
 *  Here are all the fields that will be used while running
 *  the game.
 *---------------------------------------------------------*/
	
	//master-game loop
	Timer gameLoopTimer;
	
	private static boolean isPaused;
	
	//Player object (non-static to allow multiplayer)
	Player p;
	
	//Stage object (determines stage the game is at)
	Stages stage;

	
	//Barricade objects
	Barricade b1;
	Barricade b2;
	Barricade b3;
	Barricade b4;	
	
	//Barricade Separators
	BarricadeSeparator s1;
	BarricadeSeparator s2;
	BarricadeSeparator s3;
	BarricadeSeparator s4;
	BarricadeSeparator s5;

	
	//Random initializer - used in various applications such as zombie respawn
	Random random = new Random();	
	
	//respective counters that controls speed of zombie respawn, player regeneration, and attack
	static int respawnCounter = 0;
	static int regenCounter = 0;
	static int attackCounter = 100;
	static boolean canAttack = true;
	
	//boolean that determines when to end the game 
	private static boolean gameEnd;
	
	//player lives 
	private static int lives = 10; 
	
	//Used to determine the y-position of zombie
	private int yPosition = 150 * random.nextInt(4) + 0 + 50;
	
	//background image of the playing field
	private static String background = "/images/instruction_screen.png";
	
	//ArrayList that records the amount of zombies, arrows, and barricades on the gamefield.
	static ArrayList<Zombie> zombieList = new ArrayList<Zombie>();
    ArrayList<Arrow> arrowList;
	ArrayList<Barricade> barricadeList = new ArrayList<Barricade>();
	
	//needed to draw and remove arrows and zombies
	Iterator<Arrow> iterArrow;
	Iterator<Zombie> iterZombie;
		
	//Name of person to input score to file
	private String playerName;
	
	//needed to input player's name
	Scanner scan = new Scanner(System.in);
	
	//stores the score in an sorted list to grab high scores
	private LinkedList<Score> scoreList= new LinkedList<Score>();
	
	//Determines if game started
	private static boolean startGame = false;
	
	//Determines whether or not to draw instructions
	private static boolean turnInstructions = false;

	//Game constructor
	public Game() {
		
		//aligns game screen to center of computer
		setFocusable(true);
		
		//add player to respective coordinates
		p = new Player(226-75,50);
		
		//start position of barricades
		int start = 225;
		
		//add barricades
		b1 = new Barricade(start,0);
		b2 = new Barricade(start, b1.getHeight());
		b3 = new Barricade(start, b1.getHeight() + b2.getHeight());
		b4 = new Barricade(start, b1.getHeight() + b2.getHeight() + b3.getHeight());
		
		//add barricade separators
		s1 = new BarricadeSeparator(start,0);
		s2 = new BarricadeSeparator(start, b1.getHeight() - (s1.getHeight()/2));
		s3 = new BarricadeSeparator(start, b1.getHeight() + b2.getHeight() - (s2.getHeight()/2));
		s4 = new BarricadeSeparator(start,b1.getHeight() + b2.getHeight() + b3.getHeight() 
				- (s3.getHeight()/2));
		s5 = new BarricadeSeparator(start, b1.getHeight() + b2.getHeight() + b3.getHeight() + 
				b4.getHeight() - (30));
		
		//initialize barricadeList
		barricadeList.add(b1);
		barricadeList.add(b2);
		barricadeList.add(b3);
		barricadeList.add(b4);
		
		
		//add first zombie
		zombieList.add(new NormalZombie(ZombieEnum.NORMAL,950,yPosition));
		
		//initialize arrowList
		arrowList = p.getArrows();

		//add key listener to detect player inputs.
		addKeyListener(new KeyInput(p));
		
		//start gameloop 
		gameLoopTimer = new Timer(50, this);
		gameLoopTimer.start();
		
		//start timer 
		TimerDisplay.start();
		
		//initialize stage
		stage = new Stages();
		
		//reset score
		scoreList.clear();
		
	}
	
	//paint component that recursively updates the state (drawing) of the game screen as time progresses 
	public void paintComponent(Graphics g){
		//initialize the Graphics and Graphics2D
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//draw the background 
		g2d.drawImage(getBackgroundImage(), 0, 0, null);
		if(startGame && 
				!stage.isEnd()) {
			//draw the player
			p.draw(g2d);
			
			//draw barricades
			b1.draw(g2d);
			b2.draw(g2d);
			b3.draw(g2d);
			b4.draw(g2d);
			
			//draw barricade separators
			s1.draw(g2d);
			s2.draw(g2d);
			s3.draw(g2d);
			s4.draw(g2d);
			s5.draw(g2d);
			
			//draw the zombies or remove and add cash
			iterZombie = zombieList.iterator();
			while (iterZombie.hasNext()) {
			    Zombie z = iterZombie.next();
			    if (z.isVisible()) {
			    		z.draw(g2d);
			    } else {
			    		iterZombie.remove();
			    		if(z.isDead()) {
			    		p.addCash(z.getType().getKillReward());
			    		}
			    }
			}		
			
			//initialize the arrows
			arrowList = p.getArrows();
			
			//draw the arrows or remove them when touched zombie or left screen
			iterArrow = arrowList.iterator();
			while (iterArrow.hasNext()) {
			    Arrow a = iterArrow.next();
			    if (a.isVisible()) {
			    		g.drawImage(a.getArrowImage(), a.getX(), a.getY(), this);
			    } else {
			    		iterArrow.remove();
			    }
			}
	        
	        //what to draw on screen
			g.setColor(Color.GREEN);
			
			g.drawString("Timer: " + TimerDisplay.getElapsedTime(), 10, 570);
			
			/*---------------------------------------------------------
			 *  Draw this is game hasn't ended 
			 *---------------------------------------------------------*/
			
			if (!gameEnd) {
				g.drawString("Lives: " + lives, 10, 25);
				g.drawString("Cash: " + p.getCash(), 10, 50);
				
				/*---------------------------------------------------------
				 *  Draw the upgrades of the game 
				 *---------------------------------------------------------*/
				
				g.drawString("~~UPGRADES~~", 10, 100);
				
				if(p.getWeapon().getUpgradeDamage() < p.getWeapon().getMaxUpgrade()) {
					g.drawString("Damage: " + p.getWeapon().getUpgradeDamage() +"/" + 
							p.getWeapon().getMaxUpgrade() + " - $" + 
							p.getWeapon().getUpgradeDamageCost(), 10, 125);			
				}
				else {
					g.drawString("Damage: " + p.getWeapon().getUpgradeDamage() +"/" + 
							p.getWeapon().getMaxUpgrade(), 10, 125);	
				}
				
				if(p.getWeapon().getUpgradeReload() < p.getWeapon().getMaxUpgrade()) {
					g.drawString("Reload: " + p.getWeapon().getUpgradeReload() +"/" + 
							p.getWeapon().getMaxUpgrade() + " - $" + 
									p.getWeapon().getUpgradeReloadCost(), 10, 150);				
				}
				else {
					g.drawString("Reload: " + p.getWeapon().getUpgradeReload() +"/" + 
							p.getWeapon().getMaxUpgrade(), 10, 150);
				}
				
				if(p.getUpgrade() < p.getMaxUpgrade()) {
					g.drawString("Speed: " + p.getUpgrade() +"/" + 
							p.getMaxUpgrade() + "- $" + p.getUpgradeCost(), 10, 175);				
				} else {
					g.drawString("Speed: " + p.getUpgrade() +"/" + 
							p.getMaxUpgrade(), 10, 175);
				}
				
				/*---------------------------------------------------------
				 *  Draw the barricades repair/upgrade state
				 *---------------------------------------------------------*/
				
				g.drawString("~~BARRICADES~~", 10, 225);
							
				if(p.isTouching(b1)) {
					if(b1.getUpgrade() < 4) {
						g.drawString("Repair Bar. - $" + b1.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar. - $" + b1.getCost(), 10, 275);	
						g.drawString(b1.getUpgrade() + "/5", 10, 295);
					} else {
						g.drawString("Repair Bar. - $" + b1.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar.", 10, 275);						
					}
				} else if(p.isTouching(b2)) {
					if(b2.getUpgrade() < 4) {
						g.drawString("Repair Bar. - $" + b2.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar. - $" + b2.getCost(), 10, 275);		
						g.drawString(b2.getUpgrade() + "/5", 10, 295);
					} else {
						g.drawString("Repair Bar. - $" + b2.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar.", 10, 275);						
					}
				} else if(p.isTouching(b3)) {
					if(b3.getUpgrade() < 4) {
						g.drawString("Repair Bar. - $" + b3.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar. - $" + b1.getCost(), 10, 275);	
						g.drawString(b3.getUpgrade() + "/5", 10, 295);
					} else {
						g.drawString("Repair Bar. - $" + b3.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar.", 10, 275);						
					}
				} else if(p.isTouching(b4)) {
					if(b4.getUpgrade() < 4) {
						g.drawString("Repair Bar. - $" + b4.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar. - $" + b4.getCost(), 10, 275);	
						g.drawString(b4.getUpgrade() + "/5", 10, 295);
					} else {
						g.drawString("Repair Bar. - $" + b4.getRepairCost(), 10, 250);
						g.drawString("Upgrade Bar.", 10, 275);						
					}
				} else {
					g.drawString("Repair Bar.", 10, 250);		
					g.drawString("Upgrade Bar.", 10, 275);	
				}
				
				/*---------------------------------------------------------
				 *  drawing instructions
				 *---------------------------------------------------------*/
				
				if(turnInstructions) {
				g.drawString("~~INSTRUCTIONS~~", 10, 375);
				g.drawString("Upgrade Damage (D)", 10, 400);
				g.drawString("Upgrade Reload (R)", 10, 425);
				g.drawString("Upgrade Speed (S)", 10, 450);
				g.drawString("Upgrade Barricade (U)", 10, 475);
				g.drawString("Repair Barricade (E)", 10, 500);
				g.drawString("(Space) Shoot", 10, 525);
				} else {
					g.drawString("Press (I) to show/", 10, 375);
					g.drawString("hide instructions", 10, 400);
					g.drawString("Press (P) to pause", 10, 450);
				}
				
			/*---------------------------------------------------------
			 *  Start of update methods used in action performed.
			*---------------------------------------------------------*/
	
			} else if (stage.isEnd()){
				g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20)); 
				g.drawString("You Win!",  25, 50);
			}else {
				g.drawString("You lose", 25, 50);
				
				File file = new File("ScoreboardFile");
				FileReader fr;
				FileOutputStream fo;
				String line;				
				Score score;


				try {
					fr = new FileReader(file);
					fo = new FileOutputStream(file, true);
					OutputStreamWriter ow = new OutputStreamWriter(fo);
					BufferedReader br = new BufferedReader(fr);
					BufferedWriter bw = new BufferedWriter(ow);
					bw.flush();
					
					System.out.print("Player Name: ");
					playerName = scan.nextLine();
					
					bw.write("\n" + playerName + "," + TimerDisplay.getElapsedTime());
					bw.close();
					line = br.readLine();
					while(line != null) {
						score = new Score(line); 
						scoreList.add(score);
						line = br.readLine();
					}
					br.close();
					Collections.sort(scoreList);
					g.setColor(Color.WHITE);
					g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20)); 
					g.drawString("High Scores",400,100);
					int count = 1;
					while(scoreList.peekLast() != null && count <= 5) {
						g.drawString(count +". " + scoreList.removeLast().getName() + ": " 
					+ scoreList.getLast().getTime(),400,100 + (35*(count+1)));
						count++;
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}
			
			/*---------------------------------------------------------
			 *  Draw the stage of the game
			 *---------------------------------------------------------*/
			
			g.setColor(Color.RED);
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 30)); 
	
			g.drawString("Stage: " + stage.getStage(), 500, 35);

		}
	}

	//recursive loop that updates the state of the game until the timer stops 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//update the state of the game after every iteration of the loop
		if(!isPaused && startGame) {
			if(TimerDisplay.isPaused()) {
				TimerDisplay.restart();
			}
			updatePlayer();
			updateZombie();
			updateArrow();
			updateAttack();
			stage.update();
			if(stage.boolWait()) {
				if(zombieList.size() == 0) {
					
					background = "/images/terrain_day.png";			
					
				}
			}else {
				updateRespawn();
				background = "/images/terrain_night.png";
			}
			updateBarricade();
		}
		else {
			if(!TimerDisplay.isPaused()) {
				TimerDisplay.pause();
			}
		}
		
		//repaint method. Stop if lives ran out or game ended.
		if (lives <= 0) {
			gameEnd = true;
			changeBackground("/images/black_background.png");
			repaint();
			gameLoopTimer.stop();
		} else if (stage.isEnd()) {
			changeBackground("/images/black_background.png");
			gameEnd = true;
			repaint();
			gameLoopTimer.stop();
		}
		else {
			repaint();
		}
		
		
	}

/*---------------------------------------------------------
 *  Start of update methods used in action performed.
 *---------------------------------------------------------*/
	//runs player update method 
	public void updatePlayer() {
		p.update();		
	}
	
	//runs each zombie's update method and updates barricades if zombies is attacking them
	public void updateZombie() {
		
		for(int i = 0; i < zombieList.size(); i++) {
			Zombie z = zombieList.get(i);
			z.update();
			z.powerUp(stage);
			for(Barricade b : barricadeList) {
				if(z.getBounds().intersects(b.getBounds()) && b.getHealth() > 0) {
					z.setX(225 + 27 - 1);
					b.damageBarricade(z.getType().getDamage());
				}	
			}			
		}		
	}
	
	//runs each arrow's update method and changes both arrow and zombie state when it collides with a zombie
	public void updateArrow() {
		
		//run this if player shoots only one arrow at a time 
			if(!p.isSecond()) {
				for(int i = 0; i < arrowList.size(); i++) {
					Arrow a = arrowList.get(i);
					a.update();
					for(int j = 0; j < zombieList.size(); j++) {
						Zombie z = zombieList.get(j);
						if(z.getBounds().intersects(a.getBounds())) {
							if(z.getBoundsHead().intersects(a.getBounds())) {
								z.hitZombie(2 * a.getType().getDamage());
							} else {
								z.hitZombie(a.getType().getDamage());							
							}
							if (p.getWeapon().getUpgradeDamage() == p.getWeapon().getMaxUpgrade() 
									&& p.getPenetrationOne() < 2) {
								p.incrPenetrationOne();
							} else {
								a.setVisible(false);
								p.resetPenetrationOne();
								break;	
							}								
						}
					}
				}
			}
			//run this if player shoots two arrows at a time instead
			else {
				for(int i = 0; i < arrowList.size(); i++) {
					Arrow a = arrowList.get(i);
					a.update();
					for(int j = 0; j < zombieList.size(); j++) {
						Zombie z = zombieList.get(j);
						if(z.getBounds().intersects(a.getBounds())) {
							z.hitZombie(a.getType().getDamage());
							if (p.getWeapon().getUpgradeDamage() == p.getWeapon().getMaxUpgrade()) {
								if(arrowList.size() == 2 && i == 0) {
									if(p.getPenetrationOne() < 2) {
										p.incrPenetrationOne();
									} else {
										a.setVisible(false);							
										break;
									}
								} else{
									if (p.getPenetrationTwo() < 2) {
										p.incrPenetrationTwo();
									} else {
										a.setVisible(false);							
										break;
									}
								}
							} else {
								a.setVisible(false);
							}
						}
					}
				}
			}
		}
	
	//determine if player can attack
	public void updateAttack() {
		attackCounter++;
		if(attackCounter > p.getWeapon().getReload()) {
			canAttack = true;
		}
	}
	
	//determine when zombie can respawn and what zombie to respawn at what lane
	public void updateRespawn() {
		respawnCounter++;		
		if(respawnCounter > stage.getSpawnVar()) {
			int type = stage.getType(stage.getStage());
			yPosition = 150 * random.nextInt(4) + 0 + 50;
			switch (type) {		
				case 1: 	zombieList.add(new FastZombie(ZombieEnum.FAST, 950, yPosition));
						break;
				case 2: 	zombieList.add(new ArmoredZombie(ZombieEnum.ARMORED, 950, yPosition));
						break;
				default: zombieList.add(new NormalZombie(ZombieEnum.NORMAL, 950, yPosition));
						break;
			}
			respawnCounter = 0;
		} 
	}
	
	public void updateBarricade() {
		
		b1.upgrade(p);
		b2.upgrade(p);
		b3.upgrade(p);
		b4.upgrade(p);
		
		b1.repairBarricade(p);
		b2.repairBarricade(p);
		b3.repairBarricade(p);
		b4.repairBarricade(p);
		
	}
	
/*---------------------------------------------------------
 *  End of update methods in action performed.
 *---------------------------------------------------------*/	
	

/*---------------------------------------------------------
 *  Some of helper methods.
 *---------------------------------------------------------*/	
	//method to deactivate attack
	public static void deactivateAttack() {
		attackCounter = 0;
		canAttack = false;
	}
	
	public static void yesCanAttack() {
		canAttack = true;
	}
	
	//return method used in Player Class to check if player can attack again
	public static boolean getCanAttack() {
		return canAttack;
	}
	
	//determine if game is paused
	public static boolean getPaused() {
		return isPaused;
	}
	
	//flip the pause variable 
	public static void flipPaused() {
		isPaused = !isPaused;
	}
	
	//checks if any zombies are left (needed for changing stages)
	public static boolean areZombiesLeft() {
		return zombieList.size() != 0;
	}
	
	//return background image
	public Image getBackgroundImage() {
		ImageIcon i = new ImageIcon(getClass().getResource(background));
		return i.getImage();
	}
	
	//change the background of the game
	public static void changeBackground(String name) {
		background = name;
	}
	
	//start the game and restart the timer (needed for pausing)
	public static void startGame() {
		startGame = true;
		TimerDisplay.restart();
		
	}
	
	//determine if the game has started (needed for pausing)
	public static boolean hasStarted() {
		return startGame;
	}
	
	//flip whether to display the instructions or not
	public static void flipInstructions() {
		turnInstructions = !turnInstructions;
	}
	
	public static void decrLives() {
		lives--;
	}
	
	public static int getLives() {
		return lives;
	}
	
	public static int getFrameHeight() {
		return frameHeight;
	}
	
	public static int getFrameWidth() {
		return frameWidth;
	}
/*---------------------------------------------------------
 *  Methods for testing purposes
 *---------------------------------------------------------*/		

	public void addZombie(int xCoor, int yCoor) {
		zombieList.add(new Zombie(ZombieEnum.NORMAL,xCoor,yCoor));
	}
	
	public void addArrow(int xCoor, int yCoor) {
		arrowList.add(new Arrow(new Weapon(WeaponEnum.BOW),xCoor,yCoor));
	}
	
	public void addBarricade(int xCoor, int yCoor) {
		barricadeList.add(new Barricade(xCoor,yCoor));
	}
	
	public Zombie getZombie() {
		return zombieList.get(0);
	}
	
	public Arrow getArrow() {
		return arrowList.get(0);
	}
	
	public Barricade getBarricade() {
		return barricadeList.get(0);
	}
	
	public void removeZombie(Player p1) {
		iterZombie = zombieList.iterator();
		while (iterZombie.hasNext()) {
		    Zombie z = iterZombie.next();
		    if (!z.isVisible()) {
		    		iterZombie.remove();
		    		if(z.isDead()) {
		    			p1.addCash(z.getType().getKillReward());
		    		}
		    }
		}		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.pack();
		frame.setSize(1000,600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new Game());
		frame.setVisible(true);	
		
		Rectangle r = frame.getBounds();
		frameHeight = r.height;
		frameWidth = r.width;
	}

}
