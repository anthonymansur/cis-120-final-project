package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import zombie.game.GlobalPosition;

public class BarricadeSeparator extends GlobalPosition {
	
	private int width;
	private int height;

	public BarricadeSeparator(int x, int y) {
		super(x, y);
		getImageDimensions();
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getSeparatorImage(), 
				x, y, null);
	}		
	
	public Image getSeparatorImage() { 
		ImageIcon i;
		i = new ImageIcon(getClass().getResource("/images/barricade/barricade_separator_sprite.png"));
		return i.getImage();
	}	    

    public void getImageDimensions() {

        width = getSeparatorImage().getWidth(null);
        height = getSeparatorImage().getHeight(null);
    }
    
    public int getHeight() {
    		return height;
    }
    
    public int getWidth() {
    		return width;
    }
}
