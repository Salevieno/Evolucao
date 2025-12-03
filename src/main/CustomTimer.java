package main;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

public class CustomTimer
{
	private boolean active ;
	private double initialTime ;
	private double prevCounter ;
	private double counter ;
	private double duration ;	// duration of the counter in seconds
	private double timeElapsedAtStop ;
	
	private static final Set<CustomTimer> all ;
	private static double timeAtStop ;
	static 
	{
		all = new HashSet<>() ;
	}
	
	public CustomTimer(double duration)
	{
		this.active = false ;
		this.counter = 0 ;
		this.prevCounter = counter ;
		this.duration = duration/1.0 ;
		
		all.add(this) ;
	}
	
	public double getCounter() { return counter ;}
	public double getDuration() {return duration ;}	
	public void setDuration(double duration) { this.duration = duration ;}
	private static double timeNowInSec() { return System.nanoTime() * Math.pow(10, -9) ;}
	
	public void start() { initialTime = timeNowInSec() ; active = true ;}
	public void stop() { active = false ;}
	public void resume() { active = hasStarted() ;}
	public void reset() { initialTime = timeNowInSec() ; timeElapsedAtStop = 0 ; counter = 0 ; prevCounter = 0 ;}
	public void restart() { reset() ; start() ;}
	public double rate() { return counter / duration ;}
	public boolean crossedTime(double time) { return active && (counter % time <= prevCounter % time) ;}
	public boolean isActive() { return active ;}
	public boolean hasStarted() { return 0 < counter ;}
	public boolean hasFinished() { return duration <= counter ;}
	
	public void update()
	{
		if (!active) { return ;}
		
		prevCounter = counter ;
		counter = (timeNowInSec() - initialTime - timeElapsedAtStop) ;
		if (hasFinished())
		{
			finish() ;
		}
	}

	private void finish()
	{
		counter = duration ;
		active = false ;
	}
	
	public static void stopAll()
	{
		timeAtStop = timeNowInSec() ;
		all.forEach(CustomTimer::stop) ;
	}
	
	public static void resumeAll()
	{
		all.forEach(timeCounter -> timeCounter.timeElapsedAtStop += timeCounter.hasStarted() ? timeNowInSec() - timeAtStop : 0) ;
		all.forEach(CustomTimer::resume) ;
	}
	
	public static void updateAll()
	{
		all.forEach(CustomTimer::update);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJson()
	{

        JSONObject content = new JSONObject();
        content.put("active", active);
        content.put("initialTime", initialTime);
        content.put("prevCounter", prevCounter);
        content.put("counter", counter);
        content.put("duration", duration);
        content.put("timeElapsedAtStop", timeElapsedAtStop);
        
        return content ;
	}
	

	public static CustomTimer fromJson(JSONObject jsonData)
	{
		double duration = (double) (Double) jsonData.get("duration") ;
		CustomTimer timer = new CustomTimer(duration) ;
		
		timer.active = (boolean) jsonData.get("active") ;
		timer.initialTime = (double) (Double) jsonData.get("initialTime") ;
		timer.prevCounter = (double) (Double) jsonData.get("prevCounter") ;
		timer.counter = (double) (Double) jsonData.get("counter") ;
		timer.timeElapsedAtStop = (double) (Double) jsonData.get("timeElapsedAtStop") ;
		
		return timer ;
	}
	
	@Override
	public String toString()
	{
		return "TimeCounter [active = " + active + " time = " + counter + ", duration = " + duration + "]";
	}
}
