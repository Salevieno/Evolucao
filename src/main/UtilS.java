package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.json.simple.parser.JSONParser;

public abstract class UtilS
{

	public static Object ReadJson(String filePath)
	{
		JSONParser parser = new JSONParser();
		try
		{
			Object jsonData = parser.parse(new FileReader(filePath));
			return jsonData;
		} catch (FileNotFoundException fe)
		{
			fe.printStackTrace();
			return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Point ConvertToDrawingCoords(Point originalCoords, Point canvasPos, Dimension canvasSize,
			Dimension canvasDim)
	{
		int xCoord = (int) (canvasPos.x + originalCoords.x * canvasSize.width / canvasDim.width);
		int yCoord = (int) (canvasPos.y + canvasSize.height - originalCoords.y * canvasSize.height / canvasDim.height);
		return new Point(xCoord, yCoord);
	}

	public static double dist(Point Point1, Point Point2)
	{
		return Math.sqrt(Math.pow(Point2.x - Point1.x, 2) + Math.pow(Point2.y - Point1.y, 2));
	}

	public static Point RandomPosAroundPoint(Point center, Dimension range)
	{
		// returns a point within a rectangle centered on the Point center with size 2 *
		// range
		int x = (int) (center.x + range.width * (Math.random() - Math.random()));
		int y = (int) (center.y + range.height * (Math.random() - Math.random()));
		return new Point(x, y);
	}

	public static boolean chance(double p)
	{
		return Math.random() < p;
	}
}