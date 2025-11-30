package components;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import graphics.Canva;
import graphics.DrawPrimitives;
import main.Path;
import main.UtilS;

public class Food
{
	private Point pos;
	private FoodType type;

	public Food(Point pos, FoodType type)
	{
		this.pos = pos;
		this.type = type;
	}

	public static List<Food> load()
	{
		Object data = UtilS.ReadJson(Path.DADOS + "Food.json");
		JSONArray jsonArray = (JSONArray) data;

		List<Food> food = new ArrayList<>();

		for (int i = 0; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray centerArray = (JSONArray) jsonObject.get("Center");
			JSONArray rangeArray = (JSONArray) jsonObject.get("Range");
			Point center = new Point((int) (long) centerArray.get(0), (int) (long) centerArray.get(1));
			Dimension range = new Dimension((int) (long) rangeArray.get(0), (int) (long) rangeArray.get(1));
			int numberFood = (int) (long) jsonObject.get("Amount");
			for (int j = 0; j <= numberFood - 1; j += 1)
			{
				Point pos = UtilS.RandomPosAroundPoint(center, range);
				int typeID = (int) (long) jsonObject.get("TypeID");
				FoodType type = FoodType.getAll().get(typeID);
				food.add(new Food(pos, type));
			}
		}

		return food;
	}

	public void display(Canva canva, DrawPrimitives DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension());
		DP.drawCircle(drawingPos, type.getSize(), type.getColor(), type.getColor());
	}

	public Point getPos()
	{
		return pos;
	}

	public FoodType getType()
	{
		return type;
	}
}
