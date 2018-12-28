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
        EGG(1, 20, 10), MILK(10, 2000, 1000), WOOL(5, 200, 100), DRIED_EGG(4, 100, 50), CAKE(5, 200, 100),
        CAGED_LION(20, 150, 150), CAGED_GRIZZLY(7, 80, 80);

        public final int occupiedSpace, buyCost, sellMoney;

        Type(int occupiedSpace, int buyCost, int sellMoney)
        {
            this.occupiedSpace = occupiedSpace;
            this.buyCost = buyCost;
            this.sellMoney = sellMoney;
        }
    }

}
