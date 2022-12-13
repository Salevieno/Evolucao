package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import Components.Artro;
import Components.ArtroChoices;
import Components.Directions;
import Components.Food;
import Components.FoodType;
import Components.Species;
import Graphics.Canva;
import Graphics.DrawingOnAPanel;


public class Evolution extends JFrame
{
	private static final long serialVersionUID = 1L;
	//private static Container container;
	private static JPanel mainJPanel;

	private DrawingOnAPanel DP;	// class with the drawing methods

	private Canva mainCanva ;
	
	private ArrayList<Artro> artros ;
	public static ArrayList<Species> species ;
	private ArrayList<Food> food ;
	private ArrayList<FoodType> foodTypes ;
	
	private int round ;		// number of the current iteration
	private int roundDuration ;	// amount of time between rounds
	private int foodRespawnTime ;	// time taken for the food to respawn (counted in number of rounds)
	private int maxNumberFood ;	// maximum amount of food that can exist at any given time

	public static Color[] colorPalette ;	// colors used
	
	public Evolution()
	{
		InitializeJPanel();	// initialized the JPanel and puts it inside the JFrame
		
		//container = getContentPane();	// defines container
		
		// initialization
		colorPalette = UtilS.ColorPalette(0) ;
		roundDuration = 8 ;
		
		// create canva
		mainCanva = new Canva(new Point(100, 5), new Dimension(500, 500), new Dimension(500, 500)) ;		
		
		// create species
		species = new ArrayList<>() ;
		species.add(new Species(10, 1, 100, 100, 1000, colorPalette[6])) ;
		
		// create artros
		artros = new ArrayList<>() ;
		Point center = new Point(250, 250) ;
		Dimension range = new Dimension(100, 100) ;
		for (int i = 0 ; i <= 10 - 1 ; i += 1)
		{
			Point pos = UtilS.RandomPosAroundPoint(center, range) ;
			int satiation = (int) (800 + 200 * Math.random()) ;
			Map<ArtroChoices, Double> tendency = new HashMap<>() ;
			tendency.put(ArtroChoices.eat, 1.0) ;
			tendency.put(ArtroChoices.mate, 1.0) ;
			tendency.put(ArtroChoices.wander, 0.9) ;
			Artro newArtro = new Artro(100, pos, 0, species.get(0),
					tendency, false, satiation, ArtroChoices.exist, 1, Directions.up) ;
			artros.add(newArtro) ;
		}
		
		// create food types
		foodTypes = new ArrayList<>() ;
		foodTypes.add(new FoodType(10, 100, colorPalette[3])) ;
		
		// create food
		food = new ArrayList<>() ;
		center = new Point(250, 250) ;
		range = new Dimension(200, 200) ;
		for (int i = 0 ; i <= 10 - 1 ; i += 1)
		{
			Point pos = UtilS.RandomPosAroundPoint(center, range) ;
			Food newFood = new Food(pos, foodTypes.get(0)) ;
			food.add(newFood) ;
		}
		
		foodRespawnTime = 200 ;
		maxNumberFood = 20 ;
		
		/*
		AddButtons();
		AddMenus();
		AddMenuActions(); */
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
	
	public void CreateArtro()
	{
		
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
	
	public void RunSimulation()
	{		
		mainCanva.Display(DP) ;
		
		if (round % roundDuration == 0)
		{
			for (int i = 0 ; i <= artros.size() - 1 ; i += 1)
			{				
				Artro artro = artros.get(i) ;
				
				artro.Thinks() ;
				if (artro.getWill().equals(ArtroChoices.eat))
				{
					ArtroEats(artro) ;
				}
				artro.Acts(mainCanva, food, artros) ;
				artro.Starve() ;
				artro.Lust() ;
				
				if (artro.getLife() == 0)
				{
					artros.remove(artro) ;
				}
			}
			//System.out.println(round);
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
		
		
		// color-coding the artros based on their will for debugging purposes
		if (0 < artros.size())
		{
			for (Artro artro : artros)
			{
				Color color = null ;
				if (artro.getWill().equals(ArtroChoices.eat))
				{
					color = Evolution.colorPalette[0] ; // cyan
					
					if (artro.FindFoodInRange(food, artro.getSpecies().getVision()) != null)
					{
						color = Evolution.colorPalette[1] ; // magenta
					}
				}
				if (artro.getWill().equals(ArtroChoices.mate))
				{
					color = Evolution.colorPalette[2] ; // orange
				}

				Point drawingPos = UtilS.ConvertToDrawingCoords(artro.getPos(), mainCanva.getPos(), mainCanva.getSize(), mainCanva.getDimension()) ;
				DP.DrawCircle(drawingPos, artro.getSpecies().getSize(), null, color) ;
			
			} 			
		}
		//mainCanva.DisplayQuadrants(DP) ;
		
		round = (round + 1) % roundDuration ;
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