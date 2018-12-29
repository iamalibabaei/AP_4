package models.workshop;

import models.Item;
import models.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;
import models.exceptions.ItemNotInWarehouseException;
import models.interfaces.Upgradable;
import models.map.Cell;
import models.map.Map;

import java.util.HashMap;

public class
Workshop implements Upgradable
{
    private int x, y;//place off cell where it returns products
    private int level = 1, maxProductionNum = 1, maxWorkingTime = 15,timeToReturnProduct = -1;
    private int productionNum;
    private Warehouse warehouse;
    private boolean isWorking = false;
    private Cell cell;//this is the cell where it puts the result items
    private final int buildCost;
    private final Item.Type input1, input2, output;


    public Workshop(int x, int y,int buildCost, Item.Type input1, Item.Type input2, Item.Type output) {
        this.x = x;
        this.y = y;
        this.buildCost = buildCost;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public void getMapAndWarehose(Map map, Warehouse warehouse) {
        this.cell = map.getCell(x, y);
        this.warehouse = warehouse;
    }


    public void goToWork() throws IsWorkingException, ItemNotInWarehouseException
    {
        if (!isWorking) {
            throw new IsWorkingException();
        }

        HashMap<Item.Type , Integer> base = new HashMap<>();
        base.put(input1, maxProductionNum);
        if (input2 != null) {
            base.put(input2, maxProductionNum);
        }

        int inputNumbers = warehouse.moveToWorkshop(base, 1).get();
        if (inputNumbers == 0) {
            throw new ItemNotInWarehouseException();
        }
        isWorking = true;
        timeToReturnProduct = maxWorkingTime;
        productionNum = inputNumbers;
    }

    public void turn()
    {
        timeToReturnProduct--;
        if (timeToReturnProduct == 0) {
            isWorking = false;
            returnProduct();

        }
    }

    private void returnProduct()
    {
        for (int i = 0; i < productionNum; i++) {
            cell.addEntity(new Item(cell.getX(), cell.getY(), output));
        }
    }

    @Override
    public void upgrade()
    {
        if (level == 5) {
            throw new AlreadyAtMaxLevelException();
        }
        level++;
        int[] maxWorkingTimeList = {15, 14, 13, 11,8};
        maxWorkingTime = maxWorkingTimeList[level - 1];
        maxProductionNum = level;

    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 4) {
            throw new AlreadyAtMaxLevelException();
        }
        int cost = buildCost;
        for(int i = 0; i < level; i++) {
            cost += 100;
        }
        return cost;
    }
}