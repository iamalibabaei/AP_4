package models;

import models.interfaces.Storable;

public class Item extends Entity implements Storable
{
    private final int SELL_MONEY, OCCUPATION_SPACE;
    Type type;

    public Item(Type type)
    {
        this.type = type;
        this.SELL_MONEY = 0;
        this.OCCUPATION_SPACE = 0;
    }

    @Override
    public int getSellMoney() {
        return 0;
    }

    @Override
    public int getOccupationSpace()
    {
        return 0;
    }

    public enum Type
    {
        //todo inja bayad puste shir va khers ham ezafe shavad
        // chon vaqti mikhayim puste shir befrushim miad az in ja gheimato mohasebe mikone
        EGG(0), MILK(1), WOOL(2), EGG_POWDER(3), COOKIE(4), CAKE(5);
        private final int type;

        Type(int status)
        {
            this.type = status;
        }

        public int getType() {
            return type;
        }
        public static Entity TYPE_INDEXED(int num) {
            Entity[] entitys = new Entity[] { new Item(EGG)
                            , new Item(MILK), new Item(WOOL), new Item(EGG_POWDER), new Item(COOKIE), new Item(CAKE) };
            return entitys[num];
        }

        public static Type[] ITEM_INDEXED = new Type[] { EGG, MILK, WOOL, EGG_POWDER, COOKIE, CAKE };
    }

}
