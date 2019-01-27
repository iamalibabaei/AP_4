package models;

import controller.InGameController;
import models.buildings.Warehouse;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Map implements Time
{
    public static final double WIDTH = 30.0, HEIGHT = 30.0;
    private static Map instance = new Map();
    private List<Animal> animals;
    private List<Grass> grasses;
    private List<Item> items;

    private Map()
    {
        animals = new ArrayList<>();
        grasses = new ArrayList<>();
        items = new ArrayList<>();
    }

    public static Map getInstance()
    {
        return instance;
    }

    public List<Item> store(List<Item> items)
    {
        return items;
    }

    public Iterable<Animal> getAnimals()
    {
        return animals;
    }

    public Iterable<Grass> getGrasses()
    {
        return grasses;
    }

    public void putGrass(Point point)
    {
        grasses.add(new Grass(point));
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void removeItems(Collection<Item> removedItems)
    {
        for (Item item : removedItems)
        {
            item.die();
        }
    }

    public void cage(Point point)
    {
        for (Animal animal : animals)
        {
            double distance = animal.getCoordinates().distanceFrom(point);
            if (distance <= InGameController.COLLISION_RADIUS && animal instanceof WildAnimal)
            {
                ((WildAnimal) animal).cage();
            }
        }
    }

    public void plant(Point point)
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
        for (Animal collider : animals)
        {
            for (Animal animal : animals)
            {
                if (collider.collidesWith(animal))
                {
                    collider.collide(animal);
                }
            }
            for (Item item : items)
            {
                if (collider.collidesWith(item))
                {
                    collider.collide(item);
                }
            }
            for (Grass grass : grasses)
            {
                if (collider.collidesWith(grass))
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

    public void addAnimal(Animal.Type type)
    {
        Point coordinates = Point.randomPoint(WIDTH, HEIGHT);
        switch (type)
        {
            case CAT:
                animals.add(new Cat(coordinates, type));
                break;
            case DOG:
                animals.add(new Dog(coordinates, type));
                break;
            case SHEEP:
                animals.add(new DomesticAnimal(coordinates, type));
                break;
            case HEN:
                animals.add(new DomesticAnimal(coordinates, type));
                break;
            case COW:
                animals.add(new DomesticAnimal(coordinates, type));
                break;
        }
    }

    public List<Item> getNearbyItems(Point point)
    {
        List<Item> nearbyItems = new ArrayList<>();
        for (Item item : items)
        {
            if (point.distanceFrom(item) <= InGameController.COLLISION_RADIUS)
            {
                nearbyItems.add(item);
            }
        }
        return nearbyItems;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public void addGrass(Grass grass)
    {
        grasses.add(grass);
    }

}
