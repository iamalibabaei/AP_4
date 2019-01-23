package view.gameScene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import models.Game;
import view.View;
import view.menu.Menu;

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

    }
}
