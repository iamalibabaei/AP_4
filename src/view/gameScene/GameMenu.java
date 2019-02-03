package view.gameScene;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.MainView;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

public class GameMenu extends Pane {
    private static GameMenu instance = new GameMenu();
    private ObservableList<Node> list;
    public static GameMenu getInstance() {
        return instance;
    }

    private GameMenu() {
        relocate(MainView.WIDTH * 0.375, MainView.HEIGHT * 0.25);
        setVisible(false);
        list = getChildren();
        build();
    }

    private void build() {
        buildMenuTemplate();
        Utility.makeMenuButton(list, MainView.WIDTH * 0.3,MainView.HEIGHT * 0.35,
                MainView.WIDTH * 0.1,
                MainView.HEIGHT * 0.125,"Back",
                event -> {
                    setVisible(false);
                });

    }

    private void buildMenuTemplate() {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, 0);
        imageView.setFitHeight(MainView.HEIGHT * 0.5);
        imageView.setFitWidth(MainView.WIDTH * 0.5);
        list.addAll(imageView);



        //Utility.makeMenuButton()

    }

    public void play() {
        setVisible(true);
    }
}
