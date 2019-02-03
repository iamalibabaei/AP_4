package models.objects.animals;

import javafx.scene.image.ImageView;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import view.utility.Utility;

public class DomesticAnimal extends Animal
{
    private static final int MAX_SATURATION_RATE = 20;
    private int saturationRate;
    private boolean isHungry;

    public DomesticAnimal(Point point, Animal.Type type)
    {
        super(point, type);
        saturationRate = MAX_SATURATION_RATE / 2;
        isHungry = true;
        updateImageView();
    }

    @Override
    protected void buildHashmap() {
        String str = null;
        if (type == Type.SHEEP) {
            str = "Sheep";
        } else if (type == Type.HEN) {
            str = "GuineaFowl";
        } else if (type == Type.COW) {
            str = "Cow";
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
        converter.put(stateKind.UP, Utility.getImageView("res/graphicAssets/Animals/Africa/"+ str +"/up.png"));
        converter.put(stateKind.LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/" + str+"/left.png"));
    }

    @Override
    public void collide(Entity entity)
    {
        if (isHungry && entity instanceof Grass)
        {
            ((Grass) entity).eatGrass();
            state = stateKind.EAT;
        }
    }

    @Override
    public void nextTurn()
    {
        if (saturationRate == 0)
        {
            die();
        }
        saturationRate--;
        if (!isHungry && saturationRate <= MAX_SATURATION_RATE / 2)
        {
            produce();
            isHungry = true;
        }
        setTarget();
        move();
    }

    private void produce()
    {
        map.addItem(new Item(coordinates, type.PRODUCT));
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (isHungry)
        {
            double dist = 1000;
            for (Grass grass : map.getGrasses())
            {
                double dist1 = coordinates.distanceFrom(grass.getCoordinates());
                if (dist1 < dist)
                {
                    dist = dist1;
                    target = grass.getCoordinates();
                }
            }
        }
    }

    @Override
    protected void loadAnimation()
    {

    }


}
