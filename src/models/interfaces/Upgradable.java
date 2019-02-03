package models.interfaces;


import java.io.IOException;

public interface Upgradable
{
    void upgrade() throws Exception;
    int getUpgradeCost() throws Exception;
    int getLevel();

}
