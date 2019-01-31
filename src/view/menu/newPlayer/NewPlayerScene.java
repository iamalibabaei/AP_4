package view.menu.newPlayer;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.account.Account;
import view.MainView;
import view.menu.Menu;

public class
NewPlayerScene extends Scene {

    private Group root;
    private ImageView background;
    TextField name, password;

    public NewPlayerScene() {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {






        name = new TextField();
        name.setPromptText("name");
        name.relocate(300, 300);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(300, 350);

        Button submit = new Button("SUBMIT");
        submit.relocate(300, 400);
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
                MainView.getInstance().goToMenu();
            }
        });
        back.relocate(300,450);

        root.getChildren().addAll(name, password, submit, back);



    }

    public void addNewPlayer(){
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
        Menu.getInstance().updateChoiceBox();
    }
}
