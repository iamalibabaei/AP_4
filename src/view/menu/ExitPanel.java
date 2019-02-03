package view.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        Utility.makeMenuButton(list, MainView.WIDTH * 0.27, MainView.HEIGHT * 0.05, MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Back",
                event -> {
                    setVisible(false);
                });
        Utility.makeMenuButton(list, MainView.WIDTH * 0.08, MainView.HEIGHT * 0.05, MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Exit",
                event -> {
                    System.exit(0);
        });


    }

    private void buildMenuTemplate() {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, -MainView.HEIGHT * 0.1);
        imageView.setFitHeight(MainView.HEIGHT * 0.3);
        imageView.setFitWidth(MainView.WIDTH * 0.6);
        Text text = new Text("Are you sure?");
        text.setFont(Font.font("SWItalt", 45));
        text.setFill(Color.RED);
        text.relocate(MainView.WIDTH * 0.20, -MainView.WIDTH * 0.05);
        list.addAll(imageView, text);
    }
    public void close()
    {
        setVisible(true);
    }
}
