package models;

import models.animal.DomesticAnimal;

import java.util.HashMap;

public class Mission
{
    private int money;
    HashMap<DomesticAnimal.Type, Integer> animalObjectives;
    HashMap<Item.Type, Integer> ItemObjective;

    public Mission(int money, HashMap<DomesticAnimal.Type, Integer> animalObjectives,
                   HashMap<Item.Type, Integer> itemObjective) {
        this.money = money;
        this.animalObjectives = animalObjectives;
        ItemObjective = itemObjective;
    }

    public boolean isAccomplished(int money, HashMap<DomesticAnimal.Type, Integer> animalCurrentState,
                                  HashMap<Item.Type, Integer> ItemCurentState )
    {
        if (this.money > money) {
            return false;
        }
        for (DomesticAnimal.Type type : animalCurrentState.keySet()) {
            if (animalObjectives.containsKey(type)) {
                animalObjectives.get(type) > animalCurrentState.get(type) {
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
        return true;
    }
}
