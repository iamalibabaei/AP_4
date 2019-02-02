package models.interfaces;


import java.io.IOException;

public interface Upgradable
{
    void upgrade() throws IOException;
    int getUpgradeCost() throws IOException;
    int getLevel();

}
