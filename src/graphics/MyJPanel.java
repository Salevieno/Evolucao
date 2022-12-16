package graphics;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Evolution;

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
        //DP = new DrawingOnAPanel(g);
		//V = new Visuals(DP, CanvasPos, CanvasSize, CanvasDim, DrawingPos);
    }
}
