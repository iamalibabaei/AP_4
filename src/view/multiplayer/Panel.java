package view.multiplayer;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;
//import view.utility.MenuUtility;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;


public class Panel extends Pane {

    private static Panel instance = new Panel();
    private ObservableList<Node> list;

    private Panel(){
        relocate(MainView.WIDTH * 0.375, MainView.HEIGHT * 0.25);
        setVisible(false);
        list = getChildren();
        build();
    }

    public static Panel getInstance() {
        return instance;
    }

    private void build(){
        buildMenuTemplate();
        StackPane backButton = Utility.makeMenuButton(list, MainView.WIDTH * 0.37,MainView.HEIGHT * 0.22, MainView.HEIGHT * 0.2,
                MainView.HEIGHT * 0.1,"Back",
                event -> {
                    setVisible(false);
                });
        list.addAll(backButton);

    }

    private void buildMenuTemplate() {

//        Pane mainPane = new MenuUtility("MultiPlayer Panel");

        ImageView hostButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        ImageView hostButtonFade = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON_BRIGHT));
        Text hostButtonText = new Text("Create a game");
        StackPane pane = new StackPane();
        hostButtonText.setFont(Font.font("SWItalt", 15));
        hostButtonText.setFill(Color.WHITE);
        hostButton.setFitWidth(MainView.HEIGHT * 0.2);
        hostButton.setFitHeight(MainView.HEIGHT * 0.1);
        hostButtonFade.setFitWidth(MainView.HEIGHT * 0.2);
        hostButtonFade.setFitHeight(MainView.HEIGHT * 0.1);
        pane.relocate(MainView.WIDTH * 0.17, MainView.HEIGHT * 0.15);
        pane.getChildren().addAll(hostButton, hostButtonText);
//        mainPane.getChildren().addAll(pane);

        pane.setOnMouseEntered(event -> {
            pane.getChildren().removeAll(hostButton, hostButtonText);
            pane.getChildren().addAll(hostButtonFade, hostButtonText);
        });

        pane.setOnMouseExited(event -> {
            pane.getChildren().removeAll(hostButtonFade, hostButtonText);
            pane.getChildren().addAll(hostButton, hostButtonText);
        });




        StackPane pane2 = new StackPane();
        ImageView clientButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        ImageView clientButtonFade = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON_BRIGHT));
        Text clientButtonText = new Text("Join a game");
        clientButtonText.setFont(Font.font("SWItalt", 15));
        clientButtonText.setFill(Color.WHITE);
        clientButton.setFitWidth(MainView.HEIGHT * 0.2);
        clientButton.setFitHeight(MainView.HEIGHT * 0.1);
        clientButtonFade.setFitWidth(MainView.HEIGHT * 0.2);
        clientButtonFade.setFitHeight(MainView.HEIGHT * 0.1);
        pane2.relocate(MainView.WIDTH * 0.37, MainView.HEIGHT * 0.15);
        pane.getChildren().addAll(clientButton, clientButtonText);
        pane2.getChildren().addAll(clientButton, clientButtonText);
//        list.addAll(imageView, text, pane, pane2);

        pane2.setOnMouseEntered(event -> {
            pane2.getChildren().removeAll(clientButton, clientButtonText);
            pane2.getChildren().addAll(clientButtonFade, clientButtonText);
        });

        pane2.setOnMouseExited(event -> {
            pane2.getChildren().removeAll(clientButtonFade, clientButtonText);
            pane2.getChildren().addAll(clientButton, clientButtonText);
        });

        pane2.setOnMouseClicked(event -> {

            Utility.fade(Duration.millis(1000), pane);
            Utility.fade(Duration.millis(1000), pane2);

            Text ipText = new Text("Host IP Address : ");
            ipText.setFont(Font.font("SWItalt", 20));
            ipText.setFill(Color.BLACK);
            ipText.relocate(MainView.WIDTH * 0.2, MainView.WIDTH * 0.15);
            TextField ipTextField = new TextField();
            ipTextField.relocate(MainView.WIDTH * 0.32, MainView.WIDTH * 0.15);

            StackPane joinPane = new StackPane();
            ImageView joinButton = new ImageView(hostButton.getImage());
            ImageView joinButtonFade = new ImageView(hostButtonFade.getImage());
            Text joinButtonText = new Text("Join");
            joinButtonText.setFont(Font.font("SWItalt", 15));
            joinButtonText.setFill(Color.WHITE);
            joinButton.setFitWidth(MainView.HEIGHT * 0.2);
            joinButton.setFitHeight(MainView.HEIGHT * 0.1);
            joinButtonFade.setFitWidth(MainView.HEIGHT * 0.2);
            joinButtonFade.setFitHeight(MainView.HEIGHT * 0.1);
            joinPane.relocate(MainView.WIDTH * 0.17,MainView.HEIGHT * 0.22);
            joinPane.getChildren().addAll(joinButton, joinButtonText);
            joinPane.setOnMouseEntered(event1 -> {
                joinPane.getChildren().removeAll(joinButton, joinButtonText);
                joinPane.getChildren().addAll(joinButtonFade, joinButtonText);
            });
            joinPane.setOnMouseExited(event1 -> {
                joinPane.getChildren().removeAll(joinButtonFade, joinButtonText);
                joinPane.getChildren().addAll(joinButton, joinButtonText);
            });
            joinPane.setOnMouseClicked(event1 -> {
                joinPane.getChildren().removeAll(joinButton, joinButtonText, joinButtonFade);
            });
            list.addAll(ipText, ipTextField, joinPane);


        });

        pane.setOnMouseClicked(event -> {
            Utility.fade(Duration.millis(1000), pane);
            Utility.fade(Duration.millis(1000), pane2);

        });
    }
    public void start()
    {
        setVisible(true);
    }
}
