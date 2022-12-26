package main ;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame ;

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
		
		JFrame frame = new JFrame("Jogo de 1 dia") ;
		frame.setTitle("Evolution") ;
		frame.setSize(screenDimension) ;
		frame.add(new Evolution(screenDimension)) ;
		frame.setVisible(true) ;
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		
		Container container = frame.getContentPane();
		FlowLayout layout = new FlowLayout() ;
		layout.setAlignment(FlowLayout.LEFT) ;
		container.setLayout(layout) ;
	}
}