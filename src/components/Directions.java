package components;

import utilities.Util;

public enum Directions
{
	up, down, left, right;

	public static Directions getRandom()
	{
		return Directions.values()[Util.randomInt(0, Directions.values().length - 1)];
	}
}
