package models;

public abstract class Entity
{

    protected Point point;
    protected boolean exists;

    public Entity(int x, int y)
    {
        this.point = new Point(x, y);
        exists = true;
    }

    public boolean Exists()
    {
        return exists;
    }

    public void die()
    {
        this.exists = false;
    }

    public int getX()
    {
        return point.getX();
    }

    public void setX(int x)
    {
        this.point.setX(x);
    }

    public int getY()
    {
        return point.getY();
    }

    public void setY(int y)
    {
        this.point.setY(y);
    }

    public Point getPoint() {
        return point;
    }

}
