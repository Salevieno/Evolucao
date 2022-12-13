package Main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class UtilS 
{

	public static JButton AddButton(String text, ImageIcon icon, int[] alignment, Dimension size, Color color)
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

	public static Point ConvertToDrawingCoords(Point originalCoords, Point canvasPos, Dimension canvasSize, Dimension canvasDim)
	{
		int xCoord = (int) (canvasPos.x + originalCoords.x * canvasSize.width / canvasDim.width) ;
		int yCoord = (int) (canvasPos.y + canvasSize.height - originalCoords.y * canvasSize.height / canvasDim.height) ;
		return new Point (xCoord, yCoord) ;
	}
	
	public static boolean IsInsideRect(Point Pos, Point TopLeftPos, Dimension RectSize)
	{
		if (TopLeftPos.x <= Pos.x &
			TopLeftPos.y <= Pos.y &
			Pos.x <= TopLeftPos.x + RectSize.width &
			Pos.y <= TopLeftPos.y + RectSize.height)
		{
			return true;
		} 
		else
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
		// returns a point within a rectangle centered on the Point center with size 2 * range
		int x = (int) (center.x + range.width * (Math.random() - Math.random())) ;
		int y = (int) (center.y + range.height * (Math.random() - Math.random())) ;
		return new Point(x, y) ;
	}
	
	
	// ***********************************************
	
	
	
	
	
	
	
	public static Color[] ColorPalette(int Palette)
	{
		Color[] color = new Color[28];
		if (Palette == 0)
		{
			color[0] = Color.cyan;		// Sky, crystal, menu 1, menu ornaments, player window, quest requirements, bestiary windows, knight
			color[1] = Color.magenta;	// Mage
			color[2] = Color.orange;	// Archer
			color[3] = Color.green;		// Animal, pet 0, grass, plant element, selected text, poison
			color[4] = Color.gray;		// Thief, metal, rock, unavailable stuff, player att window 1
			color[5] = Color.blue;		// ocean, text
			color[6] = Color.red;		// berry, blood, selected bag item, life, level, crit
			color[7] = Color.white;		// intro windows, snow land, choices menu, ok button, player eye
			color[8] = Color.lightGray;	// Elemental NPC color, part of window gradient backgrounds
			color[9] = Color.black;		// Contour, attack animation
			color[10] = Color.yellow;	// Satiation, fire element
			color[11] = Color.pink;		// Petal
			color[12] = new Color(0, 0, 128);	// Player legs
			color[13] = new Color(128, 0, 0);	// Player shoes
			color[14] = new Color(128, 128, 128);	// Player shirt, player arm
			color[15] = new Color(255, 179, 128);	// Player head (skin)
			color[16] = new Color(0, 128, 0);	// Grass contour color, player hair
			color[17] = new Color(200, 50, 200);	// pet 1
			color[18] = new Color(200, 200, 30);	// pet 2 and 3, map, gold color, chest reward animation
			color[19] = new Color(100, 50, 0);	// Soil, wood, bag menu, player window, pterodactile
			color[20] = new Color(150, 100, 0);	// Sand, menu 0, menu 2
			color[21] = new Color(180, 0, 180);	// Herb, exp, magic floor, shopping text
			color[22] = new Color(230, 230, 230);	// Neutral, air, light and snow elements
			color[23] = new Color(0, 50, 0);	// Herb contour
			color[24] = new Color(10, 30, 40);
			color[25] = new Color(30, 200, 30);	// Bag text
			color[26] = new Color(0, 150, 255);	// Shopping menu
			color[27] = new Color(200, 200, 255);	// Sign window
		}
		else if (Palette == 1)
		{
			color[0] = new Color(27, 224, 233);		// Sky, crystal, menu 1, menu ornaments, player window, quest requirements, bestiary windows, knight
			color[1] = new Color(230, 46, 150);	// Mage
			color[2] = new Color(244, 162, 25);	// Archer
			color[3] = new Color(29, 237, 29);		// Animal, pet 0, grass, plant element, selected text, poison
			color[4] = new Color(109, 103, 97);		// Thief, metal, rock, unavailable stuff, player att window 1
			color[5] = new Color(63, 40, 231);		// ocean, text
			color[6] = new Color(236, 44, 44);		// berry, blood, selected bag item, life, level, crit
			color[7] = new Color(241, 233, 225);		// intro windows, snow land, choices menu, ok button, player eye
			color[8] = new Color(203, 195, 188);	// Elemental NPC color, part of window gradient backgrounds
			color[9] = new Color(11, 11, 11);		// Contour, attack animation
			color[10] = new Color(234, 237, 27);	// Satiation, fire element
			color[11] = new Color(232, 93, 143);		// Petal
			color[12] = new Color(78, 36, 193);	// Player legs
			color[13] = new Color(167, 28, 70);	// Player shoes
			color[14] = new Color(109, 103, 97);	// Player shirt, player arm
			color[15] = new Color(233, 158, 125);	// Player head (skin)
			color[16] = new Color(46, 192, 81);	// Grass contour color, player hair
			color[17] = new Color(194, 35, 144);	// pet 1
			color[18] = new Color(127, 192, 42);	// pet 2 and 3, map, gold color, chest reward animation
			color[19] = new Color(101, 61, 44);	// Soil, wood, bag menu, player window, pterodactile
			color[20] = new Color(143, 90, 52);	// Sand, menu 0, menu 2
			color[21] = new Color(155, 33, 128);	// Herb, exp, magic floor, shopping text
			color[22] = new Color(137, 249, 204);	// Neutral, air, light and snow elements
			color[23] = new Color(41, 88, 66);	// Herb contour
			color[24] = new Color(46, 192, 81);	// Status 1 (poison)
			color[25] = new Color(174, 242, 94);	// Bag text
			color[26] = new Color(34, 82, 194);	// Shopping menu
			color[27] = new Color(91, 247, 217);	// Sign window
		}
		else if (Palette == 2)
		{
			color[0] = new Color(61, 240, 226);		// Sky, crystal, menu 1, menu ornaments, player window, quest requirements, bestiary windows, knight
			color[1] = new Color(235, 38, 226);	// Mage, petal, pet 1, herb, exp, magic floor, shopping text
			color[2] = new Color(234, 237, 27);	// Archer, satiation, fire element
			color[3] = new Color(122, 236, 67);		// Animal, pet 0, grass, plant element, selected text, poison
			color[4] = new Color(164, 155, 147);		// Thief, metal, rock, wall, unavailable stuff, sign window, player att window 1
			color[5] = new Color(63, 40, 231);		// ocean, text
			color[6] = new Color(236, 44, 44);		// berry, blood, selected bag item, life, level, crit
			color[7] = new Color(241, 233, 225);		// Elemental NPC color, neutral and light elements, intro windows, choices menu, ok button, player eye
			color[8] = new Color(137, 249, 204);	// snow land, air and snow elements, part of window gradient backgrounds
			color[9] = new Color(11, 11, 11);		// Contour, attack animation
			color[10] = new Color(167, 28, 70);	// Lava, volcano
			color[11] = new Color(228, 89, 80); 
			color[12] = new Color(245, 117, 170);	// Player legs, herb contour
			color[13] = new Color(43, 45, 99);	// Player shoes
			color[14] = new Color(28, 162, 208);	// Player shirt, player arm
			color[15] = new Color(219, 251, 137);	// Player head (skin)
			color[16] = new Color(41, 88, 66);	// grass contour color, player hair
			color[17] = new Color(241, 199, 128);
			color[18] = new Color(171, 195, 49);	// pet 2 and 3, map, gold color, shopping menu, chest reward animation
			color[19] = new Color(143, 90, 52);	// Soil, wood, bag menu, stalactite, player window, pterodactile
			color[20] = new Color(242, 176, 63);	// Sand, menu 0, menu 2
			color[21] = new Color(247, 224, 143);
			color[22] = new Color(101, 131, 246);
			color[23] = new Color(141, 31, 159);	// Bag, shopping and crafting items text
			color[24] = new Color(48, 99, 97);	// 
			color[25] = new Color(76, 131, 42);	// 
			color[26] = new Color(242, 91, 168);	// 
			color[27] = new Color(105, 50, 50);	// 
		}
		return color;
	}
	
	
	public double dist(int[] Point1, int[] Point2)
	{
		return Math.sqrt(Math.pow(Point2[0] - Point1[0], 2) + Math.pow(Point2[1] - Point1[1], 2) + Math.pow(Point2[2] - Point1[2], 2));
	}
	
	public void moveMouse (Point p)
	{
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();
	    // Search the devices for the one that draws the specified point.
	    for (GraphicsDevice device: gs)
	    { 
	        GraphicsConfiguration[] configurations = device.getConfigurations();
	        for (GraphicsConfiguration config: configurations)
	        {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p))
	            {
	                // Set point to screen coordinates.
	                Point b = bounds.getLocation(); 
	                Point s = new Point(p.x - b.x, p.y - b.y);
	                try
	                {
	                    Robot r = new Robot(device);
	                    r.mouseMove(s.x, s.y);
	                } catch (AWTException e)
	                {
	                    e.printStackTrace();
	                }
	                return;
	            }
	        }
	    }
	    // Couldn't move to the point, it may be off screen.
	    return;
	}
	
	public static boolean chance(double p)
	{
	    if (Math.random() < p)
	    {
	        return true;
	    }
	    return false;
	}
	
	public static boolean[] IncreaseVecSize(boolean[] OriginalVector)
	{
	    boolean[] NewVector = new boolean[OriginalVector.length + 1];
	    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
        {
            NewVector[i] = OriginalVector[i];
        }
        return NewVector;
	}
	
	public static String[] IncreaseVecSize(String[] OriginalVector)
	{
	    String[] NewVector = new String[OriginalVector.length + 1];
	    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
        {
            NewVector[i] = OriginalVector[i];
        }
        return NewVector;
	}
	
	public static int[] IncreaseVecSize(int[] OriginalVector)
	{
	    int[] NewVector = new int[OriginalVector.length + 1];
	    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
        {
            NewVector[i] = OriginalVector[i];
        }
        return NewVector;
	}
	
	public static double[] IncreaseVecSize(double[] OriginalVector)
	{
	    double[] NewVector = new double[OriginalVector.length + 1];
	    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
        {
            NewVector[i] = OriginalVector[i];
        }
        return NewVector;
	}
	
	public static double[][] IncreaseArraySize(double[][] OriginalArray)
	{
	    double[][] NewArray = new double[OriginalArray.length + 1][];
	    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
        {
            NewArray[i] = OriginalArray[i];
        }
        return NewArray;
	}
	
	public boolean[] AddElemToVec(boolean[] OriginalVector, boolean Elem)
	{
		if (OriginalVector == null)
		{
			return new boolean[] {Elem};
		}
		else
		{
		    boolean[] NewVector = new boolean[OriginalVector.length + 1];
		    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
	        {
	            NewVector[i] = OriginalVector[i];
	        }
	        NewVector[OriginalVector.length] = Elem;
	        return NewVector;
		}
	}
	
	public static String[] AddElemToVec(String[] OriginalVector, String Elem)
	{
		if (OriginalVector == null)
		{
			return new String[] {Elem};
		}
		else
		{
		    String[] NewVector = new String[OriginalVector.length + 1];
		    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
	        {
	            NewVector[i] = OriginalVector[i];
	        }
	        NewVector[OriginalVector.length] = Elem;
	        return NewVector;
		}
	}
	
	public static int[] AddElemToVec(int[] OriginalVector, int Elem)
	{
		if (OriginalVector == null)
		{
			return new int[] {Elem};
		}
		else
		{
		    int[] NewVector = new int[OriginalVector.length + 1];
		    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
	        {
	            NewVector[i] = OriginalVector[i];
	        }
	        NewVector[OriginalVector.length] = Elem;
	        return NewVector;
		}
	}
	
	public double[] AddElemToVec(double[] OriginalVector, double Elem)
	{
		if (OriginalVector == null)
		{
			return new double[] {Elem};
		}
		else
		{
		    double[] NewVector = new double[OriginalVector.length + 1];
		    for (int i = 0; i <= OriginalVector.length - 1; i += 1)
	        {
	            NewVector[i] = OriginalVector[i];
	        }
	        NewVector[OriginalVector.length] = Elem;
	        return NewVector;
		}
	}
	
	public static int[][] AddElemToArray(int[][] OriginalArray, int[] Elem)
	{
		if (OriginalArray == null)
		{
			return new int[][] {Elem};
		}
		else
		{
		    int[][] NewArray = new int[OriginalArray.length + 1][];
		    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
	        {
	            NewArray[i] = OriginalArray[i];
	        }
	        NewArray[OriginalArray.length] = Elem;
	        return NewArray;
		}
	}
	
	public static double[][] AddElemToArray(double[][] OriginalArray, double[] Elem)
	{
		if (OriginalArray == null)
		{
			return new double[][] {Elem};
		}
		else
		{
		    double[][] NewArray = new double[OriginalArray.length + 1][];
		    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
	        {
	            NewArray[i] = OriginalArray[i];
	        }
	        NewArray[OriginalArray.length] = Elem;
	        return NewArray;
		}
	}
	
	public static double[][][] AddElemToArray(double[][][] OriginalArray, double[][] Elem)
	{
		if (OriginalArray == null)
		{
			return new double[][][] {Elem};
		}
		else
		{
		    double[][][] NewArray = new double[OriginalArray.length + 1][][];
		    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
	        {
	            NewArray[i] = OriginalArray[i];
	        }
	        NewArray[OriginalArray.length] = Elem;
	        return NewArray;
		}
	}
	
	public static int[][] AddElemToArrayUpTo(int[][] OriginalArray, int[] Elem, int maxlength)
	{
		if (OriginalArray == null)
		{
			return new int[][] {Elem};
		}
		else
		{
			if (OriginalArray.length < maxlength)
			{
			    int[][] NewArray = new int[OriginalArray.length + 1][];
			    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
		        {
		            NewArray[i] = OriginalArray[i];
		        }
		        NewArray[OriginalArray.length] = Elem;
		        return NewArray;
			}
			else
			{
				int[][] NewArray = new int[OriginalArray.length][];
			    for (int i = 0; i <= OriginalArray.length - 2; i += 1)
		        {
		            NewArray[i] = OriginalArray[i + 1];
		        }
		        NewArray[OriginalArray.length - 1] = Elem;
		        return NewArray;
			}
		}
	}
	
	public static double[][] AddElemToArrayUpTo(double[][] OriginalArray, double[] Elem, int maxlength)
	{
		if (OriginalArray == null)
		{
			return new double[][] {Elem};
		}
		else
		{
			if (OriginalArray.length < maxlength)
			{
			    double[][] NewArray = new double[OriginalArray.length + 1][];
			    for (int i = 0; i <= OriginalArray.length - 1; i += 1)
		        {
		            NewArray[i] = OriginalArray[i];
		        }
		        NewArray[OriginalArray.length] = Elem;
		        return NewArray;
			}
			else
			{
				double[][] NewArray = new double[OriginalArray.length][];
			    for (int i = 0; i <= OriginalArray.length - 2; i += 1)
		        {
		            NewArray[i] = OriginalArray[i + 1];
		        }
		        NewArray[OriginalArray.length - 1] = Elem;
		        return NewArray;
			}
		}
	}

	public static boolean[] RemoveElemFromArray(int id, boolean[] Array)
	{
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			boolean[] result = new boolean[Array.length - 1] ;
			
			if (id == 0)
			{
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}		
			
			return result  ;
		}
	}
	
	public static String[] RemoveElemFromArray(int id, String[] Array)
	{
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			String[] result = new String[Array.length - 1] ;
	
			if (id == 0)
			{
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}
			
			return result ;
		}
	}
	
	public static int[] RemoveElemFromArray(int id, int[] Array)
	{
		//System.out.println("id " + id + " Array = " + Arrays.toString(Array)) ;
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			int[] result = new int[Array.length - 1] ;
	
			if (id == 0)
			{
				//System.out.println("op��o 1") ;
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				//System.out.println("op��o 2") ;
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				//System.out.println("op��o 3") ;
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}

			//System.out.println("result = " + Arrays.toString(result)) ;
			return result ;
		}
	}
	
	public static double[] RemoveElemFromArray(int id, double[] Array)
	{
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			double[] result = new double[Array.length - 1] ;
	
			if (id == 0)
			{
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}
			
			return result ;
		}
	}
	
	public static int[][] RemoveElemFromArray(int id, int[][] Array)
	{
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			int[][] result = new int[Array.length - 1][] ;
	
			if (id == 0)
			{
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}
			
			return result ;
		}
	}
	
	public static double[][] RemoveElemFromArray(int id, double[][] Array)
	{
		if (Array.length == 1)
		{
			return null ;
		}
		else
		{
			double[][] result = new double[Array.length - 1][] ;
	
			if (id == 0)
			{
				System.arraycopy(Array, 1, result, 0, Array.length - 1) ;
			}
			else if (id == Array.length - 1)
			{
				System.arraycopy(Array, 0, result, 0, Array.length - 1) ;
			}
			else
			{
				System.arraycopy(Array, 0, result, 0, id) ;
				System.arraycopy(Array, id + 1, result, id, Array.length - id - 1) ;
			}
			
			return result ;
		}
	}
	
	
	public static int FindMin(int[] Vector)
	{
		int Min = Vector[0];
		for (int s = 1; s <= Vector.length - 1; s += 1)
        {
			if (Vector[s] < Min)
			{
				Min = Vector[s];
			}
        }		
		return Min;
	}
	
	public static double FindMin(double[] Vector)
	{
		double Min = Vector[0];
		for (int s = 1; s <= Vector.length - 1; s += 1)
        {
			if (Vector[s] < Min)
			{
				Min = Vector[s];
			}
        }		
		return Min;
	}
	
	public static int FindMax(int[] Vector)
	{
		int Max = Vector[0];
		for (int s = 1; s <= Vector.length - 1; s += 1)
        {
			if (Max < Vector[s])
			{
				Max = Vector[s];
			}
        }		
		return Max;
	}
	
	public static double FindMax(double[] Vector)
	{
		double Max = Vector[0];
		for (int s = 1; s <= Vector.length - 1; s += 1)
        {
			if (Max < Vector[s])
			{
				Max = Vector[s];
			}
        }		
		return Max;
	}
	
	public static int[] ObjectToIntVec(Object object)
	{
		String[] StringVec = String.valueOf(object).split("\\s+");
		int[] Vec = new int[StringVec.length];
		for (int i = 0; i <= StringVec.length - 1; i += 1)
		{
			Vec[i] = Integer.parseInt(StringVec[i]);
		}
		return Vec;
	}

	public static double[] ObjectToDoubleVec(Object object)
	{
		String[] StringVec = String.valueOf(object).split("\\s+");
		double[] Vec = new double[StringVec.length];
		for (int i = 0; i <= StringVec.length - 1; i += 1)
		{
			Vec[i] = Double.parseDouble(StringVec[i]);
		}
		return Vec;
	}

	public static double[][] ObjectToDoubleArray(Object object)
	{
		String[] a = String.valueOf(object).split("\\s+");
		int asize = 0;
		for (int i = 0; i <= a.length - 1; i += 1)
		{
			if (a[i].equals("]"))
			{
				asize += 1;
			}
		}
		String[][] StringArray = new String[asize][];
		int cont = 0;
		for (int i = 0; i <= asize - 1; i += 1)
		{
			cont += 1;
			do
			{
				if (!a[cont].equals("]") & !a[cont].equals("["))
				{
					StringArray[i] = AddElemToVec(StringArray[i], a[cont]); 
				}
				cont += 1;
			} while (!a[cont].equals("]"));
		}
		double[][] Array = new double[StringArray.length][StringArray[0].length];
		for (int i = 0; i <= StringArray.length - 1; i += 1)
		{
			for (int j = 0; j <= StringArray[i].length - 1; j += 1)
			{
				Array[i][j] = Double.parseDouble(StringArray[i][j]);
			}
		}
		return Array;
	}
	
	public static Color[] ObjectToColorVec(Object object)
	{
		String[] a = String.valueOf(object).split("\\s+");
		int asize = 0;
		for (int i = 0; i <= a.length - 1; i += 1)
		{
			if (a[i].equals("]"))
			{
				asize += 1;
			}
		}
		String[][] StringArray = new String[asize][];
		int cont = 0;
		for (int i = 0; i <= asize - 1; i += 1)
		{
			cont += 1;
			do
			{
				if (!a[cont].equals("]") & !a[cont].equals("["))
				{
					StringArray[i] = AddElemToVec(StringArray[i], a[cont]); 
				}
				cont += 1;
			} while (!a[cont].equals("]"));
		}
		Color[] ColorVec = new Color[StringArray.length];
		for (int i = 0; i <= StringArray.length - 1; i += 1)
		{
			ColorVec[i] = new Color(Integer.parseInt(StringArray[i][0]), Integer.parseInt(StringArray[i][1]), Integer.parseInt(StringArray[i][2]));
		}
		return ColorVec;
	}
	
	public static Object[] ReadInputFile(String fileName)
	{
		int NumArtroPar = 15, NumFoodPar = 7;
		Object[] input = new Object[1 + NumArtroPar + NumFoodPar];
		
		try
		{	
			FileReader fileReader = new FileReader (fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 					
			String Line;
			for (int i = 0; i <= 1 - 1; i += 1)
			{
				Line = bufferedReader.readLine();
				input[i] = Line;
			}
			Line = bufferedReader.readLine();
			for (int i = 1; i <= NumArtroPar + 1 - 1; i += 1)
			{
				Line = bufferedReader.readLine();
				input[i] = Line;
			}
			Line = bufferedReader.readLine();
			for (int i = NumArtroPar + 1; i <= NumArtroPar + NumFoodPar + 1 - 1; i += 1)
			{
				Line = bufferedReader.readLine();
				input[i] = Line;
			}
			bufferedReader.close();	
		}	
		catch(FileNotFoundException ex) 
		{
            System.out.println("Unable to find file '" + fileName + "'");                
        }		
		catch(IOException ex) 
		{
            System.out.println("Error reading file '" + fileName + "'");                  
        }
		
		return input;
	}
	
	/*public static double[] RandomPosAroundPoint(double[] Point, double[] Dispersion)
	{
		return new double[] {Point[0] + Dispersion[0]*(Math.random() - Math.random()), Point[1] + Dispersion[1]*(Math.random() - Math.random())};
	}*/
	
	public static double Random(double n)
	{
		return n*(Math.random() - Math.random());
	}

	public static int[] OffsetFromPos(String Alignment, int l, int h)
	{
		int[] offset = new int[2];
		if (Alignment.equals("TopLeft"))
		{
			offset[0] = 0;
			offset[1] = 0;
		}
		if (Alignment.equals("BotLeft"))
		{
			offset[0] = 0;
			offset[1] = -h;
		}
		if (Alignment.equals("TopCenter"))
		{
			offset[0] = -l/2;
			offset[1] = 0;
		}
		if (Alignment.equals("BotCenter"))
		{
			offset[0] = -l/2;
			offset[1] = -h;
		}
		if (Alignment.equals("Center"))
		{
			offset[0] = -l/2;
			offset[1] = -h/2;
		}
		if (Alignment.equals("LeftCenter"))
		{
			offset[0] = 0;
			offset[1] = -h/2;
		}
		if (Alignment.equals("RightCenter"))
		{
			offset[0] = -l;
			offset[1] = -h/2;
		}
		if (Alignment.equals("BotRight"))
		{
			offset[0] = -l;
			offset[1] = -h;
		}
		if (Alignment.equals("TopRight"))
		{
			offset[0] = -l;
			offset[1] = 0;
		}
		return offset;
	}
	
	public static String[][] ReadTextFile(String Language)
	{
		String fileName = "Texto_PT-br.txt";
		String[][] Text = new String[50][25]; // [Cat][Pos]
		int cat = -1, pos = 0;
		if (Language.equals("En"))
		{
			fileName = "Text_EN.txt";
		}
		try
		{	
			FileReader fileReader = new FileReader (fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 					
			String Line = bufferedReader.readLine();
			while (!Line.equals("_"))
			{
				if (Line.contains("*"))
				{
					cat += 1;
					pos = 0;
	            }
				Text[cat][pos] = Line;
				Line = bufferedReader.readLine();
				pos += 1;
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
            System.out.println("Unable to find file '" + fileName + "' (text file)");                
        }		
		catch(IOException ex) 
		{
            System.out.println("Error reading file '" + fileName + "' (text file)");                  
        }
		return Text;
	}
	
	public static int[] CalculateNumberOfGridPoints(int[] CanvasPos, int[] CanvasSize, int[] CanvasDim)
	{
		int[] NPointsMax = new int[] {50, 50};
		int[] GridFactor = new int[] {1 + (CanvasDim[0]/10)/NPointsMax[0], 1 + (CanvasDim[1]/10)/NPointsMax[1]};
		int[] NPoints = new int[] {(int) (CanvasDim[0]/(10*GridFactor[0])), (int) (CanvasDim[1]/(10*GridFactor[1]))};
		return NPoints;
	}

	public double[] ConvertToRealCoords(int[] OriginalCoords, int[] CanvasPos, int[] CanvasSize, int[] CanvasDim)
	{
		return new double[] {(OriginalCoords[0] - CanvasPos[0])/(double)(CanvasSize[0])*CanvasDim[0], (-OriginalCoords[1] + CanvasPos[1] + CanvasSize[1])/(double)(CanvasSize[1])*CanvasDim[1]};
	}
	
	
	/*public int ConvertToDrawingSize(int[] CanvasPos, int[] CanvasSize, int[] CanvasDim, double size)
	{
		return (ConvertToDrawingCoords(new Point(0, 0), CanvasPos, CanvasSize, CanvasDim).y - 
				ConvertToDrawingCoords(new Point(0, size), CanvasPos, CanvasSize, CanvasDim).y);
	}*/

	public double Bhaskara(double a, double b, double c, String SqrtSign)
	{
		if (SqrtSign.equals("+"))
		{
			return (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
		}
		else
		{
			return (-b - Math.sqrt(b*b - 4*a*c))/(2*a);
		}
	}

	public static boolean ArrayContains(boolean[] OriginalArray, boolean elem)
	{
		for (int i = 0; i <= OriginalArray.length - 1; i += 1)
		{
			if (OriginalArray[i] == elem)
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean ArrayContains(int[] OriginalArray, int elem)
	{
		for (int i = 0; i <= OriginalArray.length - 1; i += 1)
		{
			if (OriginalArray[i] == elem)
			{
				return true;
			}
		}
		return false;
	}
	
	public static int FirstIndex(boolean[] Array, boolean elem)
	{
		for (int i = 0; i <= Array.length - 1; i += 1)
		{
			if (Array[i] == elem)
			{
				return i;
			}
		}
		return -1;
	}

	public static int FirstIndex(int[] Array, int elem)
	{
		for (int i = 0; i <= Array.length - 1; i += 1)
		{
			if (Array[i] == elem)
			{
				return i;
			}
		}
		return -1;
	}
	
	public static double[][] MultMatrix (double[][] A, double[][] B) 
	{
		/*This function multiplies two matrices*/
		
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;
        double[][] C = new double[aRows][bColumns];
        
        if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("Number of columns in A (" + aColumns + ") did not match the number of rows in B (" + bRows + ").");
        }

        for (int i = 0; i < aRows; ++i)
        { 
            for (int j = 0; j < bColumns; ++j)
            {
                for (int k = 0; k < aColumns; ++k) 
                {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
	
	public static int[][] MultMatrix (int[][] A, double[][] B) 
	{
		/*This function multiplies two matrices*/
		
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;
        int[][] C = new int[aRows][bColumns];
        
        if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("Number of columns in A (" + aColumns + ") did not match the number of rows in B (" + bRows + ").");
        }

        for (int i = 0; i < aRows; ++i)
        { 
            for (int j = 0; j < bColumns; ++j)
            {
                for (int k = 0; k < aColumns; ++k) 
                {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
	
	public static double[] MultMatrixVector (double[][] A, double[] b)
	{
		/*This function multiplies a matrix and a vector*/
        int aColumns = A[0].length;
        int bRows = b.length;
		double[] C = new double[bRows];		
		if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("Number of columns in A (" + aColumns + ") did not match the number of rows in b (" + bRows + ").");
        }	
        for (int i = 0; i < bRows; i += 1)
        { 
            for (int j = 0; j < aColumns; j += 1)
            {
            	C[i] += A[i][j] * b[j];
            }
        }      
		return C;
	}
	
	public static int[] MultMatrixVector (double[][] A, int[] b)
	{
		/*This function multiplies a matrix and a vector*/		
        int aColumns = A[0].length;
        int bRows = b.length;
		int[] C = new int[bRows];	
		if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("Number of columns in A (" + aColumns + ") did not match the number of rows in b (" + bRows + ").");
        }	
        for (int i = 0; i < bRows; i += 1)
        { 
            for (int j = 0; j < aColumns; j += 1)
            {
            	C[i] += A[i][j] * b[j];
            }
        }      
		return C;
	}
	
	public static double MultVector (double[] a, double[] b)
	{
		/*This function multiplies two vectors*/
		
        int aColumns = a.length;
        int bRows = b.length;
		double C = 0;
		
		if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("Number of columns in a (" + aColumns + ") did not match the number of rows in b (" + bRows + ").");
        }
		
        for (int i = 0; i < bRows; ++i)
        { 
        	C += a[i] * b[i];
        }   		
		return C;
	}
	
	public static double[][] Transpose (double[][] matrix) 
	{
		/*This function calculates the transpose of a matrix*/
		
        int matrixRows = matrix.length;
        int matrixColumns = matrix[0].length;
		double[][] matrixTranspose = new double[matrixColumns][matrixRows];
        for (int i = 0; i < matrixRows; ++i)
        { 
            for (int j = 0; j < matrixColumns; ++j)
            {
                matrixTranspose[j][i] = matrix[i][j];
            }
        }
		return matrixTranspose;
	}
	
	public static double[][][] Transpose (double[][][] matrix) 
	{
		/*This function calculates the transpose of a matrix*/
		
        int matrixi1 = matrix.length;
        int matrixi2 = matrix[0].length;
        int matrixi3 = matrix[0][0].length;
		double[][][] matrixTranspose = new double[matrixi3][matrixi2][matrixi1];
        for (int i1 = 0; i1 < matrixi1; ++i1)
        { 
            for (int i2 = 0; i2 < matrixi2; ++i2)
            {
            	for (int i3 = 0; i3 < matrixi2; ++i3)
                {
                    matrixTranspose[i3][i2][i1] = matrix[i1][i2][i3];
                }
            }
        }
		return matrixTranspose;
	}
	
    public static void PrintVector (double[] vector)
    {
    	/*This function prints a vector*/
    	
    	System.out.print("[");
        for (int i = 0; i <= vector.length - 1; i++) 
        {
        	System.out.print(vector[i] + " ");
        }
        System.out.print("]");
    }
	
    public static void PrintMatrix (double[][] matrix)
    {
    	/*This function prints a matrix*/
    	
        for (int i = 0; i <= matrix.length - 1; i++) 
        {
            for (int j = 0; j <= matrix[0].length - 1; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

	public static int TextL(String Text, Font TextFont, int size, Graphics G)
	{
		FontMetrics metrics = G.getFontMetrics(TextFont);
		return (int)(metrics.stringWidth(Text)*0.05*size);
	}
	
	public static int TextH(int TextSize)
	{
		return (int)(0.8*TextSize);
	}
    
	public static float Round(double num, int decimals)
	{
		return BigDecimal.valueOf(num).setScale(decimals, RoundingMode.HALF_UP).floatValue();
	}
	
	public static float Round(float num, int decimals)
	{
		return BigDecimal.valueOf(num).setScale(decimals, RoundingMode.HALF_UP).floatValue();
	}
	
	public static String[] FitText(String inputstring, int NumberOfChars)
	{
		String[] newstring = new String[inputstring.length()];
		int CharsExeeding = 0;		
		int i = 0;
		int FirstChar = 0;
		int LastChar = 0;
		do
		{
			FirstChar = i*NumberOfChars - CharsExeeding;
			LastChar = FirstChar + Math.min(NumberOfChars, Math.min((i + 1)*NumberOfChars, inputstring.length() - i*NumberOfChars) + CharsExeeding);
			char[] chararray = new char[NumberOfChars];
			inputstring.getChars(FirstChar, LastChar, chararray, 0);
			if (chararray[LastChar - FirstChar - 1] != ' ' & chararray[LastChar - FirstChar - 1] != '.' & chararray[LastChar - FirstChar - 1] != '?' & chararray[LastChar - FirstChar - 1] != '!' & chararray[LastChar - FirstChar - 1] != '/' & chararray[LastChar - FirstChar - 1] != ':')
			{
				for (int j = chararray.length - 1; 0 <= j; j += -1)
				{
					CharsExeeding += 1;
					LastChar += -1;
					if (chararray[j] == ' ' | chararray[j] == '.' | chararray[j] == '?' | chararray[j] == '!' | chararray[j] == '/' | chararray[j] == ':')
					{
						char[] chararray2 = new char[NumberOfChars];
						inputstring.getChars(Math.min(Math.max(0, FirstChar), inputstring.length()), LastChar, chararray2, 0);
						newstring[i] = String.valueOf(chararray2);
						CharsExeeding += -1;
						j = 0;
					}
				}
			}
			else
			{
				newstring[i] = String.valueOf(chararray);
			}
			i += 1;
		} while(LastChar != inputstring.length() & i != inputstring.length());		
		String[] newstring2 = new String[i];
		for (int j = 0; j <= newstring2.length - 1; j += 1)
		{
			newstring2[j] = newstring[j];
		}
		return newstring2;
	}
	
	public boolean MouseIsInside(int[] MousePos, int[] RectPos, int L, int H)
	{
		if (RectPos[0] <= MousePos[0] & RectPos[1] <= MousePos[1] & MousePos[0] <= RectPos[0] + L & MousePos[1] <= RectPos[1] + H)
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	public int FindID (String[] Array, String Elem)
	{
		for (int i = 0; i <= Array.length - 1; i += 1)
		{
			if (Array[i].equals(Elem))
			{
				return i;
			}
		}
		return -1;
	}
	
	public static Color FindColor(double value, double min, double max, String Style)
	{
		int red = -1, green = -1, blue = -1;
		double MaxAbs = Math.max(Math.abs(min), max);
		if (Style.equals("Red to Green"))
		{
			red = (int) Math.max(255*(-value/MaxAbs), 0);
			green = (int) Math.max(255*(value/MaxAbs), 0);
			blue = (int) Math.max(255*(1 - Math.abs(value)/MaxAbs), 0);
		}
		else if (Style.equals("Purple to Green"))
		{
			
		}
		return new Color(red, green, blue);
	}
}
