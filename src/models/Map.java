package models;

import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import models.objects.animal.Animal;
import models.objects.animal.WildAnimal;

import java.util.ArrayList;
import java.util.Iterator;


public class Map implements Time
{
    private static final double ROUND = 1.0;//distanceFrom to the point to cage animals
    public static final double WIDTH = 30;
    public static final double HEIGHT = 30;
    private static Map ourInstance = new Map();
    private ArrayList<Animal> animals;
    private ArrayList<Grass> grasses;
    private ArrayList<Item> items;

    private Map()
    {
        animals = new ArrayList<>();
        grasses = new ArrayList<>();
        items = new ArrayList<>();
    }

    public static Map getInstance()
    {
        return ourInstance;
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

    void cage(Point point)
    {
        for (Animal animal : animals)
        {
            double distance = Math.sqrt(Math.pow((animal.getCoordinates().getX() - point.getX()), 2)
                    + Math.pow(animal.getCoordinates().getY() - point.getY(), 2));
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
        if (entity instanceof Grass)
            grasses.add((Grass) entity);
    }

    void plant(Point point)
    {
        grasses.add(new Grass(point));
    }

    @Override
    public void nextTurn() throws NotEnoughSpaceException {
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

    private void handleCollisions() throws NotEnoughSpaceException
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
            for (Grass grass : grasses)
            {
                collider.collide(grass);
            }
        }

        Iterator<Animal> animalIterator = animals.iterator();
        while (animalIterator.hasNext())
        {
            Animal animal = animalIterator.next();
            if (animal.notExists())
            {
                animals.remove(animal);
            }
        }

        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext())
        {
            Item item = itemIterator.next();
            if (item.notExists())
            {
                items.remove(item);
            }
        }
    }

}
