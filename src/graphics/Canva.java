package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Canva
{
	private Point pos;
	private Dimension size;
	private Dimension dim;

	private static final Color bgColor = new Color(30, 30, 40);

	public Canva(Point pos, Dimension size, Dimension dim)
	{
		this.pos = pos;
		this.size = size;
		this.dim = dim;
	}

	public Point inDrawingCoords(Point2D.Double originalCoords)
	{
		int xCoord = (int) (pos.x + originalCoords.x * size.width / dim.width);
		int yCoord = (int) (pos.y + size.height - originalCoords.y * size.height / dim.height);
		return new Point(xCoord, yCoord);
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
