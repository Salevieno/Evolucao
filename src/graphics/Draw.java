package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.List;

import main.UtilS;

public abstract class Draw
{
	private static DrawPrimitives DP ;
	private static int stdStroke ;
	private static Graphics2D G;
	
	static
	{
		DP = new DrawPrimitives() ;
		stdStroke = 1;
	}
	
	public static void setDP(DrawPrimitives newDP) { DP = newDP ;}
	
    public static void grid(Point initPos, Point finalPos, int NumSpacing, Color lineColor)
	{
		int stroke = 1;
		Dimension length = new Dimension(finalPos.x - initPos.x, initPos.y - finalPos.y) ;
		for (int i = 0 ; i <= NumSpacing - 1 ; i += 1)
		{
			// horizontal lines
			Point hPoint1 = new Point (initPos.x, initPos.y - (i + 1)*length.height/NumSpacing) ;
			Point hPoint2 = new Point (initPos.x + length.width, initPos.y - (i + 1)*length.height/NumSpacing) ;
			DP.drawLine(hPoint1, hPoint2, stroke, lineColor) ;	
			
			// vertical lines
			Point vPoint1 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y) ;
			Point vPoint2 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y - length.height) ;
			DP.drawLine(vPoint1, vPoint2, stroke, lineColor) ;					
		}
	}
    

	public static void DrawImage(Image File, Point pos, float angle, float[] Scale, String Alignment)
	{       
		if (File != null)
		{
			int l = (int)(Scale[0]*File.getWidth(null)), h = (int)(Scale[1]*File.getHeight(null));		
			AffineTransform a = null;	// Rotate image
			AffineTransform backup = G.getTransform();
			if (Alignment.equals("Left"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x + l/2, pos.y - h/2);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, pos.x, pos.y - h, l, h, null);
			}
			if (Alignment.equals("Center"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x, pos.y);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, pos.x - l/2, pos.y - h/2, l, h, null);
			}
			if (Alignment.equals("Right"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x - l/2, pos.y + h/2);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, pos.x + l, pos.y - h, l, h, null);
			}
	        G.setTransform(backup);
		}
	}
	public static void DrawImage(Image image, Point pos)
	{
		G.drawImage(image, pos.x - image.getWidth(null) / 2, pos.y - image.getHeight(null) / 2, image.getWidth(null), image.getHeight(null), null);
	}
    /*public static void DrawText(Point pos, String Text, String Alignment, float angle, String Style, int size, Color color)
    {
		float TextL = UtilS.TextL(Text, TextFont, G), TextH = UtilS.TextH(size);
    	int[] Offset = UtilS.OffsetFromPos(Alignment, (int)TextL, (int)TextH);
		AffineTransform a = null;	// Rotate rectangle
		AffineTransform backup = G.getTransform();
		if (Alignment.equals("Left"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x - 0.5*TextL, pos.y + 0.5*TextH);	// Rotate text
    	}
		else if (Alignment.equals("Center"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x, pos.y + 0.5*TextH);	// Rotate text
    	}
    	else if (Alignment.equals("Right"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, pos.x, pos.y + 0.5*TextH);	// Rotate text
    	}
    	if (Style.equals("Bold"))
    	{
    		G.setFont(new Font(BoldTextFont.getName(), BoldTextFont.getStyle(), size));
    	}
    	else
    	{
    		G.setFont(new Font(TextFont.getName(), TextFont.getStyle(), size));
    	}
    	if (0 < Math.abs(angle))
    	{
    		G.setTransform(a);
    	}
    	G.setColor(color);
    	G.drawString(Text, pos.x + Offset[0], pos.y + Offset[1]);
        G.setTransform(backup);
    }*/
    /*public static void DrawFitText(Point Pos, String Text, String Alignment, float angle, String Style, int sy, int length, int size, Color color)
	{
		String[] FitText = UtilS.FitText(Text, length);
		for (int i = 0; i <= FitText.length - 1; i += 1)
		{
			DrawText(new int[] {Pos.x, Pos.y + i*sy}, FitText[i], Alignment, angle, Style, size, color);						
		}
	}*/

    public static void DrawRect(Point pos, String alignment, Dimension size, int stroke, Color color, Color contourColor)
	{
		// Rectangle by default starts at the top left
		//int[] offset = Util.OffsetFromPos(Alignment, size) ;
    	int[] offset = new int[2] ;	// draws at the top left
    	Point corner = new Point (pos.x + offset[0], pos.y + offset[1]) ;
		G.setStroke(new BasicStroke(stroke)) ;
		if (color != null)
		{
			G.setColor(color) ;
			G.fillRect(corner.x, corner.y, size.width , size.height) ;
		}
		if (contourColor != null)
		{
			G.setColor(contourColor) ;
			int[] xCoords = new int[] {corner.x, corner.x + size.width, corner.x + size.width, corner.x, corner.x} ;
			int[] yCoords = new int[] {corner.y, corner.y, corner.y + size.height, corner.y + size.height, corner.y} ;
			G.drawPolyline(xCoords, yCoords, 5) ;
		}
		G.setStroke(new BasicStroke(stdStroke)) ;
   }
    public static void DrawRoundRect(Point Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color color, Color ContourColor, boolean contour)
	{
		int[] offset = UtilS.OffsetFromPos(Alignment, l, h);
		G.setStroke(new BasicStroke(Thickness));
		if (contour)
		{
			G.setColor(ContourColor);
			G.fillRoundRect(Pos.x + offset[0] - Thickness, Pos.y + offset[1] - Thickness, l + 2*Thickness, h + 2*Thickness, ArcWidth, ArcHeight);
		}
		if (color != null)
		{
			G.setColor(color);
			G.fillRoundRect(Pos.x + offset[0], Pos.y + offset[1], l, h, ArcWidth, ArcHeight);
		}
		G.setStroke(new BasicStroke(1));
	}
    public static void DrawRoundRect(Point Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color[] colors, Color ContourColor, boolean contour)
	{
		int[] offset = UtilS.OffsetFromPos(Alignment, l, h);
		G.setStroke(new BasicStroke(Thickness));
		if (contour)
		{
			G.setColor(ContourColor);
			G.fillRoundRect(Pos.x + offset[0] - Thickness, Pos.y + offset[1] - Thickness, l + 2*Thickness, h + 2*Thickness, ArcWidth, ArcHeight);
		}
		if (colors[0] != null & colors[1] != null)
		{
		    GradientPaint gradient = new GradientPaint(Pos.x + offset[0], Pos.y + offset[1], colors[0], offset[0], offset[1] + h, colors[1]);
		    G.setPaint(gradient);
			G.fillRoundRect(Pos.x + offset[0], Pos.y +  offset[1], l, h, ArcWidth, ArcHeight);
		}
		G.setStroke(new BasicStroke(1));
	}
    public static void DrawArc(Point Pos, int l, int h, double[] angle, String unit, Color color)
    {
    	if (unit.equals("rad"))
    	{
    		angle[0] = 180.0/Math.PI*angle[0];
    		angle[1] = 180.0/Math.PI*angle[1];
    	}
    	G.drawArc(Pos.x - Math.abs(l/2), Pos.y - Math.abs(h/2), l, h, (int) angle[0], (int) angle[1]);
    }
    public static void Arrow(Point Pos, int size, double theta, boolean fill, double tipSize, Color color)
    {
    	double open = 0.8;
    	int ax1 = (int)(Pos.x - open*size*Math.cos(theta) - tipSize/3.5*Math.sin(theta));
    	int ay1 = (int)(Pos.y + open*size*Math.sin(theta) - tipSize/3.5*Math.cos(theta));
    	int ax2 = Pos.x;
    	int ay2 = Pos.y;
     	int ax3 = (int)(Pos.x - open*size*Math.cos(theta) + tipSize/3.5*Math.sin(theta));
     	int ay3 = (int)(Pos.y + open*size*Math.sin(theta) + tipSize/3.5*Math.cos(theta));
     	DP.drawPolygon(new int[] {ax1, ax2, ax3}, new int[] {ay1, ay2, ay3}, 1, color, color);
    }
    
    public static void points(List<Point> points, int size, Color fillColor, Color contourColor)
    {
    	points.forEach(pos -> DP.circle(pos, size, fillColor, contourColor)) ;
    }
    public static void points(List<Point> points, int size, Color fillColor)
    {
    	points(points, size, fillColor, null) ;
    }

}
