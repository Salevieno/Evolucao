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

	public static Image loadImage(String filePath)
	{
		Image image = new ImageIcon(filePath).getImage();
		if (image.getWidth(null) != -1 & image.getHeight(null) != -1)
		{
			return image;
		} else
		{
			System.out.println("Image not found at " + filePath);
			return null;
		}
	}

	public static JButton createButton(String text, ImageIcon icon, int[] alignment, Dimension size, Color color)
	{
		JButton newButton = new JButton();
		if (text != null)
		{
			newButton.setText(text);
		}
		if (icon != null)
		{
			newButton.setIcon(icon);
		}
		newButton.setVerticalAlignment(alignment[0]);
		newButton.setHorizontalAlignment(alignment[1]);
		newButton.setBackground(color);
		newButton.setPreferredSize(size);
		return newButton;
	}

	public static Point ConvertToDrawingCoords(Point originalCoords, Point canvasPos, Dimension canvasSize,
			Dimension canvasDim)
	{
		int xCoord = (int) (canvasPos.x + originalCoords.x * canvasSize.width / canvasDim.width);
		int yCoord = (int) (canvasPos.y + canvasSize.height - originalCoords.y * canvasSize.height / canvasDim.height);
		return new Point(xCoord, yCoord);
	}

	public static boolean IsInsideRect(Point Pos, Point TopLeftPos, Dimension RectSize)
	{
		if (TopLeftPos.x <= Pos.x & TopLeftPos.y <= Pos.y & Pos.x <= TopLeftPos.x + RectSize.width
				& Pos.y <= TopLeftPos.y + RectSize.height)
		{
			return true;
		} else
		{
			return false;
		}
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
		if (Math.random() < p)
		{
			return true;
		}

		return false;
	}

	// ***********************************************

	/*
	 * public static int TextL(String Text, Font font, Graphics2D G) { FontMetrics
	 * metrics = G.getFontMetrics(font) ; return (int) (metrics.stringWidth(Text)) ;
	 * }
	 */

	public static int TextH(int TextSize)
	{
		return (int) (0.8 * TextSize);
	}

	public static double Round(double num, int decimals)
	{
		return BigDecimal.valueOf(num).setScale(decimals, RoundingMode.HALF_EVEN).doubleValue();
	}

	public static int[] OffsetFromPos(String Alignment, int l, int h)
	{
		int[] offset = new int[2];
		if (Alignment.equals("TopLeft"))
		{
			offset[0] = 0;
			offset[1] = 0;
		}
		if (Alignment.equals("CenterLeft"))
		{
			offset[0] = 0;
			offset[1] = -h / 2;
		}
		if (Alignment.equals("BotLeft"))
		{
			offset[0] = 0;
			offset[1] = -h;
		}
		if (Alignment.equals("TopCenter"))
		{
			offset[0] = -l / 2;
			offset[1] = 0;
		}
		if (Alignment.equals("Center"))
		{
			offset[0] = -l / 2;
			offset[1] = -h / 2;
		}
		if (Alignment.equals("BotCenter"))
		{
			offset[0] = -l / 2;
			offset[1] = -h;
		}
		if (Alignment.equals("TopRight"))
		{
			offset[0] = -l;
			offset[1] = 0;
		}
		if (Alignment.equals("CenterRight"))
		{
			offset[0] = -l;
			offset[1] = -h / 2;
		}
		if (Alignment.equals("BotRight"))
		{
			offset[0] = -l;
			offset[1] = -h;
		}
		return offset;
	}

	/*
	 * public double dist(int[] Point1, int[] Point2) { return
	 * Math.sqrt(Math.pow(Point2[0] - Point1[0], 2) + Math.pow(Point2[1] -
	 * Point1[1], 2) + Math.pow(Point2[2] - Point1[2], 2)); }
	 * 
	 * public static double Random(double n) { return n*(Math.random() -
	 * Math.random()); }
	 */
}
