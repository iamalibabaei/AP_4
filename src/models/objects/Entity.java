package models.objects;

public abstract class Entity {
    protected Point coordinates;
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

    public boolean notExists() {
        return !exists;
    }

    public void die() {
        this.exists = false;
    }

}
