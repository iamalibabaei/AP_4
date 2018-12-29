package models;

import java.util.HashMap;

public class Mission
{
    private int money;
    private HashMap<Entity, Integer> objectives;

    public HashMap<Entity, Integer> getObjectives()
    {
        return objectives;
    }

    public boolean isAccomplished(int money, HashMap<Entity, Integer> currentState)
    {
        return money > this.money && currentState.equals(objectives);
    }

}
