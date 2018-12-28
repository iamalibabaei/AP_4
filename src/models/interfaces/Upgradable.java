package models.interfaces;

import models.exceptions.AlreadyAtMaxLevelException;

public interface Upgradable
{
    void upgrade() throws AlreadyAtMaxLevelException;
    int getUpgradeCost() throws AlreadyAtMaxLevelException;

}
