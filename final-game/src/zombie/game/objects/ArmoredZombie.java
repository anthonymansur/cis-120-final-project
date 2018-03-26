package zombie.game.objects;

import zombie.game.Stages;

public class ArmoredZombie extends Zombie{

	public ArmoredZombie(ZombieEnum type, int x, int y) {
		super(type, x, y);
		// TODO Auto-generated constructor stub
	}
	
	//regenerates health over time
	@Override
	public void powerUp(Stages stage) {
		if(stage.getStage() >= 8) {
			setHealth(getHealth() + 2);
		}
		
	}

}
