package models.workshop;

import models.Item;
import models.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.IsWorkingException;
import models.exceptions.ItemNotInWarehouseException;
import models.interfaces.Upgradable;
import models.map.Cell;

import java.util.HashMap;

public class
Workshop implements Upgradable
{
    private Type type;

    private int level = 1, maxProductionNum = 1, maxWorkingTime = 15,timeToReturnProduct = -1;
    private int productionNum;
    private Warehouse warehouse;
    private boolean isWorking = false;
    private Cell cell;//this is the cell where it puts the result items


    public enum Type
    {
        EGG_POWDER_PLANT(100, Item.Type.EGG, null, Item.Type.DRIED_EGG),
        COOKIE_BAKERY(200, Item.Type.DRIED_EGG, null, Item.Type.CAKE),
        CAKE_BAKERY(300, Item.Type.CAKE, Item.Type.FLOUR, Item.Type.FLOURY_CAKE),
        SPINNERY(1000, Item.Type.WOOL, null, Item.Type.SEWING),
        WEAVING_FACTORY(1500, Item.Type.SEWING, null, Item.Type.FABRIC),
        SEWING_FACTORY(2000, Item.Type.FABRIC, Item.Type.PLUME, Item.Type.CARNIVAL_DRESS);

        public final int buildCost;
        public final Item.Type input1, input2, output;

        Type(int buildCost, Item.Type input1, Item.Type input2, Item.Type output) {
            this.buildCost = buildCost;
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
        }
    }


    public Workshop(Type type, Warehouse warehouse, Cell cell)
    {
        this.type = type;
        this.warehouse = warehouse;
        this.cell = cell;
    }

    public void goToWork() throws IsWorkingException, ItemNotInWarehouseException
    {
        if (!isWorking) {
            throw new IsWorkingException();
        }

        HashMap<Item.Type , Integer> base = new HashMap<>();
        base.put(type.input1, maxProductionNum);
        if (type.input2 != null) {
            base.put(type.input2, maxProductionNum);
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
            cell.addEntity(new Item(cell.getX(), cell.getY(), this.type.output));
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
        int cost = this.type.buildCost;
        for(int i = 0; i < level; i++) {
            cost += 100;
        }
        return cost;
    }
}