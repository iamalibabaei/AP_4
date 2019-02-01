package controller;

import com.gilecode.yagson.YaGson;
import javafx.application.Application;
import models.Map;
import models.account.Account;
import models.buildings.Workshop;
import models.exceptions.*;
import models.misc.Mission;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.Animal;
import view.MainView;

import java.io.*;
import java.util.*;

public class MenuController
{
    private static MenuController menuController = new MenuController();
    private MainView mainView;
    private InGameController game;
    private Mission mission;

    public void setCurrentAccount(Account currentAccount)
    {
        this.currentAccount = currentAccount;
    }

    private Account currentAccount;
    private ArrayList<Account> accounts;




    public static void main(String[] args) {
        Application.launch(MainView.class, args);
        //serializeMission();

    }

    private static void serializeMission() {
        int moneyObjective = 4000 , moneyAtBeginning = 2000;
        HashMap<Animal.Type, Integer> animalObjectives = new HashMap<>();
        animalObjectives.put(Animal.Type.HEN, 10);
        animalObjectives.put(Animal.Type.SHEEP, 2);
        HashMap<Item.Type, Integer> ItemObjective = new HashMap<>();
        ItemObjective.put(Item.Type.EGG, 10);
        boolean dog = false, cat = false;
        HashMap<Animal.Type, Integer> animalAtBeginning = new HashMap<>();
        animalAtBeginning.put(Animal.Type.HEN, 5);



        Mission mission = new Mission(moneyObjective, animalObjectives, ItemObjective, false, false, animalAtBeginning, moneyAtBeginning);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("res/missions/mission1.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        YaGson yaGson = new YaGson();
        Formatter formatter = new Formatter(fileWriter);
        formatter.format(yaGson.toJson(mission));
        formatter.flush();
    }

    public static MenuController getInstance()
    {
        return menuController;
    }

    //    public void buy(String parameter) throws InsufficientResourcesException, InvalidArgumentException {
    //        for (Animal.Type animal : Animal.Type.values())
    //        {
    //            if (animal.toString().equals(parameter))
    //            {
    //                game.buyAnimal(animal);
    //                return;
    //            }
    //        }
    //        throw new InvalidArgumentException();
    //    }

//    public void pickUp(Point point) throws IndexOutOfBoundsException
//    {
//        if (point.getX() >= Map.WIDTH || point.getX() < 0 || point.getY() >= Map.HEIGHT || point.getY() < 0) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        game.pickUp(point);
//    }

//    public void cage(Point point) throws IndexOutOfBoundsException
//    {
//        if (point.getX() >= Map.WIDTH || point.getX() < 0 || point.getY() >= Map.HEIGHT || point.getY() < 0) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        game.cage(point);
//
//    }

    public void plant(Point point) throws InsufficientResourcesException {
        if (point.getX() >= Map.WIDTH || point.getX() < 0 || point.getY() >= Map.HEIGHT  || point.getY() < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.plant(point);

    }

    public void refillWell() throws IsWorkingException, InsufficientResourcesException {
        game.refillWell();
    }

    public void startWorkshop(String workshopName) throws IsWorkingException, InsufficientResourcesException {
        game.startWorkshop(workshopName);
    }

//    public void upgrade(String parameter) throws AlreadyAtMaxLevelException, InsufficientResourcesException {
//        if (parameter.equals("cat"))
//        {
//            game.upgradeCat();
//            return;
//        } else if (parameter.equals("dog"))
//        {
//            game.upgradeDog();
//            return;
//        } else if (parameter.equals("well"))
//        {
//            game.upgradeWell();
//            return;
//        } else if (parameter.equals("truck"))
//        {
//            game.upgradeTruck();
//            return;
//        } else if (parameter.equals("helicopter"))
//        {
//            if (game.getHelicopter() == null)
//            {
//                System.out.println("no helicopter found");
//                //TODO convert to exception
//                return;
//            }
//            game.upgradeHelicopter();
//            return;
//        } else {
//            game.upgradeWorkshop(parameter);
//        }
//    }

    public void loadGame(String gamePath) throws FileNotFoundException {
        YaGson yaGson = new YaGson();
        game = yaGson.fromJson(gamePath, InGameController.class);
    }

    public void saveGame(String gamePath) throws IOException {

        // Writing to a file
        File file=new File(gamePath);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        YaGson yaGson = new YaGson();
        fileWriter.write(yaGson.toJson(game));
        fileWriter.flush();
        fileWriter.close();
    }

    public void loadCustom(String path) throws FileNotFoundException // just adds workShop
    {
        YaGson yaGson = new YaGson();
        Workshop workshop = yaGson.fromJson(path, Workshop.class);
        //game.addWorkshop(workshop);
    }

//    public void clearStash(String transporterName) throws InvalidArgumentException, ObjectNotFoundException {
//        if (transporterName.equals("helicopter"))
//        {
//            if (game.getHelicopter() == null)
//            {
//                throw new ObjectNotFoundException();
//            } else
//            {
//                game.clearStashHelicopter();
//            }
//            return;
//        } else if (transporterName.equals("truck"))
//        {
//            game.clearTruck();
//            return;
//        }
//        throw new InvalidArgumentException();
//    }

//    public void addToStash(String transporterName, String itemName, int count)
//            throws NotEnoughSpaceException, IsWorkingException, InvalidArgumentException, ObjectNotFoundException {
//        Item.Type itemType = Item.Type.valueOf(itemName);
//        if (transporterName.equals("helicopter"))
//        {
//            if (game.getHelicopter() == null)
//            {
//                throw new ObjectNotFoundException();
//            } else
//            {
//                game.addToHelicopter(itemName, count);
//            }
//            return;
//        } else if (transporterName.equals("truck"))
//        {
//            game.addToTruck(itemName, count);
//            return;
//        }
//        throw new InvalidArgumentException();
//    }

//    public void sendTransporter(String transporterName) throws IsWorkingException, ObjectNotFoundException, InvalidArgumentException {
//        if (transporterName.equals("helicopter"))
//        {
//            game.sendHelicopter();
//
//            return;
//        } else if (transporterName.equals("truck"))
//        {
//            game.sendTruck();
//            return;
//        }
//        throw new InvalidArgumentException();
//    }
//


    public void startGame(Mission mission) {
        this.mission = mission;
        game = InGameController.getInstance();
        mainView = MainView.getInstance();
        game.startGame(mission);
        mainView.startGame(mission);
    }

    public void endGame() {
        //todo ::::::::
    }


    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void click(double x, double y) {

    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}
