package Components;

import java.awt.Color;

public class FoodType
{
	private int size ;
	private Color color ;
	
	public FoodType(int size, Color color)
	{
		this.size = size ;
		this.color = color ;
	}
	
	public int getSize() {return size ;}
	public Color getColor() {return color ;}
}
