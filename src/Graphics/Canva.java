package Graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import Components.Quadrant;
import Main.Evolution;

public class Canva
{
	Point pos ;
	Dimension size ;
	Dimension dim ;
	Quadrant[][] quadrants ;
	
	public Canva(Point pos, Dimension size, Dimension dim, int numberQuadrants)
	{
		this.pos = pos ;
		this.size = size ;
		this.dim = dim ;
		quadrants = new Quadrant[numberQuadrants][numberQuadrants] ;
		InitializeQuadrants() ;
	}
	
	public Point getPos() {return pos ;}
	public Dimension getSize() {return size ;}
	public Dimension getDimension() {return dim ;}
	public Quadrant[][] getQuadrants() {return quadrants ;}
	
	public ArrayList<Quadrant> getQuadrantsAsList()
	{
		ArrayList<Quadrant> quad = new ArrayList<>() ;
		for (int row = 0; row <= quadrants.length - 1; row += 1)
		{
			for (int col = 0; col <= quadrants[row].length - 1; col += 1)
			{
				quad.add(quadrants[row][col]) ;
			}
		}
		return quad ;
	}
	
	public void InitializeQuadrants()
	{
		int numberRows = quadrants.length ;
		int numberCols = quadrants.length ;
		Dimension quadSize = new Dimension(size.width / numberRows, size.width / numberCols) ;
		for (int row = 0; row <= numberRows - 1; row += 1)
		{
			for (int col = 0; col <= numberCols - 1; col += 1)
			{
				quadrants[row][col] = new Quadrant(new Point(row * quadSize.width, col * quadSize.height), quadSize) ;
			}
		}
	}
	
	public Quadrant FindQuadrant(Point pos)
	{
		for (int row = 0; row <= quadrants.length - 1; row += 1)
		{
			for (int col = 0; col <= quadrants[row].length - 1; col += 1)
			{
				if (quadrants[row][col].ContainsPoint(pos))
				{
					return quadrants[row][col] ;
				}
			}
		}
		
		System.out.println("Position " + pos + " outside of all quadrants!");
		return null ;
	}
	
	public void Display(DrawingOnAPanel DP)
	{
		DP.DrawRect(pos, "TopLeft", size, 2, Evolution.colorPalette[4], Evolution.colorPalette[5]) ;
	}
	
	public void DisplayQuadrants(DrawingOnAPanel DP)
	{
		for (int row = 0; row <= quadrants.length - 1; row += 1)
		{
			for (int col = 0; col <= quadrants[row].length - 1; col += 1)
			{
				quadrants[row][col].Display(pos, DP) ;
			}
		}
	}
}
