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
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import Graphics.DrawingOnAPanel;
import Output.Results;
import Output.Visuals;

public class EvolutionOld extends JFrame
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container cp;
	int[] SuperFrameSize = new int[] {1250, 680};
	int[] PanelSize = new int[] {1250, 680};
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
    public int[] ArtroLife;					// [length = NumberOfArtros][current life, 0 < Life = alive]
    public double[][] ArtroPos;				// [length = NumberOfArtros][x, y]
    public int[] ArtroSpecies;				// [length = NumberOfArtros][species]
    public double[][] ArtroChoice;			// [length = NumberOfArtros][species][0: eat, 1: mate, 2: fight, 3: flee, 4: group, 5: wander]
    public boolean[] ArtroKeepChoice;		// [length = NumberOfArtros][keep choice]
    public int[] ArtroSatiation;			// [length = NumberOfArtros][satiation]
    public String[] ArtroWill;				// [length = NumberOfArtros][will]
    public int[] ArtroSexWish;				// [length = NumberOfArtros][sex wish]
    public int[] ArtroAge;					// [length = NumberOfArtros][age]
    public int[] ArtroDir;					// [length = NumberOfArtros][direction of movement]
    public int[] ArtroQuadrant;				// [length = NumberOfArtros][quadrant in which the artro is]

    /* Species */
    public double[][] ArtroInitialPos;		// [length = species][species][x, y]
    public double[][] ArtroDispersion;		// [length = species][species][dispersion in x, dispersion in y]
    public int[] SpeciesLife;				// [length = species][Amount of life]
    public int[] ArtroSize;					// [length = species][size]
    public int[] ArtroStep;					// [length = species][step]
    public int[] ArtroStomach;				// [length = species][stomach size]
    public int[] ArtroVision;				// [length = species][vision range]
    public double[][] SpeciesChoices;		// [length = species][0: eat, 1: mate, 2: fight, 3: flee, 4: group, 5: wander]
    public int[] ArtroSexApp;				// [length = species][sex appetite]
    public int[] ArtroSexAge;				// [length = species][sexual age]
    public int[] ArtroMaxAge;				// [length = species][maximum age]
    public int[] ArtroFoodValue;			// [length = species][food value]
    public double[] ArtroMut;				// [length = species][chance of mutation]
    public Color[] ArtroColor;				// [length = species][color]

    /* Records */
    public int[] SpeciesMaxPop;				// [length = species][maximum population reached by each species]
    public double[][][] SpeciesChoicesHist;	// history of the chance of choices [choice][round][species]
    public int[][] SpeciesPopHist;			// history of the population with time [round][species]
    	
	/* Food */
    public int NumberOfFood;  
	public int NumberOfFoodTypes;                           
    public boolean[] FoodStatus;			// [true = exists]
    public int[] FoodType;					// [food type]
    public double[][] FoodPos;				// [x, y]
	public int[] FoodRespawn;				// [respawn]
    
    public double[] FoodSize;				// [food size]
    public double[][] FoodInitialPos;		// [x, y]
    public double[][] FoodDispersion;		// [dispersion in x, dispersion in y]
    public int[] FoodValue;					// [value]
	public int[] FoodRespawnRate;			// [respawn rate]
    public Color[] FoodColor;				// [color]
    public int[][] FoodHist;				// [history of the population with time]
    public int[] MaxFood;					// [maximum food amount ever reached by each food type]
	
	/* Global variables */
	int round = 0, delay = 10;
	Color[] ColorPalette;
	String[] ChoiceNames = new String[] {"comer", "reproduzir", "ca�ar", "fugir", "agrupar", "passear"};
	boolean ShowGraphs = true, ShowLegend, ShowCanvas, ProgramIsRunning;
	int[][] ArtrosInQuadrant;			// list of the artros that are in each quadrant
	int[][] FoodInQuadrant;				// list of the food that are in each quadrant
	int NumberOfQuadrants = 25;
    String view;
	String LegendTitle;
	double LegendMin, LegendMax;
	
	public EvolutionOld()
	{
	    ColorPalette = UtilS.ColorPalette(2);
		initComponents();	// Creates a JPanel inside the JFrame
		Language = "Pt-br";
		AllText = UtilS.ReadTextFile(Language);
		LegendMin = 0;
		LegendMax = 0;
		
		/* Defining Container */
		cp = getContentPane();
		//cp.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

		//Inicializacao();
		AddButtons();
		AddMenus();
		AddMenuActions(); 
		setTitle("Jarvis");								// Super frame sets its title
		setSize(SuperFrameSize[0], SuperFrameSize[1]);	// Super frame sets its initial window size
		setVisible(true);								// Super frame shows
	}

	/*public void Inicializacao()
	{
		int NumArtroPar = 15;
		Object[] object = UtilS.ReadInputFile("input.txt");
		delay = Integer.parseInt(String.valueOf(object[0]));
		NumberOfArtros = Integer.parseInt(String.valueOf(object[1]));
		ArtroInitialPos = UtilS.ObjectToDoubleArray(object[2]);
		ArtroDispersion = UtilS.ObjectToDoubleArray(object[3]);
	    SpeciesLife = UtilS.ObjectToIntVec(object[4]);	   
	    ArtroSize = UtilS.ObjectToIntVec(object[5]);	   
	    ArtroStep = UtilS.ObjectToIntVec(object[6]);
	    ArtroStomach = UtilS.ObjectToIntVec(object[7]);
	    ArtroVision = UtilS.ObjectToIntVec(object[8]);
	    SpeciesChoices = UtilS.ObjectToDoubleArray(object[9]);
	    ArtroSexApp = UtilS.ObjectToIntVec(object[10]);
	    ArtroSexAge = UtilS.ObjectToIntVec(object[11]);
	    ArtroMaxAge = UtilS.ObjectToIntVec(object[12]);
	    ArtroFoodValue = UtilS.ObjectToIntVec(object[13]);
	    ArtroMut = UtilS.ObjectToDoubleVec(object[14]);
	    ArtroColor = UtilS.ObjectToColorVec(object[15]);
	    NumberOfSpecies = ArtroSize.length;    
	    SpeciesMaxPop = new int[NumberOfSpecies];                  			// [maximum population of all times]
		ArtroLife = new int[NumberOfArtros];                				// [true = alive]
	    ArtroPos = new double[NumberOfArtros][2];              				// [x, y]
	    ArtroSpecies = new int[NumberOfArtros];                     		// [species]
	    ArtroChoice = new double[NumberOfArtros][SpeciesChoices[0].length];	// [chance to follow food][chance to reproduce][chance to wander][chance to fight]
	    ArtroKeepChoice = new boolean[NumberOfArtros];						// keep the current choice while the conditions last
	    ArtroSatiation = new int[NumberOfArtros];							// [satiation]
	    ArtroWill = new String[NumberOfArtros];								// [will]
	    ArtroSexWish = new int[NumberOfArtros];								// [sex wish]
	    ArtroAge = new int[NumberOfArtros];									// [age]
	    ArtroDir = new int[NumberOfArtros];									// [direction of movement]
	    ArtroQuadrant = new int[NumberOfArtros];							// [quadrant the artro is in]
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {	
			int species = a % NumberOfSpecies;
			double[] Pos = UtilS.RandomPosAroundPoint(new double[] {SpaceLimits[0][0] + ArtroInitialPos[species][0]*SpaceSize[0], SpaceLimits[0][0] + ArtroInitialPos[species][1]*SpaceSize[0]}, new double[] {ArtroDispersion[species][0]*SpaceSize[0], ArtroDispersion[species][1]*SpaceSize[1]});	
			int SexWish = (int) (ArtroSexApp[species]*(0.5 + 0.5*Math.random()));
			int Satiation = (int) (ArtroStomach[species]*(0.5 + 0.5*Math.random()));
			double[] Choices = new double[ArtroChoice[species].length];
			int dir = (int) (4*Math.random());
			for (int i = 0; i <= SpeciesChoices[species].length - 1; i += 1)
			{
				double var = (1.0 + UtilS.Random(0.0))*SpeciesChoices[species][i];
				Choices[i] = var;
			}
			int Age = (int) (Math.random()*ArtroMaxAge[species]);
			int Quadrant = FindQuadrant(Pos);
	        CreateArtro(a, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", SexWish, ArtroSexApp[species], Satiation, ArtroStomach[species], Age, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species], Quadrant);
	    }

	    NumberOfFood = Integer.parseInt(String.valueOf(object[NumArtroPar + 1]));
		FoodInitialPos = UtilS.ObjectToDoubleArray(object[NumArtroPar + 2]);
		FoodDispersion = UtilS.ObjectToDoubleArray(object[NumArtroPar + 3]);
	    FoodValue = UtilS.ObjectToIntVec(object[NumArtroPar + 4]);
	    FoodSize = UtilS.ObjectToDoubleVec(object[NumArtroPar + 5]);
	    FoodRespawnRate = UtilS.ObjectToIntVec(object[NumArtroPar + 6]);
	    FoodColor = UtilS.ObjectToColorVec(object[NumArtroPar + 7]);
	    NumberOfFoodTypes = FoodValue.length;    
	    FoodRespawn = new int[NumberOfFood];
		FoodStatus = new boolean[NumberOfFood];                 // [true = exists]
		FoodType = new int[NumberOfFood];                     		// [food type]
		FoodPos = new double[NumberOfFood][2];                 // [x, y]
		MaxFood = new int[NumberOfFoodTypes];                 		// [maximum population of all times]

		ArtrosInQuadrant = new int[NumberOfQuadrants][];
		FoodInQuadrant = new int[NumberOfQuadrants][];
		
		SpeciesChoicesHist = new double[SpeciesChoices[0].length][][];
	    for (int f = 0; f <= NumberOfFood - 1; f += 1)
	    {
	    	int type = (int) (Math.random()*NumberOfFoodTypes);
	    	double[] Pos = UtilS.RandomPosAroundPoint(new double[] {SpaceLimits[0][0] + FoodInitialPos[type][0]*SpaceSize[0], SpaceLimits[0][0] + FoodInitialPos[type][1]*SpaceSize[0]}, new double[] {FoodDispersion[type][0]*SpaceSize[0], FoodDispersion[type][1]*SpaceSize[1]});
			Pos = new double[] {SpaceLimits[0][0] + Math.random()*SpaceSize[0], SpaceLimits[0][0] + Math.random()*SpaceSize[0]};
	    	CreateFood(f, true, Pos, type, FoodValue[type], FoodRespawn[f]);
	    }
    	
	    for (int q = 0; q <= NumberOfQuadrants - 1; q += 1)
    	{
	    	for (int a = 0; a <= NumberOfArtros - 1; a += 1)
    	    {
		    	if (q == ArtroQuadrant[a])
				{
    	    		ArtrosInQuadrant[q] = UtilS.AddElemToVec(ArtrosInQuadrant[q], a);
				}
    	    }
    	}
    	for (int q = 0; q <= NumberOfQuadrants - 1; q += 1)
    	{
    		for (int f = 0; f <= NumberOfFood - 1; f += 1)
    	    {
    			if (q == FindQuadrant(FoodPos[f]))
    			{
    	    		FoodInQuadrant[q] = UtilS.AddElemToVec(FoodInQuadrant[q], f);
    			}
    	    }
    	}
	    ShowCanvas = true;
	    ProgramIsRunning = true;
	}*/
	
	public void CreateArtro(int a, int Life, double[] Pos, int species, int size, int step, int vision, double[] choice, String will, int sexwish, int sexapp, int satiation, int stomach, int age, int sexage, int foodvalue, int dir, double mut, int quad)
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
        ArtroQuadrant[a] = quad;
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
	    if (ArtroSexWish[a] == ArtroSexApp[ArtroSpecies[a]] & ArtroSexAge[ArtroSpecies[a]] <= ArtroAge[a])
	    {
	        return true;
	    }
	    return false;
	}
	
	/*public int ClosestFood(double[] artroPos, int artroQuad, double artroVision)
	{
	    int closestFood = -1;
	    double MinFoodDist = UtilS.dist(artroPos, FoodPos[0]);
	    if (FoodInQuadrant[artroQuad] != null)
	    {
		    for (int f = 0; f <= FoodInQuadrant[artroQuad].length - 1; f += 1)
	        {
		    	int foodid = FoodInQuadrant[artroQuad][f];
		    	double FoodDist = UtilS.dist(artroPos, FoodPos[foodid]);
	            if (FoodStatus[foodid] & FoodDist < MinFoodDist & FoodDist <= artroVision)
	            {
	                closestFood = foodid;
	                MinFoodDist = FoodDist;
	            }
	        }
	    }
        return closestFood;
	}*/
	
	/*public int ClosestMate(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int closestMate = -1;
	    double MinMateDist = artroVision;
	    if (ArtrosInQuadrant[ArtroQuadrant[artroID]] != null)
	    {
		    for (int a = 0; a <= ArtrosInQuadrant[ArtroQuadrant[artroID]].length - 1; a += 1)
	        {
		    	int mateid = ArtrosInQuadrant[ArtroQuadrant[artroID]][a];
		    	double MateDist = UtilS.dist(artroPos, ArtroPos[mateid]);
	            if (mateid != artroID & ArtroSpecies[mateid] == artroSpecies & MateDist < MinMateDist & MateDist <= artroVision)
	            {
	                closestMate = mateid;
	            }
	        }
	    }
        return closestMate;
	}*/
	
	/*public int ClosestOpponent(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int closestOpponent = -1;
	    double MinFightDist = artroVision;
	    if (ArtrosInQuadrant[ArtroQuadrant[artroID]] != null)
	    {
		    for (int a = 0; a <= ArtrosInQuadrant[ArtroQuadrant[artroID]].length - 1; a += 1)
	        {
		    	int opponentid = ArtrosInQuadrant[ArtroQuadrant[artroID]][a];
		    	double opponentDist = UtilS.dist(artroPos, ArtroPos[opponentid]);
	            if (opponentid != artroID & ArtroSpecies[opponentid] == artroSpecies & opponentDist < MinFightDist & opponentDist <= artroVision)
	            {
	                closestOpponent = opponentid;
	            }
	        }
	    }
        return closestOpponent;
	}*/

	/*public int NumberOfCloseFriends(int artroID, int artroSpecies, double[] artroPos, double artroVision)
	{
	    int NumCloseFriends = 0;
	    if (ArtrosInQuadrant[ArtroQuadrant[artroID]] != null)
	    {
		    for (int a = 0; a <= ArtrosInQuadrant[ArtroQuadrant[artroID]].length - 1; a += 1)
	        {
		    	int friendid = ArtrosInQuadrant[ArtroQuadrant[artroID]][a];
		    	if (friendid != artroID & ArtroSpecies[friendid] == artroSpecies & UtilS.dist(artroPos, ArtroPos[friendid]) <= artroVision)
	            {
		    		NumCloseFriends += 1 ;
	            }	    	
	        }
	    }
        return NumCloseFriends;
	}*/
	
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
	
	/*public double GroupFightBonus(int a)
	{
		double bonus = 1;
		bonus += (double)NumberOfCloseFriends(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]);
		return bonus;
	}*/
	
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
				acceptedmoves = UtilS.AddElemToVec(acceptedmoves, 0);
			}
			if (SpaceLimits[0][0] <= ArtroPos[a][0] - ArtroStep[ArtroSpecies[a]])
			{
				acceptedmoves = UtilS.AddElemToVec(acceptedmoves, 1);
			}
			if (ArtroPos[a][1] + ArtroStep[ArtroSpecies[a]] <= SpaceLimits[0][0] + SpaceLimits[1][0])
			{
				acceptedmoves = UtilS.AddElemToVec(acceptedmoves, 2);
			}
			if (SpaceLimits[0][0] <= ArtroPos[a][1] - ArtroStep[ArtroSpecies[a]])
			{
				acceptedmoves = UtilS.AddElemToVec(acceptedmoves, 3);
			}
			if (!UtilS.ArrayContains(acceptedmoves, ArtroDir[a]))
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
	        
	        if (Math.random() <= 0.01)
	        {
            	ArtroDir[a] = (int) (4*Math.random());
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

	
	/*public int FindQuadrant(double[] pos)
	{
		int NumberOfXQuadrants = (int) Math.sqrt(NumberOfQuadrants) ;
		double[][] QuadrantTopLeft = new double[NumberOfQuadrants][2] ;
		double QuadrantSize =  CanvasSize[0] / NumberOfXQuadrants ;
		
		for (int qx = 0; qx <= NumberOfXQuadrants - 1; qx += 1)
		{
			for (int qy = 0; qy <= NumberOfXQuadrants - 1; qy += 1)
			{
				QuadrantTopLeft[qx * NumberOfXQuadrants + qy] = new double[] {qx * QuadrantSize, qy * QuadrantSize} ;
			}
		}
		
		for (int q = 0; q <= NumberOfQuadrants - 1; q += 1)
		{
			if (UtilS.PosIsInRect(pos, QuadrantTopLeft[q], QuadrantSize, QuadrantSize))
			{
				return q ;
			}
		}
		
		return -1 ;
	}*/
	
	/*public int FindFoodInRange(int a)
	{
		if (FoodInQuadrant[ArtroQuadrant[a]] != null)
		{
			for (int i = 0; i <= FoodInQuadrant[ArtroQuadrant[a]].length - 1; i += 1)
			{
				int f = FoodInQuadrant[ArtroQuadrant[a]][i];
				if (FoodStatus[f] & UtilS.dist(ArtroPos[a], FoodPos[f]) <= (ArtroSize[ArtroSpecies[a]] + FoodSize[FoodType[f]])/2.0)
				{
					return f ;
				}
			}
		}
		return -1 ;
	}*/
	
	/*public int[] FindFoodInVision(int a)
	{
		int[] FoodInRange = null ;
		for (int f = 0; f <= NumberOfFood - 1; f += 1)
		{
			if (FoodStatus[f] & UtilS.dist(ArtroPos[a], FoodPos[f]) <= ArtroVision[a])
			{
				FoodInRange = UtilS.AddElemToVec(FoodInRange, f) ;
			}
		}
		
		return FoodInRange ;
	}*/
	
	/*public void UpdateArtrosQuadrant()
	{
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
			int newQuadrant = FindQuadrant(ArtroPos[a]) ;
			if (ArtroQuadrant[a] != newQuadrant)
			{
	    		int artroid = UtilS.FirstIndex(ArtrosInQuadrant[ArtroQuadrant[a]], a) ;
                ArtrosInQuadrant[ArtroQuadrant[a]] = UtilS.RemoveElemFromArray(artroid, ArtrosInQuadrant[ArtroQuadrant[a]]) ;
				ArtroQuadrant[a] = newQuadrant ;
                ArtrosInQuadrant[newQuadrant] = UtilS.AddElemToVec(ArtrosInQuadrant[newQuadrant], a) ;				
			}
	    }
	}*/
	
	public boolean ArtroIsHungry(int a)
	{
		if (ArtroSatiation[a] < 0.8*ArtroStomach[ArtroSpecies[a]])
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	/* Artos actions */
	/*public void ArtrosAct()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	        if (0 < ArtroLife[a])
	        {
	            if (ArtroWill[a].equals("eat"))                  // Move towards food
	            {
	                int closestFood = ClosestFood(ArtroPos[a], ArtroQuadrant[a], ArtroVision[ArtroSpecies[a]]);
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
	}*/

	
	/*public void ArtrosEat()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	    	if (ArtroWill[a].equals("eat"))
    		{
	    		int f = FindFoodInRange(a);
	    		if (-1 < f)
	    		{
		    		FoodStatus[f] = false;
		    		int quadrant = FindQuadrant(FoodPos[f]) ;
		    		int foodid = UtilS.FirstIndex(FoodInQuadrant[quadrant], f) ;
	                FoodInQuadrant[quadrant] = UtilS.RemoveElemFromArray(foodid, FoodInQuadrant[quadrant]) ;
				    ArtroSatiation[a] += FoodValue[FoodType[f]];
				    if (ArtroStomach[ArtroSpecies[a]] < ArtroSatiation[a])
				    {
				    	ArtroSatiation[a] = ArtroStomach[ArtroSpecies[a]];
				    }
	    		}
    		}
	    }
	}*/
	

	/*public void ArtrosMate2()
	{
	    for (int a1 = 0; a1 <= NumberOfArtros - 1; a1 += 1)
	    {
	       if (ArtroIsAbleToMate(a1))
	       {
                for (int a2 = 0; a2 <= NumberOfArtros - 1; a2 += 1)
                {
                	if (ArtroIsAbleToMate(a2))
                	{
                        if (a1 != a2 & ArtroSpecies[a1] == ArtroSpecies[a2] & UtilS.dist(ArtroPos[a1], ArtroPos[a2]) <= (ArtroSize[ArtroSpecies[a1]] + ArtroSize[ArtroSpecies[a2]])/2.0)
    	                {
                        	// determine new artro characteristics
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
                    			if (UtilS.chance(ArtroMut[species]) & c == 2)
                    			{
                    				Choices[c] = Math.min(1, Math.max(0, (1.0 + UtilS.Random(2))));
                    			}
                			}
                			int InitialAge = 0;
                			
                        	if (UtilS.ArrayContains(ArtroLife, 0))	// If there is a dead artro, new artro is born in that place
                        	{
                        		int a12 = UtilS.FirstIndex(ArtroLife, 0);
                                CreateArtro(a12, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", InitialSexWish, ArtroSexApp[species], InitialSatiation, ArtroStomach[species], InitialAge, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species], FindQuadrant(Pos));
                        	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        	}
                        	else	// new artro is created increasing the artro vector
                        	{
                        	    ArtroLife = UtilS.IncreaseVecSize(ArtroLife);
                                ArtroPos = UtilS.IncreaseArraySize(ArtroPos);
                                ArtroSpecies = UtilS.IncreaseVecSize(ArtroSpecies);
                                ArtroSize = UtilS.IncreaseVecSize(ArtroSize);
                                ArtroStep = UtilS.IncreaseVecSize(ArtroStep);
                                ArtroSatiation = UtilS.IncreaseVecSize(ArtroSatiation);
                                ArtroStomach = UtilS.IncreaseVecSize(ArtroStomach);
                                ArtroChoice = UtilS.IncreaseArraySize(ArtroChoice);
                                ArtroKeepChoice = UtilS.IncreaseVecSize(ArtroKeepChoice);
                                ArtroWill = UtilS.IncreaseVecSize(ArtroWill);
                                ArtroSexWish = UtilS.IncreaseVecSize(ArtroSexWish);
                                ArtroSexApp = UtilS.IncreaseVecSize(ArtroSexApp);
                                ArtroAge = UtilS.IncreaseVecSize(ArtroAge);
                                ArtroSexAge = UtilS.IncreaseVecSize(ArtroSexAge);
                                ArtroDir = UtilS.IncreaseVecSize(ArtroDir);
                                ArtroQuadrant = UtilS.IncreaseVecSize(ArtroQuadrant);
                                NumberOfArtros += 1;
                                CreateArtro(NumberOfArtros - 1, SpeciesLife[species], Pos, species, ArtroSize[species], ArtroStep[species], ArtroVision[species], Choices, "mate", InitialSexWish, ArtroSexApp[species], InitialSatiation, ArtroStomach[species], InitialAge, ArtroSexAge[species], ArtroFoodValue[species], dir, ArtroMut[species], FindQuadrant(Pos));
                                ArtrosInQuadrant[ArtroQuadrant[NumberOfArtros - 1]] = UtilS.AddElemToVec(ArtrosInQuadrant[ArtroQuadrant[NumberOfArtros - 1]], NumberOfArtros - 1) ;	
                            }
                            ArtroSexWish[a1] = 0;
                    	    ArtroSexWish[a2] = 0;            	
    	                }
                	}
                }
	       }
	    }
	}*/
	
	
	/*public void ArtrosFight()
	{
		for (int a1 = 0; a1 <= NumberOfArtros - 1; a1 += 1)
		{
			if (ArtroWill[a1].equals("fight"))
			{
				for (int a2 = 0; a2 <= NumberOfArtros - 1; a2 += 1)
				{
					if (a1 != a2 & ArtroSpecies[a1] != ArtroSpecies[a2] & UtilS.dist(ArtroPos[a1], ArtroPos[a2]) <= (ArtroSize[ArtroSpecies[a1]] + ArtroSize[ArtroSpecies[a2]])/2.0)
			        {
			        	if (ArtroLife[a2] < ArtroLife[a1])
			        	{
				        	ArtroSatiation[a1] += ArtroFoodValue[ArtroSpecies[a2]];
				        	ArtroLife[a1] += -ArtroLife[a2];
				            if (ArtroStomach[ArtroSpecies[a1]] < ArtroSatiation[a1])
				            {
				            	ArtroSatiation[a1] = ArtroStomach[ArtroSpecies[a1]];
				            }
				            ArtroDies(a2);
			        	}
			        	else
			        	{
			        		ArtroLife[a2] += -ArtroLife[a1];
			        		ArtroDies(a1);
			        	}
			        }
				}
			}
	    }
	}*/
	
	
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
	    	ArtroSatiation[a] += -1;
	        if (ArtroSatiation[a] <= 0)
	        {
	        	ArtroDies(a);
	        }
	    }
	}

	/*public void ArtrosThink()
	{
		for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
			int closestOpponent = ClosestOpponent(a, ArtroSpecies[a], ArtroPos[a], ArtroVision[ArtroSpecies[a]]) ;
			
			if (ArtroWill[a].equals("fight") & closestOpponent == -1)
            {
	            ArtroKeepChoice[a] = true;
            }
            if (ArtroWill[a].equals("flee") & closestOpponent == -1)
            {
	            ArtroKeepChoice[a] = true;
            }
            
			if ((ArtroKeepChoice[a] & UtilS.chance(1 - ArtroChoice[a][2] * GroupFightBonus(a))) & -1 < closestOpponent)		// Artro decides to flee
	        {
				ArtroWill[a] = "flee";
	        }
	       	else if ((!ArtroKeepChoice[a] & UtilS.chance(ArtroChoice[a][0])) & ArtroIsHungry(a) & closestOpponent == -1)		// Artro decides to eat food
	        {
	            ArtroWill[a] = "eat";
	            ArtroKeepChoice[a] = false;
	        }
	        else if ((!ArtroKeepChoice[a] & UtilS.chance(ArtroChoice[a][1])) & ArtroIsAbleToMate(a))							// Artro decides to mate (bang)
	        {
	            ArtroWill[a] = "mate";
	            ArtroKeepChoice[a] = false;
	        }
	        else if ((ArtroKeepChoice[a] & UtilS.chance(ArtroChoice[a][2] * GroupFightBonus(a))) & -1 < closestOpponent)
	        {
	            ArtroWill[a] = "fight";                                             										// Artro decides to fight
	        }
	        else if ((!ArtroKeepChoice[a] & UtilS.chance(ArtroChoice[a][4])))
	        {
	            ArtroWill[a] = "group";                                             										// Artro decides to group
	            ArtroKeepChoice[a] = false;
	        }
	        else if ((!ArtroKeepChoice[a] & UtilS.chance(ArtroChoice[a][5])))
	        {
	            ArtroWill[a] = "wander";                                             										// Artro decides to wander
	            ArtroKeepChoice[a] = false;
	        }
	        else
	        {
	            ArtroWill[a] = "exist";                                             										// Artro decides to just exist (do nothing)
	            ArtroKeepChoice[a] = false;
	        }	
			//if (!ArtroWill[a].equals("wander") & !ArtroWill[a].equals("exist") & ((ArtroWill[a].equals("eat") & -1 < ClosestFood(ArtroPos[a], ArtroVision[ArtroSpecies[a]])) | (ArtroWill[a].equals("fight") & -1 < ClosestOpponent) | ArtroWill[a].equals("flee")))
			//{
			//	ArtroDir[a] = (int) (4*Math.random());
			//}
			//ArtroDir[a] = (int) (4*Math.random());
	    }
	}*/
	
	public void ArtrosAge()
	{
	    for (int a = 0; a <= NumberOfArtros - 1; a += 1)
	    {
	        ArtroAge[a] += 1;
	        if (ArtroMaxAge[ArtroSpecies[a]] < ArtroAge[a])
	        {
	        	ArtroDies(a);
	        }
	    }
	}
	
	
	public void ArtroDies(int id)
	{		
		int artroid = UtilS.FirstIndex(ArtrosInQuadrant[ArtroQuadrant[id]], id) ;
        ArtrosInQuadrant[ArtroQuadrant[id]] = UtilS.RemoveElemFromArray(artroid, ArtrosInQuadrant[ArtroQuadrant[id]]) ;
        for (int q = 0; q <= ArtrosInQuadrant.length - 1; q += 1)
        {
        	if (ArtrosInQuadrant[q] != null)
        	{
        		for (int qa = 0; qa <= ArtrosInQuadrant[q].length - 1; qa += 1)
        		{
        			if (id <= ArtrosInQuadrant[q][qa])
        			{
        				ArtrosInQuadrant[q][qa] -= 1 ;
        			}
        		}
        	}
        }
        
		ArtroLife = UtilS.RemoveElemFromArray(id, ArtroLife) ;
		ArtroPos = UtilS.RemoveElemFromArray(id, ArtroPos) ;
		ArtroSpecies = UtilS.RemoveElemFromArray(id, ArtroSpecies) ;
		ArtroChoice = UtilS.RemoveElemFromArray(id, ArtroChoice) ;
		ArtroKeepChoice = UtilS.RemoveElemFromArray(id, ArtroKeepChoice) ;
		ArtroSatiation = UtilS.RemoveElemFromArray(id, ArtroSatiation) ;
		ArtroWill = UtilS.RemoveElemFromArray(id, ArtroWill) ;
		ArtroSexWish = UtilS.RemoveElemFromArray(id, ArtroSexWish) ;
		ArtroAge = UtilS.RemoveElemFromArray(id, ArtroAge) ;
		ArtroDir = UtilS.RemoveElemFromArray(id, ArtroDir) ;
		ArtroQuadrant = UtilS.RemoveElemFromArray(id, ArtroQuadrant) ;
		
		NumberOfArtros -= 1;
	}

	/* Food actions */
	
	/*public void RespawnFood()
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
	                int quadrant = FindQuadrant(FoodPos[f]) ;
	                FoodInQuadrant[quadrant] = UtilS.AddElemToVec(FoodInQuadrant[quadrant], f) ;
	            }
	            else
	            {
	                FoodRespawn[f] += -1;
	            }
	        }
	    }
	}*/

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
		JButton PlayButton = UtilS.AddButton(PlayIcon, new int[2], new int[] {30, 30}, BackgroundColor);
		JButton GraphsButton = UtilS.AddButton(GraphsIcon, new int[2], new int[] {30, 30}, BackgroundColor);
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
	
		PlayButton = UtilS.AddButton("", new int[2], new int[] {110, 30}, BackgroundColor);
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
		SpeciesPopHist = UtilS.AddElemToArrayUpTo(SpeciesPopHist, ArtrosPop, maxlength);	
		double[][] ChoicesHist = new double[SpeciesChoices[0].length][NumberOfSpecies];
		for (int c = 0; c <= SpeciesChoices[0].length - 1; c += 1)
		{
			for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
	        {
				ChoicesHist[c][s] = SpeciesAvrPar(ArtroChoice, c)[s];
	        }
			SpeciesChoicesHist[c] = UtilS.AddElemToArrayUpTo(SpeciesChoicesHist[c], ChoicesHist[c], maxlength);
		}
		int[] FoodPop = CountNumOfAlive("Food");
		for (int t = 0; t <= NumberOfFoodTypes - 1; t += 1)
        {
			if (MaxFood[0] < FoodPop[t])
			{
				MaxFood[0] = FoodPop[t];
			}
        }
		FoodHist = UtilS.AddElemToArrayUpTo(FoodHist, FoodPop, maxlength);
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
				long startTime = System.nanoTime();
				/*ArtrosThink();
				ArtrosAct();
				UpdateArtrosQuadrant();
		        ArtrosEat();
		        ArtrosStarve();
		        ArtrosLust();
		        ArtrosMate2();
		        ArtrosFight();
		        ArtrosAge();
		        RespawnFood();*/
		        //System.out.println("elapsed time = " + (System.nanoTime() - startTime));
		        if (round % (10 * delay) == 0)
				{
			        Records(100);
				}
			}
		}
        V.DrawFood(FoodPos, FoodType, FoodStatus, FoodSize, FoodColor);
        V.DrawArtros(ArtroPos, ArtroWill, ArtroSpecies, ArtroLife, ArtroSize, ArtroColor);  
        
        
        /* Draw cool stuff */
		//DP.DrawCircle(Uts.ConvertToDrawingCoords(ArtroPos[0], CanvasPos, CanvasSize, CanvasDim), 2*ArtroVision[0], false, Color.red, null) ;
		/*int[] FoodInRange = FindFoodInVision(0) ;
		if (FoodInRange != null)
		{
			for (int i = 0; i <= FoodInRange.length - 1; i += 1)
			{
				DP.DrawCircle(Uts.ConvertToDrawingCoords(FoodPos[FoodInRange[i]], CanvasPos, CanvasSize, CanvasDim), (int) (FoodSize[FoodType[FoodInRange[i]]]), true, ColorPalette[4], Color.yellow);
			}
		}*/
		
		
        if (ShowGraphs)
        {
    		int MaxArtrosPopEver = UtilS.FindMax(SpeciesMaxPop);
    		DP.DrawMenu(new int[] {CanvasPos[0] - 225, CanvasPos[1] + 320}, "Center", 330, 480, 2, new Color[] {ColorPalette[25], ColorPalette[7]}, ColorPalette[9]);
    		for (int c = 0; c <= SpeciesChoices[0].length - 1; c += 1)
    		{
        		V.DrawVarGraph(new int[] {CanvasPos[0] - 350 + 150 * (int) (c / 3), CanvasPos[1] + 220 + 150 * (c % 3)}, "Tend�ncia de " + ChoiceNames[c], SpeciesChoicesHist[c], 1, ArtroColor);
    		}
    		DP.DrawMenu(new int[] {CanvasPos[0] + CanvasSize[0] + 100, CanvasPos[1] + 170}, "Center", 180, 180, 2, new Color[] {ColorPalette[25], ColorPalette[7]}, ColorPalette[9]);
    		for (int s = 0; s <= NumberOfSpecies - 1; s += 1)
            {
    	        V.DrawVarGraph(new int[] {CanvasPos[0] + CanvasSize[0] + 50, CanvasPos[1] + 220}, "Popula��o", SpeciesPopHist, MaxArtrosPopEver, ArtroColor);
            }
    		//int MaxFoodAmountEver = Uts.FindMax(MaxFood);
    		/*for (int t = 0; t <= NumberOfFoodTypes - 1; t += 1)
            {
    	        V.DrawVarGraph(new int[] {CanvasPos[0] - 200,  CanvasPos[1] + 520}, "Comida", FoodHist, MaxFoodAmountEver, FoodColor);
            }*/
        }
        //System.out.println(ArtroSatiation[0]);
        //System.out.println(Arrays.deepToString(ArtrosInQuadrant));
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
