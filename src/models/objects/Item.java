package models.objects;

import javafx.scene.text.Text;
import view.utility.Utility;

public class Item extends Entity
{
    public final Type type;

    public Item(Point point, Type type)
    {
        super(point);
        this.type = type;
        text = new Text(type.name());
    }

    @Override
    protected void loadAnimation()
    {

    }

    @Override
    public void updateImageView() {
        imageView = Utility.getImageView("res/graphicAssets/item/"+ this.type.name()+".png");
    }

    public enum Type
    {
        EGG(1, 20, 10), MILK(10, 2000, 1000),
        WOOL(5, 200, 100), DRIED_EGG(4, 100, 50),
        CAKE(5, 200, 100), CAGED_LION(20, -1, 150),
        CAGED_GRIZZLY(7, -1, 80), FLOUR(2, 20, 10),
        SEWING(3, 300, 150), FABRIC(6, 400, 300),
        PLUME(2, 200, 100), FLOURY_CAKE(6, 400, 200),
        CARNIVAL_DRESS(8, 1400, 1300),
        SHEEP(5, -1, 1000), COW(5, -1, 10000),
        NONE(-1, -1, -1);

        public final int OCCUPIED_SPACE, BUY_COST, SELL_MONEY;

        Type(int OCCUPIED_SPACE, int BUY_COST, int SELL_MONEY)
        {
            this.OCCUPIED_SPACE = OCCUPIED_SPACE;
            this.BUY_COST = BUY_COST;
            this.SELL_MONEY = SELL_MONEY;
        }
    }
}
