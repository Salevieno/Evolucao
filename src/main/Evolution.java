package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import components.Artro;
import components.ArtroChoices;
import components.Directions;
import components.Food;
import components.FoodType;
import components.Species;
import graphics.Canva;
import graphics.DrawingOnAPanel;


public class Evolution extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static Container container;
	private static JPanel mainJPanel;
	
	private boolean simulationIsRunning ;
	private boolean graphsAreVisible ;

	private DrawingOnAPanel DP;	// class with the drawing methods
	private Records RE ;		// class that keeps the records (population, etc.)

	private Canva mainCanva ;
	
	private ArrayList<Artro> artros ;
	private ArrayList<Species> species ;
	private ArrayList<Food> food ;
	private ArrayList<FoodType> foodTypes ;
	
	private int round ;		// number of the current iteration
	private int roundDuration ;	// amount of time between rounds
	
	private int foodRespawnTime ;	// time taken for the food to respawn (counted in number of rounds)
	private int maxNumberFood ;	// maximum amount of food that can exist at any given time

	//public static Color[] colorPalette ;	// colors used
	
	public Evolution()
	{
		// initialize the JPanel and puts it inside the JFrame
		InitializeJPanel();
		
		
		// initialize container
		container = getContentPane();
		FlowLayout layout = new FlowLayout() ;
		layout.setAlignment(FlowLayout.LEFT) ;
		container.setLayout(layout) ;
		
		
		// initialize global variables
		//colorPalette = UtilS.ColorPalette(0) ;
		AddButtons() ;
		roundDuration = 8 ;
		simulationIsRunning = true ;
		graphsAreVisible = true ;
		
		RE = new Records() ;	
		
		
		// create canva
		Point canvaPos = new Point(100, 5) ;
		Dimension canvaSize = new Dimension(500, 500) ;
		Dimension canvaDimension = new Dimension(500, 500) ;
		mainCanva = new Canva(canvaPos, canvaSize, canvaDimension) ;		

		
		// load input
		LoadSpecies() ;
		LoadArtros() ;
		LoadFoodTypes() ;
		LoadFood() ;
		
		foodRespawnTime = 20 ;
		maxNumberFood = 200 ;		
		
		
		// clear Results file
		Output.ClearFile("Results.txt") ;		
		
		
		// set up super frame
		setTitle("Evolution");	// Sets super frame title
		setSize(900, 550);		// Sets super frame window size
		setVisible(true);		// Shows super frame
	}	
	
	
	
	private void InitializeJPanel() 
	{
        mainJPanel = new MyJPanel(this.getSize());			// creates a personalized JPanel
        mainJPanel.setBackground(new Color(250, 240, 220));					// set background color
        mainJPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));	// set panel border
        
        mainJPanel.addMouseListener(new MouseAdapter() 
        {
			public void mousePressed(MouseEvent evt)
			{
				
			}
			public void mouseReleased(MouseEvent evt) 
			{

		    }
		});
        mainJPanel.addMouseMotionListener(new MouseMotionAdapter() 
        {
            public void mouseDragged(MouseEvent evt) 
            {

            }
        });
        mainJPanel.addMouseWheelListener(new MouseWheelListener()
        {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) 
			{
				
			}       	
        });
        mainJPanel.addKeyListener(new KeyListener()
        {
			@Override
			public void keyPressed(KeyEvent evt)
			{
				int key = evt.getKeyCode();
				System.out.println(1);
				if (key == KeyEvent.VK_ESCAPE)
				{
					
				}
			}

			@Override
			public void keyReleased(KeyEvent evt)
			{
				
			}

			@Override
			public void keyTyped(KeyEvent evt)
			{
				
			}        	
        });
        
        this.setContentPane(mainJPanel);	// adds the component to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// sets "close on x" behavior
        pack();
    }
	
	
	public void AddButtons()
	{	
		/* Defining Button Icons */
		String imagesPath = ".\\Icons\\";
		ImageIcon playIcon = new ImageIcon(imagesPath + "PlayIcon.png");
		ImageIcon graphsIcon = new ImageIcon(imagesPath + "GraphsIcon.png");
		
		/* Defining Buttons */
		Color BGColor = Color.blue ;
		JButton playButton = UtilS.AddButton("", playIcon, new int[2], new Dimension(30, 30), null);
		JButton graphsButton = UtilS.AddButton("", graphsIcon, new int[2], new Dimension(30, 30), null);
		JButton saveButton = UtilS.AddButton("Save", null, new int[2], new Dimension(30, 30), null);
		container.add(playButton);
		container.add(graphsButton);
		container.add(saveButton);
		
		/* Defining button actions */
		playButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				simulationIsRunning = !simulationIsRunning;
				//Results Re = new Results();
				//Re.SaveOutputFile("Output.txt", SpeciesPopHist, FoodHist);
			}
		});
		graphsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				graphsAreVisible = !graphsAreVisible;
			}
		});
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Output.UpdateOutputFile("Results.txt", RE) ;
			}
		});
		
		//playButton = UtilS.AddButton("", null, new int[2], new Dimension(110, 30), BGColor);
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
	
	public void LoadSpecies()
	{
		// read input file
		Object data = ReadJson("Species.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
        
		// create species
		species = new ArrayList<>() ;
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
	}
	
	public void LoadArtros()
	{
		// read input file
		Object data = ReadJson("Artros.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		
		// create artros
        artros = new ArrayList<>() ;
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
	}
	
	public void LoadFoodTypes()
	{
		// read input file
		Object data = ReadJson("FoodTypes.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		        
        // create food types
		foodTypes = new ArrayList<>() ;
		
		for (int i = 0 ; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
			int size = (int)(long) jsonObject.get("Size") ;
			int value = (int)(long) jsonObject.get("Value") ;
			JSONArray colorArray= (JSONArray)jsonObject.get("Color");
        	Color color = new Color((int)(long)colorArray.get(0), (int)(long)colorArray.get(1), (int)(long)colorArray.get(2)) ;
        	
        	foodTypes.add(new FoodType(size, value, color)) ;
		}
	}
	
	public void LoadFood()
	{
		// read input file
		Object data = ReadJson("Food.json") ;
        
        //convert Object to JSONArray
        JSONArray jsonArray= (JSONArray)data;
		        
        // create food
		food = new ArrayList<>() ;
		
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
		
	public static Artro FindClosestOpponent(Artro artro)
	{
	    Artro closestOpponent = null;
	    /*double minDist = artro.getSpecies().getVision();
	    if (artrosInQuadrant.get(artro.getQuadrant()) != null)
	    {
		    for (int i = 0; i <= artrosInQuadrant.get(artro.getQuadrant()).size() - 1; i += 1)
	        {
		    	Artro opponent = artrosInQuadrant.get(artro.getQuadrant()).get(i);
		    	double opponentDist = UtilS.dist(artro.getPos(), opponent.getPos());
	            if (opponent != artro & opponent.getSpecies() == artro.getSpecies() & opponentDist < minDist & opponentDist <= artro.getSpecies().getVision())
	            {
	                closestOpponent = opponent;
	            }
	        }
	    }*/
	    
        return closestOpponent;
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
	
	public void RecordArtrosPop()
	{
		int maxNumberRegisters = 1000 ;
		int currentPop = artros.size() ;
		
		// update maximum number of artros ever registered
		if (RE.maxArtroPopEver < currentPop)
		{
			RE.maxArtroPopEver = currentPop ;
		}
		
		// update the latest registers to show in the graph
		if (RE.artrosPop.size() <= maxNumberRegisters - 1)
		{
			RE.artrosPop.add(currentPop) ;
		}
		else
		{
			RE.artrosPop.remove(0) ;
			RE.artrosPop.add(currentPop) ;
		}
	}
	
	public void RunSimulation()
	{		
		mainCanva.Display(DP) ;
		
		if (round % roundDuration == 0)
		{
			for (int i = 0 ; i <= artros.size() - 1 ; i += 1)
			{				
				Artro artro = artros.get(i) ;
				
				artro.Thinks() ;
				artro.Acts(mainCanva, food, artros) ;
				artro.IncHunger() ;
				artro.IncMateWill() ;
				
				if (artro.getLife() == 0)
				{
					artros.remove(artro) ;
				}
			}
			
			RecordArtrosPop() ;
		}
		if (round % foodRespawnTime == 0 & food.size() < maxNumberFood)
		{
			CreateFood() ;
		}
		
		for (Food food : food)
		{
			food.Display(mainCanva, DP) ;
		}
		for (Artro artro : artros)
		{
			artro.Display(mainCanva, DP) ;
		}  

		
		if (round % 7992 == 0)
		{			
			for (int i = 0 ; i <= RE.artrosPop.size() - 1; i += 1)
			{
				if (i % 10 == 0)
				{
					RE.artrosPopAtNRounds.add(RE.artrosPop.get(i)) ;
				}
			}
			// append to results file
			Output.UpdateOutputFile("Results.txt", RE) ;
		}
		
		
		// color-coding the artros based on their will for debugging purposes
		if (0 < artros.size())
		{
			for (Artro artro : artros)
			{
				Color color = null ;
				if (artro.getWill().equals(ArtroChoices.eat))
				{
					color = Color.cyan ;
					
					if (artro.FindFoodInRange(food, artro.getSpecies().getVision()) != null)
					{
						color = Color.magenta ;
					}
				}
				if (artro.getWill().equals(ArtroChoices.mate))
				{
					color = Color.orange ;
				}

				Point drawingPos = UtilS.ConvertToDrawingCoords(artro.getPos(), mainCanva.getPos(), mainCanva.getSize(), mainCanva.getDimension()) ;
				DP.DrawCircle(drawingPos, artro.getSpecies().getSize() / 2, null, color) ;
			
			} 			
		}
		
		if (simulationIsRunning)
		{
			round += 1 ;
		}
		
		if (graphsAreVisible)
		{
			Point graphPos = new Point(650, 150) ;
			ArrayList<ArrayList<Double>> recordsPop = new ArrayList<>() ;
			ArrayList<Double> artrosPopAsDoubleList = RE.ArrayListToDouble(RE.artrosPop) ;
			recordsPop.add(artrosPopAsDoubleList) ;
			DP.PlotGraph(graphPos, "Population", recordsPop, RE.maxArtroPopEver, new Color[] {Color.blue, Color.green}) ;
		}
		
		repaint() ;
	}
	
	
	public class MyJPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		public MyJPanel(Dimension panelSize) 
	    {
	        setPreferredSize(panelSize);	// sets a preferred size for the custom panel
	    }
		
	    @Override
	    public void paintComponent(Graphics g) 
	    {
	        super.paintComponent(g);
	        DP = new DrawingOnAPanel(g);
	        RunSimulation() ;
	    }
	}
}