package view.multiplayer;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;
//import view.utility.MenuUtility;
import view.utility.MenuUtility;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Panel extends Pane {

    private static Panel instance = new Panel();
    private ObservableList<Node> list;
    private TextArea chatText = new TextArea();

    private Panel(){
        relocate(MainView.WIDTH * 0.375, MainView.HEIGHT * 0.25);
        setVisible(false);
        list = getChildren();
        try {
            build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static Panel getInstance() {
        return instance;
    }

    private void build() throws UnknownHostException {
        buildMenuTemplate();
        Utility.makeMenuButton(list, MainView.WIDTH * 0.37,MainView.HEIGHT * 0.22, MainView.HEIGHT * 0.2,
                MainView.HEIGHT * 0.1,"Back",
                event -> {
                    setVisible(false);
        });


    }

    private void buildMenuTemplate() throws UnknownHostException {

        Pane mainPane = new MenuUtility("MultiPlayer Panel");
        ImageView hostButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        ImageView hostButtonFade = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON_BRIGHT));
        Text hostButtonText = new Text("Create a game");
        hostButtonText.setFont(Font.font("SWItalt", 15));
        hostButtonText.setFill(Color.WHITE);
        hostButtonText.relocate(MainView.WIDTH * 0.23, MainView.HEIGHT * 0.19);
        hostButton.setFitWidth(MainView.HEIGHT * 0.2);
        hostButton.setFitHeight(MainView.HEIGHT * 0.1);
        hostButtonFade.setFitWidth(MainView.HEIGHT * 0.2);
        hostButtonFade.setFitHeight(MainView.HEIGHT * 0.1);
        hostButton.relocate(MainView.WIDTH * 0.18, MainView.HEIGHT * 0.15);
        hostButtonFade.relocate(MainView.WIDTH * 0.18, MainView.HEIGHT * 0.15);
        mainPane.getChildren().addAll(hostButton, hostButtonText);
        this.getChildren().addAll(mainPane);

        hostButton.setOnMouseEntered(event -> {
            mainPane.getChildren().removeAll(hostButton, hostButtonText);
            mainPane.getChildren().addAll(hostButtonFade, hostButtonText);
        });

        hostButton.setOnMouseExited(event -> {
            mainPane.getChildren().removeAll(hostButtonFade, hostButtonText);
            mainPane.getChildren().addAll(hostButton, hostButtonText);
        });

        ImageView clientButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        ImageView clientButtonFade = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON_BRIGHT));
        Text clientButtonText = new Text("Join a game");
        clientButtonText.setFont(Font.font("SWItalt", 15));
        clientButtonText.setFill(Color.WHITE);
        clientButtonText.relocate(MainView.WIDTH * 0.43, MainView.HEIGHT * 0.19);
        clientButton.setFitWidth(MainView.HEIGHT * 0.2);
        clientButton.setFitHeight(MainView.HEIGHT * 0.1);
        clientButtonFade.setFitWidth(MainView.HEIGHT * 0.2);
        clientButtonFade.setFitHeight(MainView.HEIGHT * 0.1);
        clientButton.relocate(MainView.WIDTH * 0.38, MainView.HEIGHT * 0.15);
        clientButtonFade.relocate(MainView.WIDTH * 0.38, MainView.HEIGHT * 0.15);
        mainPane.getChildren().addAll(clientButton, clientButtonText);

        clientButton.setOnMouseEntered(event -> {
            mainPane.getChildren().removeAll(clientButton, clientButtonText);
            mainPane.getChildren().addAll(clientButtonFade, clientButtonText);
        });

        clientButton.setOnMouseExited(event -> {
            mainPane.getChildren().removeAll(clientButtonFade, clientButtonText);
            mainPane.getChildren().addAll(clientButton, clientButtonText);
        });

        clientButton.setOnMouseClicked(event -> {

            Utility.fade(Duration.millis(1000), clientButton);
            Utility.fade(Duration.millis(1000), hostButton);
            Utility.fade(Duration.millis(1000), clientButtonText);
            Utility.fade(Duration.millis(1000), hostButtonText);


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
        InetAddress localhost = InetAddress.getLocalHost();
        hostButton.setOnMouseClicked(event -> {
            Utility.fade(Duration.millis(1000), hostButton);
            Utility.fade(Duration.millis(1000), clientButton);
            Utility.fade(Duration.millis(1000), hostButtonText);
            Utility.fade(Duration.millis(1000), clientButtonText);

//            this.getChildren().remove(mainPane);
//            Pane mainpane = new Pane();
//            mainpane = new MenuUtility("Host", 0);
            mainPane.resize(MainView.WIDTH * 0.5,MainView.HEIGHT * 0.8);
            ImageView ima = (ImageView) mainPane.getChildren().get(0);
            ima.setFitHeight(MainView.HEIGHT * 0.65);
            ima.setFitWidth(MainView.WIDTH * 0.8);
            ima.relocate(MainView.WIDTH * 0.07, -MainView.HEIGHT * 0.3);
            mainPane.getChildren().remove(0);
            mainPane.getChildren().addAll(ima);
            Text ipText = new Text("Your IP Address : ");
            ipText.setFont(Font.font("SWItalt", 20));
            ipText.setFill(Color.BLACK);
            ipText.relocate(MainView.WIDTH * 0.2, -MainView.WIDTH * 0.15);
            Text ipTextField = new Text(localhost.getHostAddress());
            ipTextField.relocate(MainView.WIDTH * 0.32, -MainView.WIDTH * 0.15);
            chatText.relocate(MainView.WIDTH * 0.2, -MainView.WIDTH * 0.1);
            chatText.setMaxWidth(MainView.WIDTH * 0.28);
            chatText.setDisable(true);
            TextField chatTextwrite = new TextField();
            chatTextwrite.relocate(MainView.WIDTH * 0.2, MainView.WIDTH * 0.1);
            chatTextwrite.setMaxWidth(MainView.WIDTH * 0.45);
            Text textx = new Text("type your text :");
            textx.relocate(MainView.WIDTH * 0.2, MainView.WIDTH * 0.07);


            StackPane sendButton = Utility.makeMenuButton(list, MainView.WIDTH * 0.22,MainView.HEIGHT * 0.22, MainView.HEIGHT * 0.2,
                    MainView.HEIGHT * 0.1,"Send");

            sendButton.setOnMouseClicked(event1 -> {
                //todo
                chatTextwrite.clear();
            });
            chatTextwrite.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)){
                    //todo
                    chatTextwrite.clear();
                }
            });

            TableView table = new TableView();
            Label label = new Label("Connected players");
            label.setFont(new Font("Arial", 20));

            TableColumn username = new TableColumn("username");
            String user = "salam";
            table.getItems().addAll(user);
            table.getColumns().addAll(username);
            final VBox vbox = new VBox();
//            vbox.setSpacing(5);
//            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table);
            vbox.relocate(MainView.WIDTH * 0.55, -MainView.WIDTH * 0.15);


            mainPane.getChildren().addAll(ipTextField, ipText, chatText, chatTextwrite, textx, sendButton, vbox);
            this.getChildren().addAll(mainPane);


        });
    }
    public TextArea getMessage(String message){
        chatText.setText(message);
        return chatText;
    }
    public void start()
    {
        setVisible(true);
    }
}
