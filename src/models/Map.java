package models;

import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import models.objects.animal.Animal;
import models.objects.animal.WildAnimal;

import java.util.ArrayList;


public class Map implements Time
{
    public static final double WIDTH = 30, HEIGHT = 30;
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

    public void removeItems(ArrayList<Item> removedItems)
    {
        items.removeAll(removedItems);
    }

    void cage(Point point)
    {
        for (Animal animal : animals)
        {
            double distance = animal.getCoordinates().distanceFrom(point);
            if (distance <= Game.COLLISION_RADIUS && animal instanceof WildAnimal)
            {
                ((WildAnimal) animal).cage();
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
    public void nextTurn()
    {
        moveAnimals();
        handleCollisions();
        removeDeadEntities();
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
        double distance;
        for (Animal collider : animals)
        {
            for (Animal animal : animals)
            {
                distance = collider.getCoordinates().distanceFrom(animal.getCoordinates());
                if (distance <= Game.COLLISION_RADIUS)
                {
                    collider.collide(animal);
                }
            }
            for (Item item : items)
            {
                distance = collider.getCoordinates().distanceFrom(item.getCoordinates());
                if (distance <= Game.COLLISION_RADIUS)
                {
                    collider.collide(item);
                }
            }
            for (Grass grass : grasses)
            {
                distance = collider.getCoordinates().distanceFrom(grass.getCoordinates());
                if (distance <= Game.COLLISION_RADIUS)
                {
                    collider.collide(grass);
                }
            }
        }
    }

    private void removeDeadEntities()
    {
        for (Animal animal : animals)
        {
            if (animal.notExists())
            {
                animals.remove(animal);
            }
        }
        for (Grass grass : grasses)
        {
            if (grass.notExists())
            {
                grass.die();
            }
        }
        for (Item item : items)
        {
            if (item.notExists())
            {
                items.remove(item);
            }
        }
    }

}
