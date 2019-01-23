package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu extends Scene {
    private static Menu instance = new Menu();

    public static Menu getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;

    public Menu() {
        super(new Group(), View.WIDTH, View.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        Image menuImage = null;
        try {
            menuImage = new Image(new FileInputStream("Textures\\menuWallpaper.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView menuWallpaper = new ImageView(menuImage);
        menuWallpaper.setFitWidth(View.WIDTH);
        menuWallpaper.setFitHeight(View.HEIGHT);
        menuWallpaper.relocate(0, 0);
        root.getChildren().add(menuWallpaper);
    }
}
