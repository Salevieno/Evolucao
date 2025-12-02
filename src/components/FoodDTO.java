package components;

public class FoodDTO
{
    private int[] center;
    private int[] range;
    private int amount;
    private int typeID;

    public int[] getCenter() { return center ;}
    public void setCenter(int[] center) { this.center = center ;}
    public int[] getRange() { return range ;}
    public void setRange(int[] range) { this.range = range ;}
    public int getAmount() { return amount ;}
    public void setAmount(int amount) { this.amount = amount ;}
    public int getTypeID() { return typeID ;}
    public void setTypeID(int typeID) { this.typeID = typeID ;}
}
