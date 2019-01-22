package models.misc;

import models.Game;
import models.Map;
import models.objects.Item;
import models.objects.animal.Animal;
import models.objects.animal.Cat;
import models.objects.animal.Dog;
import models.objects.animal.DomesticAnimal;

import java.util.HashMap;

public class Mission
{
    private Game game;
    private int money;
    HashMap<DomesticAnimal.Type, Integer> animalObjectives;
    HashMap<Item.Type, Integer> ItemObjective;
    boolean dog,cat;

    public int getMoney() {
        return money;
    }

    public HashMap<DomesticAnimal.Type, Integer> getAnimalObjectives() {
        return animalObjectives;
    }

    public Mission(int money, HashMap<DomesticAnimal.Type, Integer> animalObjectives,
                   HashMap<Item.Type, Integer> itemObjective, boolean dog, boolean cat) {
        this.money = money;
        this.animalObjectives = animalObjectives;
        ItemObjective = itemObjective;
        this.dog = dog;
        this.cat = cat;
    }

    public Mission(Game game) {
        this.game = game;
    }

    public boolean isAccomplished()
    {
        boolean hasDog = false, hasCat = false;
        int gameMoney = game.getMoney();
        Map map = game.getMap();
        HashMap<DomesticAnimal.Type, Integer> animalCurrentState = new HashMap<>();
        HashMap<Item.Type, Integer> itemCurrentState = new HashMap<>();

        for (Item item : map.getItems()) {
            itemCurrentState.put(item.type, itemCurrentState.getOrDefault(item.type, 0) + 1);
        }

        for (Animal animal : map.getAnimals()) {
            if (animal instanceof Dog) {
                hasDog = true;
            } else if (animal instanceof Cat) {
                hasCat = true;
            } else if (animal instanceof DomesticAnimal) {
                animalCurrentState.put(((DomesticAnimal) animal).type
                        , animalCurrentState.getOrDefault(((DomesticAnimal) animal).type, 0) + 1);
            }

        }

        return checkAccomplishment(gameMoney, animalCurrentState, itemCurrentState, hasCat, hasDog);
    }

    public boolean checkAccomplishment(int money, HashMap<DomesticAnimal.Type, Integer> animalCurrentState,
                                       HashMap<Item.Type, Integer> ItemCurentState, boolean hasCat, boolean hasDog) {
        if (this.money > money) {
            return false;
        }
        for (DomesticAnimal.Type type : animalCurrentState.keySet()) {
            if (animalObjectives.containsKey(type)) {
                if (animalObjectives.get(type) > animalCurrentState.get(type)) {
                    return false;
                }
            }
        }

        for (Item.Type type : ItemCurentState.keySet()) {
            if (ItemObjective.containsKey(type)) {
                if (ItemObjective.get(type) > ItemCurentState.get(type)) {
                    return false;
                }
            }
        }
        if (dog) {
            if (!hasDog) {
                return false;
            }
        }
        if (cat) {
            if (!hasCat) {
                return false;
            }
        }
        return true;
    }

}
