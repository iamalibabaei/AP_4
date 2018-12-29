package models;

import models.animal.*;
import models.map.Cell;
import models.map.Map;
import models.transportation.Buyer;
import models.transportation.Seller;
import models.workshop.Workshop;

import java.util.ArrayList;

public class Game
{
    public static final int MAX_WORKSHOPS = 6, MAP_SIZE = 30;
    private static Game ourInstance = new Game();
    private int money;
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

    public void setMoney(int money) {
        this.money = money;
    }


    public void addDomesticAnimal(DomesticAnimal animal){
        if (money >= animal.getBuyCost()) {
            map.getCell(animal.x, animal.y).addEntity(animal);
            money -= animal.getBuyCost();
        }
    }

    public void addCat(Cat cat){
        if (money >= cat.getBuyCost()) {
            map.getCell(cat.x, cat.y).addEntity(cat);
            money -= cat.getBuyCost();
        }
    }

    public void addDog(Dog dog){
        if (money >= dog.getBuyCost()) {
            map.getCell(dog.x, dog.y).addEntity(dog);
            money -= dog.getBuyCost();
        }
    }

    private Game()
    {
        map = new Map();
        warehouse = new Warehouse();
        well = new Well();
        workshops = new ArrayList<>();
        truck = new Buyer(map);
        helicopter = new Seller();
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


}
