package zombie.game;

import zombie.game.objects.ZombieEnum;

public class Tuple implements Comparable<Tuple>{
	
	private ZombieEnum type;
	private double stage;
	
	public Tuple(ZombieEnum type, double stage) {
		this.type = type;
		this.stage = stage;
	}
	
	public ZombieEnum first() {
		return type;
	}
	
	public double second() {
		return stage;
	}
	public int compareTo(Tuple t) {
		
		if(this.type == ZombieEnum.NORMAL) {
			if(t.first() == this.type){
				if(this.stage < t.second() ) {
					return -1;
				} else if (this.stage > t.second()) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return -1;
			}
		} else if(this.type == ZombieEnum.FAST) {
			if(t.first() == ZombieEnum.NORMAL) {
				return 1;
			} else if (t.first() == ZombieEnum.ARMORED) {
				return -1;
			}else {
				if(this.stage < t.second() ) {
					return -1;
				} else if (this.stage > t.second()) {
					return 1;
				} else {
					return 0;
				}	
			}
		} else {
			if(t.first() == this.type) {
				if(this.stage < t.second() ) {
					return -1;
				} else if (this.stage > t.second()) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 1;
			}
		}
	}

}
