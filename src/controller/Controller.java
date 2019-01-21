package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import models.Game;
import models.Map;
import models.misc.Mission;
import models.objects.Point;
import models.objects.animal.Animal;
import models.exceptions.*;
import models.buildings.Workshop;
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

    public void upgrade(String parameter) throws AlreadyAtMaxLevelException {
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
            game.nextTurn();
        }
    }

    public void clearStash(String transporterName)
    {
        game.clearStash(transporterName);
    }

    public void addToStash(String transporterName, String itemName, int count) throws NotEnoughSpaceException, IsWorkingException {
        game.addToStash(transporterName, itemName, count);
    }

    public void sendTransporter(String transporterName) throws IsWorkingException {
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
            } catch (FileNotFoundException e)
            {
                view.handleFileNotFoundException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMap(String mapName){
        //todo loading maps from gson
    }

}
