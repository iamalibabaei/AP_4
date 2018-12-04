package models.map;

import models.Entity;

import models.interfaces.Storable;

import java.util.ArrayList;

public class Cell
{
    private ArrayList<Entity> entities;

    public void handleCollisions()
    {
    }

    public void removeAll(ArrayList<Storable> stored)
    {
        entities.removeAll(stored);
    }

    public ArrayList<Storable> getStorables()
    {

    }

    public void moveAnimals()
    {

    }

    public void cage()
    {

    }

}
