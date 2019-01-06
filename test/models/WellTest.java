package models;

import models.exceptions.AlreadyAtMaxLevelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WellTest
{
    private Well well;

    @BeforeAll
    void setUp()
    {
        well = new Well();
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