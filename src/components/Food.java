package components;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import graphics.Canva;
import graphics.DrawPrimitives;
import main.Path;

public class Food
{
	private Point2D.Double pos;
	private FoodType type;

	private static final List<Food> all = new ArrayList<>();

	public Food(Point2D.Double pos, FoodType type)
	{
		this.pos = pos;
		this.type = type;
		all.add(this);
	}

	public Food(FoodDTO dto)
	{
		this(new Point2D.Double(0, 0), FoodType.getAll().get(dto.getTypeID())) ;
		this.pos.x = 0 + dto.getRange()[0] * Math.random() ;
		this.pos.y = 0 + dto.getRange()[1] * Math.random() ;
	}

	public static List<Food> load()
	{
		try
		{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FoodDTO>>() {}.getType();
            FileReader reader = new FileReader(Path.DADOS + "Food.json");
            List<FoodDTO> foodList = gson.fromJson(reader, listType);
			List<Food> food = new ArrayList<>();
			foodList.forEach(dto -> {
				for (int i = 0; i < dto.getAmount(); i++)
				{
					Food newFood = new Food(dto) ;
					food.add(newFood) ;
				}
			}) ;
			return food;
        }
		catch (Exception e)
		{
            e.printStackTrace();
			return null;
        }
	}

	public void display(Canva canva, DrawPrimitives DP)
	{
		Point drawingPos = canva.inDrawingCoords(pos);
		DP.drawCircle(drawingPos, type.getSize(), type.getColor(), type.getColor());
	}

	public Point2D.Double getPos() { return pos ;}
	public FoodType getType() { return type ;}
	public static List<Food> getAll() { return all ;}
}
