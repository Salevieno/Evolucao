package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Visuals
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int[] ScreenSize = new int[] {(int) screenSize.getWidth(), (int) screenSize.getHeight()};
	int[] CanvasTitlePos;
    int[] CanvasPos, CanvasSize, CanvasDim;
    int[] DrawingPos;
    double StructureScale;
    double[] DiagramsScales;
    int[] DiagramsNPoints;

    Color[] ColorPalette;
	Draw DP;
        
	/*public Visuals(DrawingOnAPanel DP, int[] CanvasPos, int[] CanvasSize, int[] CanvasDim, int[] DrawingPos)
	{
		ColorPalette = UtilS.ColorPalette(2);
		this.DP = DP;
	    this.CanvasPos = CanvasPos;
	    this.CanvasSize = CanvasSize;
	    this.CanvasDim = CanvasDim;
	    this.DrawingPos = DrawingPos;
	}
	
	public void DrawWindow(int[] Pos, int L, int H, Color colortop, Color ContourColor)
	{
		Color colorbot = new Color (Math.min(colortop.getRed() + 100, 255), Math.min(colortop.getGreen() + 100, 255), colortop.getBlue());
		Color[] ColorGradient = new Color[] {colortop, colorbot};
		DP.DrawRoundRect(Pos, "BotLeft", L, H, 3, 5, 5, ColorGradient, ContourColor, true);
	}
	
	public void DrawMousePos(double[] MousePos)
	{
		int[] Pos = new int[] {680, 620};
		int[] RectSize = new int[] {50, 20};
		int FontSize = 13;
		DP.DrawText(new int[] {Pos[0], Pos[1]}, "Mouse Pos:", "Center", 0, "Plain", FontSize, ColorPalette[4]);
		//DP.DrawRect(new int[] {Pos[0] + 65, Pos[1]}, RectSize[0], RectSize[1], "Center", 0, 2, false, ColorPalette[4], null);
		DP.DrawText(new int[] {Pos[0] + 65, Pos[1]}, String.valueOf(UtilS.Round(MousePos[0], 1)), "Center", 0, "Plain", FontSize, ColorPalette[4]);
		//DP.DrawRect(new int[] {Pos[0] + 125, Pos[1]}, RectSize[0], RectSize[1], "Center", 0, 2, false, ColorPalette[4], null);
		DP.DrawText(new int[] {Pos[0] + 125, Pos[1]}, String.valueOf(UtilS.Round(MousePos[1], 1)), "Center", 0, "Plain", FontSize, ColorPalette[4]);
	}
	
	public void DrawList(int SelectedMember, String[] PropName, String Title, String Label, int[][] Input, String ArrayType)
	{
		int[] Pos = new int[] {10, 275};
		int L = 420, H = 150;
		int sx = 60, sy = 20;
		int TitleSize = 20;
		int FontSize = 13;
		int MaxTextLength = 0;
		Color TitleColor = ColorPalette[5], TextColor = ColorPalette[5];
		for (int i = 0; i <= PropName.length - 1; i += 1)
		{
			MaxTextLength = (int) Math.max(MaxTextLength, FontSize*PropName[i].length()/2.3);
		}
		sx = Math.max(sx, MaxTextLength + 10);
		L = Math.max(L, PropName.length*sx);
		H = (Input.length + 1)*sy + 10;
		DP.DrawText(new int[] {Pos[0] + L/2, Pos[1] - TitleSize}, Title, "Center", 0, "Bold", TitleSize, TitleColor);
		DrawWindow(Pos, L, H, ColorPalette[0], ColorPalette[5]);
		for (int prop = 0; prop <= PropName.length - 1; prop += 1)
		{
			DP.DrawText(new int[] {(int) (Pos[0] + prop*sx + sx/2), Pos[1] + sy/2}, PropName[prop], "Center", 0, "Bold", FontSize, TextColor);
		}
		for (int i = 0; i <= Input.length - 1; i += 1)
		{
			TextColor = ColorPalette[5];
			if (SelectedMember == i)
			{
				TextColor = ColorPalette[6];
			}
			DP.DrawText(new int[] {Pos[0] + sx/2, Pos[1] + (i + 1)*sy + sy/2}, Label + " " + String.valueOf(i), "Center", 0, "Bold", FontSize, TextColor);
			for (int prop = 0; prop <= Input[0].length - 1; prop += 1)
			{
				DP.DrawText(new int[] {(int) (Pos[0] + (prop + 1)*sx + sx/2), Pos[1] + (i + 1)*sy + sy/2}, String.valueOf(UtilS.Round(Input[i][prop], 1)), "Center", 0, "Bold", FontSize, TextColor);			
			}
		}
	}

	public void DrawList(int SelectedSec, String[] PropName, String Title, String Label, double[][] Input, String ArrayType)
	{
		int[] Pos = new int[] {10, 275};
		int L = 420, H = 150;
		int sx = 60, sy = 20;
		int TitleSize = 20;
		int FontSize = 13;
		int MaxTextLength = 0;
		Color TitleColor = ColorPalette[5], TextColor = ColorPalette[5];
		for (int i = 0; i <= PropName.length - 1; i += 1)
		{
			MaxTextLength = (int) Math.max(MaxTextLength, FontSize*PropName[i].length()/2.3);
		}
		sx = Math.max(sx, MaxTextLength + 10);
		L = Math.max(L, PropName.length*sx);
		H = (Input.length + 1)*sy + 10;
		DP.DrawText(new int[] {Pos[0] + L/2, Pos[1] - TitleSize}, Title, "Center", 0, "Bold", TitleSize, TitleColor);
		DrawWindow(Pos, L, H, ColorPalette[0], ColorPalette[5]);
		for (int prop = 0; prop <= PropName.length - 1; prop += 1)
		{
			DP.DrawText(new int[] {(int) (Pos[0] + prop*sx + sx/2), Pos[1] + sy/2}, PropName[prop], "Center", 0, "Bold", FontSize, TextColor);
		}
		for (int i = 0; i <= Input.length - 1; i += 1)
		{
			TextColor = ColorPalette[5];
			if (SelectedSec == i)
			{
				TextColor = ColorPalette[6];
			}
			DP.DrawText(new int[] {Pos[0] + sx/2, Pos[1] + (i + 1)*sy + sy/2}, Label + " " + String.valueOf(i), "Center", 0, "Bold", FontSize, TextColor);
			for (int prop = 0; prop <= Input[0].length - 1; prop += 1)
			{
				DP.DrawText(new int[] {(int) (Pos[0] + (prop + 1)*sx + sx/2), Pos[1] + (i + 1)*sy + sy/2}, String.valueOf(UtilS.Round(Input[i][prop], 1)), "Center", 0, "Bold", FontSize, TextColor);			
			}
		}
	}
	
	public void DrawLegend(String ColorSystem, String Title, double MinValue, double MaxValue)
	{
		int[] Pos = new int[] {10, 450};
		int L = 300, H = 150;
		double sx, sy;
		int BarLength;
		int TitleSize = 20;
		int FontSize = 13;
		int NumCat = 10, NumLines, NumColumns;
		NumLines = 2;
		NumColumns = (1 + NumCat)/NumLines;
		BarLength = (L/NumColumns)/2;
		sx = BarLength;
		sy = (H/(NumLines + 1));
		DP.DrawText(new int[] {Pos[0] + L/2, (int) (Pos[1] - 1.2*FontSize)}, Title, "Center", 0, "Bold", TitleSize, ColorPalette[7]);
		DrawWindow(Pos, L, H, ColorPalette[8], ColorPalette[5]);
		for (int i = 0; i <= NumCat - 1; i += 1)
		{
			double value = (MaxValue - MinValue)*i/(NumCat - 1) + MinValue;
			Color color = UtilS.FindColor(value, MinValue, MaxValue, ColorSystem);
			int[] InitPos = new int[] {(int) (Pos[0] + 2*(i % NumColumns)*sx + sx/2), (int) (Pos[1] + (i / NumColumns)*sy + sy/2)};
			DP.DrawLine(InitPos, new int[] {InitPos[0] + BarLength, InitPos[1]}, 2, color);
			DP.DrawText(new int[] {InitPos[0] + BarLength/2, InitPos[1] + FontSize/2 + 5}, String.valueOf(UtilS.Round(value, 2)), "Center", 0, "Plain", FontSize, color);
		}
	}
	
	public void DrawCanvas()
	{
		//DP.DrawRect(CanvasPos, CanvasSize[0], CanvasSize[1], "BotLeft", 0, 2, false, ColorPalette[4], ColorPalette[5]);
	}

	public void DrawGrid(int[] CanvasDim, int PointSize)
	{
		int[] NPointsMax = new int[] {50, 50};
		int[] GridFactor = new int[] {1 + (CanvasDim[0]/10)/NPointsMax[0], 1 + (CanvasDim[1]/10)/NPointsMax[1]};
		int[] NPoints = new int[] {(int) (CanvasDim[0]/(10*GridFactor[0])), (int) (CanvasDim[1]/(10*GridFactor[1]))};
		double[] PointsDist = new double[] {CanvasSize[0]/(double)(NPoints[0]), CanvasSize[1]/(double)(NPoints[1])};
		NPoints = UtilS.CalculateNumberOfGridPoints(CanvasDim, CanvasDim, CanvasDim);
		for (int i = 0; i <= NPoints[0]; i += 1)
		{	
			for (int j = 0; j <= NPoints[1]; j += 1)
			{	
				Point Pos = new Point((int) (CanvasPos[0] + i*PointsDist[0]), (int) (CanvasPos[1] + j*PointsDist[1])) ;
				DP.DrawCircle(Pos, PointSize, ColorPalette[4], ColorPalette[4]);
			}
		}
	}

	public void DrawArtros(double[][] ArtroPos, String[] ArtroWill, int[] ArtroSpecies, int[] ArtroLife, int[] ArtroSize, Color[] color)
	{
		if (ArtroPos != null)
		{
			for (int a = 0; a <= ArtroPos.length - 1; a += 1)
			{
				if (0 < ArtroLife[a])
				{
					int[] DrawingPos = UtilS.ConvertToDrawingCoords(ArtroPos[a], CanvasPos, CanvasSize, CanvasDim);
					if (ArtroWill[a].equals("fight"))
					{
						DP.DrawCircle(DrawingPos, ArtroSize[ArtroSpecies[a]], ColorPalette[4], ColorPalette[6]);
					}
					else
					{
						DP.DrawCircle(DrawingPos, ArtroSize[ArtroSpecies[a]], ColorPalette[4], color[ArtroSpecies[a]]);
					}
					//DP.DrawText(DrawingPos, ArtroWill[a], "Center", 0, "Bold", 10, ColorPalette[5]);
				}
			}
			//int[] DrawingPos = Uts.ConvertToDrawingCoords(ArtroPos[0], CanvasPos, CanvasSize, CanvasDim);
			//DP.DrawCircle(DrawingPos, (int) (1.3 * ArtroSize[ArtroSpecies[0]]), true, ColorPalette[4], Color.yellow);
		}
	}
	
	public void DrawFood(double[][] FoodPos, int[] FoodType, boolean[] FoodStatus, double[] FoodSize, Color[] color)
	{
		if (FoodPos != null)
		{
			for (int f = 0; f <= FoodPos.length - 1; f += 1)
			{
				if (FoodStatus[f])
				{
					//DP.DrawText(Uts.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), String.valueOf(f), "Center", 0, "None", 13, Color.black);
					//DP.DrawCircle(UtilS.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), (int) (FoodSize[FoodType[f]]), true, ColorPalette[4], color[FoodType[f]]);
				}
			}
		}
	}

	public void DrawVarGraph(int[] Pos, String Title, double[][][] Var, int MaxEver, Color[] color)
	{
		int size = 100;
		int NumPoints;
		if (Var != null)
		{
			NumPoints = Var.length;
		}
		else
		{
			NumPoints = 0;
		}
		DP.DrawGraph(Pos, Title, size, ColorPalette[14]);
		if (Var != null)
		{
			if (2 <= Var.length)
			{
				DP.DrawText(new int[] {(int) (Pos[0] - 0.22*size), (int) (Pos[1] - 1*size)}, String.valueOf(MaxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
				for (int i3 = 0; i3 <= Var[0].length - 1; i3 += 1)
				{
					for (int i2 = 0; i2 <= Var[0].length - 1; i2 += 1)
					{
						int[] x = new int[NumPoints], y = new int[NumPoints];
						for (int i1 = 0; i1 <= Var.length - 1; i1 += 1)
						{
							x[i1] = Pos[0] + size*i1/(Var.length - 1);
							y[i1] = Pos[1] - (int) (size*Var[i1][i2][i3]/MaxEver);
						}
						DP.DrawPolyLine(x, y, 2, color[i2]);
					}
				}
			}	
		}
	}

	public void DrawVarGraph(int[] Pos, String Title, int[][] Var, int MaxEver, Color[] color)
	{
		int size = 100;
		int NumPoints;
		if (Var != null)
		{
			NumPoints = Var.length;
		}
		else
		{
			NumPoints = 0;
		}
		DP.DrawGraph(Pos, Title, size, ColorPalette[14]);
		if (Var != null)
		{
			if (2 <= Var.length)
			{
				DP.DrawText(new int[] {(int) (Pos[0] - 0.22*size), (int) (Pos[1] - 1*size)}, String.valueOf(MaxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
				for (int j = 0; j <= Var[0].length - 1; j += 1)
				{
					int[] x = new int[NumPoints], y = new int[NumPoints];
					for (int i = 0; i <= Var.length - 1; i += 1)
					{
						x[i] = Pos[0] + size*i/(Var.length - 1);
						y[i] = Pos[1] - (int) (size*Var[i][j]/MaxEver);
					}
					DP.DrawPolyLine(x, y, 2, color[j]);
				}
			}	
		}
	}

	public void DrawVarGraph(int[] Pos, String Title, double[][] Var, int MaxEver, Color[] color)
	{
		int size = 100;
		int NumPoints;
		if (Var != null)
		{
			NumPoints = Var.length;
		}
		else
		{
			NumPoints = 0;
		}
		DP.DrawGraph(Pos, Title, size, ColorPalette[14]);
		if (Var != null)
		{
			if (2 <= Var.length)
			{
				DP.DrawText(new int[] {(int) (Pos[0] - 0.22*size), (int) (Pos[1] - 1*size)}, String.valueOf(MaxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
				for (int j = 0; j <= Var[0].length - 1; j += 1)
				{
					int[] x = new int[NumPoints], y = new int[NumPoints];
					for (int i = 0; i <= Var.length - 1; i += 1)
					{
						x[i] = Pos[0] + size*i/(Var.length - 1);
						y[i] = Pos[1] - (int) (size*Var[i][j]/MaxEver);
					}
					DP.DrawPolyLine(x, y, 2, color[j]);
				}
			}	
		}
	}

	public void DrawVarGraph2(int[] Pos, String Title, double[][] Var, int MaxEver, Color[] color)
	{
		int size = 100;
		int NumPoints;
		if (Var != null)
		{
			NumPoints = Var.length;
		}
		else
		{
			NumPoints = 0;
		}
		DP.DrawGraph(Pos, Title, size, ColorPalette[14]);
		if (Var != null)
		{
			if (2 <= Var.length)
			{
				DP.DrawText(new int[] {(int) (Pos[0] - 0.22*size), (int) (Pos[1] - 1*size)}, String.valueOf(MaxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
				for (int j = 0; j <= Var[0].length - 1; j += 1)
				{
					int[] x = new int[NumPoints], y = new int[NumPoints];
					for (int i = 0; i <= Var.length - 1; i += 1)
					{
						x[i] = Pos[0] + size*i/(Var.length - 1);
						y[i] = Pos[1] - (int) (size*Var[i][j]/MaxEver);
					}
					DP.DrawPolyLine(x, y, 2, color[j]);
				}
			}	
		}
	}

	public void DrawVarGraph(int[] Pos, String Title, double[] Var, int MaxEver, Color color)
	{
		int size = 100;
		int NumPoints;
		if (Var != null)
		{
			NumPoints = Var.length;
		}
		else
		{
			NumPoints = 0;
		}
		DP.DrawGraph(Pos, Title, size, ColorPalette[14]);
		if (Var != null)
		{
			if (2 <= Var.length)
			{
				DP.DrawText(new int[] {(int) (Pos[0] - 0.22*size), (int) (Pos[1] - 1*size)}, String.valueOf(MaxEver), "Center", 0, "Bold", 12, ColorPalette[9]);
				int[] x = new int[NumPoints], y = new int[NumPoints];
				for (int i = 0; i <= Var.length - 1; i += 1)
				{
					x[i] = Pos[0] + size*i/(Var.length - 1);
					y[i] = Pos[1] - (int) (size*Var[i]/MaxEver);
				}
				DP.DrawPolyLine(x, y, 2, color);
			}	
		}
	}
	*/
}