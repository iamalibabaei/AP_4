package models.map;

import models.Entity;

import models.animal.Animal;
import models.animal.WildAnimal;
import models.interfaces.Storable;

import java.util.ArrayList;

public class Cell
{
    //TODO chaman ro vared nakardim :)
    private ArrayList<Entity> entities;

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void handleCollisions()
    {
        outer : for (Entity animal : entities) {
            if (animal instanceof Animal) {
                inner : for (Entity entity : entities) {
                    ((Animal) animal).collide(entity);
                }
            }
        }
    }

    public void removeAll(ArrayList<Storable> stored)
    {
        entities.removeAll(stored);
    }

    public ArrayList<Storable> getStorables()
    {
        ArrayList<Storable> storables = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Storable) {
                storables.add((Storable) entity);
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
                    break outer;
                } else {
                    continue outer;
                }
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

}
