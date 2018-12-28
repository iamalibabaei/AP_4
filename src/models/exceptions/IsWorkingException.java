package models.exceptions;

public class IsWorkingException extends RuntimeException
{
    public IsWorkingException() {
        super("workshop is busy now");
    }
}
