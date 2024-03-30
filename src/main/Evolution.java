package main;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import components.Artro;
import components.Food;
import components.FoodType;
import components.Species;
import panels.CanvaPanel;
import panels.GraphsPanel;

public class Evolution
{
	
	private static int round ;		// number of the current iteration
	
	private static int foodRespawnTime ;	// time taken for the food to respawn (counted in number of rounds)
	private static int maxNumberFood ;	// maximum amount of food that can exist at any given time
	
	private static boolean isRunning ;
	
	private static List<Artro> artros ;
	private static List<Food> food ;
	
	
	public Evolution()
	{
		round = 0 ;
		
		foodRespawnTime = 20 ;
		maxNumberFood = 200 ;
		
		// load input
		Species.load() ;
		artros = Artro.load() ;
		FoodType.load() ;
		food = Food.load() ;	
		
		// clear results file
		Output.ClearFile() ;
		
		isRunning = true ;
	}
	
	public static void run()
	{
		artrosAct() ;
		
		if (round % foodRespawnTime == 0 & food.size() < maxNumberFood)
		{
			CreateFood() ;
		}
//		if (round % 7992 == 0)
//		{
//			saveRecords() ;
//		}

		Records.updatePop(artros.size()) ;
		GraphsPanel.updateRecords() ;
		
		if (isRunning)
		{
			round += 1 ;
		}

	}
	
	public static void artrosAct()
	{
		for (int i = 0 ; i <= artros.size() - 1 ; i += 1)
		{				
			Artro artro = artros.get(i) ;
			artro.Thinks() ;
			artro.Acts(CanvaPanel.getCanvaDimension(), food, artros) ;
			artro.IncHunger() ;
			artro.IncMateWill() ;
				
			if (artro.getLife() == 0)
			{
				artros.remove(artro) ;
			}
			
		}
	}
	
	public static void saveRecords()
	{
		int saveEveryNRounds = 10 ;
		for (int i = 0 ; i <= Records.artrosPop.size() - 1; i += 1)
		{
			if (i % saveEveryNRounds == 0)
			{
				Records.artrosPopAtNRounds.add(Records.artrosPop.get(i)) ;
			}
		}

		Output.UpdateOutputFile() ;
	}

	public static List<Artro> getArtros() { return artros ;}
	public static List<Food> getFood() { return food ;}
	public static boolean isRunning() { return isRunning ;}
	
	public static void switchIsRunning() { isRunning = !isRunning ;}
	
	public static void CreateFood()
	{
		Point centerOfCreation = new Point(1000, 1000) ;
		Dimension rangeOfCreation = new Dimension(500, 500) ;
		Point pos = UtilS.RandomPosAroundPoint(centerOfCreation, rangeOfCreation) ;
		FoodType type = FoodType.getAll().get(0) ;
		food.add(new Food(pos, type)) ;
	}

}
