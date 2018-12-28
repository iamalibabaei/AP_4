package models.exceptions;

public class ItemNotInWarehouseException extends Exception
{
    public ItemNotInWarehouseException() {
        super("there is no source in warehouse");
    }
}
