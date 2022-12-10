package Main;

import java.awt.Color;
import java.awt.Container;
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


public class Evolution2 extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static Container container;
	private static JPanel mainJPanel;

	private DrawingOnAPanel DP;	// class with the drawing functions

	private Canva mainCanva ;
	
	private ArrayList<Artro> artros ;
	public static ArrayList<Species> species ;
	private ArrayList<Food> food ;
	private ArrayList<FoodType> foodTypes ;
	private static ArrayList<ArrayList<Artro>> artrosInQuadrant ;
	
	private int round ;		// number of the current iteration
	private int roundDuration ;	// amount of time between rounds

	public static Color[] colorPalette ;	// colors used
	
	public Evolution2()
	{
		InitializeJPanel();	// initialized the JPanel and puts it inside the JFrame
		
		container = getContentPane();	// defines container

		colorPalette = UtilS.ColorPalette(0) ;
		roundDuration = 10 ;
		
		// initialization
		// create canva
		mainCanva = new Canva(
				new Point(450, 50),
				new Dimension(500, 500),
				new Dimension(500, 500),
				25
				) ;
		artrosInQuadrant = new ArrayList<>() ;
		for (int i = 0 ; i <= mainCanva.getQuadrants().length - 1 ; i += 1)
		{
			artrosInQuadrant.add(new ArrayList<>()) ;
		}
		
		// create species
		species = new ArrayList<>() ;
		species.add(new Species(10, 1, 100, 100, colorPalette[6])) ;
		
		// create artros
		artros = new ArrayList<>() ;
		for (int i = 0 ; i <= 10 - 1 ; i += 1)
		{
			artros.add(new Artro(100, new Point(i * 50, 50), 0, species.get(0),
					new double[] {1}, false, 100, ArtroChoices.exist, 1, Directions.up, 0)) ;
		}
		
		// create food types
		foodTypes = new ArrayList<>() ;
		foodTypes.add(new FoodType(10, colorPalette[3])) ;
		
		// create food
		food = new ArrayList<>() ;
		for (int i = 0 ; i <= 10 - 1 ; i += 1)
		{
			food.add(new Food(new Point(i * 50, 200), foodTypes.get(0))) ;
		}
		
		/*
		AddButtons();
		AddMenus();
		AddMenuActions(); */
		setTitle("Evolution");	// Sets super frame title
		setSize(1250, 680);		// Sets super frame window size
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


	public static Artro FindClosestOpponent(Artro artro)
	{
	    Artro closestOpponent = null;
	    double minDist = artro.getSpecies().getVision();
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
	    }
	    
        return closestOpponent;
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
				artro.Starve() ;
				artro.Move(mainCanva) ;
				
				if (artro.getLife() == 0)
				{
					artros.remove(artro) ;
				}
			}
			//System.out.println(round);
		}
		
		for (int i = 0; i <= food.size() - 1; i += 1)
		{
			food.get(i).Display(mainCanva, DP) ;
		}
		for (int i = 0; i <= artros.size() - 1; i += 1)
		{
			artros.get(i).Display(mainCanva, DP) ;
		}  
		
		Point drawingPos = UtilS.ConvertToDrawingCoords(artros.get(0).getPos(), mainCanva.getPos(), mainCanva.getSize(), mainCanva.getDimension()) ;
		DP.DrawText(drawingPos, "Center", 0, String.valueOf(artros.get(0).getWill()), new Font("SansSerif", Font.BOLD, 13), Color.black) ;
	
		round += 1 ;
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