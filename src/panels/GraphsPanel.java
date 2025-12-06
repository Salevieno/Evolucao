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
	private static final List<Dataset> geneData;
	private static final Dataset fpsData;
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
		fpsData = new Dataset();
		charts = new ArrayList<>();

		popChart = new Chart(ChartType.line, new Point(80, 200), Align.center, "Population", 80, Species.getColors(), Species.getColors());
		popChart.addDatasets(popData);
		charts.add(popChart);
		charts.add(new Chart(ChartType.line, new Point(80, 350), Align.center, "Eating gene", 80, Species.getColors(), Species.getColors()));
		charts.get(1).addDatasets(geneData);
		charts.add(new Chart(ChartType.line, new Point(80, 500), Align.center, "fps", 80, Color.orange));
		charts.get(2).addDataset(fpsData);
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
		for (Species specie : Species.getAll())
		{
			popChart.setMaxX(elapsedTime);
			int population = Artro.getAllOfSpecies(specie).size();
			popData.get(Species.getAll().indexOf(specie)).addPointUpToSize((int) elapsedTime, population, 1000);
			popChart.updateMaxY();
		}
		// for (int i = 0; i <= Species.getAll().size() - 1; i += 1)
		// {
		// 	Species species = Species.getAll().get(i);
		// 	popData.get(i).addYDataUpToSize(Artro.getAllOfSpecies(species).size(), 200);
		// 	OptionalDouble geneYData = Artro.getAllOfSpecies(species).stream()
		// 									.mapToDouble(artro -> artro.getChoice().get(ArtroChoices.eat))
		// 									.average() ;
		// 	if (geneYData.isPresent())
		// 	{
		// 		geneData.get(i).addYDataUpToSize(geneYData.getAsDouble(), 200);
		// 	}
		// }
		// fpsData.addYDataUpToSize(Records.fpsList.get(Records.fpsList.size() - 1), 100);

		panel.repaint();
	}

}
