package models;

import models.exceptions.AlreadyAtMaxLevelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WellTest
{
    private Well well = new Well();

    @Test
    public void shouldNotUpgradeBeyondLevel2()
    {
        assertThrows(AlreadyAtMaxLevelException.class,
                () ->
                {
                    well.upgrade();
                    well.upgrade();
                    well.upgrade();
                });
    }

}