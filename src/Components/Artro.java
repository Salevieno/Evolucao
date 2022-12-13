package Components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import Graphics.Canva;
import Graphics.DrawingOnAPanel;
import Main.Evolution;
import Main.UtilS;

public class Artro
{
	private int life ;	// life points, if they reach 0, the artro dies
	private Point pos ;	// current position of the artro
	private int age ;	// current age of the artro
	private Species species ;		// species of the artro, determines its characteristics
	private Map<ArtroChoices, Double> tendency ;	// tendency or chance (in %) of the artro choosing a certain behavior
	private boolean keepChoice ;	// should the artro keep its current choice?
	private int satiation ;			// amount of food in the artro's stomach, if it reaches 0, the artro dies
	private ArtroChoices will ;		// what the artro wants to do right now
	private int sexWill ;			// lust of the artro, if it reaches a certain level, the artro will try to mate
	private Directions direction ;	// current direction the artro is moving in
	
	public Artro(int life,
			 Point pos,
			 int age,
			 Species species,
			 Map<ArtroChoices, Double> tendency,
			 boolean keepChoice,
			 int satiation,
			 ArtroChoices will,
			 int sexWill,
			 Directions direction
			)
	{
		this.life = life ;
		this.pos = pos ;
		this.age = age ;
		this.species = species ;
		this.tendency = tendency ;
		this.keepChoice = keepChoice ;
		this.satiation = satiation ;
		this.will = will ;
		this.sexWill = sexWill ;
		this.direction = direction ;
	}
	
	public int getLife() {return life ;}
	public Point getPos() {return pos ;}
	public int getAge() {return age ;}
	public Species getSpecies() {return species ;}
	public Map<ArtroChoices, Double> getChoice() {return tendency ;}
	public boolean getKeepChoice() {return keepChoice ;}
	public int getSatiation() {return satiation ;}
	public ArtroChoices getWill() {return will ;}
	public int getSexWill() {return sexWill ;}
	public Directions getDirection() {return direction ;}
	
	public void setSexWill(int newValue) {sexWill = newValue ;}
	
	public Directions RandomDirection()
	{
		int randomNumber = (int) (4 * Math.random() - 0.001) ;
		
		switch(randomNumber)
		{
		  case 0:
				return Directions.up ;
		  case 1:
				return Directions.down ;
		  case 2:
				return Directions.right ;
		  case 3:
				return Directions.left ;
		  default:
			  	return Directions.up ;
		}		
	}
	
	public ArrayList<Directions> AcceptedDiretions(Canva canva)
	{
		ArrayList<Directions> acceptedDirections = new ArrayList<>() ;
		if (pos.x + species.getStep() <= canva.getDimension().width)
		{
			acceptedDirections.add(Directions.right) ;
		}
		if (0 <= pos.x - species.getStep())
		{
			acceptedDirections.add(Directions.left) ;
		}
		if (pos.y + species.getStep() <= canva.getDimension().height)
		{
			acceptedDirections.add(Directions.up) ;
		}
		if (0 <= pos.y - species.getStep())
		{
			acceptedDirections.add(Directions.down) ;
		}
		
		return acceptedDirections ;
	}
	
	public Food FindFoodInRange(ArrayList<Food> allFood, int range)
	{
		if (0 < allFood.size())
		{
			for (Food food : allFood)
			{
				if (UtilS.dist(pos, food.getPos()) <= range)
				{
					return food ;
				}
			}
		}
		
		return null ;
	}
	
	public Food FindClosestVisibleFood(ArrayList<Food> allFood)
	{
		Food closestFood = null ;
		if (0 < allFood.size())
		{
			double minDist = UtilS.dist(pos, allFood.get(0).getPos()) ;
			for (Food food : allFood)
			{
				double dist = UtilS.dist(pos, food.getPos()) ;
				if (dist <= minDist & dist <= species.getVision())
				{
					minDist = dist ;
					closestFood = food ;
				}
			}
		}
		
		return closestFood ;
	}
	
	public Artro FindClosestVisibleMate(ArrayList<Artro> allArtros)
	{
		Artro closestMate = null ;
		if (0 < allArtros.size())
		{
			double minDist = UtilS.dist(pos, allArtros.get(0).getPos()) ;
			for (Artro artro : allArtros)
			{
				double dist = UtilS.dist(pos, artro.getPos()) ;
				if (dist <= minDist & !this.equals(artro) & species.equals(artro.species) & dist <= species.getVision())
				{
					minDist = dist ;
					closestMate = artro ;
				}
			}
		}
		
		return closestMate ;
	}
	
	public boolean IsReachable(Point targetPos)
	{
		if (UtilS.dist(pos, targetPos) <= species.getSize())
		{
			return true ;
		}
		
		return false ;
	}
	
	public void Dies()
	{
		life = 0 ;
	}
	
	public boolean IsHungry()
	{
		if (satiation <= (int) (0.6 * species.getStomach()))
		{
			return true ;
		}
		return false ;
	}
	
	public void Starve()
	{
		satiation += -1;
        if (satiation <= 0)
        {
        	Dies() ;
        }
	}

	public void Eats(Food food)
	{
		if (food != null)
		{
		    satiation += food.getType().getValue() ;
		    if (species.getStomach() < satiation)
		    {
		    	satiation = species.getStomach() ;
		    }
		}
	}

	public boolean IsAbleToMate()
	{
		boolean ableToMate = false ;
		
		if (sexWill == species.getMatePoint())
		{
			ableToMate = true ;
		}
		
		return ableToMate ;
	}
	
	public void Lust()
	{
		if (sexWill < species.getMatePoint())
		{
			sexWill += 1 ;
		}
	}
	
	public void Mate(Artro mate, ArrayList<Artro> allArtros)
	{
		Point newPos = new Point((pos.x + mate.getPos().x) / 2, (pos.y + mate.getPos().y) / 2) ;
		int newAge = 0 ;
		Map<ArtroChoices,Double> newTendency = tendency ;
		int newSatiation = (satiation + mate.getSatiation()) / 2 ;
		ArtroChoices newWill = ArtroChoices.exist ;
		int newSexWill = 0 ;
		Artro newArtro = new Artro(100, newPos, newAge, species, newTendency, keepChoice, newSatiation, newWill, newSexWill, direction) ;
		allArtros.add(newArtro) ;
		
		sexWill = 0 ;
		mate.setSexWill(0) ;
	}
	
	public void Thinks()
	{
		Artro closestOpponent = Evolution.FindClosestOpponent(this) ;
		
		/*if (will.equals("fight") & closestOpponent == -1)
        {
            keepChoice = true;
        }
        if (will.equals("flee") & closestOpponent == -1)
        {
            keepChoice = true;
        }*/
        
		/*if ((keepChoice & UtilS.chance(1 - choice[2] * GroupFightBonus(a))) & -1 < closestOpponent)		// Artro decides to flee
        {
			will = "flee";
        }
       	else*/ 
		if (!keepChoice & UtilS.chance(tendency.get(ArtroChoices.eat)) & IsHungry() & closestOpponent == null)		// Artro decides to eat food
        {
            will = ArtroChoices.eat;
            keepChoice = false;
        }
        else if (!keepChoice & UtilS.chance(tendency.get(ArtroChoices.mate)) & IsAbleToMate())				// Artro decides to mate (bang)
        {
            will = ArtroChoices.mate;
            keepChoice = false;
        }
        /*else if ((keepChoice & UtilS.chance(choice[2] * GroupFightBonus(a))) & -1 < closestOpponent)
        {
            will = "fight";                                             										// Artro decides to fight
        }
        else if ((!keepChoice & UtilS.chance(choice[4])))
        {
            will = "group";                                             										// Artro decides to group
            keepChoice = false;
        }*/
        else if ((!keepChoice & UtilS.chance(tendency.get(ArtroChoices.wander))))
        {
            will = ArtroChoices.wander;                                             										// Artro decides to wander
            keepChoice = false;
        }
        else
        {
            will = ArtroChoices.exist;                                             										// Artro decides to just exist (do nothing)
            keepChoice = false;
        }	
	}
	
	public void Wander(Canva canva)
	{
		if (UtilS.chance(0.1))
		{
			direction = RandomDirection() ;
		}
		Move(canva) ;
	}
	
	public void Move(Canva canva)
	{
		ArrayList<Directions> acceptedDirections = AcceptedDiretions(canva) ;
		if (!acceptedDirections.contains(direction))
		{
			direction = acceptedDirections.get( (int) (acceptedDirections.size() * Math.random()) ) ;
		}
		
		if (direction.equals(Directions.right))
        {
            pos.x += species.getStep();     // move right
        }
        if (direction.equals(Directions.left))
        {
        	pos.x += -species.getStep();    // move left
        }      
        if (direction.equals(Directions.up))
        {
        	pos.y += species.getStep();     // move up
        }
        if (direction.equals(Directions.down))
        {
        	pos.y += -species.getStep();    // move down
        }
	}
	
	public void MoveTowards(Point targetPos)
	{
	    for (int i = 0; i <= species.getStep() - 1; i += 1)
	    {
		    double distx = Math.abs(pos.x - targetPos.x);
		    double disty = Math.abs(pos.y - targetPos.y);
		    if (disty < distx)
	        {
	            if (pos.x < targetPos.x)       				// Point is to the right of artro
	            {
	                pos.x += species.getStep() ;     	// move right
	            }
	            else
	            {
	            	pos.x += -species.getStep();    	// move left
	            }
	        }
	        else                                
	        {
	            if (pos.y < targetPos.y)       				// Point is above artro
	            {
	                pos.y += species.getStep();     	// move up
	            }
	            else
	            {
	                pos.y += -species.getStep();    	// move down
	            }
	        }
	    }
	}
	
	public void Acts(Canva canva, ArrayList<Food> allFood, ArrayList<Artro> allArtros)
	{
		switch(will)
		{
			case eat:
				Food food = FindClosestVisibleFood(allFood) ;
				if (food != null)
				{
					MoveTowards(food.getPos()) ;
				}
				else
				{
					Move(canva) ;
				}
				
				break ;
			case mate:
				Artro closestMate = FindClosestVisibleMate(allArtros) ;
				if (closestMate != null)
				{
					MoveTowards(closestMate.getPos()) ;
					if (IsReachable(closestMate.getPos()))
					{
						Mate(closestMate, allArtros) ;
					}
				}
				else
				{
					Wander(canva) ;
				}
				
				break ;
			case wander:
				Wander(canva) ;
				
				break ;
			case exist:
				
				break ;
				
			default:
				
				break ;
		}
	}
	
	public void Display(Canva canva, DrawingOnAPanel DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension()) ;
		if (will.equals(ArtroChoices.fight))
		{
			//DP.DrawCircle(drawingPos, species.getSize(), Evolution.colorPalette[4], Evolution.colorPalette[6]) ;
		}
		else
		{
			DP.DrawCircle(drawingPos, species.getSize(), null, species.getColor()) ;
		}
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(age, direction, keepChoice, life, pos, satiation, sexWill, species, tendency, will);
	}
	

	@Override	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artro other = (Artro) obj;
		return age == other.age && direction == other.direction && keepChoice == other.keepChoice && life == other.life
				&& Objects.equals(pos, other.pos) && satiation == other.satiation && sexWill == other.sexWill
				&& Objects.equals(species, other.species) && Objects.equals(tendency, other.tendency)
				&& will == other.will;
	}

	@Override
	public String toString() {
		return "Artro [life=" + life + ", pos=" + pos + ", age=" + age + ", species=" + species + ", tendency="
				+ tendency + ", keepChoice=" + keepChoice + ", satiation=" + satiation + ", will=" + will + ", sexWill="
				+ sexWill + ", direction=" + direction + "]";
	}
}