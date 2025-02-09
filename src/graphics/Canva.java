package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Canva
{
	Point pos;
	Dimension size;
	Dimension dim;

	private static final Color bgColor = new Color(30, 30, 40);

	public Canva(Point pos, Dimension size, Dimension dim)
	{
		this.pos = pos;
		this.size = size;
		this.dim = dim;
	}

	public Point getPos()
	{
		return pos;
	}

	public Dimension getSize()
	{
		return size;
	}

	public Dimension getDimension()
	{
		return dim;
	}

	public void display(DrawPrimitives DP)
	{
		DP.drawRect(pos, Align.topLeft, size, 2, bgColor, Color.black, 1.0);
	}
}
