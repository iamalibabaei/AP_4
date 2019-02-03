package models.objects.animals;

import controller.InGameController;
import javafx.scene.image.ImageView;
import models.Map;
import models.exceptions.Messages;
import models.buildings.Warehouse;
import models.interfaces.Upgradable;
import models.objects.Entity;
import models.objects.Item;
import models.objects.Point;
import view.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// todo cat upgrade beyond level 1
// todo probable bug: collecting more than one item in one turn

public class Cat extends Animal implements Upgradable
{
    private static final int MAX_LEVEL = 1, UPGRADE_COST = 200;
    private static int level = 0;
    private Warehouse warehouse;

    public Cat(Point point, Animal.Type type)
    {
        super(point, type);
        updateImageView();
        warehouse = Warehouse.getInstance();
    }

    @Override
    protected void buildHashmap() {
        converter.put(stateKind.DIE,  Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/down.png"));
        converter.put(stateKind.DOWN, Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/down.png"));
        converter.put(stateKind.DOWN_LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/down_left.png"));
        ImageView imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/down_left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.DOWN_RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/left.png");
        imageView.setScaleX(-1);
        converter.put(stateKind.RIGHT, imageView);
        imageView = Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/up_left.png");
        converter.put(stateKind.UP_LEFT, imageView);
        imageView.setScaleX(-1);
        converter.put(stateKind.UP_RIGHT, imageView);
        converter.put(stateKind.UP, Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/up.png"));
        converter.put(stateKind.LEFT, Utility.getImageView("res/graphicAssets/Animals/Africa/Cat/left.png"));
    }

    @Override
    public int getLevel(){
        return level;
    }
    @Override
    public void collide(Entity entity)
    {
        if (entity instanceof Item)
        {
            List<Item> item = new ArrayList<>();
            item.add((Item) entity);
            try
            {
                InGameController.getInstance().store(item);
            } catch (IOException ignore)
            {
                // nothing happens here
            }
        }
    }

    @Override
    public void setTarget()
    {
        target = null;
        if (level == 0 && !map.getItems().isEmpty())
        {
            target = map.getItems().get(0).getCoordinates();
        } else
        {
            double dist = 2.0 * Map.WIDTH;
            for (Item item : map.getItems())
            {
                if (warehouse.getRemainingCapacity() >= item.type.OCCUPIED_SPACE)
                {
                    Point newTarget = item.getCoordinates();
                    double dist1 = coordinates.distanceFrom(newTarget);
                    if (dist1 < dist)
                    {
                        dist = dist1;
                        target = newTarget;
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() throws IOException
    {
        if (level == MAX_LEVEL)
            throw new IOException(Messages.UPGRADE_BEYOND_MAX_LEVEL);
        Cat.level++;
    }

    @Override
    public int getUpgradeCost() throws IOException
    {
        if (level == MAX_LEVEL)
            throw new IOException(Messages.ALREADY_AT_MAX_LEVEL);
        return UPGRADE_COST;
    }

    @Override
    protected void loadAnimation()
    {

    }


}
