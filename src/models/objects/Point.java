package models.objects;

import java.util.Random;

public class Point
{
    private double x, y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Point randomPoint(double xBound, double yBound)
    {
        Random random = new Random();
        return new Point(random.nextDouble() * xBound, random.nextDouble() * yBound);
    }

    public void normalize()
    {
        double length = Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public <T extends Entity> double distanceFrom(T entity)
    {
        return Math.sqrt(StrictMath.pow(entity.coordinates.x - x, 2.0) + StrictMath.pow(entity.coordinates.y - y, 2.0));
    }

    public double distanceFrom(Point point1)
    {
        return Math.sqrt(StrictMath.pow(point1.x - x, 2.0) + StrictMath.pow(point1.y - y, 2.0));
    }

}
