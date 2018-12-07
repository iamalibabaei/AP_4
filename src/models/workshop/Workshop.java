package models.workshop;

import models.Item;
import models.interfaces.Upgradable;

import java.util.HashMap;

public class Workshop implements Upgradable
{
    private HashMap<Item, Integer> requirements;
    private int level;
    private Task task;

        

    @Override
    public void upgrade() {

    }
}
