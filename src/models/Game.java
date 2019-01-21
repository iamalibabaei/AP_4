package models;

import models.buildings.Warehouse;
import models.buildings.Well;
import models.buildings.Workshop;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import models.exceptions.IsWorkingException;
import models.exceptions.ObjectNotFoundException;
import models.interfaces.Time;
import models.misc.Mission;
import models.objects.Item;
import models.objects.Point;
import models.objects.animal.Animal;
import models.objects.animal.Cat;
import models.objects.animal.Dog;
import models.objects.animal.DomesticAnimal;
import models.transportation.Buyer;
import models.transportation.Seller;
import models.transportation.Transporter;

import java.util.ArrayList;

public class Game implements Time
{
    public static final int MAX_WORKSHOPS = 6, MAP_SIZE = 30;
    public static final double RADIUS = 0.2;
    private static Game ourInstance = new Game();
    private int money = 50;
    private Map map;
    private Warehouse warehouse;
    private Well well;
    private Buyer truck;
    private Seller helicopter;
    private ArrayList<Workshop> workshops;
    private Mission mission;
    private int wildAnimalsInMap;

    private Game()
    {
        map = Map.getInstance();
        warehouse = Warehouse.getInstance();
        well = Well.getInstance();
        workshops = new ArrayList<>();
        truck = new Buyer(map);
        //helicopter = new Seller();
        mission = new Mission(ourInstance);


//        map.handleCollisions();

        well.nextTurn();
        truck.nextTurn();
        helicopter.nextTurn();
        for (Workshop workshop : workshops)
        {
            workshop.nextTurn();
        }
//        for (Cell[] cells: map.getCells()) {
//            for (Cell cell: cells) {
//                for (Entity entity : cell.getEntities()) {
//                    if (entity instanceof Animal){
//                        ((Animal) entity).nextTurn();
//                    }
//
//                }
//
//            }
//
//        }
        for (Animal animal : map.getAnimals())
        {
            animal.nextTurn();
        }
    }

    public static Game getInstance()
    {
        return ourInstance;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public Mission getMission()
    {
        return mission;
    }

    public Buyer getTruck()
    {
        return truck;
    }

    public Seller getHelicopter()
    {
        return helicopter;
    }

    public ArrayList<Workshop> getWorkshops()
    {
        return workshops;
    }

    public void buyAnimal(Animal.Type type) throws InvalidArgumentException, InsufficientResourcesException
    {
        if (money < type.BUY_COST)
            throw new InsufficientResourcesException();
        switch (type)
        {
            case CAT:
                map.addToMap(new Cat(Point.randomPoint(Map.WIDTH, Map.HEIGHT), type));
                break;
            case DOG:
                map.addToMap(new Dog(Point.randomPoint(Map.WIDTH, Map.HEIGHT), type));
                break;
            case SHEEP:
                map.addToMap(new Dog(Point.randomPoint(Map.WIDTH, Map.HEIGHT), type));
                break;
            case HEN:
                map.addToMap(new Dog(Point.randomPoint(Map.WIDTH, Map.HEIGHT), type));
                break;
            case COW:
                map.addToMap(new Dog(Point.randomPoint(Map.WIDTH, Map.HEIGHT), type));
                break;
            default:
                throw new InvalidArgumentException();
        }
        money -= type.BUY_COST;
    }

    public void pickUp(Point point)
    {
        //TODO
    }

    public void cage(Point point)
    {
        map.cage(point);
    }

    public void startWorkShop(String workshopName)
    {
        for (Workshop workshop : workshops)
        {
            if (workshop.getName().equals(workshopName))
            {
                workshop.goToWork();
            }

        }
    }

    public void upgrade(String parameter)
    {
        if (parameter.equals("cat"))
        {
            //todo upgrade cat
            return;
        } else if (parameter.equals("dog"))
        {
            //TODO upgrade dod
            return;
        } else if (parameter.equals("ourInstance"))
        {
            well.upgrade();
            return;
        } else if (parameter.equals("truck"))
        {
            truck.upgrade();
            return;
        } else if (parameter.equals("helicopter"))
        {
            if (helicopter == null)
            {
                System.out.println("no helicopter found");
                //TODO convert to exception
                return;
            }
            helicopter.upgrade();
            return;
        }

        for (Workshop workshop : workshops)
        {
            if (workshop.getName().equals(parameter))
            {
                workshop.upgrade();
                return;
            }
        }
        System.out.println("no item found");
        //TODO convert to exception

    }

    public void refillWell() throws InsufficientResourcesException, IsWorkingException
    {
        if (money < Well.REFILL_COST[well.getLevel()])
        {
            throw new InsufficientResourcesException();
        }
        well.issueRefill();
    }

    public void plant(Point point) throws InsufficientResourcesException
    {
        well.putGrass();
        map.plant(point);
    }

    public void sendHelicopter() throws ObjectNotFoundException, IsWorkingException
    {
        if (helicopter == null)
        {
            throw new ObjectNotFoundException();
        }
        helicopter.go();
    }

    public void addWorkshop(Workshop workshop)
    {
        workshops.add(workshop);
    }

    public void clearStash(String transporterName)
    {
        if (transporterName.equals("helicopter"))
        {
            if (helicopter == null)
            {
                System.out.println("no helicopter");
            } else
            {
                helicopter.clearStash();
            }
        } else if (transporterName.equals("truck"))
        {
            truck.clearStash();
        }
        System.out.println("invalid input");
    }

    public void addToStash(String transporterName, String itemName, int count)
    {

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
        } else if (transporterName.equals("truck"))
        {
            transporter = truck;
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

    public void buyHelicopter()
    {
        helicopter = new Seller();
    }

    public void sendTransporter(String transporterName)
    {
        if (transporterName.equals("helicopter"))
        {
            if (helicopter == null)
            {
                System.out.println("no helicopter");
                return;
            } else
            {
                helicopter.go();
            }
        } else if (transporterName.equals("truck"))
        {
            truck.go();
        }
    }

    @Override
    public void nextTurn()
    {

    }

}
