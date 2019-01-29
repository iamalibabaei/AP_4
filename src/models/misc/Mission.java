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
import java.util.Scanner;

public class Mission
{
    public static final String DEFAULT_MISSION_PATH = "res/missions";
    private int moneyObjective;
    private EnumMap<Animal.Type, Integer> animalObjectives;
    private EnumMap<Item.Type, Integer> ItemObjective;

    public int getMoneyObjective() {
        return moneyObjective;
    }

    public EnumMap<Animal.Type, Integer> getAnimalObjectives() {
        return animalObjectives;
    }

    public EnumMap<Item.Type, Integer> getItemObjective() {
        return ItemObjective;
    }

    public boolean isDog() {
        return dog;
    }

    public boolean isCat() {
        return cat;
    }

    private boolean dog, cat;
    private EnumMap<Animal.Type, Integer> animalAtBeginning;
    private int moneyAtBeginning;

    public Mission(int money, EnumMap<Animal.Type, Integer> animalObjectives,
                   EnumMap<Item.Type, Integer> itemObjective, boolean dog, boolean cat, EnumMap<Animal.Type, Integer> animalAtBeginning, int moneyAtBeginning)
    {
        moneyObjective = money;
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
        System.out.println("before json");
        return yaGson.fromJson(new Scanner(fileReader).nextLine(), Mission.class);
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

    public EnumMap<Animal.Type, Integer> getAnimalAtBeginning() {
        return animalAtBeginning;
    }

    public int getMoneyAtBeginning() {
        return moneyAtBeginning;
    }
}
