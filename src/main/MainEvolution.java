package main ;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame ;
import javax.swing.JPanel;

/**
 * Made with love by Salevieno
 * @author Salevieno
 * @version 1.0
 * @since December 09, 2022
 **/
public class MainEvolution extends JFrame
{
	private static final long serialVersionUID = 1L ;

	
	public static void main (String[] args) 
	{
		Dimension screenDimension = new Dimension(640, 480) ;
		
		// creating frame and setting size
		JFrame frame = new JFrame("Evolution") ;
		frame.setSize(screenDimension) ;
		
		// creating and setting layout
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0) ;
		
		// adding layout to container
		Container container = frame.getContentPane();
		container.setLayout(layout) ;
		
		// creating panels and adding to frame
		ButtonsPanel buttonsPanel = new ButtonsPanel(screenDimension) ;
		GraphsPanel graphsPanel = new GraphsPanel(screenDimension) ;
		Evolution canvaPanel = new Evolution(screenDimension) ;
		frame.add(buttonsPanel) ;
		frame.add(graphsPanel) ;
		frame.add(canvaPanel) ;
		
		// final frame settings
		frame.setVisible(true) ;
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		
		new Simulation(buttonsPanel, graphsPanel, canvaPanel) ;
	}
}