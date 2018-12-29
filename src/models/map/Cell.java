package models.map;

import models.Entity;

import models.Item;
import models.animal.Animal;
import models.animal.WildAnimal;
import models.exceptions.InsufficientResourcesException;

import java.util.ArrayList;

public class Cell
{
    private int x, y;
    private int grass = 0;
    private static final int MAX_GRASS = 10;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGrass() {
        return grass;
    }

    public void eatGrass() throws InsufficientResourcesException{
        if (grass == 0) {
            throw new InsufficientResourcesException();
        }

        grass --;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void handleCollisions()
    {
        for (Entity animal : entities) {
            if (animal instanceof Animal) {
                for (Entity entity : entities) {
                    ((Animal) animal).collide(entity);
                }
            }
        }

        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i).Exists()) {
                entities.remove(entities.get(i));
            }
        }

    }

    public void removeAll(ArrayList<Entity> stored)
    {
        entities.removeAll(stored);
    }

    public ArrayList<Entity> getStorables()
    {
        ArrayList<Entity> storables = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof WildAnimal) {
                if (((WildAnimal)entity).getState() == WildAnimal.Status.CAGED) {
                    storables.add(entity);
                }
            } else if (entity instanceof Item) {
                storables.add(entity);
            }
        }
        return storables;
    }

    public void moveAnimals()
    {
        for (Entity entity : entities) {
            if (entity instanceof Animal) {
                ((Animal) entity).move();
            }
        }
    }

    public void cage()
    {
        outer : for (Entity entity : entities) {
            if (entity instanceof WildAnimal) {
                if (((WildAnimal) entity).cage()) {
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void plant(){
        grass += 5;
        if (grass > MAX_GRASS) {
            grass = MAX_GRASS;
        }
    }

}
