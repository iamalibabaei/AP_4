package models;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import view.utility.SpriteAnimation;

import java.util.HashMap;

public abstract class Viewable
{
    protected Text text;//TODO shape will be spriteAnimation
    protected HashMap<String, SpriteAnimation> states;
    protected String state;

    protected Viewable()
    {
        states = new HashMap<>();
    }

    public String getName()
    {
        return getClass().getSimpleName().toLowerCase();
    }

}
