package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Graphic
{
	private Point pos ;
	private String title ;
	private int size ;
	private Color titleColor ;
	private Color lineColor ;
	private List<ArrayList<Double>> yValues ;
	private double maxYEver ;
	private Color[] dataSetColor ;
	
	
	public Graphic(Point pos, String title, int size, Color titleColor, Color lineColor,
			double maxYEver, Color[] dataSetColor)
	{
		super();
		this.pos = pos;
		this.title = title;
		this.size = size;
		this.titleColor = titleColor;
		this.lineColor = lineColor;
		yValues = new ArrayList<ArrayList<Double>>();
		this.maxYEver = maxYEver;
		this.dataSetColor = dataSetColor;
	}

	public Point getPos()
	{
		return pos;
	}

	public String getTitle()
	{
		return title;
	}

	public int getSize()
	{
		return size;
	}

	public Color getTitleColor()
	{
		return titleColor;
	}

	public Color getLineColor()
	{
		return lineColor;
	}

	public List<ArrayList<Double>> getyValues()
	{
		return yValues;
	}

	public double getMaxYEver()
	{
		return maxYEver;
	}

	public Color[] getDataSetColor()
	{
		return dataSetColor;
	}

	public void setYValues(List<ArrayList<Double>> yValues)
	{
		this.yValues = yValues;
	}
	

	public void DrawGraph(DrawingOnPanel DP)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 13) ;
		int arrowSize = 8 * size / 100;
		DP.DrawText(new Point (pos.x + size/5, (int) (pos.y - size - 13)), "TopLeft", 0, title, textFont, titleColor);
		DP.DrawLine(pos, new Point (pos.x, (int) (pos.y - size - arrowSize)), 2, lineColor);
		DP.DrawLine(pos, new Point ((int) (pos.x + size + arrowSize), pos.y), 2, lineColor);
		DP.DrawArrow(new Point (pos.x + size + arrowSize, pos.y), arrowSize, 0, false, 0.4 * arrowSize, lineColor);
		DP.DrawArrow(new Point (pos.x, pos.y - size - arrowSize), arrowSize, Math.PI / 2, false, 0.4 * arrowSize, lineColor);
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
		DP.DrawGrid(pos, new Point (pos.x + size, pos.y - size), 10, Color.black);
	}
	
	
	public void display(DrawingOnPanel DP)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 12) ;
		DrawGraph(DP);
		if (1 <= yValues.size())
		{
			Point maxYPos = new Point (pos.x - size / 4, pos.y - size) ;
			DP.DrawText(maxYPos, "TopLeft", 0, String.valueOf(maxYEver), textFont, Color.blue);
			
			// for each data set
			for (int j = 0; j <= yValues.size() - 1; j += 1)
			{
				ArrayList<Integer> xPos = new ArrayList<>() ;
				ArrayList<Integer> yPos = new ArrayList<>() ;
				for (int i = 0; i <= yValues.get(j).size() - 1; i += 1)
				{
					if (2 <= yValues.get(j).size())
					{
						xPos.add(pos.x + size * i / (yValues.get(j).size() - 1)) ;
					}
					else
					{
						xPos.add(pos.x + size * i) ;
					}
					ArrayList<Double> yList = yValues.get(j) ;
					double y = Double.parseDouble(String.valueOf(yList.get(i))) ;
					yPos.add((pos.y - (int) (size * y / maxYEver))) ;
				}
				DP.DrawPolyLine(xPos, yPos, 2, dataSetColor[j]);
			}
		}
	}
}
