package view.gameScene;

import controller.InGameController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.interfaces.Time;
import models.objects.animals.Animal;
import view.MainView;
import view.PaneBuilder;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

public class Background extends PaneBuilder
{
    private static Background instance = new Background();

    private Background()
    {
        super(0, 0);
    }

    public static Background getInstance()
    {
        return instance;
    }

    @Override
    protected void build()
    {
        setBackgroundStuff();
        updateBuyAnimalButtons();
        setUnderBar();
        setUpperBar();
    }

    private void setBackgroundStuff()
    {
        ImageView background = Utility.getImageView(PictureAddresses.GAME_BACKGROUND);
        background.setFitWidth(MainView.WIDTH);
        background.setFitHeight(MainView.HEIGHT);
        childrenList.addAll(background);
    }

    private void updateBuyAnimalButtons()
    {
        for (Animal.Type type : Animal.Type.values())
        {
            if (type.BUY_COST != -1)
            {
                ImageView backImage = null;
                if (type.BUY_COST < InGameController.getInstance().getMoney())
                {
                    backImage = Utility.getImageView(PictureAddresses.ANIMAL_ICONS_ROOT + type.getName() + "Icon.png");
                } else
                {
                    backImage = Utility.getImageView((PictureAddresses.ANIMAL_ICONS_ROOT + type.getName() + "IconGray" +
                            ".png"));
                }
                backImage.setFitHeight(MainView.HEIGHT / 14);
                backImage.setFitWidth(MainView.WIDTH / 14);

                StackPane addAnimal = new StackPane();
                addAnimal.getChildren().addAll(backImage);
                addAnimal.relocate(20 + type.ordinal() * MainView.WIDTH / 14, 20);
                addAnimal.setOnMouseClicked(event -> {
                    InGameController.getInstance().buyAnimal(type);
                });
                childrenList.addAll(addAnimal);
            }
        }

    }

    private void setUnderBar()
    {
        ImageView imageView = Utility.getImageView(PictureAddresses.GAME_UNDER_BAR);
        imageView.setFitWidth(MainView.WIDTH);
        imageView.setFitHeight(MainView.HEIGHT / 7.7);
        StackPane underBarPane = new StackPane();
        underBarPane.getChildren().addAll(imageView);
        underBarPane.relocate(0, MainView.HEIGHT - imageView.getImage().getHeight() * 0.525);
        childrenList.addAll(underBarPane);

    }

    private void setUpperBar()
    {
        ImageView imageView = Utility.getImageView(PictureAddresses.GAME_UPPER_BAR);
        imageView.setFitWidth(MainView.WIDTH / 2.8);
        imageView.setFitHeight(MainView.HEIGHT / 7);
        imageView.relocate(MainView.WIDTH - imageView.getFitWidth(), 0);
        childrenList.addAll(imageView);
    }

}
