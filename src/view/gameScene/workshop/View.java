package view.gameScene.workshop;

import controller.InGameController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.buildings.Workshop;
import view.MainView;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class View extends Pane
{
    private static final int height = 500, width = 500;
    private static View instance = new View();
    private ArrayList<String> workshops;
    private ChoiceBox<String> choiceBox;
    private int place;

    private View()
    {
        workshops = Workshop.loadDefaultWorkshops();
        setVisible(false);
        relocate(MainView.WIDTH / 2 - width / 2, MainView.HEIGHT / 2 - height / 2);
        setHeight(height);
        setWidth(width);
        build();
    }

    private void build()
    {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
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

        ImageView buildbutton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        buildbutton.setFitHeight(100);
        buildbutton.setFitWidth(200);
        StackPane build = new StackPane();
        Text buildText = new Text("build");
        buildText.setFont(Font.font("SWItalt", 15));
        build.getChildren().addAll(buildbutton, buildText);
        build.setOnMouseClicked(event -> buildWorkshop());
        build.relocate(70, 200);
        getChildren().addAll(build);

        ImageView exitButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
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

    private void buildWorkshop()
    {
        Workshop workshop = null;
        try
        {
            workshop = Workshop.loadJson(PictureAddresses.WORKSHOP_ROOT + choiceBox.getValue() + ".json");
        } catch (FileNotFoundException e)
        {
            System.out.println("workshop not found");
            e.printStackTrace();
        }
        int cost = 0;
        try
        {
            cost = workshop.getUpgradeCost();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(workshop.name);
        InGameController.getInstance().withdrawMoney(cost);
        InGameController.getInstance().addWorkshop(workshop, place);
        choiceBox.getItems().remove(choiceBox.getValue());
        close();
    }

    private void close()
    {
        setVisible(false);
    }

    public static View getInstance()
    {
        return instance;
    }

    public void open(int place)
    {
        this.place = place;
        setVisible(true);
    }

}
