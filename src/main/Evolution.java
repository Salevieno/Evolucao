package main;

import java.awt.geom.Point2D;
import java.util.List;

import components.Artro;
import components.Food;
import components.FoodType;
import components.Species;
import panels.GraphsPanel;

public abstract class Evolution
{
	private static boolean isRunning;
	private static double currentTime ;
	private static List<Food> food;

	public static void initialize()
	{
		Species.load();
		Artro.load();
		FoodType.load();
		food = Food.load();

		currentTime = 0 ;
		isRunning = true;
	}

	public static void run(double dt)
	{
		artrosAct(dt);

		if ((int) currentTime % FoodType.spawnTime != 0 && (int) (currentTime + dt) % FoodType.spawnTime == 0 && food.size() < FoodType.maxQtd)
		{
			CreateFood();
		}

		CustomTimer.updateAll();
		GraphsPanel.updateRecords(Artro.getAll(), dt);

		currentTime += dt ;
	}

	public static void artrosAct(double dt)
	{
		for (int i = 0; i <= Artro.getAll().size() - 1; i += 1)
		{
			Artro artro = Artro.getAll().get(i);
			artro.thinks();
			artro.acts(food, dt);
			artro.age(dt);
			artro.incHunger(dt);
			artro.incMateWill();
		}
	}

	public static void CreateFood()
	{
		Point2D.Double pos = new Point2D.Double() ;
		pos.x = FoodType.centerOfCreation.x + FoodType.rangeOfCreation.width * (Math.random() - Math.random()) ;
		pos.y = FoodType.centerOfCreation.y + FoodType.rangeOfCreation.height * (Math.random() - Math.random()) ;
		FoodType type = FoodType.getAll().get(0);
		food.add(new Food(pos, type));
	}

	public static void switchIsRunning() { isRunning = !isRunning ;}
	public static List<Food> getFood() { return food ;}
	public static boolean isRunning() { return isRunning ;}
}
