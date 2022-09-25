package Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import Graphics.DrawingOnAPanel;
import Output.Results;
import Output.Visuals;

public class Evolução extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container cp;
	int[] SuperFrameSize = new int[] {1350, 700};
	int[] PanelSize = new int[] {1350, 700};
	int[] MousePos = new int[2];
    int[] ScreenTopLeft = new int[] {0, 0};				// Initial coordinates from the top left of the canvas window
    int[] CanvasPos = new int[] {450, 50}, CanvasSize = new int[] {550, 550}, CanvasDim = new int[] {550, 550};
    int[] DrawingPos = new int[] {ScreenTopLeft[0] + CanvasPos[0], ScreenTopLeft[1] + CanvasPos[1] + CanvasSize[1], 0};
	String[][] AllText;
	String Language;
	JButton PlayButton, GraphsButton;
	
	private JPanel jPanel2;
	DrawingOnAPanel DP;
	Visuals V;
	
	/* Space */  
    public double[][] SpaceLimits = new double[][] {{0, 0}, {550, 550}};     // [[xmin, ymin], [xmax, ymax]]
	public double[] SpaceSize = new double[] {SpaceLimits[1][0] - SpaceLimits[0][0], SpaceLimits[1][1] - SpaceLimits[0][1]};
	
	/* Artros */
	public int NumberOfArtros;
	public int NumberOfSpecies;
    public int[] ArtroLife;				// [true = alive]
    public double[][] ArtroPos;				// [x, y]
    public int[] ArtroSpecies;				// [species]
    public double[] ArtroSatiation;			// [satiation]
    public String[] ArtroWill;				// [will]
    public double[] ArtroSexWish;			// [sex wish]
    public int[] ArtroAge;					// [age]
    public int[] ArtroDir;					// [direction of movement]
    public double[] ArtroMut;				// [chance of mutation]
    
    public double[][] ArtroInitialPos;		// [species][x, y]
    public double[][] ArtroDispersion;		// [species][dispersion in x, dispersion in y]
    public double[] ArtroSize;				// [size]
    public double[] ArtroStep;				// [step]
    public int[] ArtroStomach;				// [stomach size]
    public double[] ArtroVision;			// [vision range]
    public double[][] ArtroChoice;			// [species][0: eat, 1: mate, 2: fight, 3: flee, 4: group, 5: wander]
    public boolean[] ArtroKeepChoice;		// [keep choice]
    public int[] ArtroSexApp;				// [sex appetite]
    public int[] ArtroSexAge;				// [sexual age]
    public int[] ArtroMaxAge;				// [maximum age]
    public int[] ArtroFoodValue;			// [food value]
    public Color[] ArtroColor;				// [color]

    /* Species */
    public int[] SpeciesLife;				// Amount of life
    public double[][] SpeciesChoices;		// [0: eat, 1: mate, 2: fight, 3: flee, 4: group, 5: wander]
    public int[] SpeciesMaxPop;				// [maximum population reached by each species]
    public double[][][] SpeciesChoicesHist;	// history of the chance of choices [choice][round][species]
    public int[][] SpeciesPopHist;			// history of the population with time [round][species]
    	
	/* Food */
    public int NumberOfFood;  
	public int NumberOfFoodTypes;                           
    public boolean[] FoodStatus;             // [true = exists]
    public int[] FoodType;                	 // [food type]
    public double[][] FoodPos;               // [x, y]
	public int[] FoodRespawn;         		 // [respawn]
    
    public double[] FoodSize;              	 // [food size]
    public double[][] FoodInitialPos;       // [x, y]
    public double[][] FoodDispersion;       // [dispersion in x, dispersion in y]
    public int[] FoodValue;                  // [value]
	public int[] FoodRespawnRate;            // [respawn rate]
    public Color[] FoodColor;                // [color]
    public int[][] FoodHist; 				 // [history of the population with time]
    public int[] MaxFood;                    // [maximum food amount ever reached by each food type]
	
	/* Global variables */
	int round = 0, delay = 10;
	Color[] ColorPalette;
	String[] ChoiceNames = new String[] {"comer", "reproduzir", "caçar", "fugir", "agrupar", "passear"};
	boolean ShowGraphs = true, ShowLegend, ShowCanvas, ProgramIsRunning;
    String view;
	String LegendTitle;
	double LegendMin, LegendMax;
	
	public Evolução()
	{
	    ColorPalette = Uts.ColorPalette(2);
		initComponents();	// Creates a JPanel inside the JFrame
		Language = "Pt-br";
		AllText = Uts.ReadTextFile(Language);
		LegendMin = 0;
		LegendMax = 0;
		
		/* Defining Container */
		cp = getContentPane();
		//cp.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

		Inicializacao();
		AddButtons();
		AddMenus();
		AddMenuActions(); 
		setTitle("Jarvis");								// Super frame sets its title
		setSize(SuperFrameSize[0], SuperFrameSize[1]);	// Super frame sets its initial window size
		setVisible(true);								// Super frame shows
	}

	public void Inicializacao()
	{
		int NumArtroPar = 15;
		Object[] object = Uts.ReadInputFile("input.txt");
		delay = Integer.parseInt(String.valueOf(object[0]));
		NumberOfArtros = Integer.parseInt(String.valueOf(object[1]));
		ArtroInitialPos = Uts.ObjectToDoubleArray(object[2]);
		ArtroDispersion = Uts.ObjectToDoubleArray(object[3]);
	    SpeciesLife = Uts.ObjectToIntVec(object[4]);	   
	    ArtroSize = Uts.ObjectToDoubleVec(object[5]);	   
	    ArtroStep = Uts.ObjectToDoubleVec(object[6]);
	    ArtroStomach = Uts.ObjectToIntVec(object[7]);
	    ArtroVision = Uts.ObjectToDoubleVec(object[8]);
	    SpeciesChoices = Uts.ObjectToDoubleArray(object[9]);
	    ArtroSexApp = Uts.ObjectToIntVec(object[10]);
	    ArtroSexAge = Uts.ObjectToIntVec(object[11]);
	    ArtroMaxAge = Uts.ObjectToIntVec(object[12]);
	    ArtroFoodValue = Uts.ObjectToIntVec(object[13]);
	    ArtroMut = Uts.ObjectToDoubleVec(object[14]);
	    ArtroColor = Uts.ObjectToColorVec(object[15]);
	    NumberOfSpecies = ArtroSize.length;    
	    SpeciesMaxPop = new int[NumberOfSpecies];                  		// [maximum population of all times]
		ArtroLife = new int[NumberOfArtros];                // [true = alive]
	    ArtroPos = new double[NumberOfArtros][2];              // [x, y]
	    ArtroSpecies = new int[NumberOfArtros];                     // [species]
	    ArtroChoice = new double[NumberOfArtros][SpeciesChoices[0].length];           // [chance to follow food][chance to reproduce][chance to wander][chance to fight]
	    ArtroKeepChoice = new boolean[NumberOfArtros];			// keep the current choice while the conditions last
	    ArtroSatiation = new double[NumberOfArtros];             // [satiation]
	    ArtroWill = new String[NumberOfArtros];                  // [will]
	    ArtroSexWish = new double[NumberOfArtros];               // [sex wish]
	    ArtroAge = new int[NumberOfArtros];                         // [age]
	    ArtroDir = new int[NumberOfArtros];                         // [direction of movement]
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {	
			int species = a % NumberOfSpecies;
			double[] Pos = Uts.RandomPosAroundPoint(new double[] {SpaceLimits[0][0] + ArtroInitialPos[species][0]*SpaceSize[0], SpaceLimits[0][0] + ArtroInitialPos[species][1]*SpaceSize[0]}, new double[] {ArtroDispersion[species][0]*SpaceSize[0], ArtroDispersion[species][1]*SpaceSize[1]});	
			int SexWish = (int) (ArtroSexApp[species]*(0.5 + 0.5*Math.random()));
			int Satiation = (int) (ArtroStomach[species]*(0.5 + 0.5*Math.random()));
			double[] Choices = new double[ArtroChoice[species].length];
			int dir = (int) (4*Math.random());
			for (int i = 0; i <= SpeciesChoices[species].length - 1; i += 1)
			{
				double var = (1.0 + Uts.Random(0.0))*SpeciesChoices[species][i];
				Choices[i] = var;
			}
			int Age = (int) (Math.random()*ArtroMaxAge[species]);
	        CreateArtro(a, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", SexWish, ArtroSexApp[species], Satiation, ArtroStomach[species], Age, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species]);
	    }

	    NumberOfFood = Integer.parseInt(String.valueOf(object[NumArtroPar + 1]));
		FoodInitialPos = Uts.ObjectToDoubleArray(object[NumArtroPar + 2]);
		FoodDispersion = Uts.ObjectToDoubleArray(object[NumArtroPar + 3]);
	    FoodValue = Uts.ObjectToIntVec(object[NumArtroPar + 4]);
	    FoodSize = Uts.ObjectToDoubleVec(object[NumArtroPar + 5]);
	    FoodRespawnRate = Uts.ObjectToIntVec(object[NumArtroPar + 6]);
	    FoodColor = Uts.ObjectToColorVec(object[NumArtroPar + 7]);
	    NumberOfFoodTypes = FoodValue.length;    
	    FoodRespawn = new int[NumberOfFood];
		FoodStatus = new boolean[NumberOfFood];                 // [true = exists]
		FoodType = new int[NumberOfFood];                     		// [food type]
		FoodPos = new double[NumberOfFood][2];                 // [x, y]
		MaxFood = new int[NumberOfFoodTypes];                 		// [maximum population of all times]
		
		SpeciesChoicesHist = new double[ArtroChoice.length][][];
	    for (int f = 0; f <= NumberOfFood - 1; f += 1)
	    {
	    	int type = (int) (Math.random()*NumberOfFoodTypes);
	    	double[] Pos = Uts.RandomPosAroundPoint(new double[] {SpaceLimits[0][0] + FoodInitialPos[type][0]*SpaceSize[0], SpaceLimits[0][0] + FoodInitialPos[type][1]*SpaceSize[0]}, new double[] {FoodDispersion[type][0]*SpaceSize[0], FoodDispersion[type][1]*SpaceSize[1]});
			Pos = new double[] {SpaceLimits[0][0] + Math.random()*SpaceSize[0], SpaceLimits[0][0] + Math.random()*SpaceSize[0]};
	    	CreateFood(f, true, Pos, type, FoodValue[type], FoodRespawn[f]);
	    }
	    ShowCanvas = true;
	    ProgramIsRunning = true;
	}
	
	public void CreateArtro(int a, int Life, double[] Pos, int species, double size, double step, double vision, double[] choice, String will, int sexwish, int sexapp, int satiation, int stomach, int age, int sexage, int foodvalue, int dir, double mut)
	{
	    ArtroLife[a] = Life;
        ArtroPos[a] = Pos;
        ArtroSpecies[a] = species;
        ArtroSize[ArtroSpecies[a]] = size;
        ArtroStep[ArtroSpecies[a]] = step;
        ArtroVision[ArtroSpecies[a]] = vision;
        ArtroChoice[a] = choice;
        ArtroWill[a] = will;
        ArtroSexWish[a] = sexwish;
        ArtroSexApp[ArtroSpecies[a]] = sexapp;
        ArtroSatiation[a] = satiation;
        ArtroStomach[ArtroSpecies[a]] = stomach;
        ArtroSexAge[ArtroSpecies[a]] = sexage;
        ArtroFoodValue[ArtroSpecies[a]] = foodvalue;
        ArtroMut[ArtroSpecies[a]] = mut;
        ArtroAge[a] = age;
        ArtroDir[a] = dir;
	}
	
	public void CreateFood(int f, boolean status, double[] Pos, int type, int value, int respawn)
	{
	    FoodStatus[f] = status;
        FoodPos[f] = Pos;
        FoodType[f] = type;
        FoodValue[FoodType[f]] = value;
        FoodRespawn[f] = respawn;
	}
	
	public boolean ArtroIsAbleToMate(int a)
	{
	    if ((0 < ArtroLife[a]) & ArtroSexWish[a] == ArtroSexApp[ArtroSpecies[a]] & ArtroSexAge[ArtroSpecies[a]] <= ArtroAge[a])
	    {
	        return true;
	    }
	    return false;
	}
	
	public int ClosestFood(double[] artroPos, double artroVision)
	{
	    int closestFood = -1;
	    double MinFoodDist = Uts.dist(artroPos, FoodPos[0]);
	    for (int f = 0; f <= NumberOfFood - 1; f += 1)
        {
            double FoodDist = Uts.dist(artroPos, FoodPos[f]);
            if (FoodStatus[f] & FoodDist < MinFoodDist & FoodDist <= artroVision)
            {
                closestFood = f;
                MinFoodDist = FoodDist;
            }
        }
        return closestFood;
	}
	
	public int ClosestMate(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int closestMate = -1;
	    double MinMateDist = artroVision;
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
        {
	    	if (0 < ArtroLife[a])
	    	{
	            double MateDist = Uts.dist(artroPos, ArtroPos[a]);
	            if (a != artroID & ArtroSpecies[a] == artroSpecies & MateDist < MinMateDist & MateDist <= artroVision)
	            {
	                closestMate = a;
	            }
	    	}
        }
        return closestMate;
	}
	
	public int ClosestOpponent(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int closestOpponent = -1;
	    double MinFightDist = artroVision;
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
        {
	    	if (0 < ArtroLife[a])
	    	{
	            double FightDist = Uts.dist(artroPos, ArtroPos[a]);
	            if (a != artroID & ArtroSpecies[a] != artroSpecies & FightDist < MinFightDist & FightDist <= artroVision)
	            {
	                closestOpponent = a;
	                MinFightDist = FightDist;
	            }
	    	}
        }
        return closestOpponent;
	}

	public int NumberOfCloseFriends(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int NumCloseFriends = 0;
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
        {
	    	if (0 < ArtroLife[a])
	    	{
	            if (a != artroID & ArtroSpecies[a] == artroSpecies & Uts.dist(artroPos, ArtroPos[a]) <= artroVision)
	            {
	                NumCloseFriends += 1;
	            }
	    	}
        }
        return NumCloseFriends;
	}
	
	public int[] CountNumOfAlive(String unit)
	{
	    if (unit.equals("Artros"))
	    {
		    int[] cont = new int[NumberOfSpecies];
	        for (int a = 0; a <= NumberOfArtros - 1; a += 1)
    	    {
    	        if (0 < ArtroLife[a])
    	        {
    	            cont[ArtroSpecies[a]] += 1;
    	        }
    	    }
	    	return cont;
	    }
	    if (unit.equals("Food"))
	    {
		    int[] cont = new int[NumberOfFoodTypes];
	        for (int f = 0; f <= NumberOfFood - 1; f += 1)
            {
                if (FoodStatus[f])
    	        {
    	            cont[FoodType[f]] += 1;
    	        }
            }
	    	return cont;
	    }
    	return new int[] {-1};
	}
	
	public double GroupFightBonus(int a)
	{
		double bonus = 1;
		bonus += (double)NumberOfCloseFriends(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
		return bonus;
	}
	
	public double[] SpeciesAvrPar(double[][] Par, int p)
	{
		double[] AvrPar = new double[NumberOfSpecies];
		
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
			if (0 < ArtroLife[a])
			{
				AvrPar[ArtroSpecies[a]] += Par[a][p];
			}
	    }
		for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
	    {
			if (0 < SpeciesPopHist[SpeciesPopHist.length - 1][s])
			{
				AvrPar[s] = AvrPar[s] / (double)SpeciesPopHist[SpeciesPopHist.length - 1][s];
			}
			else
			{
				AvrPar[s] = 0;
			}
	    }		
		return AvrPar;
	}

	public double[] SpeciesAvrPar(double[] Par)
	{
		double[] AvrPar = new double[NumberOfSpecies];
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
			AvrPar[ArtroSpecies[a]] += Par[a];
	    }
		for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
	    {
			if (0 < SpeciesPopHist[round/delay][s])
			{
				AvrPar[s] = AvrPar[s]/(double)SpeciesPopHist[round/delay][s];
			}
			else
			{
				AvrPar[s] = 0;
			}
	    }		
		return AvrPar;
	}

	public void MoveArtro(int a)
	{
        if (ArtroDir[a] == 0)
        {
            ArtroPos[a][0] += ArtroStep[ArtroSpecies[a]];     // move right
        }
        if (ArtroDir[a] == 1)
        {
            ArtroPos[a][0] += -ArtroStep[ArtroSpecies[a]];    // move left
        }      
        if (ArtroDir[a] == 2)
        {
            ArtroPos[a][1] += ArtroStep[ArtroSpecies[a]];     // move up
        }
        if (ArtroDir[a] == 3)
        {
            ArtroPos[a][1] += -ArtroStep[ArtroSpecies[a]];    // move down
        }
	}
	
	public void Move(int a)
	{
		for (int step = 0; step <= ArtroStep[ArtroSpecies[a]] - 1; step += 1)
	    {
			int[] acceptedmoves = null;
			if (ArtroPos[a][0] + ArtroStep[ArtroSpecies[a]] <= SpaceLimits[0][0] + SpaceLimits[1][0])
			{
				acceptedmoves = Uts.AddElemToVec(acceptedmoves, 0);
			}
			if (SpaceLimits[0][0] <= ArtroPos[a][0] - ArtroStep[ArtroSpecies[a]])
			{
				acceptedmoves = Uts.AddElemToVec(acceptedmoves, 1);
			}
			if (ArtroPos[a][1] + ArtroStep[ArtroSpecies[a]] <= SpaceLimits[0][0] + SpaceLimits[1][0])
			{
				acceptedmoves = Uts.AddElemToVec(acceptedmoves, 2);
			}
			if (SpaceLimits[0][0] <= ArtroPos[a][1] - ArtroStep[ArtroSpecies[a]])
			{
				acceptedmoves = Uts.AddElemToVec(acceptedmoves, 3);
			}
			if (!Uts.ArrayContains(acceptedmoves, ArtroDir[a]))
			{	// This means the artro will only change the movement direction if the current direction is not accepted anymore
				ArtroDir[a] = acceptedmoves[(int) (Math.random()*acceptedmoves.length)];
			}
	        if (ArtroDir[a] == 0)
	        {
	            ArtroPos[a][0] += ArtroStep[ArtroSpecies[a]];     // move right
	        }
	        if (ArtroDir[a] == 1)
	        {
	            ArtroPos[a][0] += -ArtroStep[ArtroSpecies[a]];    // move left
	        }      
	        if (ArtroDir[a] == 2)
	        {
	            ArtroPos[a][1] += ArtroStep[ArtroSpecies[a]];     // move up
	        }
	        if (ArtroDir[a] == 3)
	        {
	            ArtroPos[a][1] += -ArtroStep[ArtroSpecies[a]];    // move down
	        }
	    }
	}
	
	public void RandomMove(int a)
	{
		for (int step = 0; step <= ArtroStep[ArtroSpecies[a]] - 1; step += 1)
	    {
		    int move = (int) (4*Math.random());
	        if (move == 0)
	        {
	            ArtroPos[a][0] += ArtroStep[ArtroSpecies[a]];     // move right
	        }
	        if (move == 1)
	        {
	            ArtroPos[a][0] += -ArtroStep[ArtroSpecies[a]];    // move left
	        }      
	        if (move == 2)
	        {
	            ArtroPos[a][1] += ArtroStep[ArtroSpecies[a]];     // move up
	        }
	        if (move == 3)
	        {
	            ArtroPos[a][1] += -ArtroStep[ArtroSpecies[a]];    // move down
	        }
	    }
	}
	
	public void MoveTowards(int a, double[] PointPos)
	{
	    for (int step = 0; step <= ArtroStep[ArtroSpecies[a]] - 1; step += 1)
	    {
		    double distx = Math.abs(ArtroPos[a][0] - PointPos[0]);
		    double disty = Math.abs(ArtroPos[a][1] - PointPos[1]);
		    if (disty < distx)
	        {
	            if (ArtroPos[a][0] < PointPos[0])       				// Point is to the right of artro
	            {
	                ArtroPos[a][0] += ArtroStep[ArtroSpecies[a]];     	// move right
	            }
	            else
	            {
	                ArtroPos[a][0] += -ArtroStep[ArtroSpecies[a]];    	// move left
	            }
	        }
	        else                                
	        {
	            if (ArtroPos[a][1] < PointPos[1])       				// Point is above artro
	            {
	                ArtroPos[a][1] += ArtroStep[ArtroSpecies[a]];     	// move up
	            }
	            else
	            {
	                ArtroPos[a][1] += -ArtroStep[ArtroSpecies[a]];    	// move down
	            }
	        }
	    }
	}
	
	public void MoveAwayFrom(int a, double[] Point)
	{
		for (int step = 0; step <= ArtroStep[ArtroSpecies[a]] - 1; step += 1)
	    {
		    double distx = Math.abs(ArtroPos[a][0] - Point[0]);
		    double disty = Math.abs(ArtroPos[a][1] - Point[1]);
		    if (disty < distx)
	        {
	            if (ArtroPos[a][0] < Point[0])       				// Point is to the right of artro
	            {
	            	ArtroDir[a] = 1;
	                //ArtroPos[a][0] += -ArtroStep[ArtroSpecies[a]];     	// move left
	            }
	            else
	            {
	            	ArtroDir[a] = 0;
	                //ArtroPos[a][0] += ArtroStep[ArtroSpecies[a]];    	// move right
	            }
	        }
	        else                                
	        {
	            if (ArtroPos[a][1] < Point[1])       				// Point is above artro
	            {
	            	ArtroDir[a] = 3;
	                //ArtroPos[a][1] += -ArtroStep[ArtroSpecies[a]];     	// move down
	            }
	            else
	            {
	            	ArtroDir[a] = 2;
	                //ArtroPos[a][1] += ArtroStep[ArtroSpecies[a]];    	// move up
	            }
	        }
		    MoveArtro(a);
	    }
	}

	/* Artos actions */
	public void ArtrosAct()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	        if (0 < ArtroLife[a])
	        {
	            if (ArtroWill[a].equals("eat"))                  // Move towards food
	            {
	                int closestFood = ClosestFood(ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
	                if (-1 < closestFood)
	                {
	                    MoveTowards(a, FoodPos[closestFood]);
	                }
	                else
	                {
	                	Move(a);
	                }
	            }
	            else if (ArtroWill[a].equals("mate"))             // Move towards food
	            {
	                int closestMate = ClosestMate(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
	                //System.out.println("closestMate = " + closestMate);
	                if (-1 < closestMate)
	                {
	                    MoveTowards(a, ArtroPos[closestMate]);
	                }
	                else
	                {
	                	Move(a);
	                }
	            }
	            else if (ArtroWill[a].equals("fight"))             // Move towards opponent
	            {
	                int closestOpponent = ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
	                if (-1 < closestOpponent)
	                {
	                    MoveTowards(a, ArtroPos[closestOpponent]);
	                }
	                else
	                {
	                	Move(a);
	                }
	            }
	            else if (ArtroWill[a].equals("flee"))             // Move away from opponent
	            {
	                int closestOpponent = ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
	                if (-1 < closestOpponent)
	                {
	                    MoveAwayFrom(a, ArtroPos[closestOpponent]);
	                }
	                else
	                {
	                	MoveArtro(a);
	                }
	            }
	            else if (ArtroWill[a].equals("group"))             // Move towards artros of the same species
	            {
	                int closestFriend = ClosestMate(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
	                if (-1 < closestFriend)
	                {
	                    MoveTowards(a, ArtroPos[closestFriend]);
	                }
	                else
	                {
	                	Move(a);
	                }
	            }
	            else if (ArtroWill[a].equals("exist"))		  // just exist
	            {
	            	
	            }
	            else                                          // wander
	            {
        	        Move(a);
	            }
    	        
    	        if (ArtroPos[a][0] < SpaceLimits[0][0])
    	        {
    	            ArtroPos[a][0] = SpaceLimits[0][0]; // Left bound
    	        }
    	        if (ArtroPos[a][1] < SpaceLimits[0][1])
    	        {
    	            ArtroPos[a][1] = SpaceLimits[0][1]; // Bottom bound
    	        }
    	        if (SpaceLimits[1][0] < ArtroPos[a][0])
    	        {
    	            ArtroPos[a][0] = SpaceLimits[1][0]; // Right bound
    	        }
    	        if (SpaceLimits[1][1] < ArtroPos[a][1])
    	        {
    	            ArtroPos[a][1] = SpaceLimits[1][1]; // Top bound
    	        }
	        }
	    }
	}

	public void ArtrosEat()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	    	if (0 < ArtroLife[a])
	    	{
	    		if (ArtroWill[a].equals("eat") & ArtroSatiation[a] < ArtroStomach[ArtroSpecies[a]])
	    		{
	    			for (int f = 0; f <= NumberOfFood - 1; f += 1)
	    			{
	    				if (FoodStatus[f] & Uts.dist(ArtroPos[a], FoodPos[f]) <= (ArtroSize[ArtroSpecies[a]] + FoodSize[FoodType[f]])/2.0)
	    				{
	    				    FoodStatus[f] = false;
	    				    ArtroSatiation[a] += FoodValue[FoodType[f]];
	    				    if (ArtroStomach[ArtroSpecies[a]] < ArtroSatiation[a])
	    				    {
	    				    	ArtroSatiation[a] = ArtroStomach[ArtroSpecies[a]];
	    				    }
	    				}
	    			}
	    		}
	    	}
	    }
	}
	
	public void ArtrosMate()
	{
	    for (int a1 = 0; a1 <= NumberOfArtros - 1; a1 += 1)
	    {
	       if (ArtroIsAbleToMate(a1))
	       {
                for (int a2 = 0; a2 <= NumberOfArtros - 1; a2 += 1)
                {
                    if (a1 != a2 & ArtroIsAbleToMate(a2) & ArtroSpecies[a1] == ArtroSpecies[a2] & Uts.dist(ArtroPos[a1], ArtroPos[a2]) <= (ArtroSize[ArtroSpecies[a1]] + ArtroSize[ArtroSpecies[a2]])/2.0)
	                {
                	    ArtroLife = Uts.IncreaseVecSize(ArtroLife);
                        ArtroPos = Uts.IncreaseArraySize(ArtroPos);
                        ArtroSpecies = Uts.IncreaseVecSize(ArtroSpecies);
                        ArtroSize = Uts.IncreaseVecSize(ArtroSize);
                        ArtroStep = Uts.IncreaseVecSize(ArtroStep);
                        ArtroSatiation = Uts.IncreaseVecSize(ArtroSatiation);
                        ArtroStomach = Uts.IncreaseVecSize(ArtroStomach);
                        ArtroChoice = Uts.IncreaseArraySize(ArtroChoice);
                        ArtroKeepChoice = Uts.IncreaseVecSize(ArtroKeepChoice);
                        ArtroWill = Uts.IncreaseVecSize(ArtroWill);
                        ArtroSexWish = Uts.IncreaseVecSize(ArtroSexWish);
                        ArtroSexApp = Uts.IncreaseVecSize(ArtroSexApp);
                        ArtroAge = Uts.IncreaseVecSize(ArtroAge);
                        ArtroSexAge = Uts.IncreaseVecSize(ArtroSexAge);
                        ArtroDir = Uts.IncreaseVecSize(ArtroDir);
                        NumberOfArtros += 1;
                        double[] Pos = new double[] {(ArtroPos[a1][0] + ArtroPos[a2][0])/2, (ArtroPos[a1][1] + ArtroPos[a2][1])/2};
            			int species = ArtroSpecies[a1];
            			int InitialSexWish = 0;
            			double random = Math.random();
            			int InitialSatiation = (int) (random*ArtroSatiation[a1] + (1 - random)*ArtroSatiation[a2]);
            			double[] Choices = new double[ArtroChoice[species].length];
            			int dir = (int) (4*Math.random());
            			for (int i = 0; i <= ArtroChoice[species].length - 1; i += 1)
            			{
            				Choices[i] = (ArtroChoice[a1][i] + ArtroChoice[a2][i])/2.0;
            			}
            			if (Uts.chance(ArtroMut[species]))
            			{
            				Choices[2] = Math.min(1, Math.max(0, (1.0 + Uts.Random(2))*Choices[2]));
            			}
            			int InitialAge = 0;
                        CreateArtro(NumberOfArtros - 1, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", InitialSexWish, ArtroSexApp[species], InitialSatiation, ArtroStomach[species], InitialAge, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species]);
                	    ArtroSexWish[a1] = 0;
                	    ArtroSexWish[a2] = 0;
	                }
                }
	       }
	    }
	}

	public void ArtrosMate2()
	{
	    for (int a1 = 0; a1 <= NumberOfArtros - 1; a1 += 1)
	    {
	       if (ArtroIsAbleToMate(a1))
	       {
                for (int a2 = 0; a2 <= NumberOfArtros - 1; a2 += 1)
                {
                	if (ArtroIsAbleToMate(a2))
                	{
                        if (a1 != a2 & ArtroSpecies[a1] == ArtroSpecies[a2] & Uts.dist(ArtroPos[a1], ArtroPos[a2]) <= (ArtroSize[ArtroSpecies[a1]] + ArtroSize[ArtroSpecies[a2]])/2.0)
    	                {
                        	/* determine new artro characteristics*/
                            double[] Pos = new double[] {(ArtroPos[a1][0] + ArtroPos[a2][0])/2, (ArtroPos[a1][1] + ArtroPos[a2][1])/2};
                			int species = ArtroSpecies[a1];
                			int InitialSexWish = 0;
                			double random = Math.random();
                			int InitialSatiation = (int) (random*ArtroSatiation[a1] + (1 - random)*ArtroSatiation[a2]);
                			double[] Choices = new double[ArtroChoice[species].length];
                			int dir = (int) (4*Math.random());
                			for (int c = 0; c <= ArtroChoice[species].length - 1; c += 1)
                			{
                				Choices[c] = (ArtroChoice[a1][c] + ArtroChoice[a2][c])/2.0;
                    			if (Uts.chance(ArtroMut[species]) & c == 2)
                    			{
                    				Choices[c] = Math.min(1, Math.max(0, (1.0 + Uts.Random(2))));
                    			}
                			}
                			int InitialAge = 0;
                			
                        	if (Uts.ArrayContains(ArtroLife, 0))	// If there is a dead artro, new artro is born in that place
                        	{
                        		int a12 = Uts.FirstIndex(ArtroLife, 0);
                                CreateArtro(a12, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", InitialSexWish, ArtroSexApp[species], InitialSatiation, ArtroStomach[species], InitialAge, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species]);
                        	}
                        	else	// new artro is created increasing the artro vector
                        	{
                        	    ArtroLife = Uts.IncreaseVecSize(ArtroLife);
                                ArtroPos = Uts.IncreaseArraySize(ArtroPos);
                                ArtroSpecies = Uts.IncreaseVecSize(ArtroSpecies);
                                ArtroSize = Uts.IncreaseVecSize(ArtroSize);
                                ArtroStep = Uts.IncreaseVecSize(ArtroStep);
                                ArtroSatiation = Uts.IncreaseVecSize(ArtroSatiation);
                                ArtroStomach = Uts.IncreaseVecSize(ArtroStomach);
                                ArtroChoice = Uts.IncreaseArraySize(ArtroChoice);
                                ArtroKeepChoice = Uts.IncreaseVecSize(ArtroKeepChoice);
                                ArtroWill = Uts.IncreaseVecSize(ArtroWill);
                                ArtroSexWish = Uts.IncreaseVecSize(ArtroSexWish);
                                ArtroSexApp = Uts.IncreaseVecSize(ArtroSexApp);
                                ArtroAge = Uts.IncreaseVecSize(ArtroAge);
                                ArtroSexAge = Uts.IncreaseVecSize(ArtroSexAge);
                                ArtroDir = Uts.IncreaseVecSize(ArtroDir);
                                CreateArtro(NumberOfArtros - 1, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", InitialSexWish, ArtroSexApp[species], InitialSatiation, ArtroStomach[species], InitialAge, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species]);
                                NumberOfArtros += 1;  
                            }
                            ArtroSexWish[a1] = 0;
                    	    ArtroSexWish[a2] = 0;            	
    	                }
                	}
                }
	       }
	    }
	}
	
	public void ArtrosFight()
	{
		for (int a1 = 0; a1 <= NumberOfArtros - 1; a1 += 1)
		{
			if (0 < ArtroLife[a1])
			{
				if (ArtroWill[a1].equals("fight"))
				{
					for (int a2 = 0; a2 <= NumberOfArtros - 1; a2 += 1)
					{
						if (0 < ArtroLife[a2])
						{
					        if (a1 != a2 & ArtroSpecies[a1] != ArtroSpecies[a2] & Uts.dist(ArtroPos[a1], ArtroPos[a2]) <= (ArtroSize[ArtroSpecies[a1]] + ArtroSize[ArtroSpecies[a2]])/2.0)
					        {
					        	if (ArtroLife[a2] < ArtroLife[a1])
					        	{
						        	ArtroSatiation[a1] += ArtroFoodValue[ArtroSpecies[a2]];
						        	ArtroLife[a1] += -ArtroLife[a2];
						            if (ArtroStomach[ArtroSpecies[a1]] < ArtroSatiation[a1])
						            {
						            	ArtroSatiation[a1] = ArtroStomach[ArtroSpecies[a1]];
						            }
						        	ArtroLife[a2] = 0;
					        	}
					        	else
					        	{
					        		ArtroLife[a2] += -ArtroLife[a1];
					        		ArtroLife[a1] = 0;
					        	}
					        }
						}
					}
				}
        	}
	    }
	}
	
	public void ArtrosLust()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	        if (ArtroSexWish[a] <= ArtroSexApp[ArtroSpecies[a]] - 1)
	        {
	            ArtroSexWish[a] += 1;
	        }
	    }
	}
	
	public void ArtrosStarve()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	    	if (0 < ArtroLife[a])
	    	{
		        ArtroSatiation[a] += -1;
	    	}
	        if (ArtroSatiation[a] <= 0)
	        {
	            ArtroLife[a] = 0;
	        }
	    }
	}

	public void ArtrosThink()
	{
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
			if (0 < ArtroLife[a])
			{
	            if (ArtroWill[a].equals("fight") & ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]) == -1)
	            {
		            ArtroKeepChoice[a] = true;
	            }
	            if (ArtroWill[a].equals("flee") & ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]) == -1)
	            {
		            ArtroKeepChoice[a] = true;
	            }
	            
				if ((ArtroKeepChoice[a] & Uts.chance(1 - ArtroChoice[a][2] * GroupFightBonus(a))) & -1 < ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]))
		        {
					ArtroWill[a] = "flee";                                             								// Artro decides to flee
		        }
		       	else if ((!ArtroKeepChoice[a] & Uts.chance(ArtroChoice[a][0])) & ArtroSatiation[a] < 0.8*ArtroStomach[ArtroSpecies[a]] & ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]) == -1)		// Artro decides to eat food
		        {
		            ArtroWill[a] = "eat";
		            ArtroKeepChoice[a] = false;
		        }
		        else if ((!ArtroKeepChoice[a] & Uts.chance(ArtroChoice[a][1])) & ArtroIsAbleToMate(a))										// Artro decides to mate (bang)
		        {
		            ArtroWill[a] = "mate";
		            ArtroKeepChoice[a] = false;
		        }
		        else if ((ArtroKeepChoice[a] & Uts.chance(ArtroChoice[a][2] * GroupFightBonus(a))) & -1 < ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]))
		        {
		            ArtroWill[a] = "fight";                                             							// Artro decides to fight
		        }
		        else if ((!ArtroKeepChoice[a] & Uts.chance(ArtroChoice[a][4])))
		        {
		            ArtroWill[a] = "group";                                             							// Artro decides to group
		            ArtroKeepChoice[a] = false;
		        }
		        else if ((!ArtroKeepChoice[a] & Uts.chance(ArtroChoice[a][5])))
		        {
		            ArtroWill[a] = "wander";                                             							// Artro decides to wander
		            ArtroKeepChoice[a] = false;
		        }
		        else
		        {
		            ArtroWill[a] = "exist";                                             							// Artro decides to just exist (do nothing)
		            ArtroKeepChoice[a] = false;
		        }	
				/*if (!ArtroWill[a].equals("wander") & !ArtroWill[a].equals("exist") & ((ArtroWill[a].equals("eat") & -1 < ClosestFood(ArtroPos[a], ArtroVision[ArtroSpecies[a]])) | (ArtroWill[a].equals("fight") & -1 < ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]])) | ArtroWill[a].equals("flee")))
				{
					ArtroDir[a] = (int) (4*Math.random());
				}*/
				//ArtroDir[a] = (int) (4*Math.random());
			}
	    }
		System.out.println(ArtroWill[0]);
	}
	
	public void ArtrosAge()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	        ArtroAge[a] += 1;
	        if (ArtroMaxAge[ArtroSpecies[a]] < ArtroAge[a])
	        {
	        	ArtroLife[a] = 0;
	        }
	    }
	}

	/* Food actions */
	public void RespawnFood()
	{
	    for (int f = 0; f <= NumberOfFood - 1; f += 1)
	    {
	        if (!FoodStatus[f])
	        {
	            if (FoodRespawn[f] == 0)
	            {
	                FoodStatus[f] = true;
	    			//FoodPos[f] = Ut.RandomPosAroundPoint(new double[] {SpaceLimits[0][0] + FoodInitialPos[FoodType[f]][0]*SpaceSize[0], SpaceLimits[0][0] + FoodInitialPos[FoodType[f]][1]*SpaceSize[0]}, new double[] {FoodDispersion[FoodType[f]][0]*SpaceSize[0], FoodDispersion[FoodType[f]][1]*SpaceSize[1]});
	    			FoodPos[f] = new double[] {SpaceLimits[0][0] + Math.random()*SpaceSize[0], SpaceLimits[0][0] + Math.random()*SpaceSize[0]};
	                FoodRespawn[f] = FoodRespawnRate[FoodType[f]];
	            }
	            else
	            {
	                FoodRespawn[f] += -1;
	            }
	        }
	    }
	}

	/* Other functions */
	public void AddMenus()
	{
		/* Defining menu bars */
		
	}
	
	public void AddMenuActions()
	{
		
	}
	
	public void AddButtons()
	{	
		/* Defining Button Icons */
		String ImagesPath = ".\\Icons\\";
		ImageIcon PlayIcon = new ImageIcon(ImagesPath + "PlayIcon.png");
		ImageIcon GraphsIcon = new ImageIcon(ImagesPath + "GraphsIcon.png");
		
		/* Defining Buttons */
		Color BackgroundColor = ColorPalette[5];
		JButton PlayButton = Uts.AddButton(PlayIcon, new int[2], new int[] {30, 30}, BackgroundColor);
		JButton GraphsButton = Uts.AddButton(GraphsIcon, new int[2], new int[] {30, 30}, BackgroundColor);
		cp.add(PlayButton);
		cp.add(GraphsButton);
		
		/* Defining button actions */
		PlayButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ProgramIsRunning = !ProgramIsRunning;
				Results Re = new Results();
				Re.SaveOutputFile("Output.txt", SpeciesPopHist, FoodHist);
			}
		});
		GraphsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ShowGraphs = !ShowGraphs;
			}
		});
	
		PlayButton = Uts.AddButton("", new int[2], new int[] {110, 30}, BackgroundColor);
	}
	
	public void Records(int maxlength)
	{
		int[] ArtrosPop = CountNumOfAlive("Artros");
		for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
        {
			if (SpeciesMaxPop[s] < ArtrosPop[s])
			{
				SpeciesMaxPop[s] = ArtrosPop[s];
			}
        }
		SpeciesPopHist = Uts.AddElemToArrayUpTo(SpeciesPopHist, ArtrosPop, maxlength);	
		double[][] ChoicesHist = new double[ArtroChoice[0].length][NumberOfSpecies];
		for (int c = 0; c <= ArtroChoice[0].length - 1; c += 1)
		{
			for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
	        {
				ChoicesHist[c][s] = SpeciesAvrPar(ArtroChoice, c)[s];
	        }
			SpeciesChoicesHist[c] = Uts.AddElemToArrayUpTo(SpeciesChoicesHist[c], ChoicesHist[c], maxlength);
		}
		int[] FoodPop = CountNumOfAlive("Food");
		for (int t = 0; t <= NumberOfFoodTypes - 1; t += 1)
        {
			if (MaxFood[0] < FoodPop[t])
			{
				MaxFood[0] = FoodPop[t];
			}
        }
		FoodHist = Uts.AddElemToArrayUpTo(FoodHist, FoodPop, maxlength);
	}

	public void RunProgram()
	{
		//DP.DrawAxis(new int[] {CanvasPos[0], CanvasPos[1] + CanvasSize[1]}, CanvasSize[0] + 20, CanvasDim);
		if (ShowCanvas)
		{
			V.DrawCanvas();
		}
		if (ProgramIsRunning)
		{
			if (round % delay == 0)
			{
				ArtrosThink();
				ArtrosAct();
		        ArtrosEat();
		        ArtrosStarve();
		        ArtrosLust();
		        ArtrosMate2();
		        ArtrosFight();
		        RespawnFood();
		        ArtrosAge();
		        if (round % (10 * delay) == 0)
				{
			        Records(100);
				}
			}
		}
        V.DrawFood(FoodPos, FoodType, FoodStatus, FoodSize, FoodColor);
        V.DrawArtros(ArtroPos, ArtroWill, ArtroSpecies, ArtroLife, ArtroSize, ArtroColor);  
        if (ShowGraphs)
        {
    		int MaxArtrosPopEver = Uts.FindMax(SpeciesMaxPop);
    		DP.DrawMenu(new int[] {CanvasPos[0] - 225, CanvasPos[1] + 320}, "Center", 330, 480, 2, new Color[] {ColorPalette[25], ColorPalette[7]}, ColorPalette[9]);
    		for (int c = 0; c <= ArtroChoice[0].length - 1; c += 1)
    		{
        		V.DrawVarGraph(new int[] {CanvasPos[0] - 350 + 150 * (int) (c / 3), CanvasPos[1] + 220 + 150 * (c % 3)}, "Tendência de " + ChoiceNames[c], SpeciesChoicesHist[c], 1, ArtroColor);
    		}
    		DP.DrawMenu(new int[] {CanvasPos[0] + CanvasSize[0] + 100, CanvasPos[1] + 170}, "Center", 180, 180, 2, new Color[] {ColorPalette[25], ColorPalette[7]}, ColorPalette[9]);
    		for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
            {
    	        V.DrawVarGraph(new int[] {CanvasPos[0] + CanvasSize[0] + 50, CanvasPos[1] + 220}, "População", SpeciesPopHist, MaxArtrosPopEver, ArtroColor);
            }
    		//int MaxFoodAmountEver = Uts.FindMax(MaxFood);
    		/*for (int t = 0; t <= NumberOfFoodTypes - 1; t += 1)
            {
    	        V.DrawVarGraph(new int[] {CanvasPos[0] - 200,  CanvasPos[1] + 520}, "Comida", FoodHist, MaxFoodAmountEver, FoodColor);
            }*/
        }
        round += 1;
		repaint();
	}
	
	class Panel2 extends JPanel 
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Panel2() 
	    {
	        setPreferredSize(new Dimension(PanelSize[0], PanelSize[1]));	// set a preferred size for the custom panel.
	    }
	    @Override
	    public void paintComponent(Graphics g) 
	    {
	        super.paintComponent(g);
	        DP = new DrawingOnAPanel(g);
			V = new Visuals(DP, CanvasPos, CanvasSize, CanvasDim, DrawingPos);
	        RunProgram();
	    }
    }
	
	public void GetMousePos()
 	{
		MousePos = new int[] {MouseInfo.getPointerInfo().getLocation().x - 8, MouseInfo.getPointerInfo().getLocation().y - 54};
 	}
		
	private void initComponents() 
	{     
        jPanel2 = new Panel2();	// we want a custom Panel2, not a generic JPanel!
        jPanel2.setBackground(new Color(250, 240, 220));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel2.addMouseListener(new MouseAdapter() 
        {
			public void mousePressed(MouseEvent evt)
			{
				
			}
			public void mouseReleased(MouseEvent evt) 
			{
			    //mouseReleased(evt);
		    }
		});
        jPanel2.addMouseMotionListener(new MouseMotionAdapter() 
        {
            public void mouseDragged(MouseEvent evt) 
            {
                //mouseDragged(evt);
            }
        });
        jPanel2.addMouseWheelListener(new MouseWheelListener()
        {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) 
			{
				
			}       	
        });
        jPanel2.addKeyListener(new KeyListener()
        {
			@Override
			public void keyPressed(KeyEvent evt)
			{
				int key = evt.getKeyCode();
				System.out.println(1);
				if (key == KeyEvent.VK_ESCAPE)
				{
					
				}
			}

			@Override
			public void keyReleased(KeyEvent evt)
			{
				
			}

			@Override
			public void keyTyped(KeyEvent evt)
			{
				
			}        	
        });
        this.setContentPane(jPanel2);	// add the component to the frame to see it!
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
