package models.objects;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class Entity {
    protected Point coordinates;
    private boolean exists;
    private ImageView imageView;

    public Text getText() {
        return text;
    }

    protected Text text;//TODO shape will be imageView

    public Entity(Point point) {
        coordinates = point;
        exists = true;

    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Point getCoordinates() {

        return coordinates;
    }

    public boolean notExists() {
        return !exists;
    }

    public void die() {
        this.exists = false;
    }

}
