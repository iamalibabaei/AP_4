package view;


import controller.InGameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.misc.Mission;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.animals.Animal;
import view.gameScene.InGameView;
import view.menu.Menu;
import view.menu.selectMission.MissionScene;

public class MainView extends Application
{
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double HEIGHT = SCREEN_HEIGHT;
    public static final double WIDTH = HEIGHT * 4 / 3;
    public static final double OFFSET_X = (SCREEN_WIDTH - WIDTH) / 2;
    public static final double OFFSET_Y = 0;
    private static MainView instance = new MainView();
    private static Stage mainStage;
    private Scene mainScene;
    private Group root;

    public MainView()
    {
        mainScene = new Scene(new Group(), SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
        root = (Group) mainScene.getRoot();
        root.relocate(OFFSET_X, OFFSET_Y);
    }

    private static MenuController getController()
    {
        return MenuController.getInstance();
    }

    public static MainView getInstance()
    {
        return instance;
    }

    public Stage getMainStage()
    {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage)
    {

        System.out.println("start");
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setResizable(true);
        mainStage.setScene(mainScene);
        Menu.build(root);
        mainStage.show();
        mainStage.setOnCloseRequest(event -> close());
        System.out.println("end");
    }

    public void close()
    {

    }

    private void setScene(Pane pane)
    {
        root.getChildren().clear();
        root.getChildren().add(pane);
    }

    public void goToMap()
    {
        MissionScene.getInstance().updateInfo();
        mainStage.setScene(MissionScene.getInstance());
    }

    public void goToSetting()
    {
    }

    public void goToMenu()
    {
//        mainStage.setScene(Menu.getInstance());
    }

    public void startGame(Mission mission)
    {
        mainStage.setScene(InGameView.getInstance());
        InGameController.getInstance().startGame(mission);
    }

    public void showExceptions(Exception e, double x, double y)
    {
        if (e instanceof InsufficientResourcesException)
        {

        } else if (e instanceof IsWorkingException)
        {

        }
    }

    public void addEntityToMap(Entity entity)
    {
        if (entity instanceof Animal)
        {
            InGameView.getInstance().addAnimal((Animal) entity, entity.getCoordinates());
        } else if (entity instanceof Grass)
        {
            InGameView.getInstance().addGrass((Grass) entity, entity.getCoordinates());
        } else if (entity instanceof Item)
        {
            //InGameView.getInstance().addItem((Item) entity, entity.getCoordinates());
        }
    }

}
