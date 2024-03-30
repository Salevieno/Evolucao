package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.Evolution;
import main.Output;
import main.UtilS;

public class ButtonsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final Dimension STD_BUTTON_SIZE = new Dimension(30, 30) ;
	private static final Color bgColor = new Color(50, 50, 50) ;
	
	private static ImageIcon playIcon = new ImageIcon(".\\Icons\\PlayIcon.png");
	private static ImageIcon graphsIcon = new ImageIcon(".\\Icons\\GraphsIcon.png");
	private static ImageIcon displayCanvaIcon = new ImageIcon("Artro1.png");

	public ButtonsPanel(Dimension frameDimensions)
	{
		this.setPreferredSize(new Dimension(frameDimensions.width, 40));
		this.setBackground(bgColor);
		
		// creating and adding buttons to frame
		JButton playButton = UtilS.createButton("", playIcon, new int[2], STD_BUTTON_SIZE, null);
		JButton graphsButton = UtilS.createButton("", graphsIcon, new int[2], STD_BUTTON_SIZE, null);
		JButton displayCanva = UtilS.createButton("", displayCanvaIcon, new int[2], STD_BUTTON_SIZE, null);
		JButton saveButton = UtilS.createButton("Save", null, new int[2], STD_BUTTON_SIZE, null);
		this.add(playButton);
		this.add(graphsButton);
		this.add(displayCanva);
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
		displayCanva.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				displayCanvaButtonAction() ;
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
	
	public void playButtonAction()
	{
		Evolution.switchIsRunning() ;
//		Results Re = new Results();
//		Re.SaveOutputFile("Output.txt", SpeciesPopHist, FoodHist);
	}
	
	public void graphsButtonAction()
	{
		GraphsPanel.switchGraphsAreVisible() ;
	}
	
	public void displayCanvaButtonAction()
	{
		CanvaPanel.switchDisplayCanva() ;
	}
	
	public void saveButtonAction()
	{
		Output.UpdateOutputFile() ;
	}
}
