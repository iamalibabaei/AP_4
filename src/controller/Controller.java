package controller;

import com.gilecode.yagson.YaGson;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import models.Game;
import models.Map;
import models.account.Account;
import models.misc.Mission;
import models.objects.Item;
import models.objects.Point;
import models.objects.animal.Animal;
import models.exceptions.*;
import models.buildings.Workshop;
import view.View;

import java.io.*;

public class Controller
{
    private static Controller controller = new Controller();
    private static View view;
    private Game game;
    private Mission mission;
    private Account account;


    public static void main(String[] args) {
        Application.launch(View.class, args);
    }

    public static Controller getInstance()
    {
        return controller;
    }

    public void buy(String parameter) throws InsufficientResourcesException, InvalidArgumentException {
        for (Animal.Type animal : Animal.Type.values())
        {
            if (animal.NAME.equals(parameter))
            {
                game.buyAnimal(animal);
                return;
            }
        }
        throw new InvalidArgumentException();
    }

    public void pickUp(Point point)
    {
        if (point.getX() >= Map.WIDTH || point.getX() < 0 || point.getY() >= Map.HEIGHT || point.getY() < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.pickUp(point);
    }

    public void cage(Point point)
    {
        if (point.getX() >= Map.WIDTH || point.getX() < 0 || point.getY() >= Map.HEIGHT || point.getY() < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.cage(point);

    }

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
        game.startWorkShop(workshopName);
    }

    public void upgrade(String parameter) throws AlreadyAtMaxLevelException, InsufficientResourcesException {
        if (parameter.equals("cat"))
        {
            game.upgradeCat();
            return;
        } else if (parameter.equals("dog"))
        {
            game.upgradeDog();
            return;
        } else if (parameter.equals("well"))
        {
            game.upgradeWell();
            return;
        } else if (parameter.equals("truck"))
        {
            game.upgradeTruck();
            return;
        } else if (parameter.equals("helicopter"))
        {
            if (game.getHelicopter() == null)
            {
                System.out.println("no helicopter found");
                //TODO convert to exception
                return;
            }
            game.upgradeHelicopter();
            return;
        } else {
            game.upgradeWorkshop(parameter);
        }
    }

    public void loadGame(String gamePath) throws FileNotFoundException {
        YaGson yaGson = new YaGson();
        game = yaGson.fromJson(gamePath, Game.class);
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
        game.addWorkshop(workshop);
    }

    public void clearStash(String transporterName) throws InvalidArgumentException, ObjectNotFoundException {
        if (transporterName.equals("helicopter"))
        {
            if (game.getHelicopter() == null)
            {
                throw new ObjectNotFoundException();
            } else
            {
                game.clearStashHelicopter();
            }
            return;
        } else if (transporterName.equals("truck"))
        {
            game.clearTruck();
            return;
        }
        throw new InvalidArgumentException();
    }

    public void addToStash(String transporterName, String itemName, int count)
            throws NotEnoughSpaceException, IsWorkingException, InvalidArgumentException, ObjectNotFoundException {
        Item.Type itemType = Item.Type.valueOf(itemName);
        if (transporterName.equals("helicopter"))
        {
            if (game.getHelicopter() == null)
            {
                throw new ObjectNotFoundException();
            } else
            {
                game.addToHelicopter(itemName, count);
            }
            return;
        } else if (transporterName.equals("truck"))
        {
            game.addToTruck(itemName, count);
            return;
        }
        throw new InvalidArgumentException();
    }

    public void sendTransporter(String transporterName) throws IsWorkingException, ObjectNotFoundException, InvalidArgumentException {
        if (transporterName.equals("helicopter"))
        {
            game.sendHelicopter();

            return;
        } else if (transporterName.equals("truck"))
        {
            game.sendTruck();
            return;
        }
        throw new InvalidArgumentException();
    }

    public void loadMission(){
        //todo load map of startGame

    }

    public void startGame() {
        //game = Game.getInstance();
        view = View.getInstance();
        view.startGame();
        loadMission();
        while (!mission.isAccomplished()) {
            //TODO
            //game.nextTurn();
            view.nextTurn();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endGame();
    }

    public void endGame() {
        //todo ::::::::
    }


    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
