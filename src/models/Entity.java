package models;

public abstract class Entity {
    protected Point coordinates;
    protected boolean exists;

    public Entity(double x, double y) {
        coordinates.setX(x);
        coordinates.setY(y);
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
