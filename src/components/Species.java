package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main.Path;
import main.UtilS;
import utilities.Util;

public class Species
{
	private int maxLife; // maximum life of the artro of this species
	private Dimension size; // size of the species (in pixels)
	private int step; // distance the species walks each round (in pixels)
	private int vision; // radius of vision (in pixels)
	private int matePoint; // amount of sex will necessary to make the species want to mate
	private int stomach; // amount of food the species can hold
	private Color color; // color of the species
	private final Image image;

	private static final Image[] images;
	private static final List<Species> all;

	static
	{
		images = new Image[] { Util.loadImage(Path.ASSETS + "Artro1.png"), Util.loadImage(Path.ASSETS + "Artro2.png") };
		all = new ArrayList<>();
	}

	public Species(int maxLife, int step, int vision, int matePoint, int stomach, Color color)
	{
		image = images[all.size()];

		this.maxLife = maxLife;
		this.size = Util.getSize(image);
		this.step = step;
		this.vision = vision;
		this.matePoint = matePoint;
		this.stomach = stomach;
		this.color = color;
	}

	public static void load()
	{
		Object data = UtilS.ReadJson(Path.DADOS + "Species.json");
		JSONArray jsonArray = (JSONArray) data;

		for (int i = 0; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			long maxLife = (long) jsonObject.get("maxLife");
			long step = (long) jsonObject.get("step");
			long vision = (long) jsonObject.get("vision");
			long matePoint = (long) jsonObject.get("matePoint");
			long stomach = (long) jsonObject.get("stomach");
			JSONArray colorArray = (JSONArray) jsonObject.get("color");
			Color color = new Color((int) (long) colorArray.get(0), (int) (long) colorArray.get(1),
					(int) (long) colorArray.get(2));

			all.add(new Species((int) maxLife, (int) step, (int) vision, (int) matePoint, (int) stomach, color));
		}

	}

	public int getMaxLife()
	{
		return maxLife;
	}

	public Dimension getSize()
	{
		return size;
	}

	public int getStep()
	{
		return step;
	}

	public int getVision()
	{
		return vision;
	}

	public int getMatePoint()
	{
		return matePoint;
	}

	public int getStomach()
	{
		return stomach;
	}

	public Color getColor()
	{
		return color;
	}

	public Image getImage()
	{
		return image;
	}

	public static List<Color> getColors()
	{
		return all.stream().map(species -> species.getColor()).collect(Collectors.toList());
	}

	public static List<Species> getAll()
	{
		return all;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(color, matePoint, size, step, stomach, vision);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Species other = (Species) obj;
		return Objects.equals(color, other.color) && matePoint == other.matePoint && size == other.size
				&& step == other.step && stomach == other.stomach && vision == other.vision;
	}
}
