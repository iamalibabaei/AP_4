package models.misc;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import models.objects.Item;
import models.objects.animals.Animal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Mission
{
    public static final String DEFAULT_MISSION_PATH = "res/missions";
    private int moneyObjective;
    private HashMap<Animal.Type, Integer> animalObjectives;
    private HashMap<Item.Type, Integer> ItemObjective;
    private boolean dog, cat;
    private HashMap<Animal.Type, Integer> animalAtBeginning;
    private int moneyAtBeginning;


    public Mission(int money, HashMap<Animal.Type, Integer> animalObjectives,
                   HashMap<Item.Type, Integer> itemObjective, boolean dog, boolean cat, HashMap<Animal.Type, Integer> animalAtBeginning, int moneyAtBeginning)
    {
        moneyObjective = money;
        this.animalObjectives = animalObjectives;
        ItemObjective = itemObjective;
        this.dog = dog;
        this.cat = cat;
        this.animalAtBeginning = animalAtBeginning;
        this.moneyAtBeginning = moneyAtBeginning;
    }





    public int getMoneyObjective() {
        return moneyObjective;
    }

    public HashMap<Animal.Type, Integer> getAnimalObjectives() {
        return animalObjectives;
    }

    public HashMap<Item.Type, Integer> getItemObjective() {
        return ItemObjective;
    }

    public boolean isDog() {
        return dog;
    }

    public boolean isCat() {
        return cat;
    }


    public HashMap<Animal.Type, Integer> getAnimalAtBeginning() {
        return animalAtBeginning;
    }

    public int getMoneyAtBeginning() {
        return moneyAtBeginning;
    }

    public static Mission loadJson(String jsonAddress) throws FileNotFoundException {
        YaGson yaGson = new YaGson();
        FileReader fileReader = new FileReader(jsonAddress);
        Scanner scanner = new Scanner(fileReader);
        String json = scanner.nextLine();
        Mission mission = yaGson.fromJson(json, Mission.class);
        return mission;
    }

    public static ArrayList<String> loadDefaultMissions()
    {
        File folder = new File(DEFAULT_MISSION_PATH);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> usersName = new ArrayList<>();
        if (listOfFiles != null)
        {
            for (File listOfFile : listOfFiles)
            {
                String name = listOfFile.getName();
                name = name.substring(0, name.indexOf('.'));
                usersName.add(name);
            }
        }
        return usersName;
    }

}
