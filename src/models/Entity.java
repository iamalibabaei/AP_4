package models;

public abstract class Entity
{
    protected Point coordinates;
    protected boolean exists;

    public Entity(double x, double y)
    {
        coordinates.setX(x);
        coordinates.setY(y);
        exists = true;
    }

    public double getX()
    {
        return coordinates.getX();
    }

    public void setX(double x)
    {
        coordinates.setX(x);
    }

    public double getY()
    {
        return coordinates.getY();
    }

    public void setY(double y)
    {
        coordinates.setY(y);
    }

    public void setExists(boolean exists)
    {
        this.exists = exists;
    }

    public boolean Exists()
    {
        return exists;
    }

    public void die()
    {
        this.exists = false;
    }

}
