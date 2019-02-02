package models;

import models.buildings.Well;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        assertThrows(IOException.class,
                () ->
                {
                    well.upgrade();
                    well.upgrade();
                    well.upgrade();
                });
    }

}