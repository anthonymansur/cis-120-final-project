package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import zombie.game.Game;
import zombie.game.GlobalPosition;

public class Player extends GlobalPosition {
	
	private int speed = 10; //speed of the character
	private int upgradeCount = 0; //upgrade count of player's movement speed
	private int maxUpgrade = 5; //the max upgrade of player's movement speed
	
	private int cash = 0; //how much cash the player has
	
	private boolean activateBarricadeUpgrade; //activates when player touches respective key 
	private boolean activateBarricadeRepair; //activates when player touches respective key 
	
	private boolean isSecond; //is the player shooting two arrows at a time
	
	private int penetrationOne = 0; //used to determine if first arrow can penetrate
	private int penetrationTwo = 0; //used to determine if second arrow can penetrate
	
	//Player's weapon
	private Weapon weapon = new Weapon(WeaponEnum.BOW);
	
	//ArrayList that stores all the arrows in the gamefield
	private ArrayList<Arrow> arrowList = new ArrayList<Arrow>();
	
	private String playerImage = "/images/archer_sprite_small.png"; //image string
	
	int dy = 0; //increment of vertical movement
	
	private int width; //width of player
	private int height; //height of player
	
	//constructor that specifies the player's state
	public Player(int x, int y) {
		super(x, y);
		getImageDimensions();
	}
	
	//update method that moves the character
	public void update() {
		y+=dy;
		if (y < 0) {
			y = 0;
		}		
		if(y > 600-75) {
			y = 600-75;
		}
	}
	
	//draws the player
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getPlayerImage(), x, y, null);
	}	
			
	//Method that listens to key presses to move character
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keyPressed(key);
		
	}

	//helper function
	public void keyPressed(int key) {
		
		if(Game.hasStarted()) {
			if (key == KeyEvent.VK_DOWN) {
				dy = speed;
			} else if (key == KeyEvent.VK_UP) {
				dy = -speed;
			} if (key == KeyEvent.VK_SPACE) {
				fire();
			} if (key == KeyEvent.VK_P) {
				Game.flipPaused();
			}
			
			//upgrade weapon damage
			if (key == KeyEvent.VK_D) {
				if(cash >= weapon.getUpgradeDamageCost() && weapon.getUpgradeDamage() < weapon.getMaxUpgrade()) {
					cash -= weapon.getUpgradeDamageCost();				
					weapon.upgradeDamage();
					changeImage(weapon.getUpgradeDamage());
				}
			} 
			
			//upgrade reload speed
			if (key == KeyEvent.VK_R) {
				if(cash >= weapon.getUpgradeReloadCost() && weapon.getUpgradeReload() < weapon.getMaxUpgrade()) {
					cash -= weapon.getUpgradeReloadCost();				
					weapon.upgradeReload();
				}
			} 
			
			//upgrade movement speed
			if (key == KeyEvent.VK_S) {
				if(upgradeCount > 0) {
					if(cash >= 50 * upgradeCount * 1.5) {
						cash -= 50 * upgradeCount * 1.5;
						upgradeSpeed();
					}
				} else if (cash >= 50) {
						upgradeSpeed();
						cash -= 50;
				}
			} 
			
			//upgrade barricade when touching it
			if (key == KeyEvent.VK_U) {
				activateBarricadeUpgrade = true;
			} 
			
			//repair barricade when touching it 
			if (key == KeyEvent.VK_E) {
				activateBarricadeRepair = true;
			}
		}
		else {
			if (key == KeyEvent.VK_ENTER) {
				Game.startGame();
				Game.changeBackground("/images/terrain_night.png");
			}
		}
		
	}
	
	//method to stop the character from moving after key has been released
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		keyReleased(key);
		
	}
	
	//helper function
	public void keyReleased(int key) {
		
		if(Game.hasStarted()) {
			
			if (key == KeyEvent.VK_DOWN) {
				dy = 0;
			} else if (key == KeyEvent.VK_UP) {
				dy = 0;
			} 
			
			else if (key == KeyEvent.VK_U) {
				activateBarricadeUpgrade = false;
			} 
			else if (key == KeyEvent.VK_E) {
				activateBarricadeRepair = false;
			}
			else if (key == KeyEvent.VK_I) {
				Game.flipInstructions();
			}
		} 	
		
	}
	
	//method to fire an arrow
    public void fire() {
    		if(Game.getCanAttack()) {
    			if(getWeapon().getUpgradeReload() == getWeapon().getMaxUpgrade()) {
    				isSecond = true;
    				arrowList.add(new Arrow(weapon, x + Arrow.getWidth(), y - (Arrow.getHeight() / 20)));
    				arrowList.add(new Arrow(weapon, x - Arrow.getWidth(), y + (Arrow.getHeight() / 20)));
    				penetrationOne = 0;
    				penetrationTwo = 0;
    				Game.deactivateAttack();
    			}
    			else {
    				isSecond = false;
    				arrowList.add(new Arrow(weapon, x + Arrow.getWidth() - 50, y));
    				penetrationOne = 0;
    				Game.deactivateAttack();   				
    			}
    		}
    }	
    

/*---------------------------------------------------------
 *  Helpful getter and setter functions.
 *---------------------------------------------------------*/
    
    //gets ArrayList of arrows
    public ArrayList<Arrow> getArrows() {
    		return arrowList;
    }
	
    //get the bounds of the player
	public Rectangle getBounds() {
		return new Rectangle(x, y, width+25, height);
	}
	
	//gets player image
	public Image getPlayerImage() {
		ImageIcon i = new ImageIcon(getClass().getResource(playerImage));
		return i.getImage();
	}
	
	//gets the dimensions of the image
    public void getImageDimensions() {

        width = getPlayerImage().getWidth(null);
        height = getPlayerImage().getHeight(null);
        
    }	

    public Weapon getWeapon() {
    		return weapon;
    }
    
    public void addArrow(int xcoor, int ycoor) {
    		arrowList.add(new Arrow(new Weapon(WeaponEnum.BOW),xcoor,ycoor));
    }
 

/*---------------------------------------------------------
 *  helper functions to deal with upgrading weapons
 *---------------------------------------------------------*/
    
    public void upgradeSpeed() {
    		if(upgradeCount < maxUpgrade) {
    			speed *= 1.25;
    			upgradeCount++;
    		}
    }
    
    public int getUpgrade() {
    		return upgradeCount;
    }
	
	public int getUpgradeCost() {
		return 50 * (upgradeCount + 1);
	}	

	public int getMaxUpgrade() {
		return maxUpgrade;
	}
	
	public int getSpeed() {
		return speed;
	}


/*---------------------------------------------------------
 *  Getters and setters for movement.
 *---------------------------------------------------------*/	
    //get Player's x-coordinate
	public int getX() {
		return x;
	}
	
	//sets Player's y-coordinate
	public void setX(int x) {
		this.x = x;
	}
	
	//get Player's x-coordinate
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	

/*---------------------------------------------------------
 *  Getters and setters for updating player's cash
 *---------------------------------------------------------*/	
	
	public void setCash(int money) {
		cash = money; 
	}
	
	public void addCash(int cash) {
		this.cash += cash;
	}
	
	public void removeCash(int cash) {
		this.cash -= cash;
	}
	
	public int getCash() {
		return cash;
	}
	

/*---------------------------------------------------------
 *  Helper functions to interact with barricades
 *  i.e. repair and upgraded them.
 *---------------------------------------------------------*/
	
	public boolean isTouching(Barricade b) {
		return getBounds().intersects(b.getBounds());
	}

	public void repair(Barricade bar) {
		bar.repairBarricade(this);
	}
	
	
	public boolean isActiveUpgrade() {
		return activateBarricadeUpgrade;
	}
	
	public boolean isActiveRepair() {
		return activateBarricadeRepair;
	}
	
	public void deactivateUpgrade() {
		activateBarricadeUpgrade = false;
	}

	public void deactivateRepair() {
		activateBarricadeRepair = false;
	}
	

/*---------------------------------------------------------
 *  Method dealing with specialty of weapon when 
 *  max upgrade is reached (either speed or damage).
 *---------------------------------------------------------*/	
	
	public boolean isSecond() {
		return isSecond;
	}
	
	public int getPenetrationOne() {
		return penetrationOne;
	}
	
	public void resetPenetrationOne() {
		penetrationOne = 0;
	}
	
	public void incrPenetrationOne() {
		 penetrationOne++;
	}
	
	public int getPenetrationTwo() {
		return penetrationTwo;
	}
	
	public void resetPenetrationTwo() {
		penetrationTwo = 0;
	}
	
	public void incrPenetrationTwo() {
		 penetrationTwo++;
	}		
	
/*---------------------------------------------------------
 *  Changes player's image based on damage upgrade level
 *---------------------------------------------------------*/		
	
	public void changeImage(int x) {
		playerImage = "/images/archer_sprite_lvl" + x + ".png";
	}

}
