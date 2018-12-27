package models.map;

import models.Entity;

import models.Item;
import models.animal.Animal;
import models.animal.WildAnimal;
import models.interfaces.Storable;

import java.util.ArrayList;
import java.util.Iterator;

public class Cell
{
    private int grass = 0;
    private static final int MAX_GRASS = 10;
    private ArrayList<Entity> entities;

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void handleCollisions()
    {
        //todo inja be nazar bug mikhore badan ye kare dige bokonam inja ro :)
        for (Entity animal : entities) {
            if (animal instanceof Animal) {
                for (Entity entity : entities) {
                    ((Animal) animal).collide(entity);
                }
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
