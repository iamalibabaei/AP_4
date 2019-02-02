package view.gameScene;

import controller.MenuController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Map;
import models.interfaces.Time;
import models.objects.animals.Animal;
import view.MainView;

import java.util.ArrayList;

public class MapView extends Pane implements Time {
    private static MapView instance = new MapView();
    public static final double WIDTH_BASE = 20, HEIGHT_BASE = 12;
    private ArrayList<Text> entitiesInMap;
    public static MapView getInstance() {
        return instance;
    }

    private MapView() {
        entitiesInMap = new ArrayList<>();
        relocate(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3);
        setHeight(Map.HEIGHT * HEIGHT_BASE);
        setWidth(Map.WIDTH * WIDTH_BASE);
        build();
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MenuController.getInstance().click((event.getX() - 325) / WIDTH_BASE, event.getY() / HEIGHT_BASE);
            }
        });
    }

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    private void build() {
        // shows corner of map
        Rectangle rectangle1 = new Rectangle(10, 10);
        rectangle1.setFill(Color.BLUE);
        rectangle1.relocate(0,0);

        Rectangle rectangle2 = new Rectangle(10, 10);
        rectangle2.setFill(Color.BLUE);
        rectangle2.relocate(getWidth() - 10,getHeight()- 10);

        Rectangle rectangle3 = new Rectangle(10, 10);
        rectangle3.setFill(Color.BLUE);
        rectangle3.relocate(0,getHeight()- 10);

        Rectangle rectangle4 = new Rectangle(10, 10);
        rectangle4.setFill(Color.BLUE);
        rectangle4.relocate(getWidth() - 10,0);

        getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4);
    }

    @Override
    public void nextTurn() {
        getChildren().clear();
        Iterable<Animal> animals = Map.getInstance().getAnimals();
        for (Animal animal : animals) {
            animal.getText().relocate(animal.getCoordinates().getX() * WIDTH_BASE
                                     ,animal.getCoordinates().getY() * HEIGHT_BASE);
            getChildren().addAll(animal.getText());
        }
    }
}
