package graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.List;

import libUtil.Align;
import libUtil.Util;
import main.Scale;

public class DrawPrimitives
{
	private Graphics2D graphs ;
	
	public static double stdAngle ;
	public static final int stdStroke ;
	public static final Font stdFont ;

	static
	{
		stdAngle = 0 ;
		stdStroke = 1;
		stdFont = new Font("SansSerif", Font.BOLD, 13) ;
	}
	
	public void setGraphics(Graphics2D graph2D)
	{
		this.graphs = graph2D ;
	}
	
	public int TextL(String text, Font font)
	{
		if (graphs == null) { return 0 ;}
		
		FontMetrics metrics = graphs.getFontMetrics(font) ;
		return metrics.stringWidth(text) ;
	}
	
	public void drawText(Point pos, Align align, double angle, String text, Font font, Color color)
	{
		// by default starts at the left bottom
		Dimension size = new Dimension(TextL(text, font), Util.TextH(font.getSize())) ;
		Point offset = Util.offsetForAlignment(align, size) ;
		AffineTransform backup = graphs.getTransform() ;		
		
		graphs.transform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, pos.x, pos.y)) ;

		graphs.setColor(color) ;
		graphs.setFont(font) ;
		graphs.drawString(text, pos.x + offset.x, pos.y + offset.y + size.height) ;
        
		graphs.setTransform(backup) ;
	}
	
	public void drawText(Point pos, Align align, String text, Color color)
	{
		drawText(pos, align, stdAngle, text, stdFont, color) ;
	}
	
	public void drawLine(Point p1, Point p2, int stroke, Color color)
	{
		graphs.setColor(color) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		
		graphs.drawLine(p1.x, p1.y, p2.x, p2.y) ;
		
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	
	public void drawLine(Point p1, Point p2, Color color)
	{
		drawLine(p1, p2, stdStroke, color) ;
	}
	public void drawArc(Point center, int diameter, int stroke, int startAngle, int endAngle, Color color, Color contourColor)
	{
		graphs.setColor(color) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		if (color != null)
		{
			graphs.fillArc(center.x - diameter / 2, center.y - diameter / 2, diameter, diameter, startAngle, endAngle) ;
		}
		if (contourColor != null)
		{
			graphs.setColor(contourColor) ;
			graphs.drawArc(center.x - diameter / 2, center.y - diameter / 2, diameter, diameter, startAngle, endAngle) ;
		}
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawPolyLine(int[] x, int[] y, int stroke, Color color)
	{
		graphs.setColor(color) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		graphs.drawPolyline(x, y, x.length) ;
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawPolyLine(List<Integer> x, List<Integer> y, int stroke, Color color)
	{
		if (x == null | y == null) { return ;}
		if (x.isEmpty() | y.isEmpty()) { return ;}
		
		int[] arrayX = new int[x.size()];
		int[] arrayY = new int[y.size()];
		for (int i = 0 ; i < x.size() ; i += 1) arrayX[i] = x.get(i);
		for (int i = 0 ; i < y.size() ; i += 1) arrayY[i] = y.get(i);
		drawPolyLine(arrayX, arrayY, stroke, color) ;
	}
	public void drawPolyLine(List<Integer> x, List<Integer> y, Color color)
	{
		drawPolyLine(x, y, stdStroke, color) ;
	}
	public void drawPolyLine(int[] x, int[] y, Color color)
	{
		drawPolyLine(x, y, stdStroke, color) ;
	}
	
	public void drawRect(Point pos, Align align, Dimension size, int stroke, Color color, Color contourColor)
	{
		// Rectangle by default starts at the left top
		Point offset = Util.offsetForAlignment(align, size) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		if (color != null)
		{
			graphs.setColor(color) ;
			graphs.fillRect(pos.x + offset.x, pos.y + offset.y, size.width, size.height) ;
		}
		if (contourColor != null)
		{
			graphs.setColor(contourColor) ;
			graphs.drawRect(pos.x + offset.x, pos.y + offset.y, size.width, size.height) ;
		}
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawRect(Point pos, Align align, Dimension size, Color color, Color contourColor)
	{
		drawRect(pos, align, size, stdStroke, color, contourColor) ;
	}
	public void drawRoundRect(Point pos, Align align, Dimension size, int stroke, Color fillColor, boolean contour, int arcWidth, int arcHeight)
	{
		// Round rectangle by default starts at the left top
		Point offset = Util.offsetForAlignment(align, size) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		if (fillColor != null)
		{
		    graphs.setColor(fillColor) ;
			graphs.fillRoundRect(pos.x + offset.x, pos.y + offset.y, size.width, size.height, arcWidth, arcHeight) ;
		}
		if (contour)
		{
			graphs.setColor(Color.black) ;
			graphs.drawRoundRect(pos.x + offset.x, pos.y + offset.y, size.width, size.height, arcWidth, arcHeight) ;
		}
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawRoundRect(Point pos, Align align, Dimension size, int stroke, Color fillColor, boolean contour)
	{
		drawRoundRect(pos, align, size, stroke, fillColor, contour, 10, 10) ;
	}
	public void drawRoundRect(Point pos, Align align, Dimension size, Color fillColor, boolean contour)
	{
		drawRoundRect(pos, align, size, stdStroke, fillColor, contour, 10, 10) ;
	}
	public void drawGradRoundRect(Point pos, Align align, Dimension size, int stroke, int arcWidth, int arcHeight, Color topColor, Color botColor, boolean contour)
	{
		// Round rectangle by default starts at the left top
		Point offset = Util.offsetForAlignment(align, size) ;
		int[] corner = new int[] {pos.x + offset.x, pos.y + offset.y} ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		if (topColor != null & botColor != null)
		{
		    GradientPaint gradient = new GradientPaint(corner[0], corner[1], topColor, corner[0], corner[1] + size.height, botColor) ;
		    graphs.setPaint(gradient) ;
			graphs.fillRoundRect(corner[0], corner[1], size.width, size.height, arcWidth, arcHeight) ;
		}
		if (contour)
		{
			graphs.setColor(Color.black) ;
			graphs.drawRoundRect(corner[0], corner[1], size.width, size.height, arcWidth, arcHeight) ;
		}
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawGradRoundRect(Point pos, Align align, Dimension size, int stroke, Color topColor, Color botColor, boolean contour)
	{
		drawGradRoundRect(pos, align, size, stroke, 10, 10, topColor, botColor, contour) ;
	}
	public void drawGradRoundRect(Point pos, Align align, Dimension size, Color topColor, Color botColor, boolean contour)
	{
		drawGradRoundRect(pos, align, size, stdStroke, topColor, botColor, contour) ;
	}
	public void circle(Point center, int diameter, int stroke, Color color, Color contourColor)
	{
		graphs.setColor(color) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		if (color != null)
		{
			graphs.fillOval(center.x - diameter/2, center.y - diameter/2, diameter, diameter) ;
		}
		if (contourColor != null)
		{
			graphs.setColor(contourColor) ;
			graphs.drawOval(center.x - diameter/2, center.y - diameter/2, diameter, diameter) ;
		}
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void circle(Point center, int diameter, Color color, Color contourColor)
	{
		circle(center, diameter, stdStroke, color, contourColor) ;
	}
	public void circle(Point center, int diameter, Color color)
	{
		circle(center, diameter, color, null) ;
	}
	public void drawPolygon(int[] x, int[] y, int stroke, Color color, Color contourColor)
	{
		graphs.setColor(contourColor);
		graphs.drawPolygon(x, y, x.length);
		graphs.setColor(color) ;
		graphs.setStroke(new BasicStroke(stroke)) ;
		graphs.fillPolygon(x, y, x.length) ;
		graphs.setStroke(new BasicStroke(stdStroke)) ;
	}
	public void drawPolygon(int[] x, int[] y, Color color)
	{
		drawPolygon(x, y, stdStroke, color, Color.black) ;
	}

	public void drawImage(Image image, Point pos, Align align)
	{
		drawImage(image, pos, Scale.unit, align) ;
	}
	public void drawImage(Image image, Point pos, Scale scale, Align align)
	{
		drawImage(image, pos, 0, scale, align) ;
	}
	public void drawImage(Image image, Point pos, double angle, Scale scale, Align align)
	{
		drawImage(image, pos, angle, scale, false, false, align, 1) ;
	}
	public void drawImage(Image image, Point pos, double angle, Scale scale, boolean flipH, boolean flipV, Align align, double alpha)
	{       
		if (image == null) { System.out.println("Tentando desenhar imagem nula na pos " + pos) ; return ; }
		
		Dimension size = new Dimension((int)(scale.x * image.getWidth(null)), (int)(scale.y * image.getHeight(null))) ;
		size = new Dimension ((!flipH ? 1 : -1) * size.width, (!flipV ? 1 : -1) * size.height) ;
		Point offset = Util.offsetForAlignment(align, size) ;
		AffineTransform backup = graphs.getTransform() ;
		graphs.transform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, pos.x, pos.y)) ;
		graphs.setComposite(AlphaComposite.SrcOver.derive((float) alpha)) ;
		
		graphs.drawImage(image, pos.x + offset.x, pos.y + offset.y, size.width, size.height, null) ;
		
		graphs.setComposite(AlphaComposite.SrcOver.derive((float) 1.0)) ;
        graphs.setTransform(backup) ;
	}

}
