package models.animal;

import models.*;
import models.interfaces.Buyable;
import models.interfaces.Time;

public class DomesticAnimal extends Animal implements Buyable, Time {
    private static final int MAX_SATURATION_RATE = 20;
    private int saturationRate;
    private boolean isHungry;
    private Type type;

    public Type getType() {
        return type;
    }

    public DomesticAnimal(double x, double y, Map map, Type type) {
        super(x, y, Animal.Type.valueOf(type.toString()), map);
        saturationRate = MAX_SATURATION_RATE / 2;
        isHungry = true;
        this.type = type;
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
        map.addToMap(new Item(this.getCoordinates().getX(), this.getCoordinates().getY(), type.PRODUCT));
    }

    @Override
    public int getBuyCost() {
        return type.BUY_COST;
    }

    public enum Type {
        HEN(100, Item.Type.EGG, "hen"),
        SHEEP(1000, Item.Type.FABRIC, "sheep"),
        COW(10000, Item.Type.MILK, "cow");

        public final int BUY_COST;
        public final Item.Type PRODUCT;
        public final String NAME;

        Type(int BUY_COST, Item.Type item, String name) {
            this.BUY_COST = BUY_COST;
            this.PRODUCT = item;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return NAME.toUpperCase();
        }
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
            if (this.getCoordinates().collidesWith(entity.getCoordinates())){
                if (){

                }
            }
        }
        }

    }