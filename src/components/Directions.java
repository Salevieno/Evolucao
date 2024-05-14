package components;

import libUtil.Util;

public enum Directions
{
	up,
	down,
	left,
	right ;
	
	public static Directions getRandom()
	{
		return Directions.values()[Util.randomIntFromTo(0, Directions.values().length - 1)] ;
	}
}
