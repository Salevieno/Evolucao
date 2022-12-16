package components;

import java.awt.Color;
import java.util.Objects;

public class Species
{
	private int size ;		// size of the species (in pixels)
	private int step ;		// distance the species walks each round (in pixels)
	private int vision ;	// radius of vision (in pixels)
	private int matePoint ;	// amount of sex will necessary to make the species want to mate
	private int stomach ;	// amount of food the species can hold
	private Color color ;	// color of the species
	
	public Species(int size, int step, int vision, int matePoint, int stomach, Color color)
	{
		this.size = size ;
		this.step = step ;
		this.vision = vision ;
		this.matePoint = matePoint ;
		this.stomach = stomach ;
		this.color = color ;
	}
	
	public int getSize() {return size ;}
	public int getStep() {return step ;}
	public int getVision() {return vision ;}
	public int getMatePoint() {return matePoint ;}
	public int getStomach() {return stomach ;}
	public Color getColor() {return color ;}

	@Override
	public int hashCode() {
		return Objects.hash(color, matePoint, size, step, stomach, vision);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Species other = (Species) obj;
		return Objects.equals(color, other.color) && matePoint == other.matePoint && size == other.size
				&& step == other.step && stomach == other.stomach && vision == other.vision;
	}
}
