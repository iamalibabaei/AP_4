package view.gameScene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameScene extends Scene
{
    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;

    public GameScene() {
        super(new Group(), View.WIDTH, View.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        Image menuImage = null;
        try {
            menuImage = new Image(new FileInputStream("Textures\\back.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView wallpaper = new ImageView(menuImage);
        wallpaper.setFitWidth(View.WIDTH);
        wallpaper.setFitHeight(View.HEIGHT);
        wallpaper.relocate(0, 0);

        root.getChildren().addAll(wallpaper);

    }
}
