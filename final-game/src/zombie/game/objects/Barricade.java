package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import zombie.game.GlobalPosition;

public class Barricade extends GlobalPosition{
	
	private static final double UPGRADE_MULTIPLIER = 1.5;
	private int cost;
	
	private int health;
	private int maxHealth;
	private int upgradeCount;
	private int width;
	private int height;
	
	public Barricade(int x, int y) {
		super(x, y);
		health = 10000;
		maxHealth = 10000;
		cost = 100;
		getImageDimensions();
		// TODO Auto-generated constructor stub
	}
	
	//inflict damage on barricade
	public void damageBarricade(int dmg) {
		health -= dmg;
	}
	
	//repair barricade back to max health
	public void repairBarricade(Player p) {
		int difference = (maxHealth - health)/100;
		if(p.isTouching(this) && p.isActiveRepair()) {
			if(p.getCash() >= difference) {
				health = maxHealth;
				p.deactivateRepair();
				p.removeCash(difference);
			}else {
				health += p.getCash()*100;
				p.removeCash(p.getCash());
			}
		}
	}
	
	//upgrade the barricade which increase the max health
	public void upgrade(Player p) {
		if (upgradeCount < 5 && p.getCash() >= cost && p.isTouching(this) && p.isActiveUpgrade()) {
			maxHealth *= UPGRADE_MULTIPLIER;
			health = maxHealth;
			upgradeCount++;
			p.deactivateUpgrade();
			p.removeCash(cost);
			cost *= 3;
		}
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getBarricadeImage(), 
				x, y, null);
	}	
	
    //is a player touching barricade?
	public boolean isTouching(Player p) {
		return getBounds().intersects(p.getBounds());
	}
	
	//check if the barricade is open 
	public boolean isBroken() {
		return health <= 0;
	}
	
	/*---------------------
	 *  getter methods 
	 *---------------------*/	
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
    public void getImageDimensions() {

        width = getBarricadeImage().getWidth(null);
        height = getBarricadeImage().getHeight(null);
    }
    
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
    
	//returns the image of the zombie
	public Image getBarricadeImage() {
		int level = getBarricadeLevel(); 
		ImageIcon i;
		i = new ImageIcon(getClass().getResource(getBarricadeImageString(level)));
		return i.getImage();
	}	    
	
    	public int getBarricadeLevel() {
    		int levelByHealth = maxHealth;
    		
    		switch(levelByHealth) {
    		case 10000: return 1; 
    		case (int) (10000*1.5): return 2; 
    		case (int) (10000*1.5*1.5): return 3;
    		case (int) (10000*1.5*1.5*1.5): return 4;
    		default: return 5;
    		}
    }
    
    public String getBarricadeImageString(int level) {
    		double dHealth = health;
    		double dMaxHealth = maxHealth;
    		double division = dHealth/dMaxHealth;
		if (division > (3.0/4.0)) {
			return "/images/barricade/barricade_lvl_" + level +"_sprite.png";
		} else if (division > (2.0/4.0)) {
			return "/images/barricade/barricade_lvl_" + level +"_sprite_1.png";			
		} else if (division > (1.0/4.0)) {
			return "/images/barricade/barricade_lvl_" + level +"_sprite_2.png";				
		} else if (health > 0) {
			return "/images/barricade/barricade_lvl_" + level +"_sprite_3.png";				
		} else {		
			return "/images/barricade/barricade_lvl_" + level +"_sprite_broken.png";			
		}   		
    }
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getRepairCost() {
		return (maxHealth - health)/200;
	}
	
	public int getUpgrade() {
		return upgradeCount;
	}

}
