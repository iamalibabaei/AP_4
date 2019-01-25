package view.menu;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import view.View;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuBackground extends Pane {
    private static MenuBackground instance = new MenuBackground();

    public static MenuBackground getInstance() {
        return instance;
    }

    private MenuBackground() {
        build();

    }

    private void build() {

        Image background = null;
        try {
            background = new Image(new FileInputStream("Textures\\menuWallpaper.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BackgroundSize backgroundSize = new BackgroundSize(View.WIDTH, View.HEIGHT, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(View.WIDTH * 2, View.HEIGHT);
        pane.setBackground(new Background(backgroundImage));
        this.getChildren().add(pane);
        Image image = null;
        try {
            image = new Image(new FileInputStream("Textures\\Animals\\Cow\\down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView cowImage = new ImageView(image);
        this.getChildren().addAll(cowImage);
        final Animation animation = new SpriteAnimation(
                cowImage,
                Duration.millis(2000),
                13, 13,
                0, 0,
                // 64=829/13
                64, 66
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

    }

}
