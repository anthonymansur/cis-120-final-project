package zombie.game.objects;

public class Weapon {
	private int upgradeDamageCount = 0;
	private int upgradeReloadCount = 0;
	
	private static final int WEAPON_DEFAULT_COST = 50;
	 
    private final WeaponEnum type;
    
	public Weapon(WeaponEnum type) {
		this.type = type;
	}
	 
	public int getDamage() {
		if(upgradeDamageCount == 0) {
			return type.getInitialDmg();
		} else {
		return (int) (type.getInitialDmg() * ((upgradeDamageCount * type.getDamageMult())));
		}
	}
	
	public int getReload() {
		if(upgradeReloadCount == 5) {
			return (int) ( type.getInitialSpeed() + ((upgradeReloadCount - 2) * type.getSpeedMult()));			
		} else {
		return (int) ( type.getInitialSpeed() + (upgradeReloadCount * type.getSpeedMult()));
		}
	}
	
	public void upgradeDamage() {
		if(upgradeDamageCount < type.getMaxUpg()) {
			upgradeDamageCount++;
		}
	}
	
	public void upgradeReload() {
		if(upgradeReloadCount < type.getMaxUpg()) {
			upgradeReloadCount++;
		}
	}
	
	public int getUpgradeReload() {
		return upgradeReloadCount;
	}
	
	public int getUpgradeDamage() {
		return upgradeDamageCount;
	}
	
	public int getMaxUpgrade() {
		return type.getMaxUpg();
	}
	
	public int getUpgradeDamageCost() {
		return (int) (WEAPON_DEFAULT_COST * (Math.pow(upgradeDamageCount, 2) + 1));
	}	
	
	public int getUpgradeReloadCost() {
		return (int) (WEAPON_DEFAULT_COST * (Math.pow(upgradeReloadCount, 2) + 1));
	}
}
