package models;

import models.animal.*;
import models.exceptions.InsufficientResourcesException;
import models.map.Cell;
import models.map.Map;
import models.transportation.Buyer;
import models.transportation.Seller;
import models.transportation.Transporter;
import models.workshop.Workshop;

import java.util.ArrayList;

public class Game
{
    public static final int MAX_WORKSHOPS = 6, MAP_SIZE = 30;
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

    public static Game getInstance()
    {
        return ourInstance;
    }

    public Map getMap() {
        return map;
    }

    public int getMoney() {
        return money;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public  Warehouse getWarehouse() {
        return warehouse;
    }

    public Well getWell() {
        return well;
    }

    public Mission getMission() {
        return mission;
    }

    public Buyer getTruck() {
        return truck;
    }

    public Seller getHelicopter() {
        return helicopter;
    }

    public ArrayList<Workshop> getWorkshops() {
        return workshops;
    }

    public void addDomesticAnimal(DomesticAnimal animal) throws InsufficientResourcesException {
        if (money >= animal.getBuyCost()) {
            map.addToMap(animal);
            money -= animal.getBuyCost();
            return;
        }
        throw new InsufficientResourcesException();

    }

    public void addCat(Cat cat) throws InsufficientResourcesException {
        if (money >= cat.getBuyCost()) {
            map.addToMap(cat);
            money -= cat.getBuyCost();
            return;
        }
        throw new InsufficientResourcesException();
    }

    public void addDog(Dog dog) throws InsufficientResourcesException {
        if (money >= dog.getBuyCost()) {
            map.addToMap(dog);
            money -= dog.getBuyCost();
            return;
        }
        throw new InsufficientResourcesException();

    }

    public void pickUp(int x, int y) {
        //TODO
    }

    private Game()
    {
        map = new Map();
        warehouse = new Warehouse();
        well = new Well();
        workshops = new ArrayList<>();
        truck = new Buyer(map);
        //helicopter = new Seller();
        mission = new Mission(ourInstance);


        map.handleCollisions();

        well.nextTurn();
        truck.nextTurn();
        helicopter.nextTurn();
        for (Workshop workshop : workshops) {
            workshop.nextTurn();
        }
        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                for (Entity entity : cell.getEntities()) {
                    if (entity instanceof Animal){
                        ((Animal) entity).nextTurn();
                    }

                }

            }

        }
    }



    public void cage(int x, int y) {
        map.getCell(x, y).cage();
    }

    public void startWorkShop(String workshopName) {
        for (Workshop workshop : workshops) {
            if (workshop.getName().equals(workshopName)) {
                workshop.goToWork();
            }

        }
    }

    public void upgrade(String parameter) {
        if (parameter.equals("cat")) {
            //todo upgrade cat
            return;
        } else if (parameter.equals("dog")) {
            //TODO upgrade dod
            return;
        } else if (parameter.equals("well")) {
            well.upgrade();
            return;
        } else if (parameter.equals("truck")) {
            truck.upgrade();
            return;
        } else if (parameter.equals("helicopter")) {
            if (helicopter == null) {
                System.out.println("no helicopter found");
                //TODO convert to exception
                return;
            }
            helicopter.upgrade();
            return;
        }

        for (Workshop workshop : workshops) {
            if (workshop.getName().equals(parameter)) {
                workshop.upgrade();
                return;
            }
        }
        System.out.println("no item found");
        //TODO convert to exception

    }

    public void refillWell() {
        //TODO money handle nashode
    }

    public void plant(int x, int y) {
        if (well.getRemainingWater() != 0 ) {
            //todo kam kardan ab az well
            map.plant(x, y);
        }
    }

    public void helicopterGo() {
        if (helicopter != null) {
            helicopter.go();
            return;
        }
        System.out.println("no helicopter");
    }

    public void buy(DomesticAnimal.Type animalType) throws InsufficientResourcesException {
        if (animalType.BUY_COST > money) {
            throw new  InsufficientResourcesException();
        }
        int x = (int)(Math.random() * 30), y = (int)(Math.random() * 30);
        DomesticAnimal domesticAnimal = new DomesticAnimal(x, y, map, animalType);
        map.addToMap(domesticAnimal);
    }

    public void addWorkshop(Workshop workshop) {
        workshops.add(workshop);
    }

    public void turn() {
        //TODO
    }

    public void clearStash(String transporterName) {
        if (transporterName.equals("helicopter")) {
            if (helicopter == null) {
                System.out.println("no helicopter");
            } else {
                helicopter.clearStash();
            }
        } else if (transporterName.equals("truck")) {
            truck.clearStash();
        }
        System.out.println("invalid input");
    }

    public void addToStash(String transporterName, String itemName, int count) {

        Transporter transporter = truck;
        if (transporterName.equals("helicopter")) {
            if (helicopter == null) {
                System.out.println("no helicopter");
                return;
            } else {
                transporter = helicopter;
            }
        } else if (transporterName.equals("truck")) {
            transporter = truck;
        }

        for (Item.Type item : Item.Type.values()) {
            if (item.toString().toLowerCase().equals(itemName)) {
                transporter.addToList(item, count);
                return;
            }
        }
    }

    public void buyHelicopter() {
        helicopter = new Seller();
    }

    public void sendTransporter(String transporterName) {
        if (transporterName.equals("helicopter")) {
            if (helicopter == null) {
                System.out.println("no helicopter");
                return;
            } else {
                helicopter.go();
            }
        } else if (transporterName.equals("truck")) {
            truck.go();
        }
    }
}
