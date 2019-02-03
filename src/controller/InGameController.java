package controller;

import com.gilecode.yagson.YaGson;
import javafx.animation.AnimationTimer;
import models.Map;
import models.account.Account;
import models.buildings.Warehouse;
import models.buildings.Well;
import models.buildings.Workshop;
import models.exceptions.InvalidArgumentException;
import models.exceptions.Messages;
import models.interfaces.Time;
import models.misc.Mission;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.Animal;
import models.objects.animals.Cat;
import models.objects.animals.Dog;
import models.objects.animals.DomesticAnimal;
import models.transportation.Helicopter;
import models.transportation.Truck;
import view.MainView;
import view.gameScene.MapView;
import view.gameScene.View;
import view.utility.constants.JsonAddresses;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// todo you have to buy helicopter in the beginning
// todo multiple trucks and helicopters

public class InGameController implements Time
{
    public static final double COLLISION_RADIUS = 2.0;
    private static InGameController instance = new InGameController();
    private final int FPS = 60, MILI_SECOND_PER_FRAME = 1000 / FPS;
    private Integer money;
    private Map map;
    private Warehouse warehouse;
    private Well well;
    private Truck truck;
    private Helicopter helicopter;
    private List<Workshop> workshops;
    private Mission mission;
    private ArrayList<String> availableWorkshops;
    private Account account;
    private AnimationTimer timer;

    public static boolean loadGame(Mission mission, Account account) throws Exception
    {
        YaGson yaGson = new YaGson();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(JsonAddresses.SAVE_GAME_ROOT + mission.getName() + '@' + account.getName()+".json");
        } catch (FileNotFoundException e) {
            throw new Exception(Messages.SAVED_GAME_NOT_FOUND);
        }
        Scanner scanner = new Scanner(fileReader);
        String json = scanner.nextLine();
        InGameController game = yaGson.fromJson(json, InGameController.class);
        instance = game;
        MainView.getInstance().startGame(mission);
        return true;
    }

    private InGameController()
    {
        availableWorkshops = new ArrayList<>();
        map = Map.getInstance();
        warehouse = Warehouse.getInstance();
        well = Well.getInstance();
        workshops = new ArrayList<>();
        truck = Truck.getInstance();
        helicopter = Helicopter.getInstance();
        money = 0;
        timer = new AnimationTimer() {
            private long lastTime = 0;
            private double time = 0;
            private long second = 1000000000;
            private long nextTurnTime;
            @Override
            public void handle(long now) {
                if (lastTime == 0)
                    lastTime = now;
                if (now > lastTime + (second)) {
                    lastTime = now;
                    time += 1;
                }

                if (nextTurnTime == 0) {
                    nextTurnTime = now;
                }

                if (now > nextTurnTime + MILI_SECOND_PER_FRAME) {
                    nextTurnTime = now;
                    nextTurn();
                    MapView.getInstance().nextTurn();

                }
            }
        };
    }

    public static InGameController getInstance() {
        return instance;
    }

    public void buyAnimal(Animal.Type type)
    {
        map.addAnimal(type);
        withdrawMoney(type.BUY_COST);
    }

    public void withdrawMoney(int cost)
    {
        if (money < cost)
        {
            throw new AssertionError();
        }
        money -= cost;
        View.getInstance().getMoney();

    }

    public void pickUp(Point point) throws IOException
    {
        List<Item> nearbyItems = map.getNearbyItems(point);
        List<Item> storedItems = warehouse.store(nearbyItems);
        map.removeItems(storedItems);
    }

    public void store(List<Item> items) throws IOException
    {
        List<Item> storedItems = warehouse.store(items);
        map.removeItems(storedItems);
    }

    public void cage(Point point)
    {
        map.cage(point);
    }

    public void startWorkshop(String workshopName) throws IOException
    {
        for (Workshop workshop : workshops)
        {
            if (workshop.name.equals(workshopName))
            {
                int factor = warehouse.moveToWorkshop(workshop.inputs, workshop.getMaxProductionFactor());
                workshop.startWorking(factor);
            }
        }
    }

    public void upgrade(String parameter) throws IOException
    {
        // todo
    }

    public void refillWell() throws Exception
    {
        int cost = Well.REFILL_COST[well.getLevel()];
        if (money < cost)
        {
            throw new IOException(Messages.NOT_ENOUGH_MONEY);
        }
        withdrawMoney(cost);
        well.issueRefill();
    }

    public void upgradeWell() throws Exception
    {
        int cost = well.getUpgradeCost();
        if (money < cost)
        {
            throw new IOException(Messages.NOT_ENOUGH_MONEY);
        }
        withdrawMoney(cost);
        well.upgrade();
    }

    public void plant(Point point) throws Exception
    {
        well.extractWater();
        map.plant(point);
    }

    public void sendHelicopter() throws IOException
    {
        int cost = helicopter.computePrice();
        if (money < cost)
        {
            throw new IOException(Messages.NOT_ENOUGH_MONEY);
        }
        withdrawMoney(cost);
        helicopter.go();
    }

    public void addWorkshop(Workshop workshop, int place)
    {
        workshops.add(workshop);
        View.getInstance().drawWorkshop(place, workshop);
    }

    public void clearStash(String transporterName) throws InvalidArgumentException
    {
        if (transporterName.equals(helicopter.getName()))
        {
            helicopter.clearStash();
        } else if (transporterName.equals(truck.getName()))
        {
            truck.clearStash();
        } else
            throw new InvalidArgumentException();
    }

    public void removeFromStash(String transporterName, String itemName) throws InvalidArgumentException
    {
        Item.Type item = Item.Type.NONE;
        for (Item.Type type : Item.Type.values())
        {
            if (type.toString().equals(itemName))
            {
                item = type;
                break;
            }
        }
        if (item == Item.Type.NONE)
            throw new InvalidArgumentException();
        if (helicopter.getName().equals(transporterName))
        {
            helicopter.removeFromList(item, 1);
        } else if (truck.getName().equals(transporterName))
        {
            truck.removeFromList(item, 1);
        } else
        {
            throw new InvalidArgumentException();
        }
    }

    public void sendTruck()
    {
        truck.go();
        warehouse.remove(truck.getList());
    }

    public void addToStash(String transporterName, String itemName, int count) throws IOException
    {
        Item.Type item = Item.Type.NONE;
        for (Item.Type type : Item.Type.values())
        {
            if (type.toString().equals(itemName))
            {
                item = type;
                break;
            }
        }
        if (item == Item.Type.NONE)
            throw new RuntimeException();
        if (helicopter.getName().equals(transporterName))
        {
            helicopter.addToList(item, count);
        } else if (truck.getName().equals(transporterName))
        {
            truck.addToList(item, count);
        } else
        {
            throw new RuntimeException();
        }
    }

    @Override
    public void nextTurn()
    {
        map.nextTurn();
        truck.nextTurn();
        helicopter.nextTurn();
        for (Workshop workshop : workshops)
        {
            workshop.nextTurn();
        }
    }

    public void loadMission(){
        moneyDeposit(mission.getMoneyAtBeginning());
        for (Animal.Type animal : mission.getAnimalObjectives().keySet()) {
            for (int i = 0; i < mission.getAnimalAtBeginning().get(animal); i++) {
                System.out.println(animal.name());
                Map.getInstance().addAnimal(animal);
            }
        }
        InGameController.getInstance().moneyDeposit(mission.getMoneyAtBeginning());
    }

    public void moneyDeposit(int amount) {
        money += amount;
    }

    public void startGame(Mission mission) {
        this.mission = mission;
        map = Map.getInstance();

        loadMission();
        timer.start();

    }

    private boolean isAccomplished()
    {
        boolean hasDog = false, hasCat = false;
        int gameMoney = getMoney();
        Map map = Map.getInstance();
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

    public Integer getMoney()
    {
        return money;
    }

    private boolean checkAccomplishment(int money, HashMap<DomesticAnimal.Type, Integer> animalCurrentState,
                                        HashMap<Item.Type, Integer> ItemCurrentState, boolean hasCat, boolean hasDog)
    {
        if (mission.getMoneyObjective() > money)
        {
            return false;
        }
        for (DomesticAnimal.Type type : animalCurrentState.keySet())
        {
            if (mission.getAnimalObjectives().containsKey(type))
            {
                if (mission.getAnimalObjectives().get(type) > animalCurrentState.get(type))
                {
                    return false;
                }
            }
        }

        for (Item.Type type : ItemCurrentState.keySet())
        {
            if (mission.getItemObjective().containsKey(type))
            {
                if (mission.getItemObjective().get(type) > ItemCurrentState.get(type))
                {
                    return false;
                }
            }
        }
        if (mission.isDog() && !hasDog)
        {
            return false;
        }
        return !mission.isCat() || hasCat;
    }

    public void setMission(Mission mission)
    {
        this.mission = mission;
    }

    public ArrayList<String> getAvailableWorkshops()
    {
        return availableWorkshops;
    }

    public void saveAndQuit() {
        timer.stop();
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(JsonAddresses.SAVE_GAME_ROOT + mission.getName() + '@' + account.getName()+".json");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        YaGson yaGson = new YaGson();
        Formatter formatter = new Formatter(fileWriter);
        formatter.format(yaGson.toJson(this));
        formatter.flush();
        MainView.getInstance().goToMenu();
    }


    public void pauseGame(){
        timer.stop();
    }
    public void resumeGame() {
        timer.start();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
