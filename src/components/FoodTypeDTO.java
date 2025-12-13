package components;

import java.awt.Color;

public class FoodTypeDTO
{
    private int size;
    private int value;
    private double spawnTime;
    private int[] color;

    public int getSize() { return size ;}
    public void setSize(int size) { this.size = size ;}
    public int getValue() { return value ;}
    public void setValue(int value) { this.value = value ;}
    public double getSpawnTime() { return spawnTime ;}
    public void setSpawnTime(double spawnTime) { this.spawnTime = spawnTime ;}
    public Color getColor() { return new Color(color[0], color[1], color[2]) ;}
    public void setColor(int[] color) { this.color = color ;}
}
