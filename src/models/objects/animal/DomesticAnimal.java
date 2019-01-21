package models.objects.animal;

import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;

public class DomesticAnimal extends Animal implements Time {
    private static final int MAX_SATURATION_RATE = 20;
    private int saturationRate;
    private boolean isHungry;

    public Type getType() {
        return type;
    }

    public DomesticAnimal(Point point, Type type) {
        super(point, type);
        saturationRate = MAX_SATURATION_RATE / 2;
        isHungry = true;
    }


    @Override
    public void nextTurn() {
        if (saturationRate == 0) {
            this.die();
        }
        saturationRate--;
        if (!isHungry && saturationRate <= MAX_SATURATION_RATE / 2) {
            produce();
            isHungry = true;
        }
        super.nextTurn();
    }

    private void produce() {
        map.addToMap(new Item(this.getCoordinates(), type.PRODUCT));
    }

    @Override
    public void setTarget() {
        target = null;

        if (isHungry) {
            double dist = 1000;
            for (Grass grass : map.getGrasses()) {
                if (grass.getGrass() > 0) {
                    double dist1 = this.getCoordinates().distance(grass.getCoordinates());
                    if (dist1 < dist) {
                        dist = dist1;
                        target.setX(grass.getCoordinates().getX());
                        target.setY(grass.getCoordinates().getY());
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity) {
        if (isHungry) {
            if (entity instanceof Grass){
                ((Grass) entity).eatGrass();
            }
        }
    }

}