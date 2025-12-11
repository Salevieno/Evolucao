package main;

import components.Artro;
import components.Food;
import components.FoodType;
import components.Species;
import panels.GraphsPanel;

public abstract class Evolution
{
	private static boolean isRunning;
	private static double currentTime ;

	public static void initialize()
	{
		Species.load();
		Artro.load();
		FoodType.load();
		Food.load();

		currentTime = 0 ;
		isRunning = true;
	}

	public static void run(double dt)
	{
		Artro.updateAll(dt);
		FoodType.spawnFoodIfNeeded(currentTime, dt);

		CustomTimer.updateAll();
		GraphsPanel.updateRecords(Artro.getAll(), dt);

		currentTime += dt ;
	}

	public static void switchIsRunning() { isRunning = !isRunning ;}
	public static boolean isRunning() { return isRunning ;}
}
