package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Records
{
	public static List<Integer> artrosPop; // number of artros at any given time
	public static int maxArtroPopEver; // maximum number of artros that ever lived simultaneously
	public static List<Integer> fpsList;
	public static int maxFPSEver;
	public static List<Integer> artrosPopAtNRounds; // number of artros recorded every N rounds
	public static final int saveEveryNRounds = 10;
	private static final int maxNumberRegisters = 1000;

	static
	{
		artrosPop = new ArrayList<>();
		fpsList = new ArrayList<>();
		maxArtroPopEver = 0;
		artrosPopAtNRounds = new ArrayList<>();
	}

	public static void updatePop(int currentPop)
	{
		updateMaxPopEver(currentPop);
		addIfNumberAllows(artrosPop, currentPop);
	}

	public static void updateFPS(int fps)
	{
		updateMaxFPSEver(fps);
		addIfNumberAllows(fpsList, fps);
	}

	private static void updateMaxFPSEver(int fps)
	{
		if (maxFPSEver < fps)
		{
			maxFPSEver = fps;
		}
	}

	private static void updateMaxPopEver(int currentPop)
	{
		if (maxArtroPopEver < currentPop)
		{
			maxArtroPopEver = currentPop;
		}
	}

	private static void addIfNumberAllows(List<Integer> record, int newRecord)
	{
		if (record.size() <= maxNumberRegisters - 1)
		{
			record.add(newRecord);
		} else
		{
			record.remove(0);
			record.add(newRecord);
		}
	}
}
