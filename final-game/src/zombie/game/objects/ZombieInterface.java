package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import zombie.game.Stages;

public interface ZombieInterface {
	
	//update method to update the zombie's status 
	public void update();
	
	//draws the zombie at the specified (x,y) coordinate
	public void draw(Graphics2D g2d);
	
	//return zombie's type
	public ZombieEnum getType();
	
	//return the rectangular bound of the zombie
	public Rectangle getBounds();
	
	//return the rectangular bound of the zombie's head
	public Rectangle getBoundsHead();
	
	//set the dimensions of zombie based on image specs
	public void setImageDimensions();
	
	//returns the image of the zombie
	public Image getZombieImage();
	
	//get Zombie's x-coordinate
	public int getX();
	
	//set Zombie's x-coordinate
	public void setX(int x);
	
	//get Zombie's y-coordinate
	public int getY();
	
	//get Zombie's y-coordinate
	public void setY(int y);
	
	//attack the barricade or player - fix
	public void attack(Barricade bar);
	
	//lower the health of the zombie and return its current health
	public void hitZombie(int dmg);
	
	//determines if zombie is dead
	public boolean isDead();
	
	//determine if zombie is visible
	public boolean isVisible();
	
	//sets visibility of zombie 
	public void setVisible(Boolean bool);
	
	//get zombie's health
	public int getHealth();
	
	//sets zombie's health
	public void setHealth(int health);
	
	//pay user when zombie dies
	public void payUser(Player p, boolean isDead);
	
	
	//zombie's powerup
	public void powerUp(Stages stage);
	
}



