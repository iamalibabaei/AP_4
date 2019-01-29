package models;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public abstract class Viewable
{
    protected Text text;//TODO shape will be imageView
    protected ImageView imageView;
    private St

    public String getName()
    {
        return getClass().getSimpleName().toLowerCase();
    }

}
