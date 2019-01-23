package models;

import models.buildings.Well;
import models.exceptions.AlreadyAtMaxLevelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WellTest
{
    private static Well well;

    @BeforeAll
    static void setUp()
    {
        well = Well.getInstance();
    }

    @Test
    void shouldNotUpgradeBeyondLevel2()
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