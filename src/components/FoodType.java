package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.Path;

public class FoodType
{
	public static final Point2D.Double centerOfCreation;
	public static final Dimension rangeOfCreation;
	public static final int spawnTime; // number of rounds taken for the food to respawn
	public static final int maxQtd; // maximum amount of food that can exist at a time

	private int size; // size of this type of fruit
	private int value; // amount of satiation this type of food restores
	private Color color; // colors of this type of fruit

	private static final List<FoodType> all;

	static
	{
		all = new ArrayList<>();
		centerOfCreation = new Point2D.Double(1000, 1000);
		rangeOfCreation = new Dimension(500, 500);
		spawnTime = 2;
		maxQtd = 200;
	}

	public FoodType(int size, int value, Color color)
	{
		this.size = size;
		this.value = value;
		this.color = color;
		all.add(this) ;
	}

	public FoodType(FoodTypeDTO dto)
	{
		this(dto.getSize(), dto.getValue(), dto.getColor()) ;
	}

	public static void load()
	{
		try
		{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FoodTypeDTO>>() {}.getType();
            FileReader reader = new FileReader(Path.DADOS + "FoodTypes.json");
            List<FoodTypeDTO> foodTypesList = gson.fromJson(reader, listType);
			foodTypesList.forEach(dto -> new FoodType(dto)) ;
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
	}

	public int getSize() { return size ;}
	public int getValue() { return value ;}
	public Color getColor() { return color ;}
	public static List<FoodType> getAll() { return all ;}
}
