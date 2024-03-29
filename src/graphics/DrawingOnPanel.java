package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import main.UtilS;

public class DrawingOnPanel
{
    Font stdFont = new Font("SansSerif", Font.PLAIN, 20);
	Font boldStdFont = new Font("SansSerif", Font.BOLD, 20);
	int stdStroke = 1;
	private Graphics2D G;
	
	public DrawingOnPanel(Graphics g)
	{
		G = (Graphics2D) g;
	}	
	public void paint(Graphics g) 
    { 
    	// This method is necessary, don't delete it
    } 	
	public void refresh()
	{
		
	}   

	
	
	public Graphics2D getG()
	{
		return G;
	}
	public void setG(Graphics2D g)
	{
		G = g;
	}
	// primitive functions
	public void DrawText(Point pos, String alignment, double angle, String text, Font font, Color color)
	{
		// Rectangle by default starts at the left bottom
		//int TextL = UtilG.TextL(Text, font, G), TextH = UtilG.TextH(font.getSize()) ;
		//int[] offset = UtilG.OffsetFromPos(Alignment, TextL, TextH) ;
		int[] offset = new int[2] ;
		AffineTransform backup = G.getTransform() ;		
		G.setColor(color) ;
		G.setFont(font) ;
		//G.setTransform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, pos.x, pos.y)) ;	// Rotate text
		G.drawString(text, pos.x + offset[0], pos.y + offset[1]) ;
        G.setTransform(backup) ;
    }
	

    public void DrawLine(Point initPos, Point finalPos, int stroke, Color color)
    {
    	G.setColor(color);
    	G.setStroke(new BasicStroke(stroke));
    	G.drawLine(initPos.x, initPos.y, finalPos.x, finalPos.y);
    	G.setStroke(new BasicStroke(stdStroke));
    }
	
	public void DrawCircle(Point pos, int diam, Color contourColor, Color fillColor)
    {
		if (contourColor != null)
    	{
	    	G.setColor(contourColor);
	    	G.drawOval(pos.x - diam / 2, pos.y - diam / 2, diam, diam);
    	}
    	if (fillColor != null)
    	{
        	G.setColor(fillColor);
        	G.fillOval(pos.x - diam / 2, pos.y - diam / 2, diam, diam);
    	}
    }
	
	// composed functions
	/*public void DrawArtros(ArrayList<Artro> artros, Canva canva)
	{
		if (artros != null)
		{
			for (int i = 0; i <= artros.size() - 1; i += 1)
			{
				Point drawingPos = UtilS.ConvertToDrawingCoords(artros.get(i).getPos(), canva.getPos(), canva.getSize(), canva.getDimension());
				if (artros.get(i).getWill().equals("fight"))
				{
					DrawCircle(drawingPos, artros.get(i).getSpecies().getSize(), ColorPalette[4], ColorPalette[6]);
				}
				else
				{
					System.out.println(drawingPos);
					DrawCircle(drawingPos, artros.get(i).getSpecies().getSize(), ColorPalette[4], artros.get(i).getSpecies().getColor());
				}
				//DP.DrawText(DrawingPos, ArtroWill[a], "Center", 0, "Bold", 10, zAxisColor);
			}
			//int[] DrawingPos = Uts.ConvertToDrawingCoords(ArtroPos.x, CanvasPos, CanvasSize, CanvasDim);
			//DP.DrawCircle(DrawingPos, (int) (1.3 * ArtroSize[ArtroSpecies[0]]), true, ColorPalette[4], Color.yellow);
		}
	}
	
	public void DrawFood(ArrayList<Food> food, Canva canva)
	{
		if (food != null)
		{
			for (int i = 0; i <= food.size() - 1; i += 1)
			{
				Point drawingPos = UtilS.ConvertToDrawingCoords(food.get(i).getPos(), canva.getPos(), canva.getSize(), canva.getDimension());
				DrawCircle(drawingPos, food.get(i).getType().getSize(), ColorPalette[4], food.get(i).getType().getColor());
			}
		}
	}*/
    
    public void DrawPolyLine(ArrayList<Integer> x, ArrayList<Integer> y, int thickness, Color color)
    {
    	int[] xCoords = new int [x.size()] ;
    	int[] yCoords = new int [y.size()] ;
    	for (int i = 0 ; i <= x.size() - 1 ; i += 1)
    	{
    		xCoords[i] = (int) x.get(i) ;
    		yCoords[i] = (int) y.get(i) ;
    	}
    	G.setColor(color);
    	G.setStroke(new BasicStroke(thickness));
    	G.drawPolyline(xCoords, yCoords, x.size());
    	G.setStroke(new BasicStroke(stdStroke));
    }
	

    public void DrawGrid(Point initPos, Point finalPos, int NumSpacing, Color lineColor)
	{
		int stroke = 1;
		Dimension length = new Dimension(finalPos.x - initPos.x, initPos.y - finalPos.y) ;
		for (int i = 0 ; i <= NumSpacing - 1 ; i += 1)
		{
			// horizontal lines
			Point hPoint1 = new Point (initPos.x, initPos.y - (i + 1)*length.height/NumSpacing) ;
			Point hPoint2 = new Point (initPos.x + length.width, initPos.y - (i + 1)*length.height/NumSpacing) ;
			DrawLine(hPoint1, hPoint2, stroke, lineColor) ;	
			
			// vertical lines
			Point vPoint1 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y) ;
			Point vPoint2 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y - length.height) ;
			DrawLine(vPoint1, vPoint2, stroke, lineColor) ;					
		}
	}
    

    public void DrawAxis(Point Pos, int size, int[] CanvasDim, Color xAxisColor, Color yAxisColor, Color zAxisColor)
    {
    	Font textFont = new Font("SansSerif", Font.BOLD, 15) ;
    	int stroke = 2;
    	int ArrowSize = 20;
    	int TextOffset = 10;
    	// x axis
    	DrawLine(Pos, new Point (Pos.x + size, Pos.y), stroke, xAxisColor);
    	DrawArrow(new Point (Pos.x + size + ArrowSize/2, Pos.y), ArrowSize, 0, true, ArrowSize, xAxisColor);
    	//Point Pos, String Alignment, double angle, String Text, Font font, Color color
    	DrawText(new Point (Pos.x + size, Pos.y - TextOffset), "Center", 0, "x", textFont, xAxisColor);
    	DrawText(new Point (Pos.x + size, Pos.y + 2*TextOffset), "BotLeft", 0, String.valueOf(UtilS.Round(CanvasDim[0], 1)) + " m", textFont, xAxisColor);
    	// y axis
    	DrawLine(Pos, new Point (Pos.x, Pos.y - size), stroke, yAxisColor);
    	DrawArrow(new Point (Pos.x, Pos.y - size - ArrowSize/2), ArrowSize, Math.PI/2, true, ArrowSize, yAxisColor);
    	DrawText(new Point (Pos.x - TextOffset, Pos.y - size), "Center", 0, "y", textFont, yAxisColor);
    	DrawText(new Point (Pos.x + TextOffset, Pos.y - size), "BotLeft", 0, String.valueOf(UtilS.Round(CanvasDim[1], 1)) + " m", textFont, yAxisColor);
    	// z axis
    	DrawLine(Pos, new Point (Pos.x - (int) (size/25), Pos.y + (int) (size/25)), stroke, zAxisColor);
    	DrawArrow(new Point (Pos.x - (int) (size/25) - ArrowSize/4, Pos.y + (int) (size/25) + ArrowSize/4), ArrowSize, -3*Math.PI/4, true, ArrowSize, zAxisColor);
    	DrawText(new Point (Pos.x - (int) (size/25) - TextOffset, Pos.y + (int) (size/25) - TextOffset), "Center", 0, "z", textFont, zAxisColor);
    }
    
    public void DrawGraph(Point pos, String Title, int size, Color titleColor, Color lineColor)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 13) ;
		int arrowSize = 8 * size / 100;
		DrawText(new Point (pos.x + size/5, (int) (pos.y - size - 13)), "TopLeft", 0, Title, textFont, titleColor);
		DrawLine(pos, new Point (pos.x, (int) (pos.y - size - arrowSize)), 2, lineColor);
		DrawLine(pos, new Point ((int) (pos.x + size + arrowSize), pos.y), 2, lineColor);
		DrawArrow(new Point (pos.x + size + arrowSize, pos.y), arrowSize, 0, false, 0.4 * arrowSize, lineColor);
		DrawArrow(new Point (pos.x, pos.y - size - arrowSize), arrowSize, Math.PI / 2, false, 0.4 * arrowSize, lineColor);
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
		DrawGrid(pos, new Point (pos.x + size, pos.y - size), 10, Color.black);
	}
	
	public void PlotGraph(Point pos, String title, ArrayList<ArrayList<Double>> yValues, double maxYEver, Color[] color)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 12) ;
		int size = 100;
		DrawGraph(pos, title, size, Color.blue, Color.black);
		if (1 <= yValues.size())
		{
			Point maxYPos = new Point (pos.x - size / 4, pos.y - size) ;
			DrawText(maxYPos, "TopLeft", 0, String.valueOf(maxYEver), textFont, Color.blue);
			
			// for each curve
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
				DrawPolyLine(xPos, yPos, 2, color[j]);
			}
		}
	}
	
	//****************************************
	
	
	
	
	
	
	
	
	
	
	// Primitive functions
	public void DrawImage(Image File, Point pos, float angle, float[] Scale, String Alignment)
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
    /*public void DrawText(Point pos, String Text, String Alignment, float angle, String Style, int size, Color color)
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
    /*public void DrawFitText(Point Pos, String Text, String Alignment, float angle, String Style, int sy, int length, int size, Color color)
	{
		String[] FitText = UtilS.FitText(Text, length);
		for (int i = 0; i <= FitText.length - 1; i += 1)
		{
			DrawText(new int[] {Pos.x, Pos.y + i*sy}, FitText[i], Alignment, angle, Style, size, color);						
		}
	}*/
    public void DrawPoint(Point Pos, int size, boolean fill, Color ContourColor, Color FillColor)
    {
    	G.setColor(ContourColor);
    	G.drawOval(Pos.x - size/2, Pos.y - size/2, size, size);
    	if (fill)
    	{
        	G.setColor(FillColor);
        	G.fillOval(Pos.x - size/2, Pos.y - size/2, size, size);
    	}
    }
    public void DrawRect(Point pos, String alignment, Dimension size, int stroke, Color color, Color contourColor)
	{
		// Rectangle by default starts at the top left
		//int[] offset = UtilG.OffsetFromPos(Alignment, size) ;
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
    public void DrawRoundRect(Point Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color color, Color ContourColor, boolean contour)
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
    public void DrawRoundRect(Point Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color[] colors, Color ContourColor, boolean contour)
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
    public void DrawPolygon(int[] x, int[] y, boolean fill, Color ContourColor, Color FillColor)
    {
    	G.setColor(ContourColor);
    	G.drawPolygon(x, y, x.length);
    	if (fill)
    	{
    		G.setColor(FillColor);
        	G.fillPolygon(x, y, x.length);
    	}
    }
    public void DrawArc(Point Pos, int l, int h, double[] angle, String unit, Color color)
    {
    	if (unit.equals("rad"))
    	{
    		angle[0] = 180.0/Math.PI*angle[0];
    		angle[1] = 180.0/Math.PI*angle[1];
    	}
    	G.drawArc(Pos.x - Math.abs(l/2), Pos.y - Math.abs(h/2), l, h, (int) angle[0], (int) angle[1]);
    }
    public void DrawArrow(Point Pos, int size, double theta, boolean fill, double tipSize, Color color)
    {
    	double open = 0.8;
    	int ax1 = (int)(Pos.x - open*size*Math.cos(theta) - tipSize/3.5*Math.sin(theta));
    	int ay1 = (int)(Pos.y + open*size*Math.sin(theta) - tipSize/3.5*Math.cos(theta));
    	int ax2 = Pos.x;
    	int ay2 = Pos.y;
     	int ax3 = (int)(Pos.x - open*size*Math.cos(theta) + tipSize/3.5*Math.sin(theta));
     	int ay3 = (int)(Pos.y + open*size*Math.sin(theta) + tipSize/3.5*Math.cos(theta));
     	DrawPolygon(new int[] {ax1, ax2, ax3}, new int[] {ay1, ay2, ay3}, fill, color, color);
    }
    
    // Composed functions
    public void DrawPoints(Point[] Pos, int size, boolean fill, Color ContourColor, Color FillColor)
    {
    	for (int i = 0; i <= Pos.length - 1; i += 1)
    	{
    		DrawPoint(Pos[i], size, fill, ContourColor, FillColor);
    	}
    }
    public void DrawMenu(Point Pos, String Alignment, int l, int h, int Thickness, Color[] colors, Color ContourColor)
    {
    	int border = 6;
    	DrawRoundRect(Pos, Alignment, l, h, Thickness, 5, 5, colors[0], ContourColor, true);
    	DrawRoundRect(Pos, Alignment, l - 2*border, h - 2*border, Thickness, 5, 5, colors[1], ContourColor, true);
    }
    /*public void DrawColorPallete(Color[] Pallete)
	{
		Point Pos = new Point (400, 300);
		int L = 20, H = 20;
		int NumCol = 4;
		DrawRoundRect(Pos, "TopLeft", NumCol * L + 10, (Pallete.length / NumCol + 1) * H + 10, 1, 5, 5, ColorPalette[7], ColorPalette[7], true);
		for (int i = 0; i <= Pallete.length - 1; i += 1)
		{
			int[] ColorPos = new int[] {(int) (Pos.x + 5 + L / 2 + (i % NumCol) * L), (int) (Pos.y + 5 + H / 2 + i / NumCol * H)};
			DrawRoundRect(ColorPos, "Center", L, H, 1, 5, 5, Pallete[i], Pallete[i], false);
		}
	}*/

    

}
