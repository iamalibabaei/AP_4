package view.menu;

import controller.MenuController;
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
import models.account.Account;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.Utility;

import java.io.FileNotFoundException;

public class ChooseProfile extends Pane {
    private static ChooseProfile instance = new ChooseProfile();
    private ChoiceBox<String> choiceBox;
    TextField name, password;
    Text choosePlayer;

    public static ChooseProfile getInstance() {
        return instance;
    }

    private ChooseProfile() {
        relocate(200, 200);
        setVisible(false);
        build();
    }

    private void build() {
        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.GAME_MENU));
        imageView.relocate(0, 0);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        getChildren().addAll(imageView);

        Text text = new Text("choose your account");
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(60, 100);
        getChildren().addAll(text);

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(250, 100);

        ImageView button = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        button.setFitWidth(200);
        button.setFitHeight(100);
        StackPane goButton = new StackPane();
        Text goText = new Text("GO");
        goText.setFont(Font.font("SWItalt", 15));
        goText.setFill(Color.WHITE);
        goButton.getChildren().addAll(button, goText);
        goButton.setOnMouseClicked(event -> go());
        goButton.relocate(150, 100);
        getChildren().addAll(goButton, choiceBox);


        //////new player
        Text newPlayerText = new Text("or creat new account");
        newPlayerText.setFont(Font.font("SWItalt", 15));
        newPlayerText.relocate(60, 200);
        getChildren().addAll(newPlayerText);
        name = new TextField();
        name.setPromptText("name");
        name.relocate(60, 250);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(60, 300);

        Button submit = new Button("SUBMIT");
        submit.relocate(60, 330);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addNewPlayer();
            }
        });

        Button back = new Button("back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setVisible(false);
            }
        });
        back.relocate(270,330);

        getChildren().addAll(name, password, submit, back);

        choosePlayer = new Text("choose an account");
        choosePlayer.setFill(Color.RED);
        choosePlayer.setFont(Font.font(30));
        choosePlayer.relocate(60, 50);
        getChildren().addAll(choosePlayer);


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

        clearMessage();
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
        clearMessage();
        setVisible(false);
        MenuController.getInstance().setCurrentAccount(account);
        MainView.getInstance().goToMap();

    }

    private void clearMessage() {
        choosePlayer.setVisible(false);
    }

    private void choosePlayerMessage() {
        choosePlayer.setVisible(true);
    }


    public void open(){
        clearMessage();
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        setVisible(true);
    }
}
