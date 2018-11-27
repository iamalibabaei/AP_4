package models.workshop;

import models.Item;
import models.interfaces.Upgradable;

import java.util.HashMap;

public class Workshop implements Upgradable
{
    private HashMap<Item, Integer> requirements;
    private int level;
    private Task task;
    boolean isUpgrading;

    @Override
    public void issueUpgrade()
    {

    }

    @Override
    public void upgrade()
    {

    }

}
