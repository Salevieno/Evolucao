package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph
{
	private Point pos ;
	private String title ;
	private int size ;
	private Color titleColor ;
	private Color lineColor ;
	private List<List<Double>> yValues ;
	private double maxYEver ;
	private Color[] dataSetColor ;
	public static final Color[] stdColors ;
	private static final Font textFont ;
	// TODO criar classe DataSet
	
	static
	{
		stdColors = new Color[] {Color.cyan, Color.green} ;
		textFont = new Font("SansSerif", Font.BOLD, 11) ;
	}
	
	public Graph(Point pos, String title, int size, Color titleColor, Color lineColor, Color[] dataSetColor)
	{
		this.pos = pos;
		this.title = title;
		this.size = size;
		this.titleColor = titleColor;
		this.lineColor = lineColor;
		yValues = new ArrayList<List<Double>>();
		this.dataSetColor = dataSetColor;
	}
	
	public Graph(Point pos, String title, int size, Color dataSetColor)
	{
		this(pos, title, size, Color.white, Color.black, new Color[] {dataSetColor}) ;
	}
	
	public Graph(Point pos, String title, int size)
	{
		this(pos, title, size, Color.white, Color.black, stdColors) ;
	}

	public void setYValues(List<List<Double>> yValues)
	{
		this.yValues = yValues;
		
		if (yValues.isEmpty()) { return ;}
		if (yValues.get(0).isEmpty()) { return ;}
		
		maxYEver = Collections.max(yValues.get(0)) ;
	}
	

	public void DrawGraph(DrawingOnPanel DP)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 13) ;
		int arrowSize = 8 * size / 100;
		DP.DrawGrid(pos, new Point (pos.x + size, pos.y - size), 10, new Color(250, 250, 250, 50));
		DP.DrawText(new Point (pos.x, (int) (pos.y - size - 13)), "Center", 0, title, textFont, titleColor);
		DP.DrawLine(pos, new Point (pos.x, (int) (pos.y - size - arrowSize)), 2, lineColor);
		DP.DrawLine(pos, new Point ((int) (pos.x + size + arrowSize), pos.y), 2, lineColor);
		DP.DrawArrow(new Point (pos.x + size + arrowSize, pos.y), arrowSize, 0, false, 0.4 * arrowSize, lineColor);
		DP.DrawArrow(new Point (pos.x, pos.y - size - arrowSize), arrowSize, Math.PI / 2, false, 0.4 * arrowSize, lineColor);
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
	}
	
	
	public void display(DrawingOnPanel DP)
	{
		DrawGraph(DP);
		if (1 <= yValues.size())
		{
			Point titlePos = new Point (pos.x - size / 2, pos.y - size) ;
			DP.DrawText(titlePos, "TopLeft", 0, String.valueOf(maxYEver), textFont, Color.white);
			
			// for each data set
			for (int j = 0; j <= yValues.size() - 1; j += 1)
			{
				List<Integer> xPos = new ArrayList<>() ;
				List<Integer> yPos = new ArrayList<>() ;
				Color color = j <= dataSetColor.length - 1 ? dataSetColor[j] : Color.white ;
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
					List<Double> yList = yValues.get(j) ;
					double y = Double.parseDouble(String.valueOf(yList.get(i))) ;
					yPos.add((pos.y - (int) (size * y / maxYEver))) ;
				}
				DP.DrawPolyLine(xPos, yPos, 2, color);
			}
		}
	}
	
}
