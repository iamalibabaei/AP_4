package models.objects;

import controller.InGameController;
import javafx.scene.text.Text;
import models.Viewable;

public abstract class Entity extends Viewable
{
    protected Point coordinates;
    private boolean exists;

    public Entity(Point point)
    {
        coordinates = point;
        exists = true;
    }

    public boolean collidesWith(Entity entity)
    {
        return coordinates.distanceFrom(entity.coordinates) <= InGameController.COLLISION_RADIUS;
    }

    public Point getCoordinates()
    {

        return coordinates;
    }

    public void setCoordinates(Point coordinates)
    {
        this.coordinates = coordinates;
    }

    public boolean notExists()
    {
        return !exists;
    }

    public void die()
    {
        exists = false;
    }

}
