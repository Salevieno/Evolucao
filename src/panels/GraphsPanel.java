package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import graphics.DrawingOnPanel;
import graphics.Graph;
import main.MainEvolution;
import main.Records;

public abstract class GraphsPanel
{
	private static final JPanel panel ;
	private static final List<Graph> plots ;
	private static DrawingOnPanel DP ;

	private static boolean graphsAreVisible ;
	private static final Color bgColor ;

	static
	{
		graphsAreVisible = true ;
		bgColor = new Color(50, 50, 50) ;
		
		panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			public void displayGraphs()
			{
				plots.forEach(plot -> plot.display(DP)) ;
			}
			
			@Override
		    public void paintComponent(Graphics g) 
		    {
		        super.paintComponent(g);
		        DP = new DrawingOnPanel(g);
		        if (graphsAreVisible)
		        {
			        displayGraphs() ;
		        }
		    }
		};
		panel.setPreferredSize(new Dimension(150, MainEvolution.screenSize.height - 40));
		panel.setBackground(bgColor);
		
		plots = new ArrayList<>() ;		

		plots.add(new Graph(new Point(45, 200), "Population", 80)) ;
		plots.add(new Graph(new Point(45, 350), "fps", 80, Color.orange)) ;
	}
	
	public static JPanel getPanel() { return panel ;}

	public static void switchGraphsAreVisible() { graphsAreVisible = !graphsAreVisible ;}
	
	public static void updateRecords()
	{
		List<List<Double>> popRecords = new ArrayList<>() ;
		List<Double> artrosPopAsDoubleList = Records.artrosPop.stream().map(i -> (double) i).collect(Collectors.toList()) ;
		popRecords.add(artrosPopAsDoubleList) ;
		List<List<Double>> fpsRecords = new ArrayList<>() ;
		List<Double> fpsRecordsAsDoubleList = Records.fpsList.stream().map(i -> (double) i).collect(Collectors.toList()) ;
		fpsRecords.add(fpsRecordsAsDoubleList) ;
		
		plots.get(0).setYValues(popRecords) ;
		plots.get(1).setYValues(fpsRecords) ;
		panel.repaint() ;
	}

}
