package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import Graphics.DrawingOnAPanel;

public class Quadrant
{
	Point topLeftPos ;
	Dimension size ;
	ArrayList<Artro> artros ;
	ArrayList<Food> food ;
	
	public Quadrant(Point topLeftPos, Dimension size)
	{
		this.topLeftPos = topLeftPos ;
		this.size = size ;
		artros = new ArrayList<>() ;
		food = new ArrayList<>() ;
	}
	
	public Point getTopLeftPos() {return topLeftPos ;}
	public Dimension getSize() {return size ;}
	public ArrayList<Artro> getArtrosInside() {return artros ;}
	public ArrayList<Food> getFoodInside() {return food ;}
	
	public boolean ContainsPoint(Point pos)
	{
		if (topLeftPos.x <= pos.x &
				topLeftPos.y <= pos.y &
				pos.x <= topLeftPos.x + size.width &
				pos.y <= topLeftPos.y + size.height)
			{
				return true;
			} 
			else
			{
				return false;
			}
	}
	
	public void ComputeArtrosInside(ArrayList<Artro> allArtros)
	{
		for (Artro a : allArtros)
	    {
			if (ContainsPoint(a.getPos()))
			{
				artros.add(a) ;
			}
	    }
	}
	
	public void ComputeFoodInside(ArrayList<Food> allFood)
	{
		for (Food f : allFood)
	    {
			if (ContainsPoint(f.getPos()))
			{
				food.add(f) ;
			}
	    }
	}

	public void AddArtro(Artro newArtro)
	{
		artros.add(newArtro) ;
	}
	
	public void AddFood(Food newFood)
	{
		food.add(newFood) ;
	}
	
	public void RemoveFood(Food newFood)
	{
		food.remove(newFood) ;
	}
	
	public void Display(Point canvaPos, DrawingOnAPanel DP)
	{
		DP.DrawRect(new Point(canvaPos.x + topLeftPos.x, canvaPos.y + topLeftPos.y), "TopLeft", size, 2, null, Color.black) ;
	}

	@Override
	public String toString() {
		return "Quadrant [topLeftPos=" + topLeftPos + ", size=" + size + "]";
	}
}
