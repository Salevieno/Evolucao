package Graphics;

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

import Components.Artro;
import Components.Food;
import Main.UtilS;

public class DrawingOnAPanel
{
    Font TextFont = new Font("SansSerif", Font.PLAIN, 20);
	Font BoldTextFont = new Font("SansSerif", Font.BOLD, 20);
	int stdStroke = 1;
	private Graphics2D G;
	Color[] ColorPalette;
	
	public DrawingOnAPanel(Graphics g)
	{
		G = (Graphics2D) g;
		ColorPalette = UtilS.ColorPalette(2);
	}	
	public void paint(Graphics g) 
    { 
    	// This method is necessary, don't delete it
    } 	
	public void refresh()
	{
		
	}   
	
	
	// primitive functions
	public void DrawText(Point Pos, String Alignment, double angle, String Text, Font font, Color color)
	{
		// Rectangle by default starts at the left bottom
		//int TextL = UtilG.TextL(Text, font, G), TextH = UtilG.TextH(font.getSize()) ;
		//int[] offset = UtilG.OffsetFromPos(Alignment, TextL, TextH) ;
		int[] offset = new int[2] ;
		AffineTransform backup = G.getTransform() ;		
		G.setColor(color) ;
		G.setFont(font) ;
		G.setTransform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, Pos.x, Pos.y)) ;	// Rotate text
		G.drawString(Text, Pos.x + offset[0], Pos.y + offset[1]) ;
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
	public void DrawArtros(ArrayList<Artro> artros, Canva canva)
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
				//DP.DrawText(DrawingPos, ArtroWill[a], "Center", 0, "Bold", 10, ColorPalette[5]);
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
				/*if (FoodStatus[f])
				{
					//DP.DrawText(Uts.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), String.valueOf(f), "Center", 0, "None", 13, Color.black);
					//DP.DrawCircle(UtilS.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), (int) (FoodSize[FoodType[f]]), true, ColorPalette[4], color[FoodType[f]]);
				}*/
			}
		}
	}
    
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
	

    public void DrawGrid(Point initPos, Point finalPos, int NumSpacing)
	{
		int LineThickness = 1;
		int[] Length = new int[] {finalPos.x - initPos.x, initPos.y - finalPos.y};
		for (int i = 0; i <= NumSpacing - 1; i += 1)
		{
			DrawLine(new Point (initPos.x + (i + 1)*Length[0]/NumSpacing, initPos.y),
					new Point (initPos.x + (i + 1)*Length[0]/NumSpacing, initPos.y - Length[1]), LineThickness, ColorPalette[4]);						
			DrawLine(new Point (initPos.x, initPos.y - (i + 1)*Length[1]/NumSpacing),
					new Point (initPos.x + Length[0], initPos.y - (i + 1)*Length[1]/NumSpacing), LineThickness, ColorPalette[4]);						
		}
	}
    

    public void DrawAxis(Point Pos, int size, int[] CanvasDim)
    {
    	int thickness = 2;
    	int ArrowSize = 20;
    	int TextSize = 15, TextOffset = 10;
    	// x axis
    	DrawLine(Pos, new Point (Pos.x + size, Pos.y), thickness, ColorPalette[7]);
    	DrawArrow(new Point (Pos.x + size + ArrowSize/2, Pos.y), ArrowSize, 0, true, ArrowSize, ColorPalette[7]);
    	DrawText(new Point (Pos.x + size, Pos.y - TextOffset), "x", "Center", 0, "Bold", TextSize, ColorPalette[7]);
    	DrawText(new Point (Pos.x + size, Pos.y + 2*TextOffset), String.valueOf(UtilS.Round(CanvasDim[0], 1)) + " m", "BotLeft", 0, "Plain", TextSize, ColorPalette[7]);
    	// y axis
    	DrawLine(Pos, new Point (Pos.x, Pos.y - size), thickness, ColorPalette[6]);
    	DrawArrow(new Point (Pos.x, Pos.y - size - ArrowSize/2), ArrowSize, Math.PI/2, true, ArrowSize, ColorPalette[6]);
    	DrawText(new Point (Pos.x - TextOffset, Pos.y - size), "y", "Center", 0, "Bold", TextSize, ColorPalette[6]);
    	DrawText(new Point (Pos.x + TextOffset, Pos.y - size), String.valueOf(UtilS.Round(CanvasDim[1], 1)) + " m", "BotLeft", 0, "Plain", TextSize, ColorPalette[6]);
    	// z axis
    	DrawLine(Pos, new Point (Pos.x - (int) (size/25), Pos.y + (int) (size/25)), thickness, ColorPalette[5]);
    	DrawArrow(new Point (Pos.x - (int) (size/25) - ArrowSize/4, Pos.y + (int) (size/25) + ArrowSize/4), ArrowSize, -3*Math.PI/4, true, ArrowSize, ColorPalette[5]);
    	DrawText(new Point (Pos.x - (int) (size/25) - TextOffset, Pos.y + (int) (size/25) - TextOffset), "z", "Center", 0, "Bold", TextSize, ColorPalette[5]);
    }
    
    public void DrawGraph(Point pos, String Title, int size, Color color)
	{
		int asize = 8 * size / 100;
		double aangle = Math.PI * 30 / 180.0;
		DrawText(new Point (pos.x + size/2, (int) (pos.y - size - 13 - 2)), Title, "Center", 0, "Bold", 13, color);
		DrawLine(pos, new Point (pos.x, (int) (pos.y - size - asize)), 2, ColorPalette[9]);
		DrawLine(pos, new Point ((int) (pos.x + size + asize), pos.y), 2, ColorPalette[9]);
		DrawArrow(new Point (pos.x + size + asize, pos.y), asize, aangle, false, 0.4 * asize, ColorPalette[9]);
		DrawArrow(new Point (pos.x + size + asize, pos.y), asize, aangle, false, 0.4 * asize, ColorPalette[9]);
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
		DrawGrid(pos, new Point (pos.x + size, pos.y - size), 10);
	}
	
	public void DrawGraph(Point pos, String title, ArrayList<ArrayList<Double>> yValues, double maxEver, Color[] color)
	{
		int size = 100;
		DrawGraph(pos, title, size, ColorPalette[14]);
		if (1 <= yValues.size())
		{
			Point maxYPos = new Point (pos.x - size / 4, pos.y - size) ;
			DrawText(maxYPos, String.valueOf(maxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
			
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
					yPos.add((pos.y - (int) (size * y / maxEver))) ;
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
    public void DrawText(Point pos, String Text, String Alignment, float angle, String Style, int size, Color color)
    {
		float TextL = UtilS.TextL(Text, TextFont, size, G), TextH = UtilS.TextH(size);
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
    }
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
    public void DrawArrow(Point Pos, int size, double theta, boolean fill, double ArrowSize, Color color)
    {
    	double open = 0.8;
    	int ax1 = (int)(Pos.x - open*size*Math.cos(theta) - ArrowSize/3.5*Math.sin(theta));
    	int ay1 = (int)(Pos.y + open*size*Math.sin(theta) - ArrowSize/3.5*Math.cos(theta));
    	int ax2 = Pos.x;
    	int ay2 = Pos.y;
     	int ax3 = (int)(Pos.x - open*size*Math.cos(theta) + ArrowSize/3.5*Math.sin(theta));
     	int ay3 = (int)(Pos.y + open*size*Math.sin(theta) + ArrowSize/3.5*Math.cos(theta));
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
