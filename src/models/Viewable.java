package models;


import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import view.utility.SpriteAnimation;

public abstract class Viewable
{
    protected ImageView imageView;
    protected Text text;//TODO shape will be spriteAnimation

    public stateKind getState() {
        return state;
    }

    public void setState(stateKind state) {
        this.state = state;
    }

    protected stateKind state;
    protected SpriteAnimation spriteAnimation;

    public enum stateKind {
        EAT, DIE, LEFT, RIGHT, UP, DOWN, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
    }
    protected abstract void loadAnimation();

    public ImageView getImageView(){

        return imageView;
    }

    public abstract void updateImageView();

    public String getName()
    {
        return getClass().getSimpleName().toLowerCase();
    }
}
