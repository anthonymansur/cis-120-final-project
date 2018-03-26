package zombie.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import zombie.game.objects.Player;

public class KeyInput extends KeyAdapter{
	
	Player p;
	
	public KeyInput(Player p) {
		this.p = p;
	}
	
	public void keyPressed(KeyEvent e) {
		p.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}

}
