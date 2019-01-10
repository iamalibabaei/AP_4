package models.animal;

import models.Entity;
import models.Item;
import models.Map;
import models.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.interfaces.Buyable;
import models.interfaces.Upgradable;

import java.util.HashMap;

// todo cat upgrade

public class Cat extends Animal implements Buyable, Upgradable {
    private static final int BUY_COST = 2500, MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level;
    private Warehouse warehouse;

    public Cat(double x, double y, Animal.Type type, Map map, Warehouse warehouse) {
        super(x, y, type, map);
        this.warehouse = warehouse;
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
    public void collide(Entity entity) {
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
                map.addToMap(new Item(this.getCoordinates().getX(), this.getCoordinates().getY(), item));
            }
        }
    }

    @Override
    public int getBuyCost() {
        return BUY_COST;
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
