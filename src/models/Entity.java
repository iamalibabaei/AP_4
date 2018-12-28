package models;

public abstract class Entity
{
    protected int x, y;
    protected boolean exists;

    public Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
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
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

}
