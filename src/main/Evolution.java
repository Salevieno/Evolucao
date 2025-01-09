package main;

import java.awt.Point;
import java.util.List;

import components.Artro;
import components.Food;
import components.FoodType;
import components.Species;
import panels.CanvaPanel;
import panels.GraphsPanel;

public abstract class Evolution
{

	private static int round; // number of the current iteration

	private static int foodRespawnTime; // number of rounds taken for the food to respawn
	private static int maxNumberFood; // maximum amount of food that can exist at a time

	private static boolean isRunning;

	private static List<Artro> artros;
	private static List<Food> food;

	public static void initialize()
	{

		Species.load();
		artros = Artro.load();
		FoodType.load();
		food = Food.load();

		Output.ClearFile();

		foodRespawnTime = 20;
		maxNumberFood = 200;
		round = 0;
		isRunning = true;

	}

	public static void run()
	{
		artrosAct();

		if (round % foodRespawnTime == 0 & food.size() < maxNumberFood)
		{
			CreateFood();
		}

		Records.updateFPS((int) CanvaPanel.getFPS());
		GraphsPanel.updateRecords(artros);

		if (isRunning)
		{
			round += 1;
		}

	}

	public static void artrosAct()
	{
		for (int i = 0; i <= artros.size() - 1; i += 1)
		{
			Artro artro = artros.get(i);
			artro.thinks();
			artro.acts(food);
			artro.incHunger();
			artro.incMateWill();

			if (artro.getLife() == 0)
			{
				artros.remove(artro);
			}

		}
	}

	public static void CreateFood()
	{
		Point pos = UtilS.RandomPosAroundPoint(FoodType.centerOfCreation, FoodType.rangeOfCreation);
		FoodType type = FoodType.getAll().get(0);
		food.add(new Food(pos, type));
	}

	public static List<Artro> getArtros()
	{
		return artros;
	}

	public static List<Food> getFood()
	{
		return food;
	}

	public static boolean isRunning()
	{
		return isRunning;
	}

	public static void switchIsRunning()
	{
		isRunning = !isRunning;
	}

}
