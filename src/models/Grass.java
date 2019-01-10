package models;

public class Grass extends Entity
{
    public Grass(double x, double y)
    {
        super(x, y);
        grass = 0;
    }

    public void eatgrass(){
        grass--;

    }

    public final static int MAX_GRASS = 10;
    private int grass;

    public void setGrass(int grass) {
        this.grass = grass;
    }

    public static int getMaxGrass() {

        return MAX_GRASS;
    }

    public int getGrass() {
        return grass;
    }
}
