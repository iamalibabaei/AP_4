package models.objects.animals;

import javafx.scene.image.ImageView;
import models.objects.Entity;
import models.objects.Point;
import view.utility.Utility;

// todo dog upgrade

public class Dog extends Animal
{
    public Dog(Point point, Animal.Type type)
    {
        super(point, type);
        updateImageView();
    }

    @Override
    protected void buildHashmap() {
        converter.put(stateKind.DIE,  Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/down.png"));
        converter.put(stateKind.DOWN, Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/down.png"));
        converter.put(stateKind.DOWN_LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/down_left.png"));
        ImageView imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/down_left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.DOWN_RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/up_left.png");
        converter.put(stateKind.UP_LEFT, imageView);
        imageView.setScaleX(-1);
        converter.put(stateKind.UP_RIGHT, imageView);
        converter.put(stateKind.UP, Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/up.png"));
        converter.put(stateKind.LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/Dog/left.png"));
    }

    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof WildAnimal)
        {
            entity.die();
            entity.setState(stateKind.DIE);
        }
    }

    @Override
    public void setTarget()
    {
        target = null;
        for (Animal animal : map.getAnimals())
        {
            if (animal instanceof WildAnimal)
            {
                target = animal.getCoordinates();
                return;
            }
        }
    }

    @Override
    protected void loadAnimation()
    {

    }


}
