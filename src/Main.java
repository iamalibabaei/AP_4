import com.gilecode.yagson.YaGson;
import controller.Controller;
import models.buildings.Workshop;
import models.misc.Mission;
import models.objects.Item;
import models.objects.Point;
import models.objects.animal.Animal;
import models.objects.animal.DomesticAnimal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        //Controller.getInstance().startGame();
        serialize();

    }


    private static void serialize(){
        HashMap<Item.Type, Integer> ItemObjective = new HashMap<>();
        ItemObjective.put(Item.Type.EGG, 15);
        ItemObjective.put(Item.Type.DRIED_EGG,10);
        HashMap<DomesticAnimal.Type, Integer> animalObjectives = new HashMap<>();
        animalObjectives.put(DomesticAnimal.Type.HEN, 10);
        animalObjectives.put(DomesticAnimal.Type.SHEEP, 5);
        animalObjectives.put(DomesticAnimal.Type.COW, 2);
        Mission mission = new Mission(1000, animalObjectives, ItemObjective, false, false);
        YaGson yaGson = new YaGson();
        String json = yaGson.toJson(mission);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("res//missions//mission2.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Formatter formatter = new Formatter(fileWriter);
        formatter.format(json).flush();


    }

}
