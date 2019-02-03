package view.gameScene;

import controller.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import models.Map;
import models.Viewable;
import models.interfaces.Time;
import models.objects.Entity;
import models.objects.Point;
import models.objects.animals.Animal;
import models.objects.animals.DomesticAnimal;
import view.MainView;
import view.PaneBuilder;
import view.utility.SpriteAnimation;

import java.io.File;

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

        childrenList.addAll(entity.getImageView());

        if (entity instanceof Animal) {
            int column = 0, row = 0;
            Animal.Type type = ((Animal) entity).type;
            if (type == Animal.Type.HEN) {
                column = 5;
                row = 5;
            }else if (type == Animal.Type.SHEEP) {
                column = 4;
                row = 6;
            }else if (type == Animal.Type.LION) {
                column = 6;
                row = 4;
            }else if (type == Animal.Type.BEAR) {
                column = 6;
                row = 4;
            }else if (type == Animal.Type.COW) {
                column = 3;
                row = 8;
            }else if (type == Animal.Type.DOG) {
                column = 6;
                row = 4;
            }else if (type == Animal.Type.CAT) {
                if (entity.getState() == Viewable.stateKind.LEFT){
                    column = 4;
                    row = 6;
                }
                column = 6;
                row = 4;
            }
            relocate(entity.getCoordinates().getX(), entity.getCoordinates().getY());
            SpriteAnimation spriteAnimation = new SpriteAnimation(entity.getImageView(), Duration.INDEFINITE,
                    column * row, column, 0, 0, (int) (entity.getImageView().getImage().getWidth() / column),
                    (int)(entity.getImageView().getImage().getHeight() / row));
            spriteAnimation.stop();
            spriteAnimation.play();
        }
    }

    @Override
    public void nextTurn()
    {
        for(Animal animal :  Map.getInstance().getAnimals()) {
            double endX = animal.getTarget().getX() - animal.getCoordinates().getX();
            double endY = animal.getTarget().getY() - animal.getCoordinates().getY();
            if (endX + endY < 0.3) animal.setArrived(true);
            animal.updateImageView();
            System.out.println("state " + animal.getState() + " animal " + animal.getName());
            System.out.println("direct " + animal.getCoordinates().getX() + " " + animal.getCoordinates().getY());
            System.out.println("target " + animal.getTarget().getX() + " " + animal.getTarget().getY());
            KeyValue xForGoing = new KeyValue(animal.getImageView().xProperty(), endX);
            KeyValue yForGoing = new KeyValue(animal.getImageView().yProperty(), endY);
            KeyFrame going = new KeyFrame(Duration.millis(2000), xForGoing, yForGoing);
            Timeline timeLineGoing = new Timeline(going);
            timeLineGoing.setOnFinished(event -> {

            });
            timeLineGoing.getKeyFrames().addAll(going);
            timeLineGoing.play();
        }

    }

/*
    public void ShowChickenMoving(int xcell1, int yCell1, int xCell2, int yCell2) {
        int speed = 3;
        int duration = (int) (((2000000000) - (speed * 15881818)) / 1000000);
        File chickenFile = null;
        Image chickenImage = null;
        ImageView chickenView = null;
        Animation chickenAnimation = null;
        final ImageView[] chickenArrayView = new ImageView[1];
        if (xcell1 == xCell2 & yCell1 == yCell2) {
            ;
        } else {
            if (xcell1 == xCell2) {
                if (yCell1 > yCell2) {
                    chickenFile = new File("Data\\Textures\\Animals\\Africa\\GuineaFowl\\up.png");
                    chickenImage = new Image(chickenFile.toURI().toString());
                    chickenView = new ImageView(chickenImage);
                    chickenAnimation = new SpriteAnimation(chickenView, Duration.millis(duration), 24, 5, 0, 0, 64, 84);
                    chickenView.setViewport(new Rectangle2D(0, 0, 64, 84));
                } else {
                    chickenFile = new File("Data\\Textures\\Animals\\Africa\\GuineaFowl\\down.png");
                    chickenImage = new Image(chickenFile.toURI().toString());
                    chickenView = new ImageView(chickenImage);
                    chickenAnimation = new SpriteAnimation(chickenView, Duration.millis(duration), 24, 5, 0, 0, 66, 72);
                    chickenView.setViewport(new Rectangle2D(0, 0, 66, 72));
                }
            } else if (yCell1 == yCell2) {
                chickenFile = new File("Data\\Textures\\Animals\\Africa\\GuineaFowl\\left.png");
                chickenImage = new Image(chickenFile.toURI().toString());
                chickenView = new ImageView(chickenImage);
                chickenAnimation = new SpriteAnimation(chickenView, Duration.millis(duration), 24, 5, 0, 0, 80, 74);
                chickenView.setViewport(new Rectangle2D(0, 0, 80, 74));
                if (xcell1 < xCell2) {
                    chickenView.setScaleX(-1);
                }
            } else if (yCell2 > yCell1) {
                chickenFile = new File("res/graphicAssets/Animals/Africa/cow/death.png");
                chickenImage = new Image(chickenFile.toURI().toString());
                chickenView = new ImageView(chickenImage);
                chickenAnimation = new SpriteAnimation(chickenView, Duration.millis(duration), 24, 5, 0, 0, 70, 72);
                chickenView.setViewport(new Rectangle2D(0, 0, 70, 72));
                if (xcell1 < xCell2) {
                    chickenView.setScaleX(-1);
                }
            } else if (yCell1 > yCell2) {
                chickenFile = new File("res/graphicAssets/Animals/Africa/cow/death.png");
                chickenImage = new Image(chickenFile.toURI().toString());
                chickenView = new ImageView(chickenImage);
                chickenAnimation = new SpriteAnimation(chickenView, Duration.millis(duration), 24, 5, 0, 0, 68, 80);
                chickenView.setViewport(new Rectangle2D(0, 0, 68, 80));
                if (xcell1 < xCell2) {
                    chickenView.setScaleX(-1);
                }
            }
            int[] position1 = getPositionByCellPosition(xcell1, yCell1);
            int[] position2 = getPositionByCellPosition(xCell2, yCell2);
            int x1Position = position1[0] - 30;
            int y1Position = position1[1] - 30;
            int x2Position = position2[0] - 30;
            int y2Position = position2[1] - 30;
            chickenView.relocate(x1Position, y1Position);
            this.getChildren().addAll(chickenView);
            chickenArrayView[0] = chickenView;

            KeyValue xChicken = new KeyValue(chickenView.xProperty(), x2Position - x1Position);
            KeyValue yChicken = new KeyValue(chickenView.yProperty(), y2Position - y1Position);
            KeyFrame xChickenFrame = new KeyFrame(Duration.millis(duration), xChicken, yChicken);
            Timeline chickenTimeLine = new Timeline(xChickenFrame);
            chickenTimeLine.setOnFinished(event -> this.getChildren().removeAll(chickenArrayView[0]));
            chickenAnimation.play();
            chickenTimeLine.play();
        }
    }
*/
}
