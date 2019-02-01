package view.menu;

import controller.MenuController;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.account.Account;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.Utility;

import java.io.FileNotFoundException;

public class ChooseProfile extends Pane {
    private static ChooseProfile instance = new ChooseProfile();
    private ChoiceBox<String> choiceBox;
    TextField name, password;

    public static ChooseProfile getInstance() {
        return instance;
    }

    private ChooseProfile() {
        relocate(MainView.WIDTH * 0.4, MainView.HEIGHT * 0.3);
        setVisible(false);
        build();
    }

    private void build() {
        final double width = MainView.HEIGHT * 0.3, height = width / 2;
        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.GAME_MENU));
        imageView.relocate(0, - MainView.HEIGHT * 0.1);
        imageView.setFitHeight(2 * MainView.HEIGHT / 3);
        imageView.setFitWidth(MainView.WIDTH * 0.4);
        getChildren().addAll(imageView);

        Text text = new Text("choose your account");
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(MainView.WIDTH * 0.1, 0);
        getChildren().addAll(text);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);

        ImageView button = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        button.setFitWidth(width);
        button.setFitHeight(height);
        StackPane goButton = new StackPane();
        Text goText = new Text("GO");
        goText.setFont(Font.font("SWItalt", 15));
        goText.setFill(Color.WHITE);
        goButton.getChildren().addAll(button, goText);
        goButton.setOnMouseClicked(event -> go());
        goButton.relocate(MainView.WIDTH * 0.125, 20);
        getChildren().addAll(goButton, choiceBox);


        //////new player
        Text newPlayerText = new Text("or creat new account");
        newPlayerText.setFont(Font.font("SWItalt", 15));
        newPlayerText.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.15);
        getChildren().addAll(newPlayerText);
        name = new TextField();
        name.setPromptText("name");
        name.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.2);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.25);

        ImageView submitButtin = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        submitButtin.setFitWidth(width);
        submitButtin.setFitHeight(height);
        StackPane submit = new StackPane();
        Text submitText = new Text("SUBMIT");
        submitText.setFont(Font.font("SWItalt", 15));
        submitText.setFill(Color.WHITE);
        submit.getChildren().addAll(submitButtin, submitText);
        submit.setOnMouseClicked(event -> addNewPlayer());
        submit.relocate(MainView.WIDTH * 0.05, MainView.HEIGHT * 0.3);



//        Button submit = new Button("SUBMIT");
//        submit.relocate(60, 330);
//        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                addNewPlayer();
//            }
//        });

//        Button back = new Button("back");
//        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                setVisible(false);
//            }
//        });
//        back.relocate(MainView.WIDTH * 0.05, 0);
        ImageView exitButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        exitButton.setFitWidth(width);
        exitButton.setFitHeight(height);
        StackPane exit = new StackPane();
        Text backText = new Text("back");
        backText.setFont(Font.font("SWItalt", 15));
        backText.setFill(Color.WHITE);
        exit.getChildren().addAll(exitButton, backText);
        exit.setOnMouseClicked(event -> setVisible(false));
        exit.relocate(MainView.WIDTH * 0.05, MainView.HEIGHT * 0.4);

        getChildren().addAll(name, password, submit, exit);

    }

    private void addNewPlayer() {
        String nameOfPlayer = name.getText();
        if (Account.getAllAccounts().contains(nameOfPlayer)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "this name is already taken");
            alert.show();
            return;
        }
        String passwordOfPlayer = password.getText();
        name.clear();
        password.clear();
        if (passwordOfPlayer.equals("") && nameOfPlayer.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "you didn't choose any name or password");
            alert.show();
            return;
        }
        Account account = new Account(nameOfPlayer, passwordOfPlayer);
        Account.toJason(account);

        setVisible(false);
        MenuController.getInstance().setCurrentAccount(account);
        MainView.getInstance().goToMap();

    }

    private void go() {
        if (choiceBox.getValue() == null) {
            choosePlayerMessage();
            return;
        }
        Account account = null;

        try {
            account = Account.loadJson(choiceBox.getValue());
        } catch (FileNotFoundException e) {
            System.out.println("null goToMap");
            return;
        }
        setVisible(false);
        MenuController.getInstance().setCurrentAccount(account);
        MainView.getInstance().goToMap();

    }

    private void choosePlayerMessage() {
        Text choosePlayer = new Text("choose an account");
        choosePlayer.setFill(Color.RED);
        choosePlayer.setFont(Font.font(30));
        choosePlayer.relocate(MainView.WIDTH * 0.4, MainView.HEIGHT * 0.1);
        getChildren().addAll(choosePlayer);

        FadeTransition ft = new FadeTransition(Duration.millis(3000), choosePlayer);
        ft.setFromValue(2.0);
        ft.setToValue(0.0);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ft.play();
            }
        }.start();


    }


    public void open(){
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        setVisible(true);
    }
}
