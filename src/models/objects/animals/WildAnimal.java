package models.objects.animals;

import javafx.scene.image.ImageView;
import models.objects.Entity;
import models.objects.Point;
import view.utility.Utility;

// todo upgrade cage

public class WildAnimal extends Animal
{
    private WildAnimal.State cagedStatus;

    public WildAnimal(Point point, Animal.Type type)
    {
        super(point, type);
        String str = null;
        if (type == Type.BEAR) {
            str = "Grizzly";
        } else if (type == Type.LION) {
            str = "Lion";
        }
        cagedStatus = WildAnimal.State.NOT_CAGED;
        updateImageView();
    }

    @Override
    protected void buildHashmap() {
        String str = null;
        if (type == Type.BEAR) {
            str = "Grizzly";
        } else if (type == Type.LION) {
            str = "Lion";
        }
        converter.put(stateKind.DIE,  Utility.getImageView("res/graphicAssets/Animals/Africa/" + str + "/death.png"));
        converter.put(stateKind.DOWN, Utility.getImageView("res/graphicAssets/Animals/Africa/" + str + "/down.png"));
        converter.put(stateKind.DOWN_LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/" + str + "/down_left.png"));
        ImageView imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/" + str + "/down_left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.DOWN_RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/"+str +"/left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/"+str + "/up_left.png");
        converter.put(stateKind.UP_LEFT, imageView);
        imageView.setScaleX(-1);
        converter.put(stateKind.UP_RIGHT, imageView);
        converter.put(stateKind.UP, Utility.getImageView("graphicAssets/Animals/Africa/"+ str +"/up.png"));
        converter.put(stateKind.LEFT, Utility.getImageView("graphicAssets/Animals/Africa/" + str+"/left.png"));
    }

    public WildAnimal.State getCagedStatus()
    {
        return cagedStatus;
    }

    public void cage()
    {
        if (cagedStatus != WildAnimal.State.CAGED)
        {
            cagedStatus = WildAnimal.State.values()[cagedStatus.ordinal() + 1];
        }
    }

    @Override
    public void collide(Entity entity)
    {
        if (cagedStatus != WildAnimal.State.CAGED && !(entity instanceof WildAnimal))
        {
            entity.die();
            entity.setState(stateKind.DIE);
        }
    }

    @Override
    public void move()
    {
        if (cagedStatus != WildAnimal.State.CAGED)
        {
            super.move();
        }
    }

    @Override
    protected void loadAnimation()
    {

    }



    @Override
    public void updateImageView() {
        String str = null;
        if (type == Type.BEAR) {
            str = "Grizzly";
        } else if (type == Type.LION) {
            str = "Lion";
        }
        if (cagedStatus == State.CAGED ) {
            imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/" + str + "/caged.png");
            return;
        }
        imageView = converter.get(state);
    }

    public enum State
    {
        NOT_CAGED, HARDLY_CAGED, AVERAGELY_CAGED, ALMOST_CAGED, CAGED
    }

}
