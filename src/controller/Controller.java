package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import models.Game;
import models.Item;
import models.Mission;
import models.animal.Animal;
import models.animal.DomesticAnimal;
import models.exceptions.*;
import models.map.Map;
import models.workshop.Workshop;
import view.View;

import java.io.*;

public class Controller
{
    private static Controller controller = new Controller();
    private View view;
    private Game game;
    Mission mission;

    private Controller()
    {
        game = Game.getInstance();
        view = View.getInstance();
    }

    public static Controller getInstance()
    {
        return controller;
    }

    public void buy(String parameter) throws NotEnoughMoney {
        for (Animal.Type animal : Animal.Type.values())
        {
            if (animal.NAME.equals(parameter))
            {
                game.buy(animal);
                return;
            }
        }
        throw new InvalidArgumentException();
    }

    public void pickUp(int x, int y)
    {
        if (x >= Map.WIDTH || x < 0 || y >= Map.HEIGHT || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.pickUp(x, y);
    }

    public void cage(int x, int y)
    {
        if (x >= Map.WIDTH || x < 0 || y >= Map.HEIGHT || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.cage(x, y);

    }

    public void plant(int x, int y)
    {
        if (x >= Map.WIDTH || x < 0 || y >= Map.HEIGHT  || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        game.plant(x, y);

    }

    public void refillWell()
    {
        game.refillWell();
    }

    public void startWorkshop(String workshopName)
    {
        game.startWorkShop(workshopName);
    }

    public void upgrade(String parameter)
    {
        game.upgrade(parameter);
    }

    public void loadGame(String gamePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(gamePath));
        game = gson.fromJson(reader, Game.class);

    }

    public void saveGame(String gamePath) throws IOException {

        // Writing to a file
        File file=new File(gamePath);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        Gson gson = new Gson();


        fileWriter.write(gson.toJson(gamePath));
        fileWriter.flush();
        fileWriter.close();
    }

    public void loadCustom(String path) throws FileNotFoundException // just adds workShop
    {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Workshop workshop = gson.fromJson(reader, Workshop.class);

        game.addWorkshop(workshop);
    }

    public void nextTurn(int nTurn)
    {
        for (int i = 0; i < nTurn; i++) {
            game.turn();
        }
    }

    public void clearStash(String transporterName)
    {
        game.clearStash(transporterName);
    }

    public void addToStash(String transporterName, String itemName, int count)
    {
        game.addToStash(transporterName, itemName, count);
    }

    public void sendTransporter(String transporterName)
    {
        game.sendTransporter(transporterName);
    }

    public void startGame()
    {
        mission = view.getMission();

        while (true)
        {
            try
            {
                view.nextLine();
            } catch (AlreadyAtMaxLevelException e)
            {
                view.handleAlreadyAtMaxLevelException();
            } catch (InsufficientResourcesException e)
            {
                view.handleInsufficientResourcesException();
            } catch (InvalidArgumentException e)
            {
                view.handleInvalidArgumentException();
            } catch (ItemNotInWarehouseException e)
            {
                view.handleItemNotInWarehouseException();
            } catch (IsWorkingException e)
            {
            } catch (NotEnoughSpaceException e)
            {
                view.handleNotEnoughSpaceException();
            }
        }
    }

    public void loadMap(String mapName){
        //todo loading maps frpm gson
    }

}
