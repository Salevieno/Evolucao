package components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import graphics.Align;
import graphics.Canva;
import graphics.DrawPrimitives;
import main.CustomTimer;
import main.Path;
import panels.CanvaPanel;
import utilities.Util;

public class Artro
{
	private int life; // life points, if they reach 0, the artro dies
	private Point2D.Double pos; // current position of the artro
	private double age; // current age of the artro
	private CustomTimer ageTimer; // timer to track the age of the artro
	private Species species; // species of the artro, determines its characteristics
	private Map<ArtroChoices, Double> tendency; // tendency or chance (in %) of the artro choosing a certain behavior
	private boolean keepChoice; // should the artro keep its current choice?
	private double satiation; // amount of food in the artro's stomach, if it reaches 0, the artro dies
	private ArtroChoices will; // what the artro wants to do right now
	private int sexWill; // lust of the artro, if it reaches a certain level, the artro will try to mate
	private double speed = 300;
	private double direction; // angle counterclockwise

	private static final List<Artro> all;

	static
	{
		all = new ArrayList<>();
	}

	public Artro(Point2D.Double pos, Species species, Map<ArtroChoices, Double> tendency, int satiation, int sexWill)
	{
		this.pos = pos;
		this.species = species;
		this.tendency = tendency;
		this.satiation = satiation;
		this.sexWill = sexWill;
		this.age = 0;
		this.ageTimer = new CustomTimer(1.0);
		this.ageTimer.start();
		this.life = species.getMaxLife();
		this.will = ArtroChoices.wander;
		this.direction = 360 * Math.random();
		all.add(this);
	}

	public Artro(ArtroDTO dto)
	{
		this(new Point2D.Double(0, 0), Species.getAll().get(dto.getSpeciesID()), Map.of(
			ArtroChoices.eat, dto.getTendencies()[0] / 100.0,
			ArtroChoices.mate, dto.getTendencies()[1] / 100.0,
			ArtroChoices.wander, dto.getTendencies()[2] / 100.0
		), dto.getMinSatiation(), dto.getMinSexWill());
		
		this.pos.x = dto.getCenter()[0] + dto.getRange()[0] * (Math.random() - Math.random()) ;
		this.pos.y = dto.getCenter()[1] + dto.getRange()[1] * (Math.random() - Math.random()) ;
	}

	public Artro(Point2D.Double pos, Species species, Map<ArtroChoices, Double> tendency)
	{
		this(pos, species, tendency, species.getStomach(), 0);
	}

	public static List<Artro> getAllOfSpecies(Species species)
	{
		return all.stream().filter(artro -> artro.getSpecies().equals(species)).toList();
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
		if (allFood == null) { return null ;}
		if (allFood.isEmpty()) { return null ;}

		Food closestFood = null;
		double minDist = pos.distance(allFood.get(0).getPos());
		for (Food food : allFood)
		{
			double dist = pos.distance(food.getPos());
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
		double minDist = pos.distance(all.get(0).getPos());
		for (Artro artro : all)
		{
			double dist = pos.distance(artro.getPos());
			if (dist <= species.getVision() && species.equals(artro.species) && dist <= minDist && !this.equals(artro))
			{
				minDist = dist;
				closestMate = artro;
			}
		}

		return closestMate;
	}

	public boolean isReachable(Point2D.Double targetPos)
	{
		return pos.distance(targetPos) <= avrSize();
	}

	public void dies()
	{
		all.remove(this);
	}

	public boolean isHungry()
	{
		return satiation <= (int) (0.6 * species.getStomach());
	}

	public void incHunger(double dt)
	{
		satiation += -dt;
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
		Point2D.Double newPos = new Point2D.Double((pos.x + mate.getPos().x) / 2, (pos.y + mate.getPos().y) / 2);
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

	private boolean isInsideCanvas(Point2D.Double newPos)
	{
		return Util.isInside(new Point((int) newPos.x, (int) newPos.y), new Point(0, 0), CanvaPanel.getCanvaDimension());
	}

	public void move(double dt)
	{
		Point2D.Double newPos = new Point2D.Double(pos.x + Math.cos(direction) * speed * dt, pos.y + Math.sin(direction) * speed * dt);
		if (isInsideCanvas(newPos))
		{
			pos = newPos;
		}
	}

	public void age(double dt)
	{
		age += dt;
		if (40 <= age)
		{
			dies();
		}
	}

	public void moveTowards(Point2D.Double targetPos, double dt)
	{
		direction = Math.atan2(targetPos.y - pos.y, targetPos.x - pos.x);
		move(dt);
	}

	public void wander(double dt)
	{
		if (Math.random() <= 0.1)
		{
			direction = 360 * Math.random();
		}
		move(dt);
	}

	private boolean wantsTo(ArtroChoices action)
	{
		return Math.random() <= tendency.get(action);
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

	public static void updateAll(List<Food> food, double dt)
	{
		all.forEach(artro ->
			{
				artro.thinks();
				artro.acts(food, dt);
				artro.age(dt);
				artro.incHunger(dt);
				artro.incMateWill();
			}
		);
	}

	public void acts(List<Food> allFood, double dt)
	{

		switch (will)
		{
		case eat:
			Food food = FindClosestVisibleFood(allFood);
			if (food != null)
			{
				moveTowards(food.getPos(), dt);
				if (isReachable(food.getPos()))
				{
					eats(food);
					allFood.remove(food);
				}
			} else
			{
				wander(dt);
				return;
			}

			return;

		case mate:
			Artro closestMate = findClosestVisibleMate();
			if (closestMate != null)
			{
				moveTowards(closestMate.getPos(), dt);
				if (isReachable(closestMate.getPos()))
				{
					mate(closestMate);
				}
			} else
			{
				wander(dt);
			}

			return;

		case wander:
			wander(dt);

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
		Point drawingPos = canva.inDrawingCoords(pos);
		DP.drawImage(species.getImage(), drawingPos, Align.center);
	}

	public static List<Artro> load()
	{
		try
		{
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ArtroDTO>>() {}.getType();
            FileReader reader = new FileReader(Path.DADOS + "Artros.json");
            List<ArtroDTO> artrosList = gson.fromJson(reader, listType);
			List<Artro> artros = new ArrayList<>();
			artrosList.forEach(dto -> {
				for (int i = 0; i < dto.getAmount(); i++)
				{
					Artro newArtro = new Artro(dto) ;
					artros.add(newArtro) ;
				}
			}) ;
			return artros;
        }
		catch (Exception e)
		{
            e.printStackTrace();
			return null;
        }
	}

	private double avrSize() { return (species.getSize().getWidth() + species.getSize().getHeight()) / 2 ;}
	public int getLife() { return life ;}
	public Point2D.Double getPos() { return pos ;}
	public double getAge() { return age ;}
	public Species getSpecies() { return species ;}
	public Map<ArtroChoices, Double> getChoice() { return tendency ;}
	public boolean getKeepChoice() { return keepChoice ;}
	public double getSatiation() { return satiation ;}
	public ArtroChoices getWill() { return will ;}
	public int getSexWill() { return sexWill ;}	
	public void setSexWill(int newValue) { sexWill = newValue ;}
	public double getDirection() { return direction ;}
	public static List<Artro> getAll() { return all ;}

	@Override
	public String toString()
	{
		// return "Artro [life=" + life + ", pos=" + pos + ", age=" + age + ", species=" + species + ", tendency="
		// 		+ tendency + ", keepChoice=" + keepChoice + ", satiation=" + satiation + ", will=" + will + ", sexWill="
		// 		+ sexWill + ", direction=" + direction + "]";

		return "Artro: life=" + life + ", age=" + age + ", species=" + Species.getAll().indexOf(species) + ", tendency="
				+ tendency + ", satiation=" + satiation + ", will=" + will + ", sexWill="
				+ sexWill;
	}
}