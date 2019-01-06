package models.animal;

import models.Entity;
import models.Point;
import models.map.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private static DomesticAnimal hen1;
    private static DomesticAnimal hen2;
    private static DomesticAnimal cow1;

    @BeforeAll
        private static void setup(){
            Map map = new Map();
            hen1 = new DomesticAnimal(1, 1, map, DomesticAnimal.Type.HEN);
            hen1.target = new Point(-1, -1);
            hen2 = new DomesticAnimal(2, 5, map, DomesticAnimal.Type.HEN);
            cow1 = new DomesticAnimal(12,7, map, DomesticAnimal.Type.COW);
    }

    @Test
    void moveCorrectly(){
        hen1.target.setX(5);
        hen1.target.setY(7);

        hen1.move();
        assertTrue(hen1.getX() == 2);
        assertTrue(hen1.getY() == 2);

        hen1.move();
        assertTrue(hen1.getX() == 3);
        assertTrue(hen1.getY() == 3);

        hen1.move();
        assertTrue(hen1.getX() == 4);
        assertTrue(hen1.getY() == 4);

        hen1.move();
        assertTrue(hen1.getX() == 5);
        assertTrue(hen1.getY() == 5);

        hen1.move();
        assertTrue(hen1.getX() == 5);
        assertTrue(hen1.getY() == 6);

        hen1.move();
        assertTrue(hen1.getX() == 5);
        assertTrue(hen1.getY() == 7);

        hen1.target.setX(12);
        hen1.target.setY(12);

        hen1.move();
        assertTrue(hen1.getX() == 6);
        assertTrue(hen1.getY() == 8);

        hen1.move();
        assertTrue(hen1.getX() == 7);
        assertTrue(hen1.getY() == 9);

        hen1.move();
        assertTrue(hen1.getX() == 8);
        assertTrue(hen1.getY() == 10);

        hen1.move();
        assertTrue(hen1.getX() == 9);
        assertTrue(hen1.getY() == 11);

        hen1.move();
        assertTrue(hen1.getX() == 10);
        assertTrue(hen1.getY() == 12);

        hen1.move();
        assertTrue(hen1.getX() == 11);
        assertTrue(hen1.getY() == 12);

        hen1.move();
        assertTrue(hen1.getX() == 12);
        assertTrue(hen1.getY() == 12);

        hen1.target = null;

        hen1.move();
        assertTrue(hen1.getX() >= 11 && hen1.getX() <= 13);
        assertTrue(hen1.getY() >= 11 && hen1.getY() <= 13);
        }// moves correctly



}