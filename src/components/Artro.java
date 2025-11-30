package components;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import graphics.Align;
import graphics.Canva;
import graphics.DrawPrimitives;
import main.Path;
import main.UtilS;
import panels.CanvaPanel;
import utilities.Util;

public class Artro
{
	private int life; // life points, if they reach 0, the artro dies
	private Point pos; // current position of the artro
	private int age; // current age of the artro
	private Species species; // species of the artro, determines its characteristics
	private Map<ArtroChoices, Double> tendency; // tendency or chance (in %) of the artro choosing a certain behavior
	private boolean keepChoice; // should the artro keep its current choice?
	private int satiation; // amount of food in the artro's stomach, if it reaches 0, the artro dies
	private ArtroChoices will; // what the artro wants to do right now
	private int sexWill; // lust of the artro, if it reaches a certain level, the artro will try to mate
	private double speed = 3;
	private double direction; // angle counterclockwise

	private static final List<Artro> all;

	static
	{
		all = new ArrayList<>();
	}

	public Artro(Point pos, Species species, Map<ArtroChoices, Double> tendency, int satiation, int sexWill)
	{
		this.pos = pos;
		this.species = species;
		this.tendency = tendency;
		this.satiation = satiation;
		this.sexWill = sexWill;
		this.age = 0;
		this.life = species.getMaxLife();
		this.will = ArtroChoices.wander;
		this.direction = 360 * Math.random();
		all.add(this);
	}

	public Artro(Point pos, Species species, Map<ArtroChoices, Double> tendency)
	{
		this(pos, species, tendency, species.getStomach(), 0);
	}

	public static List<Artro> getAllOfSpecies(Species species)
	{
		return all.stream().filter(artro -> artro.getSpecies().equals(species)).collect(Collectors.toList());
	}
	
	public void setSexWill(int newValue)
	{
		sexWill = newValue;
	}

	public Directions RandomDirection()
	{
		int randomNumber = (int) (4 * Math.random() - 0.001);

		switch (randomNumber)
		{
			case 0: return Directions.up;
			case 1: return Directions.down;
			case 2: return Directions.right;
			case 3: return Directions.left;
			default: return Directions.up;
		}
	}

	public List<Directions> AcceptedDiretions(Dimension canvaSize)
	{
		List<Directions> acceptedDirections = new ArrayList<>();
		if (pos.x + species.getStep() <= canvaSize.width)
		{
			acceptedDirections.add(Directions.right);
		}
		if (0 <= pos.x - species.getStep())
		{
			acceptedDirections.add(Directions.left);
		}
		if (pos.y + species.getStep() <= canvaSize.height)
		{
			acceptedDirections.add(Directions.up);
		}
		if (0 <= pos.y - species.getStep())
		{
			acceptedDirections.add(Directions.down);
		}

		return acceptedDirections;
	}

	public Food FindClosestVisibleFood(List<Food> allFood)
	{
		if (allFood == null)
		{
			return null;
		}
		if (allFood.isEmpty())
		{
			return null;
		}

		Food closestFood = null;
		double minDist = UtilS.dist(pos, allFood.get(0).getPos());
		for (Food food : allFood)
		{
			double dist = UtilS.dist(pos, food.getPos());
			if (dist <= species.getVision() && dist <= minDist)
			{
				minDist = dist;
				closestFood = food;
			}
		}

		return closestFood;
	}

	public Artro findClosestVisibleMate()
	{
		if (all == null) { return null ;}
		if (all.isEmpty()) { return null ;}

		Artro closestMate = null;
		double minDist = UtilS.dist(pos, all.get(0).getPos());
		for (Artro artro : all)
		{
			double dist = UtilS.dist(pos, artro.getPos());
			if (dist <= species.getVision() && species.equals(artro.species) && dist <= minDist && !this.equals(artro))
			{
				minDist = dist;
				closestMate = artro;
			}
		}

		return closestMate;
	}

	public boolean isReachable(Point targetPos)
	{
		return UtilS.dist(pos, targetPos) <= avrSize();
	}

	public void dies()
	{
		all.remove(this);
	}

	public boolean isHungry()
	{
		return satiation <= (int) (0.6 * species.getStomach());
	}

	public void incHunger()
	{
		satiation += -1;
		if (satiation <= 0)
		{
			dies();
		}
	}

	public void eats(Food food)
	{
		if (food == null) { return ;}

		satiation += food.getType().getValue();
		if (species.getStomach() < satiation)
		{
			satiation = species.getStomach();
		}
	}

	public boolean isAbleToMate()
	{
		return species.getMatePoint() <= sexWill;
	}

	public void incMateWill()
	{
		if (sexWill < species.getMatePoint())
		{
			sexWill += 1;
		}
	}

	private boolean mutation()
	{
		return Util.chance(0.1);
	}

	public void mate(Artro mate)
	{
		Point newPos = new Point((pos.x + mate.getPos().x) / 2, (pos.y + mate.getPos().y) / 2);
		Map<ArtroChoices, Double> newTendency = new HashMap<>();
		for (ArtroChoices choice : ArtroChoices.values())
		{
			double gene = mutation() ? Math.random() : (tendency.get(choice) + mate.getChoice().get(choice)) / 2.0;
			newTendency.put(choice, gene);
		}
		new Artro(newPos, species, newTendency);

		sexWill = 0;
		mate.setSexWill(0);
	}

	private boolean isInsideCanvas(Point newPos)
	{
		return Util.isInside(newPos, new Point(0, 0), CanvaPanel.getCanvaDimension());
	}

	public void move()
	{
		Point newPos = Util.translate(pos, (int) (speed * Math.cos(direction)), (int) (speed * Math.sin(direction)));
		if (isInsideCanvas(newPos))
		{
			pos = newPos;
		}
	}

	public void moveTowards(Point targetPos)
	{
		direction = Util.calcAngle(pos, targetPos);
		move();
	}

	public void wander()
	{
		if (UtilS.chance(0.1))
		{
			direction = 360 * Math.random();
		}
		move();
	}

	private boolean wantsTo(ArtroChoices action)
	{
		return UtilS.chance(tendency.get(action));
	}

	public void thinks()
	{
//		Artro closestOpponent = FindClosestOpponent(this) ;

		/*
		 * if (will.equals("fight") & closestOpponent == -1) { keepChoice = true; } if
		 * (will.equals("flee") & closestOpponent == -1) { keepChoice = true; }
		 */

		/*
		 * if ((keepChoice & UtilS.chance(1 - choice[2] * GroupFightBonus(a))) & -1 <
		 * closestOpponent) { will = "flee"; } else
		 */
		if (!keepChoice & wantsTo(ArtroChoices.eat) & isHungry()) // & closestOpponent == null
		{
			will = ArtroChoices.eat;
			keepChoice = false;
		} else if (!keepChoice & wantsTo(ArtroChoices.mate) & isAbleToMate())
		{
			will = ArtroChoices.mate;
			keepChoice = false;
		}
		/*
		 * else if ((keepChoice & UtilS.chance(choice[2] * GroupFightBonus(a))) & -1 <
		 * closestOpponent) { will = "fight"; } else if ((!keepChoice &
		 * UtilS.chance(choice[4]))) { will = "group"; keepChoice = false; }
		 */
		else if ((!keepChoice & wantsTo(ArtroChoices.wander)))
		{
			will = ArtroChoices.wander;
			keepChoice = false;
		}
//        else
//        {
//            will = ArtroChoices.exist;
//            keepChoice = false;
//        }	
	}

	public void acts(List<Food> allFood)
	{

		switch (will)
		{
		case eat:
			Food food = FindClosestVisibleFood(allFood);
			if (food != null)
			{
				moveTowards(food.getPos());
				if (isReachable(food.getPos()))
				{
					eats(food);
					allFood.remove(food);
				}
			} else
			{
				wander();
				return;
			}

			return;

		case mate:
			Artro closestMate = findClosestVisibleMate();
			if (closestMate != null)
			{
				moveTowards(closestMate.getPos());
				if (isReachable(closestMate.getPos()))
				{
					mate(closestMate);
				}
			} else
			{
				wander();
			}

			return;

		case wander:
			wander();

			return;

//			case exist:
//				
//				return ;

		default:

			return;
		}
	}

	public void display(Canva canva, DrawPrimitives DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension());
		DP.drawImage(species.getImage(), drawingPos, Align.center);
	}

	public static List<Artro> load()
	{
		// read input file
		Object data = UtilS.ReadJson(Path.DADOS + "Artros.json");

		// convert Object to JSONArray
		JSONArray jsonArray = (JSONArray) data;

		// create artros
		List<Artro> artros = new ArrayList<>();
		for (int i = 0; i <= jsonArray.size() - 1; i += 1)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray centerArray = (JSONArray) jsonObject.get("Center");
			JSONArray rangeArray = (JSONArray) jsonObject.get("Range");
			Point center = new Point((int) (long) centerArray.get(0), (int) (long) centerArray.get(1));
			Dimension range = new Dimension((int) (long) rangeArray.get(0), (int) (long) rangeArray.get(1));
			int numberArtros = (int) (long) jsonObject.get("Amount");
			int speciesID = (int) (long) jsonObject.get("SpeciesID");
			int minSatiation = (int) (long) jsonObject.get("MinSatiation");
			JSONArray tendArray = (JSONArray) jsonObject.get("Tendencies");
			Map<ArtroChoices, Double> tendency = new HashMap<>();
			tendency.put(ArtroChoices.eat, (int) (long) tendArray.get(0) / 100.0);
			tendency.put(ArtroChoices.mate, (int) (long) tendArray.get(1) / 100.0);
			tendency.put(ArtroChoices.wander, (int) (long) tendArray.get(2) / 100.0);
			Species species = Species.getAll().get(speciesID);
			int minSexWill = (int) (long) jsonObject.get("MinSexWill");
			for (int j = 0; j <= numberArtros - 1; j += 1)
			{
				Point pos = UtilS.RandomPosAroundPoint(center, range);
				int satiation = (int) (minSatiation + (species.getStomach() - minSatiation) * Math.random());
				int sexWill = (int) (minSexWill + (species.getMatePoint() - minSexWill) * Math.random());

//    			Artro newArtro = new Artro(100, pos, 0, species, tendency, false, satiation, ArtroChoices.exist, sexWill, Directions.getRandom()) ;
				Artro newArtro = new Artro(pos, species, tendency, satiation, sexWill);
				artros.add(newArtro);
			}
		}

		return artros;
	}

	private double avrSize()
	{
		return (species.getSize().getWidth() + species.getSize().getHeight()) / 2;
	}

	public int getLife()
	{
		return life;
	}

	public Point getPos()
	{
		return pos;
	}

	public int getAge()
	{
		return age;
	}

	public Species getSpecies()
	{
		return species;
	}

	public Map<ArtroChoices, Double> getChoice()
	{
		return tendency;
	}

	public boolean getKeepChoice()
	{
		return keepChoice;
	}

	public int getSatiation()
	{
		return satiation;
	}

	public ArtroChoices getWill()
	{
		return will;
	}

	public int getSexWill()
	{
		return sexWill;
	}

	public double getDirection()
	{
		return direction;
	}

	public static List<Artro> getAll()
	{
		return all;
	}

	@Override
	public String toString()
	{
		return "Artro [life=" + life + ", pos=" + pos + ", age=" + age + ", species=" + species + ", tendency="
				+ tendency + ", keepChoice=" + keepChoice + ", satiation=" + satiation + ", will=" + will + ", sexWill="
				+ sexWill + ", direction=" + direction + "]";
	}
}