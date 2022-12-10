package Components;

import java.awt.Point;

import Graphics.Canva;
import Graphics.DrawingOnAPanel;
import Main.Evolution2;
import Main.UtilS;

public class Food
{
	private Point pos ;
	private FoodType type ;
	
	public Food(Point pos, FoodType type)
	{
		this.pos = pos ;
		this.type = type ;
	}
	
	public Point getPos() {return pos ;}
	public FoodType getType() {return type ;}
	
	public void Display(Canva canva, DrawingOnAPanel DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension());
		DP.DrawCircle(drawingPos, type.getSize(), Evolution2.colorPalette[4], type.getColor());
		/*if (FoodStatus[f])
		{
			//DP.DrawText(Uts.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), String.valueOf(f), "Center", 0, "None", 13, Color.black);
			//DP.DrawCircle(UtilS.ConvertToDrawingCoords(FoodPos[f], CanvasPos, CanvasSize, CanvasDim), (int) (FoodSize[FoodType[f]]), true, ColorPalette[4], color[FoodType[f]]);
		}*/
	}
}
