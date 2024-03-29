package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import components.Artro;
import components.ArtroChoices;
import components.Directions;
import components.Food;
import components.FoodType;
import components.Species;

public class Simulation
{
	ButtonsPanel buttonsPanel ;
	GraphsPanel graphsPanel ;
	Evolution evolutionPanel ;
	
	private Records records ;		// class that keeps the records (population, etc.)
	
	private int round ;		// number of the current iteration
	private int roundDuration ;	// amount of time between rounds
	
	private int foodRespawnTime ;	// time taken for the food to respawn (counted in number of rounds)
	private int maxNumberFood ;	// maximum amount of food that can exist at any given time
	
	private boolean simulationIsRunning ;
	private boolean graphsAreVisible ;
	
	private List<Artro> artros ;
	private List<Species> species ;
	private List<Food> food ;
	private List<FoodType> foodTypes ;
	
	public Simulation(ButtonsPanel buttonsPanel, GraphsPanel graphsPanel, Evolution evolutionPanel)
	{
		this.buttonsPanel = buttonsPanel ;
		this.graphsPanel = graphsPanel ;
		this.evolutionPanel = evolutionPanel ;
		
		records = new Records() ;
		
		round = 0 ;
		roundDuration = 8 ;
		
		foodRespawnTime = 20 ;
		maxNumberFood = 200 ;
		
		// load input
		species = LoadSpecies() ;
		artros = LoadArtros() ;
		foodTypes = LoadFoodTypes() ;
		food = LoadFood() ;	
		
		// clear results file
		Output.ClearFile("Results.txt") ;
		
		simulationIsRunning = true ;
		graphsAreVisible = true ;

		runSimulation () ;
	}
	
	public void runSimulation()
	{
		if (round % roundDuration == 0)
		{
			for (Artro artro : artros)
			{				
				
				artro.Thinks() ;
				artro.Acts(evolutionPanel.getMainCanva(), food, artros) ;
				artro.IncHunger() ;
				artro.IncMateWill() ;
				
				if (artro.getLife() == 0)
				{
					artros.remove(artro) ;
				}
				
			}
			
			records.RecordArtrosPop(artros.size());
		}
		if (round % foodRespawnTime == 0 & food.size() < maxNumberFood)
		{
			CreateFood() ;
		}

		
		if (round % 7992 == 0)
		{			
			for (int i = 0 ; i <= records.artrosPop.size() - 1; i += 1)
			{
				if (i % 10 == 0)
				{
					records.artrosPopAtNRounds.add(records.artrosPop.get(i)) ;
				}
			}
			// append to results file
			Output.UpdateOutputFile("Results.txt", records) ;
		}
		
		if (simulationIsRunning)
		{
			round += 1 ;
		}
		
		if (graphsAreVisible)
		{
			//GraphsPanel.displayGraphs(RE) ;
		}

		
		evolutionPanel.repaint() ;
		evolutionPanel.getDP().DrawPoint(new Point(200, 200), 10, true, null, Color.red);
		evolutionPanel.displayFood(food) ;
		evolutionPanel.displayArtros(artros) ;
	}
	

	
	public Object ReadJson(String filePath)
	{
		JSONParser parser = new JSONParser();
        try
        {
            Object jsonData = parser.parse(new FileReader(filePath));
            return jsonData ;
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
            return null ;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null ;
        }
	}
	
	public List<Species> LoadSpecies()
	{
		// read input file
		Object data = ReadJson("Species.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
        
		// create species
		List<Species> species = new ArrayList<>() ;
        for (int i = 0 ; i <= jsonArray.size() - 1; i += 1)
        {
        	JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
        	long size = (long) jsonObject.get("size") ;
        	long step = (long) jsonObject.get("step") ;
        	long vision = (long) jsonObject.get("vision") ;
        	long matePoint = (long) jsonObject.get("matePoint") ;
        	long stomach = (long) jsonObject.get("stomach") ;
            JSONArray colorArray= (JSONArray)jsonObject.get("color");
        	Color color = new Color((int)(long)colorArray.get(0), (int)(long)colorArray.get(1), (int)(long)colorArray.get(2)) ;
        	
    		species.add(new Species((int)size, (int)step, (int)vision, (int)matePoint, (int)stomach, color)) ;
        }
        
        return species ;
	}
	
	public List<Artro> LoadArtros()
	{
		// read input file
		Object data = ReadJson("Artros.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		
		// create artros
        List<Artro> artros = new ArrayList<>() ;
        for (int i = 0 ; i <= jsonArray.size() - 1; i += 1)
        {
        	JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
            JSONArray centerArray= (JSONArray)jsonObject.get("Center");
            JSONArray rangeArray= (JSONArray)jsonObject.get("Range");
            Point center = new Point((int)(long)centerArray.get(0), (int)(long)centerArray.get(1)) ;
    		Dimension range = new Dimension((int)(long)rangeArray.get(0), (int)(long)rangeArray.get(1)) ;
    		int numberArtros = (int)(long)jsonObject.get("Amount") ;
    		for (int j = 0 ; j <= numberArtros - 1 ; j += 1)
    		{
    			Point pos = UtilS.RandomPosAroundPoint(center, range) ;
    			int speciesID = (int)(long)jsonObject.get("SpeciesID");
    			Species sp = species.get(speciesID) ;
    			int minSatiation = (int)(long)jsonObject.get("MinSatiation");
    			int satiation = (int) (minSatiation + (sp.getStomach() - minSatiation) * Math.random()) ;
                JSONArray tendArray= (JSONArray)jsonObject.get("Tendencies");
    			Map<ArtroChoices, Double> tendency = new HashMap<>() ;
    			tendency.put(ArtroChoices.eat, (int)(long)tendArray.get(0) / 100.0) ;
    			tendency.put(ArtroChoices.mate, (int)(long)tendArray.get(1) / 100.0) ;
    			tendency.put(ArtroChoices.wander, (int)(long)tendArray.get(2) / 100.0) ;
    			int minSexWill = (int)(long)jsonObject.get("MinSexWill");
    			int sexWill = (int) (minSexWill + (sp.getMatePoint() - minSexWill) * Math.random()) ;
    			
    			Artro newArtro = new Artro(100, pos, 0, sp, tendency, false, satiation, ArtroChoices.exist, sexWill, Directions.up) ;
    			artros.add(newArtro) ;
    		}
    	}
        
        return artros ;
	}
	
	public List<FoodType> LoadFoodTypes()
	{
		// read input file
		Object data = ReadJson("FoodTypes.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		        
        // create food types
		List<FoodType> foodTypes = new ArrayList<>() ;
		
		for (int i = 0 ; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
			int size = (int)(long) jsonObject.get("Size") ;
			int value = (int)(long) jsonObject.get("Value") ;
			JSONArray colorArray= (JSONArray)jsonObject.get("Color");
        	Color color = new Color((int)(long)colorArray.get(0), (int)(long)colorArray.get(1), (int)(long)colorArray.get(2)) ;
        	
        	foodTypes.add(new FoodType(size, value, color)) ;
		}
		
		return foodTypes ;
	}
	
	public List<Food> LoadFood()
	{
		// read input file
		Object data = ReadJson("Food.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		        
        // create food
		List<Food> food = new ArrayList<>() ;
		
		for (int i = 0 ; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
			JSONArray centerArray= (JSONArray)jsonObject.get("Center");
            JSONArray rangeArray= (JSONArray)jsonObject.get("Range");
            Point center = new Point((int)(long)centerArray.get(0), (int)(long)centerArray.get(1)) ;
    		Dimension range = new Dimension((int)(long)rangeArray.get(0), (int)(long)rangeArray.get(1)) ;
    		int numberFood = (int)(long)jsonObject.get("Amount") ;
    		for (int j = 0 ; j <= numberFood - 1 ; j += 1)
			{
				Point pos = UtilS.RandomPosAroundPoint(center, range) ;
				int typeID = (int)(long)jsonObject.get("TypeID");
    			FoodType type = foodTypes.get(typeID) ;
    			food.add(new Food(pos, type)) ;
			}
		}
		
		return food ;
	}
	

	public void CreateFood()
	{
		// food is created around a point (center of creation) within a certain range
		Point centerOfCreation = new Point(250, 250) ;
		Dimension rangeOfCreation = new Dimension(150, 150) ;
		Point pos = UtilS.RandomPosAroundPoint(centerOfCreation, rangeOfCreation) ;
		FoodType type = foodTypes.get(0) ;
		Food newFood = new Food(pos, type) ;
		food.add(newFood) ;
	}

	
	public void ArtroEats(Artro artro)
	{
		Food foodInRange = artro.FindFoodInRange(food, artro.getSpecies().getSize());		
		artro.Eats(foodInRange) ;
		food.remove(foodInRange) ;
	}
	
	public void ArtrosMate(Artro artro)
	{
		
	}
	
}
