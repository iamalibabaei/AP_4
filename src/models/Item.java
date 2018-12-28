package models;

public class Item extends Entity
{
    Type type;

    public Item(int x, int y, Type type)
    {
        super(x, y);
        this.type = type;
    }

    public enum Type
    {
        EGG(1, 20, 10), MILK(10, 2000, 1000),
        WOOL(5, 200, 100), DRIED_EGG(4, 100, 50),
        CAKE(5, 200, 100), CAGED_LION(20, 150, 150),
        CAGED_GRIZZLY(7, 80, 80), FLOUR(2, 20, 10),
        SEWING(3, 300, 150), FABRIC(6, 400, 300),
        PLUME(2, 200, 100), FLOURY_CAKE(6, 400, 200),
        CARNIVAL_DRESS(8, 1400, 1300);

        public final int occupiedSpace, buyCost, sellMoney;

        Type(int occupiedSpace, int buyCost, int sellMoney)
        {
            this.occupiedSpace = occupiedSpace;
            this.buyCost = buyCost;
            this.sellMoney = sellMoney;
        }
    }

}
