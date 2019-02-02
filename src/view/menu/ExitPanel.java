package view.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import view.MainView;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

public class ExitPanel extends Pane {
    private static ExitPanel instance = new ExitPanel();
    private ObservableList<Node> list;

    private ExitPanel(){
        relocate(MainView.WIDTH * 0.3, MainView.HEIGHT * 0.4);

        setVisible(false);
        list = getChildren();
        build();
    }

    public static ExitPanel getInstance() {
        return instance;
    }

    private void build(){
        buildMenuTemplate();
        StackPane exitButton = View.makeMenuButton(MainView.WIDTH * 0.3, 0, "Back",
                event -> {
                    setVisible(false);
                });
        StackPane backButton = View.makeMenuButton(MainView.WIDTH * 0.08, 0, "exit",
                event -> {
                    System.exit(0);
                });
        list.addAll(backButton, exitButton);

    }

    private void buildMenuTemplate() {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, -MainView.HEIGHT * 0.1);
        imageView.setFitHeight(MainView.HEIGHT * 0.3);
        imageView.setFitWidth(MainView.WIDTH * 0.6);
        list.add(imageView);
    }
    public void close()
    {
        setVisible(true);
    }
}
