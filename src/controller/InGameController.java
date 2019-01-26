package controller;

import models.Map;
import models.buildings.Warehouse;
import models.buildings.Well;
import models.buildings.Workshop;
import models.exceptions.*;
import models.interfaces.Time;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.Animal;
import models.transportation.Helicopter;
import models.transportation.Truck;
import models.transportation.Transporter;

import java.util.ArrayList;
import java.util.List;

// todo you have to buy helicopter in the beginning
// todo multiple trucks and helicopters

public class InGameController implements Time
{
    public static final double COLLISION_RADIUS = 2.0;
    private static InGameController instance = new InGameController();
    private Integer money;
    private Map map;
    private Warehouse warehouse;
    private Well well;
    private Truck truck;
    private Helicopter helicopter;
    private List<Workshop> workshops;
    private List<Workshop> availableWorkshops;

    private InGameController()
    {
        availableWorkshops = new ArrayList<>();
        map = Map.getInstance();
        warehouse = Warehouse.getInstance();
        well = Well.getInstance();
        workshops = new ArrayList<>();
        truck = new Truck();
        helicopter = new Helicopter();
    }

    public static InGameController getInstance()
    {
        return instance;
    }

    public Integer getMoney()
    {
        return money;
    }

    public void buyAnimal(String name) throws InvalidArgumentException, InsufficientResourcesException
    {
        for (Animal.Type type : Animal.Type.values())
        {
            if (name.equals(type.toString().toLowerCase()))
            {
                if (money < type.BUY_COST)
                {
                    throw new InsufficientResourcesException();
                }
                map.addAnimal(type);
                money -= type.BUY_COST;
                return;
            }
        }
        throw new InvalidArgumentException();
    }

    public void pickUp(Point point) throws NotEnoughSpaceException
    {
        List<Item> nearbyItems = map.getNearbyItems(point);
        List<Item> storedItems = warehouse.store(nearbyItems);
        map.removeItems(storedItems);
    }

    public void cage(Point point)
    {
        map.cage(point);
    }

    public void startWorkshop(String workshopName) throws InsufficientResourcesException, IsWorkingException {
        for (Workshop workshop : workshops)
        {
            if (workshop.name.equals(workshopName))
            {
                int factor = warehouse.moveToWorkshop(workshop.inputs, workshop.getMaxProductionFactor());
                workshop.startWorking(factor);
            }
        }
    }

    public void upgrade(String parameter) throws AlreadyAtMaxLevelException, InsufficientResourcesException {

    }

    public void refillWell() throws InsufficientResourcesException, IsWorkingException
    {
        if (money < Well.REFILL_COST[Well.getLevel()])
        {
            throw new InsufficientResourcesException();
        }
        well.issueRefill();
    }

    public void upgradeWell() throws InsufficientResourcesException, AlreadyAtMaxLevelException
    {
        int cost = well.getUpgradeCost();
        if (money < cost)
        {
            throw new InsufficientResourcesException();
        }
        money -= cost;
        well.upgrade();
    }

    public void plant(Point point) throws InsufficientResourcesException
    {
        well.extractWater();
        map.plant(point);
    }

    public void sendHelicopter() throws IsWorkingException
    {
        helicopter.go();
    }

    public void addWorkshop(Workshop workshop)
    {
        workshops.add(workshop);
    }

    public void clearStash(String transporterName)
    {
    }

    public void upgradeWarehouse() throws AlreadyAtMaxLevelException, InsufficientResourcesException
    {
        int cost = warehouse.getUpgradeCost();
        if (money < cost)
        {
            throw new InsufficientResourcesException();
        }
        money -= cost;
        warehouse.upgrade();
    }

    public void addToStashHelicopter(String item) throws InsufficientResourcesException, NotEnoughSpaceException
    {
        for (Item.Type type : Item.Type.values())
        {
            if (type.toString().toLowerCase().equals(item))
            {
                if (money < type.BUY_COST)
                {
                    throw new InsufficientResourcesException();
                }
                helicopter.addToList(type, 1);
                money -= type.BUY_COST;
            }
        }
    }

    public void addToStashTruck(String item) throws NotEnoughSpaceException
    {
        for (Item.Type type : Item.Type.values())
        {
            if (type.toString().toLowerCase().equals(item))
            {
                truck.addToList(type, 1);
            }
        }
    }

    public void removeFromStash(String name, String item)
    {
        for (Item.Type type : Item.Type.values())
        {
            if (type.toString().toLowerCase().equals(item))
            {
                helicopter.removeFromList(type, 1);
            }
        }
    }

    public void sendTransporter(String name)
    {
        if (name.equals(Helicopter.NAME))
        {
            helicopter.go();
        }
        else if (name.equals(Truck.NAME))
        {
            truck.go();
        }
    }


    public void addToStash(String transporterName, String itemName, int count) throws NotEnoughSpaceException, IsWorkingException {

        Transporter transporter = truck;
        if (transporterName.equals("helicopter"))
        {
            if (helicopter == null)
            {
                System.out.println("no helicopter");
                return;
            } else
            {
                transporter = helicopter;
            }
        }

        for (Item.Type item : Item.Type.values())
        {
            if (item.toString().toLowerCase().equals(itemName))
            {
                transporter.addToList(item, count);
                return;
            }
        }
    }

    @Override
    public void nextTurn()
    {
        map.nextTurn();
        well.nextTurn();
        truck.nextTurn();
        helicopter.nextTurn();
        for (Workshop workshop : workshops)
        {
            workshop.nextTurn();
        }
    }

    public void upgradeHelicopter() throws AlreadyAtMaxLevelException, InsufficientResourcesException
    {
        int cost = helicopter.getUpgradeCost();
        if (money < cost)
        {
            throw new InsufficientResourcesException();
        }
        money -= cost;
        helicopter.upgrade();
    }

}
