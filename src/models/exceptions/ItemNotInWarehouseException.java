package models.exceptions;

public class ItemNotInWarehouseException extends RuntimeException
{
    public ItemNotInWarehouseException() {
        super("there is no source in warehouse");
    }
}
