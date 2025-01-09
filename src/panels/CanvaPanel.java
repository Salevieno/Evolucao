package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import components.Artro;
import components.Food;
import graphics.Canva;
import graphics.DrawPrimitives;
import main.Evolution;

public class CanvaPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private static final DrawPrimitives DP;
	private static final Canva canva;
	private static final Color bgColor;
	public static final Point canvaPos;
	public static final Dimension canvaSize;
	public static final Dimension canvaDimension;
	private static long frameTime;
	private static double fps;
	private static final double maxFPS;

	private static boolean displayCanva;

	static
	{
		DP = new DrawPrimitives();
		bgColor = new Color(50, 50, 50);
		canvaPos = new Point(50, 25);
		canvaSize = new Dimension(650, 650);
		canvaDimension = new Dimension(2000, 2000);
		canva = new Canva(canvaPos, canvaSize, canvaDimension);
		frameTime = System.nanoTime();
		fps = 0;
		maxFPS = 60;
		displayCanva = true;
	}

	public CanvaPanel(Dimension frameDimension)
	{
		InitializeJPanel(frameDimension);
	}

	public static Dimension getCanvaSize()
	{
		return canva.getSize();
	}

	public static Dimension getCanvaDimension()
	{
		return canva.getDimension();
	}

	public static void switchDisplayCanva()
	{
		displayCanva = !displayCanva;
	}

	public void displayArtros(List<Artro> artros)
	{
		artros.forEach(artro -> artro.display(canva, DP));
	}

	public void displayFood(List<Food> foods)
	{
		foods.forEach(food -> food.display(canva, DP));
	}

	private void InitializeJPanel(Dimension frameSize)
	{
		JPanel panel = this;
		panel.setBackground(bgColor);
		panel.setPreferredSize(new Dimension(frameSize.width - GraphsPanel.getPanel().getPreferredSize().width - 14,
				frameSize.height - 40));

		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt)
			{

			}

			public void mouseReleased(MouseEvent evt)
			{

			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt)
			{

			}
		});
		panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt)
			{

			}
		});
		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent evt)
			{
				int key = evt.getKeyCode();
				if (key == KeyEvent.VK_ESCAPE)
				{

				}
			}

			@Override
			public void keyReleased(KeyEvent evt)
			{

			}

			@Override
			public void keyTyped(KeyEvent evt)
			{

			}
		});

	}

	private static boolean frameReady()
	{
		return 1.0 / maxFPS <= (Math.pow(10, -9) * (System.nanoTime() - frameTime));
	}

	public static double getFPS()
	{
		return fps;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		DP.setGraphics((Graphics2D) g);

		canva.display(DP);
		fps = 1.0 / (Math.pow(10, -9) * (System.nanoTime() - frameTime));
		if (frameReady() && Evolution.isRunning())
		{
			frameTime = System.nanoTime();
			Evolution.run();
		}
		if (displayCanva)
		{
			displayFood(Evolution.getFood());
			displayArtros(Evolution.getArtros());
		}
		repaint();
	}
}