package components;

public class ArtroDTO
{
    private int[] center;
    private int[] range;
    private int amount;
    private int speciesID;
    private int minSatiation;
    private int[] tendencies;
    private int minSexWill;

    public int[] getCenter() { return center ;}
    public void setCenter(int[] center) { this.center = center ;}
    public int[] getRange() { return range ;}
    public void setRange(int[] range) { this.range = range ;}
    public int getAmount() { return amount ;}
    public void setAmount(int amount) { this.amount = amount ;}
    public int getSpeciesID() { return speciesID ;}
    public void setSpeciesID(int speciesID) { this.speciesID = speciesID ;}
    public int getMinSatiation() { return minSatiation ;}
    public void setMinSatiation(int minSatiation) { this.minSatiation = minSatiation ;}
    public int[] getTendencies() { return tendencies ;}
    public void setTendencies(int[] tendencies) { this.tendencies = tendencies ;}
    public int getMinSexWill() { return minSexWill ;}
    public void setMinSexWill(int minSexWill) { this.minSexWill = minSexWill ;}
}
