package models;

import models.animal.Animal;

public class Game
{
    private static Game game;
    private int money, time;

    private Game()
    {
    }

    public Game getInstance()
    {
        return game;
    }

    public void addAnimal(Animal.Type type)
    {

    }

}
