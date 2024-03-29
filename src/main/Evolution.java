package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import components.Artro;
import components.Food;
import graphics.Canva;
import graphics.DrawingOnPanel;


public class Evolution extends JPanel
{
	private static final long serialVersionUID = 1L;

	private DrawingOnPanel DP;	// class with the drawing methods

	private Canva mainCanva ;

	//public static Color[] colorPalette ;	// colors used
	
	public Evolution(Dimension frameDimension)
	{
		
		// initialize the JPanel
		InitializeJPanel(frameDimension);	
		
		// create canva
		Point canvaPos = new Point(100, 5) ;
		Dimension canvaSize = new Dimension(500, 500) ;
		Dimension canvaDimension = new Dimension(500, 500) ;
		mainCanva = new Canva(canvaPos, canvaSize, canvaDimension) ;	
		
		DP = new DrawingOnPanel(null) ;
	}		
	
	
	public Canva getMainCanva()
	{
		return mainCanva;
	}


	public DrawingOnPanel getDP()
	{
        System.out.println(DP.getG());
		return DP;
	}

	public void displayCanva()
	{
		mainCanva.display(DP) ;
	}

	public void displayArtros(List<Artro> artros)
	{
		for (Artro artro : artros)
		{
			artro.display(mainCanva, DP) ;
		} 
	}

	public void displayFood(List<Food> foods)
	{
		for (Food food : foods)
		{
			food.display(mainCanva, DP) ;
		}
	}

	private void InitializeJPanel(Dimension frameDimension) 
	{
		JPanel panel = this;
       // mainJPanel = new MyJPanel(this.getSize());			// creates a personalized JPanel
        panel.setBackground(new Color(250, 240, 220));	// set background color
        panel.setPreferredSize(new Dimension (frameDimension.width - 120, frameDimension.height - 40));	
        //panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));	// set panel border
        
        panel.addMouseListener(new MouseAdapter() 
        {
			public void mousePressed(MouseEvent evt)
			{
				
			}
			public void mouseReleased(MouseEvent evt) 
			{

		    }
		});
        panel.addMouseMotionListener(new MouseMotionAdapter() 
        {
            public void mouseDragged(MouseEvent evt) 
            {

            }
        });
        panel.addMouseWheelListener(new MouseWheelListener()
        {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) 
			{
				
			}       	
        });
        panel.addKeyListener(new KeyListener()
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
        
        /*this.setContentPane(mainJPanel);	// adds the component to the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// sets "close on x" behavior
        pack();*/
    }
		
	
	
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        DP.setG((Graphics2D) g) ;
        
        displayCanva() ;
        System.out.println(DP.getG());
    }
}