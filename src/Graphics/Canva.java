package Graphics;

import java.awt.Dimension;
import java.awt.Point;

import Main.Evolution;

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
	
	public void Display(DrawingOnAPanel DP)
	{
		DP.DrawRect(pos, "TopLeft", size, 2, Evolution.colorPalette[24], Evolution.colorPalette[5]) ;
	}
}
