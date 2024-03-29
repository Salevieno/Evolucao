package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import main.Evolution;

public class Canva
{
	Point pos ;
	Dimension size ;
	Dimension dim ;
	
	public Canva(Point pos, Dimension size, Dimension dim)
	{
		this.pos = pos ;
		this.size = size ;
		this.dim = dim ;
	}
	
	public Point getPos() {return pos ;}
	public Dimension getSize() {return size ;}
	public Dimension getDimension() {return dim ;}
	
	public void display(DrawingOnPanel DP)
	{
		DP.DrawRect(pos, "TopLeft", size, 2, new Color(60, 100, 60), Color.blue) ;
	}
}
