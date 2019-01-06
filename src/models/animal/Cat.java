package models.animal;

import models.Entity;
import models.Item;
import models.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.interfaces.Buyable;
import models.interfaces.Upgradable;
import models.map.Cell;
import models.map.Map;
import java.util.HashMap;

// todo cat upgrade

public class Cat extends Animal implements Buyable, Upgradable
{
    private static final int BUY_COST = 2500, MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level;
    private Warehouse warehouse;

    public Cat(int x, int y, Animal.Type type, Map map, Warehouse warehouse)
    {
        super(x, y, type, map);
        this.warehouse = warehouse;
    }

    @Override
    public void setTarget()
    {
        super.target = null;

        if (level == 0){
            for (Cell[] cells: map.getCells()) {
                for (Cell cell: cells) {
                    for (Entity entity : cell.getEntities()) {
                        if (entity instanceof Item){
                            target = entity.getPoint();
                            return;
                        }
                    }
                }
            }
        }
        else {
            int dist = 1000;
            for (Cell[] cells: map.getCells()) {
                for (Cell cell: cells) {
                    for (Entity entity : cell.getEntities()) {
                        if (entity instanceof Item){
                            int dist1 = Math.abs(this.getX() - target.getX()) + Math.abs(this.getY() - target.getY());
                            if (dist1 < dist) {
                                dist = dist1;
                                target = entity.getPoint();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void collide(Entity entity)
    {
        HashMap<Item.Type, Integer> items = new HashMap<>();

        for (Entity entity1 : map.getCell(this.getX(), this.getY()).getEntities()) {
            if (entity1 instanceof Item) {
                if (items.containsKey(((Item) entity1).getType())){
                    int num = items.get(((Item) entity1).getType()) + 1;
                    items.put(((Item) entity1).getType(), num);
                }
                else {
                    items.put(((Item) entity1).getType(), 1);
                }
                entity1.die();
            }
        }

        items = warehouse.store(items);

        for (Item.Type item : items.keySet()) {
            for (int i = 0; i < items.get(item); i++) {
                map.addToMap(new Item(this.getX(), this.getY(), item));
            }
        }
    }

    @Override
    public int getBuyCost()
    {
        return BUY_COST;
    }

    @Override
    public void upgrade() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        level++;
    }
    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException
    {
        if (level == MAX_LEVEL)
            throw new AlreadyAtMaxLevelException();
        return UPGRADE_COST;
    }

    @Override
    public void nextTurn() {
        super.nextTurn();
    }
}
