package Components;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import Graphics.Canva;
import Graphics.DrawingOnAPanel;
import Main.Evolution2;
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
	private int quadrant ;
	
	public Artro(int life,
			 Point pos,
			 int age,
			 Species species,
			 double[] choice,
			 boolean keepChoice,
			 int satiation,
			 ArtroChoices will,
			 int sexWill,
			 Directions direction, int quadrant
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
		this.quadrant = quadrant ;
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
	public int getQuadrant() {return quadrant ;}
	
	/*public Artro GenericArtro()
	{
		int life = 100 ;
		Point pos = new Point(50, 100) ;
		Species species = Evolution2.species.get(0) ;
		ArtroChoices choice = ArtroChoices.exist ;
		boolean keepChoice = true ;
		int satiation = 100 ;
		String will = "" ;
		int sexWill = 1 ;
		Directions direction = Directions.up ;
		int quadrant = 0 ;
		
		return new Artro(life, pos, 0, species, choice, keepChoice, satiation, will, sexWill, direction, quadrant) ;
	}*/
	
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
	
	public void Dies()
	{
		life = 0 ;
	}
	
	public void Eats()
	{
		if (will.equals(ArtroChoices.eat))
		{
    		int f = FindFoodInRange(a);
    		if (f != null)
    		{
	    		int quadrant = FindQuadrant(FoodPos[f]) ;
	    		int foodid = UtilS.FirstIndex(FoodInQuadrant[quadrant], f) ;
                FoodInQuadrant[quadrant] = UtilS.RemoveElemFromArray(foodid, FoodInQuadrant[quadrant]) ;
			    satiation += FoodValue[FoodType[f]];
			    if (species.getStomach() < satiation)
			    {
			    	satiation = species.getStomach() ;
			    }
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
		if (satiation <= 80)
		{
			return true ;
		}
		return false ;
	}
	
	public void UpdateQuadrant(Canva canva)
	{
		// find the current quadrant
		int currentQuadrant = canva.QuadrantPosIsIn(pos) ;
		
		// update quadrant
		if (quadrant != currentQuadrant)
		{
    		//int artroid = UtilS.FirstIndex(ArtrosInQuadrant[quadrant], a) ;
            //ArtrosInQuadrant[quadrant] = UtilS.RemoveElemFromArray(artroid, ArtrosInQuadrant[quadrant]) ;
            quadrant = currentQuadrant ;
            //ArtrosInQuadrant[currentQuadrant] = UtilS.AddElemToVec(ArtrosInQuadrant[currentQuadrant], a) ;				
		}
	}
	
	
	
	public void Thinks()
	{
		Artro closestOpponent = Evolution2.FindClosestOpponent(this) ;
		
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
		System.out.println(UtilS.chance(choice[0]) + " " + IsHungry());
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
        }
        else if ((!keepChoice & UtilS.chance(choice[5])))
        {
            will = "wander";                                             										// Artro decides to wander
            keepChoice = false;
        }
        else
        {
            will = "exist";                                             										// Artro decides to just exist (do nothing)
            keepChoice = false;
        }	*/
	}
	
	public void Move(Canva canva)
	{
		ArrayList<Directions> acceptedDirections = AcceptedDiretions(canva) ;
		if (!acceptedDirections.contains(direction))
		{
			direction = RandomDirection() ;
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
	
	public void Display(Canva canva, DrawingOnAPanel DP)
	{
		Point drawingPos = UtilS.ConvertToDrawingCoords(pos, canva.getPos(), canva.getSize(), canva.getDimension()) ;
		if (will.equals(ArtroChoices.fight))
		{
			DP.DrawCircle(drawingPos, species.getSize(), Evolution2.colorPalette[4], Evolution2.colorPalette[6]) ;
		}
		else
		{
			DP.DrawCircle(drawingPos, species.getSize(), Evolution2.colorPalette[4], species.getColor()) ;
		}
	}
}