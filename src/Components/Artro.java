package Components;

import java.awt.Point;
import java.util.ArrayList;

import Graphics.Canva;
import Graphics.DrawingOnAPanel;
import Main.Evolution;
import Main.UtilS;

public class Artro
{
	private int life ;
	private Point pos ;
	private int age ;
	private Species species ;
	private double[] choice ;
	private boolean keepChoice ;
	private int satiation ;
	private ArtroChoices will ;
	private int sexWill ;
	private Directions direction ;
	
	public Artro(Canva canva, int life,
			 Point pos,
			 int age,
			 Species species,
			 double[] choice,
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
		this.choice = choice ;
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
	public double[] getChoice() {return choice ;}
	public boolean getKeepChoice() {return keepChoice ;}
	public int getSatiation() {return satiation ;}
	public ArtroChoices getWill() {return will ;}
	public int getSexWill() {return sexWill ;}
	public Directions getDirection() {return direction ;}
	
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
	
	public void Dies()
	{
		life = 0 ;
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
	
	public void Starve()
	{
		satiation += -1;
        if (satiation <= 0)
        {
        	Dies() ;
        }
	}
	
	public boolean IsHungry()
	{
		if (satiation <= (int) (0.6 * species.getStomach()))
		{
			return true ;
		}
		return false ;
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
		if ((!keepChoice & UtilS.chance(choice[0])) & IsHungry() & closestOpponent == null)		// Artro decides to eat food
        {
            will = ArtroChoices.eat;
            keepChoice = false;
        }
        /*else if ((!keepChoice & UtilS.chance(choice[1])) & ArtroIsAbleToMate(a))							// Artro decides to mate (bang)
        {
            will = "mate";
            keepChoice = false;
        }
        else if ((keepChoice & UtilS.chance(choice[2] * GroupFightBonus(a))) & -1 < closestOpponent)
        {
            will = "fight";                                             										// Artro decides to fight
        }
        else if ((!keepChoice & UtilS.chance(choice[4])))
        {
            will = "group";                                             										// Artro decides to group
            keepChoice = false;
        }*/
        else if ((!keepChoice & UtilS.chance(choice[5])))
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
	    for (int step = 0; step <= species.getStep() - 1; step += 1)
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
	
	public void Acts(Canva canva, ArrayList<Food> allFood)
	{
		switch(will)
		{
			case eat:
			{
				Food food = FindFoodInRange(allFood, species.getVision()) ;
				if (food != null)
				{
					MoveTowards(food.getPos()) ;
				}
				else
				{
					Move(canva) ;
				}
			}
			case wander:
				if (UtilS.chance(0.1))
				{
					direction = RandomDirection() ;
				}
				Move(canva) ;
			case exist:
				
			default:
				Move(canva) ;
		}
		Move(canva) ;
	}
	
	public void Display(Canva canva, DrawingOnAPanel DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension()) ;
		if (will.equals(ArtroChoices.fight))
		{
			DP.DrawCircle(drawingPos, species.getSize(), Evolution.colorPalette[4], Evolution.colorPalette[6]) ;
		}
		else
		{
			DP.DrawCircle(drawingPos, species.getSize(), Evolution.colorPalette[4], species.getColor()) ;
		}
	}
}