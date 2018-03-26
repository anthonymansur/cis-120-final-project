package zombie.game.objects;

import java.util.Random;

import zombie.game.Stages;

public class NormalZombie extends Zombie{
	
	private boolean powerUsed;
	Random random = new Random();
	private int yPosition;

	public NormalZombie(ZombieEnum type, int x, int y) {
		super(type, x, y);
		powerUsed = false;
		// TODO Auto-generated constructor stub
	}
	
	//Teleports zombie to a different lane
	@Override
	public void powerUp(Stages stage) {
		if(getX() < 750 && stage.getStage() >= 8 && 
				!powerUsed) {
			do {
				yPosition = 150 * random.nextInt(4) + 0 + 50;
				}while(yPosition == getY());
			setY(yPosition);
			powerUsed = !powerUsed;
		}
	}
}
