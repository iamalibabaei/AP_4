package models.objects;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Grass extends Entity
{
    private final static int MAX_GRASS = 10;
    private int grass;

    public Grass(Point point)
    {
        super(point);
        grass = MAX_GRASS;
        text = new Text("grass");
    }

    public void eatGrass()
    {
        grass--;
    }

    public int getGrass()
    {
        return grass;
    }

}
