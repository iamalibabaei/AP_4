package models.objects;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import view.utility.Utility;

public class Grass extends Entity
{
    private static final int MAX_GRASS = 10;
    private int grass;

    public Grass(Point point)
    {
        super(point);
        grass = MAX_GRASS;
        text = new Text("grass");
        updateImageView();
    }

    public void eatGrass()
    {
        grass--;
    }

    public int getGrass()
    {
        return grass;
    }

    @Override
    protected void loadAnimation()
    {

    }

    @Override
    public void updateImageView() {
        imageView = Utility.getImageView("res/graphicAssets/Grass/grass1.png");
    }

}
