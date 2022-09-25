package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import Main.Uts;

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
		ColorPalette = Uts.ColorPalette(2);
	}	
	public void paint(Graphics g) 
    { 
    	// This method is necessary, don't delete it
    } 	
	public void refresh()
	{
		
	}   
	
	// Primitive functions
	public void DrawImage(Image File, int[] Pos, float angle, float[] Scale, String Alignment)
	{       
		if (File != null)
		{
			int l = (int)(Scale[0]*File.getWidth(null)), h = (int)(Scale[1]*File.getHeight(null));		
			AffineTransform a = null;	// Rotate image
			AffineTransform backup = G.getTransform();
			if (Alignment.equals("Left"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0] + l/2, Pos[1] - h/2);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, Pos[0], Pos[1] - h, l, h, null);
			}
			if (Alignment.equals("Center"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0], Pos[1]);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, Pos[0] - l/2, Pos[1] - h/2, l, h, null);
			}
			if (Alignment.equals("Right"))
			{
				a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0] - l/2, Pos[1] + h/2);	// Rotate image
				G.setTransform(a);
				G.drawImage(File, Pos[0] + l, Pos[1] - h, l, h, null);
			}
	        G.setTransform(backup);
		}
	}
    public void DrawText(int[] Pos, String Text, String Alignment, float angle, String Style, int size, Color color)
    {
		float TextL = Uts.TextL(Text, TextFont, size, G), TextH = Uts.TextH(size);
    	int[] Offset = Uts.OffsetFromPos(Alignment, (int)TextL, (int)TextH);
		AffineTransform a = null;	// Rotate rectangle
		AffineTransform backup = G.getTransform();
		if (Alignment.equals("Left"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0] - 0.5*TextL, Pos[1] + 0.5*TextH);	// Rotate text
    	}
		else if (Alignment.equals("Center"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0], Pos[1] + 0.5*TextH);	// Rotate text
    	}
    	else if (Alignment.equals("Right"))
    	{
			a = AffineTransform.getRotateInstance(-angle*Math.PI/180, Pos[0], Pos[1] + 0.5*TextH);	// Rotate text
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
    	G.drawString(Text, Pos[0] + Offset[0], Pos[1] + Offset[1]);
        G.setTransform(backup);
    }
    public void DrawFitText(int[] Pos, String Text, String Alignment, float angle, String Style, int sy, int length, int size, Color color)
	{
		String[] FitText = Uts.FitText(Text, length);
		for (int i = 0; i <= FitText.length - 1; i += 1)
		{
			DrawText(new int[] {Pos[0], Pos[1] + i*sy}, FitText[i], Alignment, angle, Style, size, color);						
		}
	}
    public void DrawPoint(int[] Pos, int size, boolean fill, Color ContourColor, Color FillColor)
    {
    	G.setColor(ContourColor);
    	G.drawOval(Pos[0] - size/2, Pos[1] - size/2, size, size);
    	if (fill)
    	{
        	G.setColor(FillColor);
        	G.fillOval(Pos[0] - size/2, Pos[1] - size/2, size, size);
    	}
    }
    public void DrawLine(int[] PosInit, int[] PosFinal, int thickness, Color color)
    {
    	G.setColor(color);
    	G.setStroke(new BasicStroke(thickness));
    	G.drawLine(PosInit[0], PosInit[1], PosFinal[0], PosFinal[1]);
    	G.setStroke(new BasicStroke(stdStroke));
    }
    public void DrawRect(int[] Pos, int l, int h, String Alignment, float angle, int Thickness, boolean fill, Color ContourColor, Color FillColor)
    {
    	int[] Offset = new int[2];
		AffineTransform Tx = new AffineTransform();
		Tx.translate(0, 22);    // Translation to correct positions
		//AffineTransform a = null;	// Rotate rectangle
		AffineTransform backup = G.getTransform();
		G.setStroke(new BasicStroke(Thickness));
    	if (Alignment.equals("Left"))
    	{
    	     Tx.translate(Pos[0], Pos[1]);    // S3: final translation
    	     Tx.rotate(-angle*Math.PI/180.0);                  // S2: rotate around anchor
    	     Tx.translate(-Pos[0], -Pos[1]);  // S1: translate anchor to origin
			//a = AffineTransform.getRotateInstance(angle*Math.PI/180.0, Pos[0], Pos[1]);	// Rotate rectangle
    		Offset = new int[] {0, 0};
    	}
    	else if (Alignment.equals("Center"))
    	{
			//a = AffineTransform.getRotateInstance(-angle*Math.PI/180.0, Pos[0], Pos[1]);	// Rotate rectangle
    		Offset = new int[] {l/2, h/2};
    	}
    	else if (Alignment.equals("Right"))
    	{
			//a = AffineTransform.getRotateInstance(-angle*Math.PI/180.0, Pos[0], Pos[1] + h/2);	// Rotate rectangle
    		Offset = new int[] {l, 0};
    	}
    	if (0 < Math.abs(angle))
    	{
    		G.setTransform(Tx);
    	}
    	if (fill)
    	{
    		G.setColor(FillColor);
        	G.fillRect(Pos[0] - Offset[0], Pos[1] - Offset[1], l, h);
    	}
    	G.setColor(ContourColor);
    	G.drawRect(Pos[0] - Offset[0], Pos[1] - Offset[1], l, h);
        G.setTransform(backup);
		G.setStroke(new BasicStroke(1));
    }
    public void DrawRoundRect(int[] Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color color, Color ContourColor, boolean contour)
	{
		int[] offset = Uts.OffsetFromPos(Alignment, l, h);
		G.setStroke(new BasicStroke(Thickness));
		if (contour)
		{
			G.setColor(ContourColor);
			G.fillRoundRect(Pos[0] + offset[0] - Thickness, Pos[1] + offset[1] - Thickness, l + 2*Thickness, h + 2*Thickness, ArcWidth, ArcHeight);
		}
		if (color != null)
		{
			G.setColor(color);
			G.fillRoundRect(Pos[0] + offset[0], Pos[1] + offset[1], l, h, ArcWidth, ArcHeight);
		}
		G.setStroke(new BasicStroke(1));
	}
    public void DrawRoundRect(int[] Pos, String Alignment, int l, int h, int Thickness, int ArcWidth, int ArcHeight, Color[] colors, Color ContourColor, boolean contour)
	{
		int[] offset = Uts.OffsetFromPos(Alignment, l, h);
		G.setStroke(new BasicStroke(Thickness));
		if (contour)
		{
			G.setColor(ContourColor);
			G.fillRoundRect(Pos[0] + offset[0] - Thickness, Pos[1] + offset[1] - Thickness, l + 2*Thickness, h + 2*Thickness, ArcWidth, ArcHeight);
		}
		if (colors[0] != null & colors[1] != null)
		{
		    GradientPaint gradient = new GradientPaint(Pos[0] + offset[0], Pos[1] + offset[1], colors[0], offset[0], offset[1] + h, colors[1]);
		    G.setPaint(gradient);
			G.fillRoundRect(Pos[0] + offset[0], Pos[1] +  offset[1], l, h, ArcWidth, ArcHeight);
		}
		G.setStroke(new BasicStroke(1));
	}
    public void DrawCircle(int[] Pos, int diam, boolean fill, Color ContourColor, Color FillColor)
    {
    	G.setColor(ContourColor);
    	G.drawOval(Pos[0] - diam/2, Pos[1] - diam/2, diam, diam);
    	if (fill)
    	{
        	G.setColor(FillColor);
        	G.fillOval(Pos[0] - diam/2, Pos[1] - diam/2, diam, diam);
    	}
    }
    public void DrawPolyLine(int[] x, int[] y, int thickness, Color color)
    {
    	G.setColor(color);
    	G.setStroke(new BasicStroke(thickness));
    	G.drawPolyline(x, y, x.length);
    	G.setStroke(new BasicStroke(stdStroke));
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
    public void DrawArc(int[] Pos, int l, int h, double[] angle, String unit, Color color)
    {
    	if (unit.equals("rad"))
    	{
    		angle[0] = 180.0/Math.PI*angle[0];
    		angle[1] = 180.0/Math.PI*angle[1];
    	}
    	G.drawArc(Pos[0] - Math.abs(l/2), Pos[1] - Math.abs(h/2), l, h, (int) angle[0], (int) angle[1]);
    }
    public void DrawArrow(int[] Pos, int size, double theta, boolean fill, double ArrowSize, Color color)
    {
    	double open = 0.8;
    	int ax1 = (int)(Pos[0] - open*size*Math.cos(theta) - ArrowSize/3.5*Math.sin(theta));
    	int ay1 = (int)(Pos[1] + open*size*Math.sin(theta) - ArrowSize/3.5*Math.cos(theta));
    	int ax2 = Pos[0];
    	int ay2 = Pos[1];
     	int ax3 = (int)(Pos[0] - open*size*Math.cos(theta) + ArrowSize/3.5*Math.sin(theta));
     	int ay3 = (int)(Pos[1] + open*size*Math.sin(theta) + ArrowSize/3.5*Math.cos(theta));
     	DrawPolygon(new int[] {ax1, ax2, ax3}, new int[] {ay1, ay2, ay3}, fill, color, color);
    }
    public void DrawAxis(int[] Pos, int size, int[] CanvasDim)
    {
    	int thickness = 2;
    	int ArrowSize = 20;
    	int TextSize = 15, TextOffset = 10;
    	// x axis
    	DrawLine(Pos, new int[] {Pos[0] + size, Pos[1]}, thickness, ColorPalette[7]);
    	DrawArrow(new int[] {Pos[0] + size + ArrowSize/2, Pos[1]}, ArrowSize, 0, true, ArrowSize, ColorPalette[7]);
    	DrawText(new int[] {Pos[0] + size, Pos[1] - TextOffset}, "x", "Center", 0, "Bold", TextSize, ColorPalette[7]);
    	DrawText(new int[] {Pos[0] + size, Pos[1] + 2*TextOffset}, String.valueOf(Uts.Round(CanvasDim[0], 1)) + " m", "BotLeft", 0, "Plain", TextSize, ColorPalette[7]);
    	// y axis
    	DrawLine(Pos, new int[] {Pos[0], Pos[1] - size}, thickness, ColorPalette[6]);
    	DrawArrow(new int[] {Pos[0], Pos[1] - size - ArrowSize/2}, ArrowSize, Math.PI/2, true, ArrowSize, ColorPalette[6]);
    	DrawText(new int[] {Pos[0] - TextOffset, Pos[1] - size}, "y", "Center", 0, "Bold", TextSize, ColorPalette[6]);
    	DrawText(new int[] {Pos[0] + TextOffset, Pos[1] - size}, String.valueOf(Uts.Round(CanvasDim[1], 1)) + " m", "BotLeft", 0, "Plain", TextSize, ColorPalette[6]);
    	// z axis
    	DrawLine(Pos, new int[] {Pos[0] - (int) (size/25), Pos[1] + (int) (size/25)}, thickness, ColorPalette[5]);
    	DrawArrow(new int[] {Pos[0] - (int) (size/25) - ArrowSize/4, Pos[1] + (int) (size/25) + ArrowSize/4}, ArrowSize, -3*Math.PI/4, true, ArrowSize, ColorPalette[5]);
    	DrawText(new int[] {Pos[0] - (int) (size/25) - TextOffset, Pos[1] + (int) (size/25) - TextOffset}, "z", "Center", 0, "Bold", TextSize, ColorPalette[5]);
    }
    public void DrawGrid(int[] InitPos, int[] FinalPos, int NumSpacing)
	{
		int LineThickness = 1;
		int[] Length = new int[] {FinalPos[0] - InitPos[0], InitPos[1] - FinalPos[1]};
		for (int i = 0; i <= NumSpacing - 1; i += 1)
		{
			DrawLine(new int[] {InitPos[0] + (i + 1)*Length[0]/NumSpacing, InitPos[1]}, new int[] {InitPos[0] + (i + 1)*Length[0]/NumSpacing, InitPos[1] - Length[1]}, LineThickness, ColorPalette[4]);						
			DrawLine(new int[] {InitPos[0], InitPos[1] - (i + 1)*Length[1]/NumSpacing}, new int[] {InitPos[0] + Length[0], InitPos[1] - (i + 1)*Length[1]/NumSpacing}, LineThickness, ColorPalette[4]);						
		}
	}
    public void DrawGraph(int[] Pos, String Title, int size, Color color)
	{
		int asize = 8 * size / 100;
		double aangle = Math.PI * 30 / 180.0;
		DrawText(new int[] {Pos[0] + size/2, (int) (Pos[1] - size - 13 - 2)}, Title, "Center", 0, "Bold", 13, color);
		DrawLine(Pos, new int[] {Pos[0], (int) (Pos[1] - size - asize)}, 2, ColorPalette[9]);
		DrawLine(Pos, new int[] {(int) (Pos[0] + size + asize), Pos[1]}, 2, ColorPalette[9]);
		DrawArrow(new int[] {Pos[0] + size + asize, Pos[1]}, asize, aangle, false, 0.4 * asize, ColorPalette[9]);
		DrawArrow(new int[] {Pos[0] + size + asize, Pos[1]}, asize, aangle, false, 0.4 * asize, ColorPalette[9]);
		//DrawPolyLine(new int[] {Pos[0] - asize, Pos[0], Pos[0] + asize}, new int[] {(int) (Pos[1] - 1.1*size) + asize, (int) (Pos[1] - 1.1*size), (int) (Pos[1] - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (Pos[0] + 1.1*size - asize), (int) (Pos[0] + 1.1*size), (int) (Pos[0] + 1.1*size - asize)}, new int[] {Pos[1] - asize, Pos[1], Pos[1] + asize}, 2, ColorPalette[4]);
		DrawGrid(Pos, new int[] {Pos[0] + size, Pos[1] - size}, 10);
	}
    
    // Composed functions
    public void DrawPoints(int[][] Pos, int size, boolean fill, Color ContourColor, Color FillColor)
    {
    	for (int i = 0; i <= Pos.length - 1; i += 1)
    	{
    		DrawPoint(Pos[i], size, fill, ContourColor, FillColor);
    	}
    }
    public void DrawMenu(int[] Pos, String Alignment, int l, int h, int Thickness, Color[] colors, Color ContourColor)
    {
    	int border = 6;
    	DrawRoundRect(Pos, Alignment, l, h, Thickness, 5, 5, colors[0], ContourColor, true);
    	DrawRoundRect(Pos, Alignment, l - 2*border, h - 2*border, Thickness, 5, 5, colors[1], ContourColor, true);
    }
    public void DrawColorPallete(Color[] Pallete)
	{
		int[] Pos = new int[] {400, 300};
		int L = 20, H = 20;
		int NumCol = 4;
		DrawRoundRect(Pos, "TopLeft", NumCol * L + 10, (Pallete.length / NumCol + 1) * H + 10, 1, 5, 5, ColorPalette[7], ColorPalette[7], true);
		for (int i = 0; i <= Pallete.length - 1; i += 1)
		{
			int[] ColorPos = new int[] {(int) (Pos[0] + 5 + L / 2 + (i % NumCol) * L), (int) (Pos[1] + 5 + H / 2 + i / NumCol * H)};
			DrawRoundRect(ColorPos, "Center", L, H, 1, 5, 5, Pallete[i], Pallete[i], false);
		}
	}
}