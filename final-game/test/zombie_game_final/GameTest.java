package zombie_game_final;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.awt.event.KeyEvent;
import org.junit.Test;
import zombie.game.Game;
import zombie.game.objects.Barricade;
import zombie.game.objects.Player;
import zombie.game.objects.Weapon;
import zombie.game.objects.WeaponEnum;


public class GameTest{

	@Test
	public void testEmptyTest() {
	    assertTrue(true);
	  }
	
	@Test
	public void testUpgradeInsufficientCash() {
		
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		
		p.setCash(0);
		p.keyPressed(KeyEvent.VK_ENTER);
				
		p.keyPressed(KeyEvent.VK_D);
		assertEquals(p.getWeapon().getUpgradeDamage(), 0);
		
		p.keyPressed(KeyEvent.VK_R);
		assertEquals(p.getWeapon().getUpgradeReload(), 0);
		
		p.keyPressed(KeyEvent.VK_S);
		assertEquals(p.getUpgrade(), 0);
		
		p.keyPressed(KeyEvent.VK_U);
		b1.upgrade(p);
		assertEquals(b1.getUpgrade(), 0);
		
		b1.damageBarricade(100);
		
		p.keyPressed(KeyEvent.VK_E);
		
		assertEquals(b1.getHealth(), b1.getMaxHealth() - 100);

	}
	
	@Test
	public void testUpgradeSufficientCash() {
		
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		
		p.setCash(100000);
		p.keyPressed(KeyEvent.VK_ENTER);
		
		p.keyPressed(KeyEvent.VK_D);
		p.keyReleased(KeyEvent.VK_D);
		assertEquals(p.getWeapon().getUpgradeDamage(), 1);
		
		p.keyPressed(KeyEvent.VK_R);
		p.keyReleased(KeyEvent.VK_R);
		assertEquals(p.getWeapon().getUpgradeReload(), 1);
		
		p.keyPressed(KeyEvent.VK_S);
		p.keyReleased(KeyEvent.VK_S);
		assertEquals(p.getUpgrade(), 1);
		
		p.keyPressed(KeyEvent.VK_U);
		b1.upgrade(p);
		p.keyReleased(KeyEvent.VK_U);
		assertEquals(b1.getUpgrade(), 1);
		
		b1.damageBarricade(100);
		
		p.keyPressed(KeyEvent.VK_E);
		b1.repairBarricade(p);
		p.keyReleased(KeyEvent.VK_E);
		
		assertEquals(b1.getHealth(), b1.getMaxHealth());
		
	}
	
	@Test
	public void testUpgradeMaxUpgrade() {
		
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		
		p.setCash(100000);
		Game.startGame();
		
		for(int i = 0; i < 6; i++) {
			p.keyPressed(KeyEvent.VK_D);
			p.keyReleased(KeyEvent.VK_D);
			p.keyPressed(KeyEvent.VK_R);
			p.keyReleased(KeyEvent.VK_R);
			p.keyPressed(KeyEvent.VK_S);
			p.keyReleased(KeyEvent.VK_S);
			p.keyPressed(KeyEvent.VK_U);
			b1.upgrade(p);
			p.keyReleased(KeyEvent.VK_U);

		}
		
		assertEquals("weapon upgrade",p.getWeapon().getUpgradeDamage(), 5);
		assertEquals("reload upgrade",p.getWeapon().getUpgradeReload(), 5);
		assertEquals("speed upgrade", p.getUpgrade(), 5);
		assertEquals("barricade upgrade", b1.getUpgrade(), 5);
		
		
		
		
	}
	
	@Test
	public void testUpgradeDamageCheckDamage() {
		
		Player p = new Player(0,0); 
		
		int count = 1;
		while(count <= 5) {
			int dmg = p.getWeapon().getDamage();
			p.getWeapon().upgradeDamage();
			assertTrue("Damage increased upon upgrade " + count +"/5",
					p.getWeapon().getDamage() > dmg);
			count++;
		}
	}
	
	@Test
	public void testUpgradeSpeedCheckSpeed() {
		
		Player p = new Player(0,0); 
		
		int count = 1;
		while(count <= 5) {
			int speed = p.getSpeed();
			p.upgradeSpeed();
			assertTrue("Speed increased upon upgrade " + count +"/5",
					p.getSpeed() > speed);
			count++;
		}
	}
	
	@Test
	public void testUpgradeReloadCheckReload() {
		
		Player p = new Player(0,0); 
		
		int count = 1;
		while(count <= 4) {
			int reload = p.getWeapon().getReload();
			p.getWeapon().upgradeReload();
			assertTrue("Reload increased upon upgrade " + count +"/5",
					p.getWeapon().getReload() < reload);
			Game.yesCanAttack();
			p.fire();
			assertFalse("Only shoots one arrow", p.isSecond());
			count++;
		}
		p.getWeapon().upgradeReload();
		Game.yesCanAttack();
		p.fire();
		assertTrue("Max upgrade: shoot two arrows", p.isSecond());
	}
	
	@Test
	public void testUpgradeBarricadeCheckHealth() {
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		
		p.setCash(100000);
		Game.startGame();
		
		b1.damageBarricade(100);
		int health = b1.getMaxHealth();
		p.keyPressed(KeyEvent.VK_U);
		b1.upgrade(p);
		p.keyReleased(KeyEvent.VK_U);
		int newHealth = b1.getMaxHealth();
		assertTrue("upgrade increased barricade's health", newHealth > health);
		assertEquals(b1.getMaxHealth(),b1.getHealth());
	}
	
	@Test
	public void testRepairBarricade() {
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		
		p.setCash(100000);
		Game.startGame();
		
		b1.damageBarricade(100);
		p.keyPressed(KeyEvent.VK_E);
		b1.repairBarricade(p);
		assertEquals(b1.getMaxHealth(), b1.getHealth());
	}

	@Test
	public void testIsTouchingBarricade() {
		Player p = new Player(0,0); 
		Barricade b1 = new Barricade(0,0); 
		Barricade b2 = new Barricade(100,100);
		
		p.setCash(100000);
		Game.startGame();
		
		b1.damageBarricade(100);
		b2.damageBarricade(100);
		p.keyPressed(KeyEvent.VK_E);
		b1.repairBarricade(p);
		b2.repairBarricade(p);
		p.keyReleased(KeyEvent.VK_E);
		assertEquals(b1.getHealth(), b1.getMaxHealth());
		assertNotEquals(b2.getHealth(), b2.getMaxHealth());
		
		p.keyPressed(KeyEvent.VK_U);
		b1.upgrade(p);
		b2.upgrade(p);
		p.keyReleased(KeyEvent.VK_U);
		assertEquals(b1.getUpgrade(), 1);
		assertEquals(b2.getUpgrade(), 0);
	
	}
	
	@Test
	public void testZombieHit() {
		
		Weapon w = new Weapon(WeaponEnum.BOW);
		Game g = new Game();
		g.addZombie(0, 0);
		g.addArrow(g.getZombie().getX(),g.getZombie().getY());
		int health = g.getZombie().getHealth();
		g.updateArrow();
		assertEquals(health, g.getZombie().getHealth() + w.getDamage());
		
	}
	
	@Test
	public void testZombieHeadHit() {
		Weapon w = new Weapon(WeaponEnum.BOW);
		Game g = new Game();
		g.addZombie(0, 0);
		g.addArrow(g.getZombie().getX(),g.getZombie().getY()-20);
		int health = g.getZombie().getHealth();
		g.updateArrow();
		assertEquals(health, g.getZombie().getHealth() + 2 * w.getDamage());
	}
	
	@Test
	public void testZombieDeadRemoved() {
		Game g = new Game();
		g.addZombie(0, 0);
		g.addArrow(g.getZombie().getX(),g.getZombie().getY()-20);
		
		assertFalse("Zombie is alive", g.getZombie().isDead());
		
		g.updateArrow();
		g.updateArrow();
		g.updateZombie();
		
		assertTrue("Zombie is dead", g.getZombie().isDead());
	}
	
	@Test
	public void testZombieAttack() {
		
		Game g = new Game();
		
		g.addZombie(225, 0);
		g.addBarricade(0, 0);
		
		int health = g.getBarricade().getHealth();
		
		g.updateZombie();
		
		assertTrue("Zombie damaged barricade", health > g.getBarricade().getHealth());
		
	}
	
	@Test
	public void testPlayerLive() {
		Game g = new Game();
		
		int lives = Game.getLives();
		
		g.addZombie(225, 0);
		g.getZombie().setX(200);
		
		g.getZombie().update();
		
		assertTrue(Game.getLives() < lives);
		
		
	}
	
	@Test
	public void testKillReward() {
		
		Game g = new Game();
		Player p = new Player(0,0);
		int cash = p.getCash();
		g.addZombie(0, 0);
		g.addArrow(g.getZombie().getX(),g.getZombie().getY()-20);
		g.updateArrow();
		g.updateArrow();
		g.updateZombie();
		
		g.removeZombie(p);
		
		
		assertTrue(p.getCash() > cash);
		
	}
	
	
	
}
