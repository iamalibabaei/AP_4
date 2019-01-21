package models.exceptions;

import java.io.IOException;

public class IsWorkingException extends IOException
{
    public IsWorkingException() {
        super("workshop is busy now");
    }
}
