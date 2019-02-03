package view.gameScene;

import controller.MenuController;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import models.Map;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.animals.Animal;
import view.MainView;
import view.PaneBuilder;
import view.utility.SpriteAnimation;

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




    public void addEntity(Entity entity) {

        SpriteAnimation animal = new SpriteAnimation(entity.getImageView(), Duration.millis(500), 24, 6,
                0, 0, (int) (entity.getImageView().getImage().getWidth() / 6),(int) (entity.getImageView().getImage().getHeight() / 4) );
        animal.play();
        childrenList.addAll(entity.getImageView());

    }

    @Override
    public void nextTurn()
    {
//        for(Animal animal :  Map.getInstance().getAnimals()) {
////            if (animal.getImageView() == null) {
////                continue;
////            }
//            ImageView imageView = animal.getImageView();
//            imageView.relocate(animal.getCoordinates().getX()+ MapView.OFFSET_X, animal.getCoordinates().getY() + MapView.OFFSET_Y);
//        }

    }

}
