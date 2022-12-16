package components;

import java.awt.Color;

public class FoodType
{
	private int size ;		// size of this type of fruit
	private int value ;		// amount of satiation this type of food restores
	private Color color ;	// colors of this type of fruit
	
	public FoodType(int size, int value, Color color)
	{
		this.size = size ;
		this.value = value ;
		this.color = color ;
	}
	
	public int getSize() {return size ;}
	public int getValue() {return value ;}
	public Color getColor() {return color ;}
}
