package models.buildings;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.Messages;
import models.Viewable;
import models.interfaces.Upgradable;
import view.utility.SpriteAnimation;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.io.IOException;

// todo implement well last upgrade (automatic planting)

public class Well extends Viewable implements Upgradable
{
    public static final int[] UPGRADE_COST = {250, 500}, REFILL_COST = {19, 17, 15};
    public static final int[] CAPACITY = {5, 7, 10}, REFILL_TIME = {4, 4, 3};
    private static final int MAX_LEVEL = 2;
    private static Well instance = new Well();
    private int level;
    private boolean isRefilling;
    private int remainingWater;

    private Well()
    {
        isRefilling = false;
        level = 0;
        state = "level" + level;
        remainingWater = CAPACITY[level];
        for (int i = 0; i < 3; i++)
        {
            ImageView imageView = Utility.getImageView(PictureAddresses.WELL_PICTURE_ROOT + level + ".png");
            SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4, 0, 0,
                    (int) (imageView.getImage().getWidth() / 4), (int) (imageView.getImage().getHeight() / 4));
            states.put("level" + level, spriteAnimation);
            spriteAnimation.setOnFinished(event -> refill());
            spriteAnimation.setCycleCount(REFILL_TIME[level]);
        }
    }

    public static Well getInstance()
    {
        return instance;
    }

    public void extractWater() throws IOException
    {
        if (remainingWater == 0)
            throw new IOException(Messages.WELL_EMPTY);
        remainingWater--;
    }

    public void issueRefill() throws IOException
    {
        if (isRefilling)
            throw new IOException(Messages.WELL_WORKING);
        isRefilling = true;
        states.get(Integer.toString(level)).play();
    }

    private void refill()
    {
        isRefilling = false;
        remainingWater = CAPACITY[level];
    }

    @Override
    public void upgrade() throws IOException
    {
        if (level == MAX_LEVEL)
            throw new IOException(Messages.UPGRADE_BEYOND_MAX_LEVEL);
        level++;
        state = "level" + level;

    }

    @Override
    public int getUpgradeCost() throws IOException
    {
        if (level == MAX_LEVEL)
            throw new IOException(Messages.ALREADY_AT_MAX_LEVEL);
        return UPGRADE_COST[level];
    }

    @Override
    public int getLevel()
    {
        return level;
    }

}
