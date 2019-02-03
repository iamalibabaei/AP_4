package view.gameScene;

import controller.MenuController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Map;
import models.interfaces.Time;
import models.objects.animals.Animal;
import view.MainView;
import view.PaneBuilder;

public class MapView extends PaneBuilder implements Time
{
    private static final double OFFSET_X = MainView.WIDTH * 0.25;
    private static final double OFFSET_Y = MainView.HEIGHT * 0.3;
    private static MapView instance = new MapView();

    private MapView()
    {
        super(OFFSET_X, OFFSET_Y, Map.WIDTH, Map.HEIGHT);
        setOnMouseClicked(event -> MenuController.getInstance().click(event.getX() - OFFSET_X,
                event.getY() - OFFSET_Y));
        // 200, 180 | 1/4, 3/10
        // 600, 480 | 3/4, 8/10
        // 800, 600
    }

    public static MapView getInstance()
    {
        return instance;
    }

    @Override
    protected void build()
    {
        // todo
    }

    @Override
    public void nextTurn()
    {
        childrenList.clear();
        Iterable<Animal> animals = Map.getInstance().getAnimals();
        // todo
    }

}
