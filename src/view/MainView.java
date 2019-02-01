package view;


import controller.InGameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Scene;
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
    public static final int SCREEN_WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
    public static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();
    public static final int HEIGHT = SCREEN_HEIGHT;
    public static final int WIDTH = HEIGHT * 4 / 3;
    public static final int OFFSET_X = (SCREEN_WIDTH - WIDTH) / 2;
    public static final int OFFSET_Y = 0;
    private static MainView instance = new MainView();
    private static Stage mainStage;

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
        mainStage.setScene(Menu.getInstance());
        mainStage.show();
        mainStage.setOnCloseRequest(event -> close());
        System.out.println("end");
    }

    public void close()
    {

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
        mainStage.setScene(Menu.getInstance());
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
