package models.interfaces;

import models.exceptions.MaxlevelException;

public interface Upgradable
{

    void upgrade() throws MaxlevelException;

}
