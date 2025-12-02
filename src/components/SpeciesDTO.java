package components;

import java.awt.Color;

public class SpeciesDTO
{
    private int maxLife;
    private int step;
    private int vision;
    private int matePoint;
    private int stomach;
    private int[] color;

    public int getMaxLife() { return maxLife ;}
    public int getStep() { return step ;}
    public int getVision() { return vision ;}
    public int getMatePoint() { return matePoint ;}
    public int getStomach() { return stomach ;}
    public Color getColor() { return new Color(color[0], color[1], color[2]) ;}
}
