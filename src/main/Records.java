package main;

import java.util.ArrayList;

public class Records
{
	public ArrayList<Integer> artrosPop ;	// number of artros at any given time
	public int maxArtroPopEver ;	// maximum number of artros that ever lived simultaneously 
	public ArrayList<Integer> artrosPopAtNRounds ;	// number of artros recorded every N rounds
	
	public Records()
	{
		artrosPop = new ArrayList<>() ;
		maxArtroPopEver = 0 ;
		artrosPopAtNRounds = new ArrayList<>() ;
	}
	
	public ArrayList<Double> ArrayListToDouble(ArrayList<Integer> originalArray)
	{
		return (ArrayList<Double>) originalArray.clone() ;
	}
	
	public void RecordArtrosPop(int currentPop)
	{
		int maxNumberRegisters = 1000 ;
		
		// update maximum number of artros ever registered
		if (maxArtroPopEver < currentPop)
		{
			maxArtroPopEver = currentPop ;
		}
		
		// update the latest registers to show in the graph
		if (artrosPop.size() <= maxNumberRegisters - 1)
		{
			artrosPop.add(currentPop) ;
		}
		else
		{
			artrosPop.remove(0) ;
			artrosPop.add(currentPop) ;
		}
	}
}
