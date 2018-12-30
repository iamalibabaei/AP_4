package models.workshop;

import models.Item;
import models.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;
import models.exceptions.ItemNotInWarehouseException;
import models.interfaces.Time;
import models.interfaces.Upgradable;
import models.map.Cell;
import models.map.Map;

import java.util.HashMap;

public class
Workshop implements Upgradable, Time
{
    String name;
    private int x, y;//place off cell where it returns products
    private int level = 1, maxProductionNum , maxWorkingTime = 15,timeToReturnProduct = -1;
    private int productionNum;
    private Warehouse warehouse;
    private boolean isWorking = false;
    private Cell cell;//this is the cell where it puts the result items
    private final int buildCost;
    private final HashMap<Item.Type, Integer> inputs, outputs;


    public Workshop(int x, int y,int buildCost, HashMap<Item.Type, Integer> inputs,
                    HashMap<Item.Type, Integer> outputs, int maxProductionNum) {
        this.x = x;
        this.y = y;
        this.buildCost = buildCost;
        this.inputs = inputs;
        this.outputs = outputs;
        this.maxProductionNum = maxProductionNum;/* for game factors equals 1*/
    }

    //TODO name be constructor vasl she + json files

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

        int inputNumbers = warehouse.moveToWorkshop(inputs, maxProductionNum).get();
        if (inputNumbers == 0) {
            throw new ItemNotInWarehouseException();
        }
        isWorking = true;
        timeToReturnProduct = maxWorkingTime;
        productionNum = inputNumbers;
    }

    @Override
    public void nextTurn() {
        timeToReturnProduct--;
        if (timeToReturnProduct == 0) {
            isWorking = false;
            returnProduct();

        }
    }

    private void returnProduct()
    {
        for(Item.Type entry : outputs.keySet()) {
            for (int i = 0; i < productionNum; i++) {
                cell.addEntity(new Item(x, y, entry));
            }
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
        maxProductionNum ++;

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