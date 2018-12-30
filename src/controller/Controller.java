package controller;

import models.Game;
import models.Mission;
import models.animal.Animal;
import models.exceptions.*;
import view.View;

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

    public void buy(String parameter)
    {
        if (parameter.equals("helicopter"))
        {
            // todo buy helicopter
            return;
        }
        for (Animal.Type animal : Animal.Type.values())
        {
            if (animal.NAME.equals(parameter))
            {
                // todo buy animal
                return;
            }
        }
        throw new InvalidArgumentException();
    }

    public void pickUp(int x, int y)
    {
        // todo if out of bounds
        throw new IndexOutOfBoundsException();
        // todo pickup
    }

    public void cage(int x, int y)
    {
        // todo if out of bounds
        throw new IndexOutOfBoundsException();
        // todo cage

    }

    public void plant(int x, int y)
    {
        // todo if out of bounds
        throw new IndexOutOfBoundsException();
        // todo plant

    }

    public void refillWell()
    {

    }

    public void startWorkshop(String workshopName)
    {

    }

    public void upgrade(String parameter)
    {

    }

    public void loadGame(String gamePath)
    {

    }

    public void saveGame(String gamePath)
    {

    }

    public void loadCustom(String path)
    {

    }

    public void print(String parameter)
    {


    }

    public void nextTurn(int nTurn)
    {

    }

    public void clearStash(String transporterName)
    {

    }

    public void addToStash(String transporterName, String itemName, int count)
    {

    }

    public void sendTransporter(String transporterName)
    {

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

}
