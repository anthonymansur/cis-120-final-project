
package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import zombie.game.Game;
import zombie.game.GlobalPosition;

/*
 * you left off here. Please finish the arrow class to start shooting. You were also thinking
 * about changing from different bow types to just upgrades on speed, penetration, and damage.
 */
public class Arrow extends GlobalPosition{
	
    private Weapon weapon; //weapon object
    private String imageDirect = "/images/arrow_sprite.png"; //immage of arrow 
    private boolean vis = true; //visibility of arrow
    private static int width; //width of arrow
    private static int height; //height of arrow 

    //constructor that specifies field
	public Arrow(Weapon weapon, int x, int y) {
		super(x, y);
		this.weapon = weapon;
		getImageDimensions();
	}
	
	//update method that runs continuously in the gameloop
	public void update() {
		if (x >= Game.getFrameWidth() - width) {
			vis = false;
		}
		else {
			x+= 50;
		}
	}
	
	//draw's arrow
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getArrowImage(), x, y, null);
	}	

	//get weapon's type
	public Weapon getType() {
		return weapon;
	}	
	
	//set's visibility of arrow
	public void setVisible(Boolean visible) {
        vis = visible;
    }	
	
	//determines if arrow is visible
	public boolean isVisible() {
		return vis;
	}
	
	//get's x-coordinate of arrow
	public int getX() {
		return x;
	}
	
	//get's y-coordinate of arrow
	public int getY() {
		return y;
	}
	
	//get rectangular bounds of arrow
	public Rectangle getBounds() {
		return new Rectangle(x, y+(height/4), width-25, (height/4));
	}
	
	//get dimension of arrow image
    public void getImageDimensions() {

        width = getArrowImage().getWidth(null);
        height = getArrowImage().getHeight(null);
        
    }
	
	//get the arrow image
	public Image getArrowImage(){
		ImageIcon i = new ImageIcon(getClass().getResource(imageDirect));
		return i.getImage();
	}
	
	//get width of arrow
	public static int getWidth() {
		return width;
	}
	
	//get height of arrow
	public static int getHeight() {
		return height;
	}
	
	
}
