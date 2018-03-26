package zombie.game.objects;

public enum ZombieEnum {
	NORMAL (95, 25, 5, 15), FAST (65, 15, 10, 25), ARMORED (250, 50, 2, 40);

	private int health; //health of zombie
	private final int damage; //damage of zombie
	private final int moveSpeed; //movement speed of zombie
	private final int killReward; //cash reward upon kill
	
	ZombieEnum(int health, int damage, int moveSpeed, int killReward){
		this.health = health;
		this.damage = damage;
		this.moveSpeed = moveSpeed;
		this.killReward = killReward;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int hp) {
		health = hp;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public double getMoveSpeed() {
		return moveSpeed;
	}
	
	public int getKillReward() {
		return killReward;
	}

}
