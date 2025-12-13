package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import charts.Chart;
import charts.ChartType;
import charts.Dataset;
import components.Artro;
import components.ArtroChoices;
import components.Species;
import graphics.Align;
import graphics.DrawPrimitives;
import main.MainEvolution;

public abstract class GraphsPanel
{
	private static final JPanel panel;
	private static final List<Chart> charts;
	private static final Chart popChart;
	private static final List<Dataset> popData;
	private static final Chart geneChart;
	private static final List<Dataset> geneData;
	private static final DrawPrimitives DP;
	
	private static double elapsedTime;
	private static boolean graphsAreVisible;
	private static final Color bgColor;

	static
	{
		elapsedTime = 0;
		graphsAreVisible = true;
		bgColor = new Color(50, 50, 50);
		DP = new DrawPrimitives();

		panel = new JPanel(){
			private static final long serialVersionUID = 1L;

			public void displayGraphs()
			{
				// charts.forEach(Chart::updateMaxYEver);
				charts.forEach(plot -> plot.display(DP));
			}

			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				DP.setGraphics((Graphics2D) g);
				if (graphsAreVisible)
				{
					displayGraphs();
				}
			}
		};
		panel.setPreferredSize(new Dimension(150, MainEvolution.screenSize.height - 40));
		panel.setBackground(bgColor);
		popData = new ArrayList<>();
		geneData = new ArrayList<>();
		Species.getAll().forEach(species -> popData.add(new Dataset()));
		Species.getAll().forEach(species -> geneData.add(new Dataset()));
		charts = new ArrayList<>();

		popChart = new Chart(ChartType.line, new Point(80, 200), Align.center, "Population", 80, Species.getColors(), Species.getColors());
		popChart.addDatasets(popData);
		charts.add(popChart);
		geneChart = new Chart(ChartType.line, new Point(80, 350), Align.center, "Eating gene", 80, Species.getColors(), Species.getColors());
		geneChart.addDatasets(geneData);
		geneChart.setMaxY(100) ;
		charts.add(geneChart);
	}

	public static JPanel getPanel()
	{
		return panel;
	}

	public static void switchGraphsAreVisible()
	{
		graphsAreVisible = !graphsAreVisible;
	}

	public static void updateRecords(List<Artro> artros, double dt)
	{
		elapsedTime += dt;
		for (Species species : Species.getAll())
		{
			// popChart.setMaxX(elapsedTime);
			int population = Artro.getAllOfSpecies(species).size();
			popData.get(Species.getAll().indexOf(species)).addPointUpToSize((int) elapsedTime, population, 1000);
			popChart.updateMaxY();
			if (1000 <= popData.get(0).size())
			{
				popChart.setMinX(popData.get(0).getX().get(0));
			}
			popChart.setMaxX(popData.get(0).getX().get(popData.get(0).size() - 1));
		}

		
		for (Species species : Species.getAll())
		{
			double averageEatGene = Artro.getAverageGene(species, ArtroChoices.eat) ;
			geneData.get(Species.getAll().indexOf(species)).addPointUpToSize((int) elapsedTime, (int) (100 * averageEatGene), 1000);
			if (1000 <= geneData.get(Species.getAll().indexOf(species)).size())
			{
				geneChart.setMinX(geneData.get(0).getX().get(0));
			}
			geneChart.setMaxX(geneData.get(0).getX().get(geneData.get(0).size() - 1));
		}

		panel.repaint();
	}

}
