package models;

import com.google.gson.Gson;
import models.exceptions.AlreadyAtMaxLevelException;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.interfaces.Upgradable;

import java.util.HashMap;

public class
Workshop implements Upgradable, Time {
    String name;
    private final Point outputPlace;
    private static final int[] PRODUCTION_TIME = {15, 14, 13, 11, 8};
    private int level, maxProductionFactor, productionTime, productionRemainingTime;
    private int productionFactor;
    private Warehouse warehouse;
    private boolean isWorking;
    private final int buildCost;
    private final HashMap<Item.Type, Integer> inputs, outputs;
    private Map map;

    public Workshop(String name, double outputX, double outputY, int maxProductionFactor, int productionFactor,
                    int buildCost, HashMap<Item.Type, Integer> inputs, HashMap<Item.Type, Integer> outputs) {
        this.name = name;
        this.outputPlace = new Point(outputX, outputY);
        this.level = 0;
        this.maxProductionFactor = maxProductionFactor;
        this.productionTime = 15;
        this.productionRemainingTime = -1;
        this.productionFactor = productionFactor;
        this.isWorking = false;
        this.buildCost = buildCost;
        this.inputs = inputs;
        this.outputs = outputs;
    }


    public static Workshop loadJson(String json, Map map, Warehouse warehouse) {
        Gson gson = new Gson();
        Workshop workshop = gson.fromJson(json, Workshop.class);
        workshop.getMapAndWarehouse(map, warehouse);
        return workshop;
    }


    private void getMapAndWarehouse(Map map, Warehouse warehouse) {
        this.map = map;
        this.warehouse = warehouse;
    }

    //TODO name be constructor vasl she + json files
    public String getName() {
        return name;
    }

    public void goToWork() throws IsWorkingException, InsufficientResourcesException {
        if (!isWorking) {
            throw new IsWorkingException();
        }

        productionFactor = warehouse.moveToWorkshop(inputs, maxProductionFactor).get();
        isWorking = true;
        productionRemainingTime = productionTime;
    }

    @Override
    public void nextTurn() {
        if (!isWorking) {
            return;
        }
        productionRemainingTime--;
        if (productionRemainingTime == 0) {
            isWorking = false;
            returnProduct();

        }
    }

    private void returnProduct() {
        for (Item.Type itemType : outputs.keySet()) {
            for (int i = 0; i < productionFactor; i++) {
                //todo hame ye item ha ru ham mioftan x ro ykam jabeja mikonim
                map.addToMap(new Item(outputPlace.getX(), outputPlace.getY(), itemType));
            }
        }
    }

    @Override
    public void upgrade() {
        if (level == 4) {
            throw new AlreadyAtMaxLevelException();
        }
        level++;
        productionTime = PRODUCTION_TIME[level];
        maxProductionFactor++;

    }

    @Override
    public int getUpgradeCost() throws AlreadyAtMaxLevelException {
        if (level == 4) {
            throw new AlreadyAtMaxLevelException();
        }
        return buildCost + 100 * (1 + level);
    }

}