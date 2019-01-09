package models;

import models.animal.Animal;
import models.animal.WildAnimal;
import models.interfaces.Time;

import java.util.ArrayList;
import java.util.Iterator;


public class Map implements Time
{
    public static final double ROUND = 1.0;//distance to the point to cage animals
    private ArrayList<Animal> animals;
    private ArrayList<Grass> grasses;
    private ArrayList<Item> items;


    public Map()
    {
        animals = new ArrayList<>();
        grasses = new ArrayList<>();
        items = new ArrayList<>();
    }

    public ArrayList<Animal> getAnimals()
    {
        return animals;
    }

    public ArrayList<Grass> getGrasses()
    {
        return grasses;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public void removeItems(ArrayList<Entity> removedEntities)
    {
        animals.removeAll(removedEntities);
    }

    public void cage(double x, double y)
    {
        for (Animal animal : animals)
        {
            double distance = Math.sqrt(Math.pow((animal.getX() - x), 2) + Math.pow(animal.getY() - y, 2));
            if (distance <= ROUND)
            {
                if (animal instanceof WildAnimal)
                {
                    ((WildAnimal) animal).cage();
                }
            }
        }
    }

    public void addToMap(Entity entity)
    {
        if (entity instanceof Animal)
        {
            animals.add((Animal) entity);
        }
        if (entity instanceof Item)
        {
            items.add((Item) entity);
        }
    }

    public void plant(int x, int y)
    {
        grasses.add(new Grass(x, y));
    }

    @Override
    public void nextTurn()
    {
        moveAnimals();
        handleCollisions();
    }

    private void moveAnimals()
    {
        for (Animal animal : animals)
        {
            animal.move();
        }
    }

    private void handleCollisions()
    {
        for (Animal collider : animals)
        {
            for (Animal animal : animals)
            {
                collider.collide(animal);
            }
            for (Item item : items)
            {
                collider.collide(item);
            }
        }

        Iterator<Animal> animalIterator = animals.iterator();
        while (animalIterator.hasNext())
        {
            Animal animal = animalIterator.next();
            if (!animal.Exists())
            {
                animals.remove(animal);
            }
        }

        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext())
        {
            Item item = itemIterator.next();
            if (!item.Exists())
            {
                items.remove(item);
            }
        }
    }

}
