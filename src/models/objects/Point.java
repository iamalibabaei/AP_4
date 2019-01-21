package models.objects;

import models.Game;

import java.util.Random;

public class Point
{
    private double x, y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Point normalize(Point point)
    {
        double length = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        point.setX(point.getX() / length);
        point.setY(point.getY() / length);
        return point;
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

    public boolean collidesWith(Point point)
    {
        return this.distance(point) <= Game.RADIUS;
    }

    public double distance(Point point1)
    {
        return Math.sqrt(Math.pow(point1.getX() - this.getX(), 2) + Math.pow(point1.getY() - this.getY(), 2));
    }

    public static Point randomPoint(double xBound, double yBound)
    {
        Random random = new Random();
        return new Point(random.nextDouble() * xBound, random.nextDouble() * yBound);
    }

}
