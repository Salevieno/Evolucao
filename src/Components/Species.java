package Components;

import java.awt.Color;

public class Species
{
	private int size ;		// size of the species (in pixels)
	private int step ;		// distance the species walks each round (in pixels)
	private int vision ;	// radius of vision (in pixels)
	private int stomach ;	// amount of food the species can hold
	private Color color ;	// color of the species
	
	public Species(int size, int step, int vision, int stomach, Color color)
	{
		this.size = size ;
		this.step = step ;
		this.vision = vision ;
		this.stomach = stomach ;
		this.color = color ;
	}
	
	public int getSize() {return size ;}
	public int getStep() {return step ;}
	public int getVision() {return vision ;}
	public int getStomach() {return stomach ;}
	public Color getColor() {return color ;}
}
