package zombie.game.objects;

/*
 * Enum is used to include the idea of turrets 
 * in the game (which has been unimplemented in
 * this version of the game 
 */

public enum WeaponEnum {
	BOW(1.21, -2.5, 25, 20, 5), TURRET(1.5, -2.5, 10, 20, 3);
	
	private final double damageMultiplier;
	private final double speedMultiplier;
	private int initialDamage;
	private int initialSpeed;
	private int maxUpgrade;
	
	WeaponEnum(double damageMultiplier, double speedMultiplier, 
			int initialDamage, int initialSpeed, int maxUpgrade) {
		this.damageMultiplier = damageMultiplier;
		this.speedMultiplier = speedMultiplier;
		this.initialDamage = initialDamage;
		this.initialSpeed = initialSpeed;
		this.maxUpgrade = maxUpgrade;
	}
	
	public double getDamageMult() {
		return damageMultiplier;
	}
	
	public double getSpeedMult() {
		return speedMultiplier;
	}
	
	public int getInitialDmg() {
		return initialDamage;
	}
	
	public int getInitialSpeed() {
		return initialSpeed;
	}
	
	public int getMaxUpg() {
		return maxUpgrade;
	}

}