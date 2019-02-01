package view.gameScene;

import controller.InGameController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.buildings.Workshop;
import models.exceptions.AlreadyAtMaxLevelException;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.Utility;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WorkshopView extends Pane {
    private static WorkshopView instance = new WorkshopView();
    private static final int height = 500, width = 500;
    private ArrayList<String> workshops;
    private ChoiceBox<String> choiceBox;
    private int place;
    public static WorkshopView getInstance() {
        return instance;
    }

    private WorkshopView() {
        workshops = Workshop.loadDefaultWorkshops();
        //setVisible(false);
        relocate(MainView.WIDTH/2 - width / 2, MainView.HEIGHT / 2 - height / 2);
        setHeight(height);
        setWidth(width);
        build();
    }

    private void build() {
        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.GAME_MENU));
        imageView.relocate(0, 0);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        getChildren().addAll(imageView);
        Text text = new Text("choose your workshop");
        text.setFont(Font.font("Segoe UI", 20));
        text.relocate(60, 70);
        getChildren().addAll(text);


        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(workshops);
        choiceBox.relocate(70, 130);
        getChildren().addAll(choiceBox);

        ImageView buildbutton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        buildbutton.setFitHeight(100);
        buildbutton.setFitWidth(200);
        StackPane build = new StackPane();
        Text buildText = new Text("build");
        buildText.setFont(Font.font("SWItalt", 15));
        build.getChildren().addAll(buildbutton, buildText);
        build.setOnMouseClicked(event -> buildWorkshop());
        build.relocate(70, 200);
        getChildren().addAll(build);

        ImageView exitButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        exitButton.setFitHeight(100);
        exitButton.setFitWidth(200);
        StackPane exit = new StackPane();
        Text exitText = new Text("back");
        exitText.setFont(Font.font("SWItalt", 15));
        exit.getChildren().addAll(exitButton, exitText);
        exit.setOnMouseClicked(event -> close());
        exit.relocate(70, 260);
        getChildren().addAll(exit);




    }

    private void buildWorkshop() {
        Workshop workshop = null;
        try {
            workshop = Workshop.loadJson(AddressConstants.WORKSHOP_ROOT + choiceBox.getValue() + ".json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int cost = 0;
        try {
            cost = workshop.getUpgradeCost();
        } catch (AlreadyAtMaxLevelException e) {
            e.printStackTrace();
        }
        System.out.println(workshop.name);
        if (InGameController.getInstance().withdrawMoney(cost)){
            InGameController.getInstance().addWorkshop(workshop, place);
            close();
            return;
        }
        notEnoughMoney(cost);
    }

    private void notEnoughMoney(int cost) {

    }

    private void close() {
        setVisible(false);
    }
    public void open(int place) {
        this.place = place;
        setVisible(true);
    }
}
