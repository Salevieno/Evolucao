package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ButtonsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final Dimension STD_BUTTON_SIZE = new Dimension(30, 30) ;

	public ButtonsPanel(Dimension frameDimensions)
	{
		initializePanel(frameDimensions) ;
		
		// defining button icons
		ImageIcon playIcon = new ImageIcon(".\\Icons\\PlayIcon.png");
		ImageIcon graphsIcon = new ImageIcon(".\\Icons\\GraphsIcon.png");
		
		// creating and adding buttons to frame
		JButton playButton = UtilS.createButton("", playIcon, new int[2], STD_BUTTON_SIZE, null);
		JButton graphsButton = UtilS.createButton("", graphsIcon, new int[2], STD_BUTTON_SIZE, null);
		JButton saveButton = UtilS.createButton("Save", null, new int[2], STD_BUTTON_SIZE, null);
		this.add(playButton);
		this.add(graphsButton);
		this.add(saveButton);
		
		// Defining button actions
		playButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				playButtonAction() ;
			}
		});
		graphsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				graphsButtonAction() ;
			}
		});
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				saveButtonAction() ;
			}
		});
		
	}
	
	public void initializePanel(Dimension frameDimensions)
	{

		this.setPreferredSize(new Dimension(frameDimensions.width, 40));
		this.setBackground(new Color(250, 240, 220));	// set background color
		
	}
	
	public void playButtonAction()
	{
		//simulationIsRunning = !simulationIsRunning;
		//Results Re = new Results();
		//Re.SaveOutputFile("Output.txt", SpeciesPopHist, FoodHist);
	}
	
	public void graphsButtonAction()
	{
		//graphsAreVisible = !graphsAreVisible;
	}
	
	public void saveButtonAction()
	{
		//Output.UpdateOutputFile("Results.txt", RE) ;
	}
}
