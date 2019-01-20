package models;

public class Grass extends Entity
{
    public Grass(double x, double y)
    {
        super(x, y);
        grass = 0;
    }

    public void eatGrass(){
        grass--;
        if (grass == 0) {
            this.setExists(false);
        }
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
