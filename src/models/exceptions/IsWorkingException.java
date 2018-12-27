package models.exceptions;

public class IsWorkingException extends Exception
{
    public IsWorkingException() {
        super("workshop is busy now");
    }
}
