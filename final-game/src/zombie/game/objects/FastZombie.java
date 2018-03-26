package zombie.game.objects;

import zombie.game.Stages;

public class FastZombie extends Zombie{
	
	private boolean powerUsed;

	public FastZombie(ZombieEnum type, int x, int y) {
		super(type, x, y);
		// TODO Auto-generated constructor stub
	}

	//Teleports zombie to lane's barricade
	@Override
	public void powerUp(Stages stage) {
		if(getX() < 750 && !powerUsed && stage.getStage() >= 8) {
			setX(450);
			powerUsed = !powerUsed;
		}	
	}
	
}

