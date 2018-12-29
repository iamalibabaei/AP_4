package models;

import models.animal.Cat;
import models.animal.Dog;
import models.animal.DomesticAnimal;
import models.map.Cell;
import models.map.Map;

import java.util.HashMap;

public class Mission
{
    private Game game;
    private int money;
    HashMap<DomesticAnimal.Type, Integer> animalObjectives;
    HashMap<Item.Type, Integer> ItemObjective;
    boolean dog,cat;

    public Mission(int money, HashMap<DomesticAnimal.Type, Integer> animalObjectives,
                   HashMap<Item.Type, Integer> itemObjective) {
        this.money = money;
        this.animalObjectives = animalObjectives;
        ItemObjective = itemObjective;
    }

    public Mission(Game game) {
        this.game = game;
    }

    public boolean isAccomplished()
    {
        boolean hasDog = false, hasCat = false;
        int gameMoney = 0;// game.getMoney();
        Map map = null;// game.getMap;
        HashMap<DomesticAnimal.Type, Integer> animalCurrentState = new HashMap<>();
        HashMap<Item.Type, Integer> ItemCurentState = new HashMap<>();
        for (Cell[] cells: map.getCells()) {
            for (Cell cell : cells) {
                for (Entity entity : cell.getEntities()) {
                    if (entity instanceof Item) {
                        if(ItemCurentState.containsKey(((Item) entity).getType())) {
                            ItemCurentState.put(((Item) entity).getType(),
                                    ItemCurentState.get(((Item) entity).getType()) + 1);
                        } else {
                            ItemCurentState.put(((Item) entity).getType(), 1);
                        }

                    }
                    if (entity instanceof DomesticAnimal) {
                        if (animalCurrentState.containsKey(((DomesticAnimal) entity).getType)) {
                            animalCurrentState.put(((DomesticAnimal) entity).getType,
                                    animalCurrentState.get(((DomesticAnimal) entity).getType) + 1);
                        } else {
                            animalCurrentState.put(((DomesticAnimal) entity).getType, 1);
                        }
                    }
                    if (entity instanceof Dog) {
                        hasDog = true;
                    }
                    if (entity instanceof Cat) {
                        hasCat = true;
                    }

                }
            }
        }

        return checkAccomplishment(gameMoney, animalCurrentState, ItemCurentState);
    }

    public boolean checkAccomplishment(int money, HashMap<DomesticAnimal.Type, Integer> animalCurrentState,
                                       HashMap<Item.Type, Integer> ItemCurentState ) {
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
        return true;
    }

}
