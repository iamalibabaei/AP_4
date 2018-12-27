package models.exceptions;

public class NotINWarehouseException extends Exception
{
    public NotINWarehouseException() {
        super("there is no source in warehouse");
    }
}
