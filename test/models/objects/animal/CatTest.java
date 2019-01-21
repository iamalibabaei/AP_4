package models.objects.animal;

import models.objects.Item;
import models.buildings.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.map.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatTest
{
    private static Cat cat1;
    private static Map map;
    private static Warehouse warehouse;

    @BeforeAll
        static void setup(){
            cat1 = new Cat(7, 7, Animal.Type.CAT, map, warehouse);
            map = new Map();
            warehouse = new Warehouse();
    }

    @Test
        void basics(){
            assertTrue(cat1.getBuyCost() == 2500);
            assertTrue(cat1.getUpgradeCost() == 200);
        }//OK

    @Test
        void shouldNotUpgradeBeyondMaxLevel(){
            assertThrows(AlreadyAtMaxLevelException.class,
                    () ->
                    {
                        cat1.upgrade();
                        cat1.getUpgradeCost();
                        cat1.upgrade();
                    });
        }//It's OK. // both getUpgradeCost() and upgrade() checked separately

    @Test
        void targetSetting(){
            map.addToMap(new Item(3, 3, Item.Type.EGG));
            map.addToMap(new Item(5, 5, Item.Type.EGG));
    //        map.getCell(3,3).addEntity(new Item(3, 3, Item.Type.EGG));
    //        map.getCell(5,5).addEntity(new Item(5, 5, Item.Type.EGG));
            cat1.setTarget();
            assertTrue(cat1.target.getX() == 3);
            assertTrue(cat1.target.getY() == 3);
            cat1.upgrade();
            cat1.setX(5);
            cat1.setY(5);
            assertTrue(cat1.target.getX() == 5);
            assertTrue(cat1.target.getY() == 5);
    }

    @Test
        void store(){
            map.addToMap(new Item(3, 3, Item.Type.EGG));
            map.addToMap(new Item(5, 5, Item.Type.EGG));
            map.addToMap(new Item(3, 3, Item.Type.EGG));
            map.addToMap(new Item(5, 5, Item.Type.MILK));
            map.addToMap(new Item(3, 3, Item.Type.FLOUR));
            map.addToMap(new Item(5, 5, Item.Type.FABRIC));
            map.addToMap(new Item(3, 3, Item.Type.EGG));
            map.addToMap(new Item(5, 5, Item.Type.COW));
            cat1.setTarget();

    }




}