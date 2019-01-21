package models.objects.animal;

import models.buildings.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Upgradable;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;

import java.util.HashMap;

// todo cat upgrade

public class Cat extends Animal implements Upgradable {
    private static final int MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level;
    private Warehouse warehouse;

    public Cat(Point point, Animal.Type type) {
        super(point, type);
        this.warehouse = Warehouse.getInstance();
    }

    @Override
    public void setTarget() {
        super.target = null;

        if (level == 0) {
            for (Item item : map.getItems()) {
                target.setX(item.getCoordinates().getX());
                target.setY(item.getCoordinates().getY());
                return;

            }
        } else {
            double dist = 1000;
            for (Item item : map.getItems()) {
                double dist1 = this.getCoordinates().distance(item.getCoordinates());
                if (dist1 < dist) {
                    dist = dist1;
                    target.setX(item.getCoordinates().getX());
                    target.setY(item.getCoordinates().getY());
                }
            }
        }
    }

    @Override
    public void collide(Entity entity) throws NotEnoughSpaceException {
        HashMap<Item.Type, Integer> items = new HashMap<>();

        for (Item item : map.getItems()) {
            if (items.containsKey(item)) {
                int num = items.get(item) + 1;
                items.put(item.getType(), num);
            } else {
                items.put(item.getType(), 1);
            }
            item.die();
        }

        items = warehouse.store(items);

        for (Item.Type item : items.keySet()) {
            for (int i = 0; i < items.get(item); i++) {
                map.addToMap(new Item(this.getCoordinates(), item));
            }
        }
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        level++;
    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        return UPGRADE_COST;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
    }
}
