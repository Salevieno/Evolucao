package main;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import panels.ButtonsPanel;
import panels.CanvaPanel;
import panels.GraphsPanel;

/**
 * Made with love by Salevieno
 * 
 * @author Salevieno
 * @version 2.0
 * @since December 09, 2022
 **/
public class MainEvolution extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static final Dimension screenSize = new Dimension(1280, 800);

	public MainEvolution()
	{

		// creating frame and setting size
		this.setTitle("Evolution");
		this.setSize(screenSize);
		this.setLocation(200, 20);

		// creating and setting layout
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		this.getContentPane().setLayout(layout);

		Evolution.initialize();
		// creating panels and adding to frame
		this.add(new ButtonsPanel(screenSize));
		this.add(GraphsPanel.getPanel());
		this.add(new CanvaPanel(screenSize));

		// final frame settings
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args)
	{
		new MainEvolution();
	}
}