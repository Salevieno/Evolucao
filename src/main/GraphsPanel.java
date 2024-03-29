package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import graphics.DrawingOnPanel;
import graphics.Graphic;

public class GraphsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private List<Graphic> plots ;
	private Records records ;
	private DrawingOnPanel DP ;

	public GraphsPanel(Dimension frameDimensions)
	{
		initializePanel(frameDimensions) ;
		plots = new ArrayList<>() ;
		records = new Records() ;
		

		Graphic graphPop = new Graphic(new Point(10, 200), "Population", 100, Color.blue, Color.black, records.maxArtroPopEver,
				new Color[] {Color.blue, Color.green}) ;
		plots.add(graphPop) ;
	}
	
	public void initializePanel(Dimension frameDimensions)
	{

		this.setPreferredSize(new Dimension(105, frameDimensions.height - 40));
		this.setBackground(new Color(250, 240, 220));	// set background color
		
	}
	
	
	
	public void updateRecords()
	{
		ArrayList<ArrayList<Double>> recordsPop = new ArrayList<>() ;
		ArrayList<Double> artrosPopAsDoubleList = records.ArrayListToDouble(records.artrosPop) ;
		recordsPop.add(artrosPopAsDoubleList) ;
		
		plots.get(0).setYValues(recordsPop) ;
		
	}

	public void displayGraphs()
	{
		System.out.println(plots.get(0).getyValues());
		for (Graphic plot : plots)
		{
			plot.display(DP) ;
		}
	}
	
	@Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        DP = new DrawingOnPanel(g);
        displayGraphs() ;
    }
}
