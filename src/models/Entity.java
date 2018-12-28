package models;

public abstract class Entity
{
    protected int x, y;
    protected boolean exists;

    public boolean Exists()
    {
        return exists;
    }

    public void setExists(boolean exists)
    {
        this.exists = exists;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x) { this.x = x; }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public abstract int getOccupationSpace();

}
