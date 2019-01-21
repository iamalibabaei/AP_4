package models.objects;

public abstract class Entity {
    private Point coordinates;
    private boolean exists;

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

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean Exists() {
        return exists;
    }

    public void die() {
        this.exists = false;
    }

}
