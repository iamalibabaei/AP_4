package models.objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
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
        int x = 0;
        if (grass > 5) {
            x = 1;
        }
        ImageView imageView = Utility.getImageView("res/graphicAssets/Grass/grass1.png");
        imageView.setViewport(new Rectangle2D(x, 0, imageView.getImage().getWidth() / 4,
                imageView.getImage().getHeight() / 4));
        this.imageView = imageView;
    }

}
