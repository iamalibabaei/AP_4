package view;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import controller.Controller;
import models.*;
import models.animal.Animal;
import models.animal.DomesticAnimal;
import models.Map;
import models.workshop.Workshop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException
        ;
import java.util.Scanner;

public class View {
    private static View view = new View();
    private Scanner scanner;
    private Controller controller;

    private View() {
        this.controller = Controller.getInstance();
        scanner = new Scanner(System.in);
    }

    public static View getInstance() {
        return view;
    }

    public void handleAlreadyAtMaxLevelException() {
        System.out.println("Can't upgrade beyond maxLevel.");
    }

    public void handleInsufficientResourcesException() {
        System.out.println("You don't have enough resources");
    }

    public void handleInvalidArgumentException() {
        System.out.println("Unknown paratemer; To see all command and their arguments type 'print info'.");
    }

    public void handleIsWorkingException(String name) {
        System.out.println(name + " is busy now.");
    }

    public void handleItemNotInWarehouseException() {
    }

    public void handleNotEnoughSpaceException() {

    }

    public void handleFileNotFoundException() {

    }

    public void nextLine() throws IOException {
        String input = scanner.nextLine();
        String[] args = input.split(" ", -1);
        if (args[0].equals("buy")) {
            controller.buy(args[1]);
        }
        if (args[0].equals("pickup")) {
            controller.pickUp(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        if (args[0].equals("cage")) {
            controller.cage(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        if (args[0].equals("plant")) {
            controller.plant(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        if (args[0].equals("well")) {
            controller.refillWell();
        }
        if (args[0].equals("start")) {
            controller.startWorkshop(args[1]);
        }
        if (args[0].equals("upgrade")) {
            controller.upgrade(args[1]);
        }
        if (args[1].equals("custom")) {
            controller.loadCustom(args[2]);
        }
        if (args[0].equals("run")) {
            controller.loadMap(args[1]);
        }
        if (args[0].equals("save")) {
            controller.saveGame(args[2]);
        }
        if (args[0].equals("load") && args[1].equals("game")) {
            controller.loadGame(args[3]);
        }
        if (args[0].equals("print")) {
            if (args[1].equals("map")) {
                printMap();
            }
            if (args[1].equals("info")) {
                printInfo();
            }
            if (args[1].equals("levels")) {
                printLevel();
            }
            if (args[1].equals("warehouse")) {
                printWarehouse();
            }
            if (args[1].equals("well")) {
                printWell();
            }
            if (args[1].equals("workshops")) {
                printWorkshops();
            }
            if (args[1].equals("truck")) {
                printTruck();
            }
            if (args[1].equals("helicopter")) {
                printHelicopter();
            }
        }
        if (args[0].equals("turn")) {
            controller.nextTurn(Integer.parseInt(args[1]));
        }
        if (args[1].equals("add")) {
            controller.addToStash(args[0], args[2], Integer.parseInt(args[3]));
        }
        if (args[1].equals("clear")) {
            controller.clearStash(args[0]);
        }
        if (args[1].equals("go")) {
            controller.sendTransporter(args[0]);
        }


    }

    private void printWorkshops() {
        for (Workshop workshop : Game.getInstance().getWorkshops()) {
            System.out.println("Workshop name: " + workshop.getName());
            System.out.println("Workshop level: " + workshop.getLevel());
            if (workshop.isWorking()) {
                System.out.println("Time to finish: " + workshop.getTimeToReturnProduct() + " turn(s)");
            } else {
                System.out.println("Workshop is free");
            }
        }
    }

    public void printMap() {
        System.out.println("Map Height :" + Map.HEIGHT);
        System.out.println("Map Width :" + Map.WIDTH);
    }

    public void printInfo() {
        System.out.println("Animals :");
        for (Animal.Type animal : Animal.Type.values()) {
            int count = 0;
            for (Animal animal1 : Game.getInstance().getMap().getAnimals()) {
                if (animal.equals(animal1.type)) {
                    count++;
                }
            }
            if (count != 0) {
                System.out.println(count + " " + animal.toString() + "(s)");
            }
        }
        System.out.println("Items :");
        for (Item.Type item : Item.Type.values()) {
            int count = 0;
            for (Item item1 : Game.getInstance().getMap().getItems()) {
                if (item.equals(item1.getType())) {
                    count++;
                }
            }
            if (count != 0) {
                System.out.println(count + " " + item.toString() + "(s)");
            }
        }
    }

    public void printWarehouse() {
        System.out.println("Level: " + Game.getInstance().getWarehouse().getLevel());
        System.out.println("Capacity: " + Warehouse.CAPACITY[Game.getInstance().getWarehouse().getLevel()]);
        System.out.println("Remaining capacity: " + Game.getInstance().getWarehouse().getRemainingCapacity());
        System.out.println("Item lists:" + Game.getInstance().getWarehouse().getItems());
    }

    public void printWell() {
        System.out.println("Level: " + Game.getInstance().getWell().getLevel());
        System.out.println("Capacity: " + Well.CAPACITY[Game.getInstance().getWell().getLevel()]);
        if (Game.getInstance().getWell().isRefilling()) {
            System.out.println("Remaining water: " + Game.getInstance().getWell().getRemainingWater());
        }
    }

    public void printTruck() {
        System.out.println("Level: " + Game.getInstance().getTruck().getLevel());
        System.out.println("Capacity: " + Game.getInstance().getTruck().getCapacity());
        System.out.println("Maximum time to arrive: " + Game.getInstance().getTruck().getTimeToArrive() + " turns");
        System.out.println("Item lists: " + Game.getInstance().getTruck().getList());
        if (Game.getInstance().getTruck().isWorking()) {
            System.out.println("Remaining time to arrive): " + Game.getInstance().getTruck().getRemainTimeToArrive() + "turn(s)");
        }
    }

    public void printHelicopter() {
        System.out.println("Level: " + Game.getInstance().getHelicopter().getLevel());
        System.out.println("Capacity: " + Game.getInstance().getHelicopter().getCapacity());
        System.out.println("Maximum time to arrive: " + Game.getInstance().getHelicopter().getTimeToArrive() + " turns");
        System.out.println("Item lists: " + Game.getInstance().getHelicopter().getList());
        if (Game.getInstance().getHelicopter().isWorking()) {
            System.out.println("Remaining time to arrive): " + Game.getInstance().getHelicopter().getRemainTimeToArrive() + "turn(s)");
        }
    }

    public void printLevel() {
        if (Game.getInstance().getMission().getMoney() != 0) {
            System.out.println("Money : " + Game.getInstance().getMoney() + " / " + Game.getInstance().getMission().getMoney());
        }
        for (DomesticAnimal.Type animal : Game.getInstance().getMission().getAnimalObjectives().keySet()) {
            int count = 0;
            for (Animal animal1 : Game.getInstance().getMap().getAnimals()) {
                if (animal.equals(animal1.type)) {
                    count++;
                }
            }
            System.out.println("Animal : " + animal.NAME + "    "
                    + count + " / " + Game.getInstance().getMission().getAnimalObjectives().get(animal));
        }
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
