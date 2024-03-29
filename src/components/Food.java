package components;

import java.awt.Point;

import graphics.Canva;
import graphics.DrawingOnPanel;
import main.Evolution;
import main.UtilS;

public class Food
{
	private Point pos ;
	private FoodType type ;
	
	public Food(Point pos, FoodType type)
	{
		this.pos = pos ;
		this.type = type ;
	}
	
	public Point getPos() {return pos ;}
	public FoodType getType() {return type ;}
	
	public void display(Canva canva, DrawingOnPanel DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension());
		DP.DrawCircle(drawingPos, type.getSize(), null, type.getColor());
	}
}
