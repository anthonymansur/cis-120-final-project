package zombie.game;

public class Score implements Comparable<Score> {

	private String name;
	private String time;
	private int intTime;
	
	Score(String line){
		name = line.substring(0,line.indexOf(','));
		time = line.substring(line.indexOf(',') + 1);
		intTime = new Integer(time.split(":")[0])*60 + new Integer(time.split(":")[1]);
	}
	
	public String getName() {
		return name;

	}
	
	public String getTime() {
		return time;
	}
	
	public int getIntTime() {
		return Integer.parseInt(time.replaceAll("\\D+",""));
	}
	
	@Override
	public int compareTo(Score o) {
		return intTime - o.getIntTime();
	}

}
