package models.map;

import models.Entity;

import java.util.ArrayList;

public class Map
{
    public static final int WIDTH = 30, HEIGHT = 30;
    private Cell[][] cells = new Cell[WIDTH][HEIGHT];

    public ArrayList<Storable> getStorables(int x, int y)
    {
        return cells[x][y].getStorables();
    }

    void removeItems(int x, int y, ArrayList<Storable> stored)
    {
        cells[x][y].removeAll(stored);
    }

    public void handleCollisions()
    {
        for (Cell[] rowCell : cells) {
            for (Cell cell : rowCell) {
                cell.handleCollisions();
            }
        }
    }

    public void moveAnimals()
    {
        for (Cell[] rowCell : cells) {
            for (Cell cell : rowCell) {
                cell.moveAnimals();
            }
        }
    }

    public void cage(int x, int y)
    {
        cells[x][y].cage();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void addToMap(Entity entity) {
        int x = (int)(Math.random() * 20), y = (int)(Math.random() * 20);
        cells[x][y].addEntity(entity);
    }

    public void plant(int x, int y) {
        cells[x][y].plant();
    }

}
