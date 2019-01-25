package models.misc;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.Game;
import models.Map;
import models.objects.Item;
import models.objects.animal.Animal;
import models.objects.animal.Cat;
import models.objects.animal.Dog;
import models.objects.animal.DomesticAnimal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Mission
{
    private static Game game;
    private int moneyObjective;
    private HashMap<DomesticAnimal.Type, Integer> animalObjectives;
    private HashMap<Item.Type, Integer> ItemObjective;
    private boolean dog, cat;
    private HashMap<DomesticAnimal.Type, Integer> animalAtBeginning;
    private int moneyAtBeginning;

    public Mission(int money, HashMap<DomesticAnimal.Type, Integer> animalObjectives,
                   HashMap<Item.Type, Integer> itemObjective, boolean dog, boolean cat, HashMap<Animal.Type, Integer> animalAtBeginning, int moneyAtBeginning)
    {
        this.moneyObjective = money;
        this.animalObjectives = animalObjectives;
        ItemObjective = itemObjective;
        this.dog = dog;
        this.cat = cat;
        this.animalAtBeginning = animalAtBeginning;
        this.moneyAtBeginning = moneyAtBeginning;
    }

    public static Mission loadJson(String jsonAddress) throws FileNotFoundException {
        YaGsonBuilder yaGsonBuilder = new YaGsonBuilder();
        YaGson yaGson = yaGsonBuilder.create();
        FileReader fileReader = new FileReader(jsonAddress);
        Mission mission = yaGson.fromJson(new Scanner(fileReader).nextLine(), Mission.class);
        return mission;
    }

    public static ArrayList<String> getAllMission(){
        File folder = new File("res\\missions");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> usersName = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            String name = listOfFiles[i].getName();
            name = name.substring(0, name.indexOf('.'));
            usersName.add(name);
        }
        return usersName;
    }

    public boolean isAccomplished()
    {
        boolean hasDog = false, hasCat = false;
        int gameMoney = game.getMoney();
        Map map = game.getMap();
        HashMap<DomesticAnimal.Type, Integer> animalCurrentState = new HashMap<>();
        HashMap<Item.Type, Integer> itemCurrentState = new HashMap<>();

        for (Item item : map.getItems())
        {
            itemCurrentState.put(item.type, itemCurrentState.getOrDefault(item.type, 0) + 1);
        }

        for (Animal animal : map.getAnimals())
        {
            if (animal instanceof Dog)
            {
                hasDog = true;
            } else if (animal instanceof Cat)
            {
                hasCat = true;
            } else if (animal instanceof DomesticAnimal)
            {
                animalCurrentState.put(((DomesticAnimal) animal).type
                        , animalCurrentState.getOrDefault(((DomesticAnimal) animal).type, 0) + 1);
            }

        }
        return checkAccomplishment(gameMoney, animalCurrentState, itemCurrentState, hasCat, hasDog);
    }

    private boolean checkAccomplishment(int money, HashMap<DomesticAnimal.Type, Integer> animalCurrentState,
                                        HashMap<Item.Type, Integer> ItemCurentState, boolean hasCat, boolean hasDog)
    {
        if (this.moneyObjective > money)
        {
            return false;
        }
        for (DomesticAnimal.Type type : animalCurrentState.keySet())
        {
            if (animalObjectives.containsKey(type))
            {
                if (animalObjectives.get(type) > animalCurrentState.get(type))
                {
                    return false;
                }
            }
        }

        for (Item.Type type : ItemCurentState.keySet())
        {
            if (ItemObjective.containsKey(type))
            {
                if (ItemObjective.get(type) > ItemCurentState.get(type))
                {
                    return false;
                }
            }
        }
        if (dog && !hasDog)
        {
            return false;
        }
        return !cat || hasCat;
    }

    public HashMap<DomesticAnimal.Type, Integer> getAnimalAtBeginning() {
        return animalAtBeginning;
    }

    public int getMoneyAtBeginning() {
        return moneyAtBeginning;
    }
}
