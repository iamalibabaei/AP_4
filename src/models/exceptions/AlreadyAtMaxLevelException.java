package models.exceptions;

public class AlreadyAtMaxLevelException extends RuntimeException
{
    public AlreadyAtMaxLevelException()
    {
        super("this object reached to its maximum level");
    }

}
