package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.Path;
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

	public Species(SpeciesDTO dto)
	{
		this(dto.getMaxLife(), dto.getStep(), dto.getVision(), dto.getMatePoint(), dto.getStomach(), dto.getColor());
	}

	public static void load()
	{
		try
		{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<SpeciesDTO>>() {}.getType();
            FileReader reader = new FileReader(Path.DADOS + "Species.json");
            List<SpeciesDTO> speciesList = gson.fromJson(reader, listType);
			speciesList.forEach(dto -> all.add(new Species(dto))) ;
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
	}

	public int getMaxLife() { return maxLife ;}
	public Dimension getSize() { return size ;}
	public int getStep() { return step ;}
	public int getVision() { return vision ;}
	public int getMatePoint() { return matePoint ;}
	public int getStomach() { return stomach ;}
	public Color getColor() { return color ;}
	public Image getImage() { return image ;}
	public static List<Species> getAll() { return all ;}
	public static List<Color> getColors() { return all.stream().map(Species::getColor).toList() ;}

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
