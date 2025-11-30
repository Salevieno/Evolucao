package main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

	public static Point ConvertToDrawingCoords(Point2D.Double originalCoords, Point canvasPos, Dimension canvasSize,
			Dimension canvasDim)
	{
		int xCoord = (int) (canvasPos.x + originalCoords.x * canvasSize.width / canvasDim.width);
		int yCoord = (int) (canvasPos.y + canvasSize.height - originalCoords.y * canvasSize.height / canvasDim.height);
		return new Point(xCoord, yCoord);
	}

	public static Point2D.Double RandomPosAroundPoint(Point2D.Double center, Dimension range)
	{
		// returns a point within a rectangle centered on the Point center with size 2 *
		// range
		int x = (int) (center.x + range.width * (Math.random() - Math.random()));
		int y = (int) (center.y + range.height * (Math.random() - Math.random()));
		return new Point2D.Double(x, y);
	}

	public static boolean chance(double p)
	{
		return Math.random() < p;
	}
}