package zombie.game;

public class TimerDisplay {
	
    private static long startTime = 0; //start time 
    
    private static long pauseTime = 0; //pause time 
    
    private static boolean isPaused; //determine if game is paused 
    
    private static long longSeconds; //current time of game in seconds 

    //start the clock
    public static void start()
    {
        startTime = 0;
        isPaused = true;
    }

    //return time 
    public static String getElapsedTime() 
    {
    		if(!isPaused) {
    			longSeconds = (System.currentTimeMillis() - startTime) / 1000;
    		} else {
    			longSeconds = (pauseTime - startTime) / 1000;
    		}
    		//long longSeconds = (System.currentTimeMillis() - startTime) / 1000;
    		int minutes = (int) (longSeconds / 60);
    		int seconds = (int) (longSeconds - 60*minutes);
    		if (seconds < 10 && minutes > 9) {
    			return "" + minutes + ":" + "0" + seconds; //return time
    		} else if(seconds < 10) {
    			return "0" + minutes + ":" + "0" + seconds; //return time
    		}
        return "" + minutes + ":" + seconds; //returns time
    }
    
    //pause the clock 
    public static void pause() {
    		isPaused = true;
    		pauseTime = System.currentTimeMillis();
    }
    
    //restart the clock
    public static void restart() {
    		startTime += System.currentTimeMillis() - pauseTime;
    		isPaused = !isPaused;
    }
    
    //determine if game is paused
    public static boolean isPaused() {
    		return isPaused;
    }
    
}
