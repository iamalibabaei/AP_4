package models.misc;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.InGameController;
import models.Map;
import models.objects.Item;
import models.objects.animals.Animal;
import models.objects.animals.Cat;
import models.objects.animals.Dog;
import models.objects.animals.DomesticAnimal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Mission
{
    private static InGameController game = InGameController.getInstance();
    private int moneyObjective;
    private HashMap<DomesticAnimal.Type, Integer> animalObjectives;
    private HashMap<Item.Type, Integer> ItemObjective;

    public int getMoneyObjective() {
        return moneyObjective;
    }

    public HashMap<DomesticAnimal.Type, Integer> getAnimalObjectives() {
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

    public HashMap<DomesticAnimal.Type, Integer> getAnimalAtBeginning() {
        return animalAtBeginning;
    }

    public int getMoneyAtBeginning() {
        return moneyAtBeginning;
    }
}
