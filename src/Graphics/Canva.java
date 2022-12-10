package Graphics;

import java.awt.Dimension;
import java.awt.Point;

import Main.Evolution2;
import Main.UtilS;

public class Canva
{
	Point pos ;
	Dimension size ;
	Dimension dim ;
	Point[] quadrants ;
	
	public Canva(Point pos, Dimension size, Dimension dim, int numberQuadrants)
	{
		this.pos = pos ;
		this.size = size ;
		this.dim = dim ;
		quadrants = new Point[numberQuadrants] ;
		InitializeQuadrants() ;
	}
	
	public Point getPos() {return pos ;}
	public Dimension getSize() {return size ;}
	public Dimension getDimension() {return dim ;}
	public Point[] getQuadrants() {return quadrants ;}
	
	public void InitializeQuadrants()
	{
		int numberXQuadrants = (int) Math.sqrt(quadrants.length) ;
		Point[] quadrantTopLeft = new Point[quadrants.length] ;
		int quadrantSize =  size.width / numberXQuadrants ;
		for (int qx = 0; qx <= numberXQuadrants - 1; qx += 1)
		{
			for (int qy = 0; qy <= numberXQuadrants - 1; qy += 1)
			{
				quadrantTopLeft[qx * numberXQuadrants + qy] = new Point(qx * quadrantSize, qy * quadrantSize) ;
			}
		}
	}
	
	public int QuadrantPosIsIn(Point pos)
	{
		int numberXQuadrants = (int) Math.sqrt(quadrants.length) ;
		int quadrantSize = size.width / numberXQuadrants ;
		for (int q = 0; q <= quadrants.length - 1; q += 1)
		{
			if (UtilS.IsInsideRect(pos, quadrants[q], new Dimension(quadrantSize, quadrantSize)))
			{
				return q ;
			}
		}
		
		System.out.println("Position outside of all quadrants!");
		return -1 ;
	}
	
	public void Display(DrawingOnAPanel DP)
	{
		DP.DrawRect(pos, "TopLeft", size, 2, Evolution2.colorPalette[4], Evolution2.colorPalette[5]) ;
	}
}
