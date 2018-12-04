package models.map;

import models.interfaces.Storable;
import models.map.Cell;
import java.util.ArrayList;

public class Map
{
    public static final int WIDTH = 0, HEIGHT = 0;
    private Cell[][] cells = new Cell[WIDTH][HEIGHT];

    public ArrayList<Storable> getStorables(int x, int y)
    {
        return cells[x][y].getStorables();
    }

    public void removeItems(int x, int y, ArrayList<Storable> stored)
    {
        cells[x][y].removeAll(stored);
    }

    public void handleCollisions()
    {
    }

    public void moveAnimals()
    {

    }

    public void cage(int x, int y)
    {
    }

}
