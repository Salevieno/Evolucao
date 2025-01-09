package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main.UtilS;

public class FoodType
{
	public static final Point centerOfCreation;
	public static final Dimension rangeOfCreation;

	private int size; // size of this type of fruit
	private int value; // amount of satiation this type of food restores
	private Color color; // colors of this type of fruit

	private static final List<FoodType> all;

	static
	{
		all = new ArrayList<>();
		centerOfCreation = new Point(1000, 1000);
		rangeOfCreation = new Dimension(500, 500);
	}

	public FoodType(int size, int value, Color color)
	{
		this.size = size;
		this.value = value;
		this.color = color;
	}

	public static void load()
	{
		// read input file
		Object data = UtilS.ReadJson("FoodTypes.json");

		// convert Object to JSONArray
		JSONArray jsonArray = (JSONArray) data;

		for (int i = 0; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			int size = (int) (long) jsonObject.get("Size");
			int value = (int) (long) jsonObject.get("Value");
			JSONArray colorArray = (JSONArray) jsonObject.get("Color");
			Color color = new Color((int) (long) colorArray.get(0), (int) (long) colorArray.get(1),
					(int) (long) colorArray.get(2));

			all.add(new FoodType(size, value, color));
		}
	}

	public int getSize()
	{
		return size;
	}

	public int getValue()
	{
		return value;
	}

	public Color getColor()
	{
		return color;
	}

	public static List<FoodType> getAll()
	{
		return all;
	}

}
