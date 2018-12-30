package view;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import controller.Controller;
import models.Mission;
import models.Warehouse;
import models.Well;
import models.transportation.Buyer;
import models.transportation.Seller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class View
{
    private static View view = new View();
    Scanner scanner;
    private Controller controller;

    private View()
    {
        this.controller = Controller.getInstance();
        scanner = new Scanner(System.in);
    }

    public static View getInstance()
    {
        return view;
    }

    public void handleAlreadyAtMaxLevelException()
    {
        System.out.println("Can't upgrade beyond maxLevel.");
    }

    public void handleInsufficientResourcesException()
    {
        System.out.println("You don't have enough resources");
    }

    public void handleInvalidArgumentException()
    {
        System.out.println("Unknown paratemer; To see all command and their arguments type 'print info'.");
    }

    public void handleIsWorkingException(String name)
    {
        System.out.println(name + " is busy now.");
    }

    public void handleItemNotInWarehouseException()
    {
    }

    public void handleNotEnoughSpaceException()
    {

    }

    public void nextLine()
    {
        String s = scanner.nextLine();
        Pattern p = Pattern.compile(s);
    }

    public void printMap()
    {
    }

    public void printInfo()
    {

    }

    public void printWarehouse(Warehouse warehouse)
    {
        System.out.println("Level: " + warehouse.getLevel());
        System.out.println("Capacity: " + warehouse.CAPACITY[warehouse.getLevel()]);
        System.out.println("Remaining capacity: " + warehouse.getRemainingCapacity());
        System.out.println("Item lists:" + warehouse.getItems());
    }

    public void printWell(Well well)
    {
        System.out.println("Level: " + well.getLevel());
        System.out.println("Capacity: " + Well.CAPACITY[well.getLevel()]);
        if (well.isRefilling()){
            System.out.println("Remaining water: " + well.getRemainingWater());
        }
    }

    public void printTruck(Seller truck)
    {
        System.out.println("Level: " + truck.getLevel());
        System.out.println("Capacity: " + truck.getCapacity());
        System.out.println("Maximum time to arrive: " + truck.getMaxtimeToArriveToFarm() + " turns");
        System.out.println("Item lists: " + truck.getList());
        if (truck.isWorking()){
            System.out.println("Remaining time to arrive): " + truck.getArriveToFarm() + "turn(s)");
        }
    }

    public void printHelicopter(Buyer helicopter)
    {
        System.out.println("Level: " + helicopter.getLevel());
        System.out.println("Capacity: " + helicopter.getCapacity());
        System.out.println("Maximum time to arrive: " + helicopter.getMaxtimeToArriveToFarm() + " turns");
        System.out.println("Item lists: " + helicopter.getList());
        if (helicopter.isWorking()){
            System.out.println("Remaining time to arrive): " + helicopter.getArriveToFarm() + "turn(s)");
        }
    }

    public void printLevel()
    {
    }

    public Mission getMission() {
        System.out.println("selecet your mission level");
        Gson gson = new Gson();
        String missionPath = "res/missions/mission" + scanner.next() + ".json";

        try {
            JsonReader reader = new JsonReader(new FileReader(missionPath));
            Mission mission = gson.fromJson(reader, Mission.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
