package zombie.game.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import zombie.game.Game;
import zombie.game.GlobalPosition;
import zombie.game.Stages;

public class Zombie extends GlobalPosition  implements ZombieInterface{
	
    private final ZombieEnum type; //type of zombie (normal, fast, armored)
	
	//image strings for the different types of zombies
	private String normalImage = "/images/zombie_sprite.png";
	private String fastImage = "/images/fast_zombie_sprite.png";
	private String armoredImage = "/images/armored_zombie_sprite.png";
	
	private int width; //width of zombie
	private int height; //height of zombie
	
    private boolean vis = true;	//visibility of the zombie
    
    private int health;     //health of the zombie 
    
    //constructor that takes in the type of zombie and its position
	public Zombie(ZombieEnum type, int x, int y) {
		super(x,y);
		this.type = type;
		health = type.getHealth();
		setImageDimensions();		
	}
	
	public void update() {
		x-=type.getMoveSpeed();
		if (isDead()) {
			vis = false;
		}		
		
		//Collision with outside
		if (x < 200) {
			Game.decrLives();
			vis = false;
		}
				
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(getZombieImage(), x, y, null);
	}
	
	public ZombieEnum getType() {
		return type;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBoundsHead() {
		if(type == ZombieEnum.NORMAL) {
			return new Rectangle(x,y, width, height/6);
		}
		return new Rectangle(x,y, width, height/4);
	}
	
    public void setImageDimensions() {

        width = getZombieImage().getWidth(null);
        height = getZombieImage().getHeight(null);
    }
	
	public Image getZombieImage() {
		ImageIcon i;
		switch(type) {
		case FAST: i = new ImageIcon(getClass().getResource(fastImage));
				     break;
		case ARMORED: i = new ImageIcon(getClass().getResource(armoredImage));
					  break;
		default: i = new ImageIcon(getClass().getResource(normalImage));
		}
		return i.getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void attack(Barricade bar) {
		bar.damageBarricade(type.getDamage());
	}
	
	public void hitZombie(int dmg) {
		health -= dmg;
	}
	
	public boolean isDead() {
		return health <=0;
	}
	
	
	public boolean isVisible() {
		return vis;
	}
	
	
	public void setVisible(Boolean bool) {
		vis = bool;
	}
	
	
	public int getHealth() {
		return health;
	}
	
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	
	public void payUser(Player p, boolean isDead) {
		p.setCash(type.getKillReward());
	}

	
	public void powerUp(Stages stage) {
		// TODO Auto-generated method stub
		
	}


}
