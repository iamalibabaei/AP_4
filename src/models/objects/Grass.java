package models.objects;

public class Grass extends Entity
{
    public final static int MAX_GRASS = 10;
    private int grass;

    public Grass(Point point)
    {
        super(point);
        grass = 0;
    }

    public void eatGrass()
    {
        grass--;
    }

    public int getGrass()
    {
        return grass;
    }

    public void setGrass(int grass)
    {
        this.grass = grass;
    }

}
