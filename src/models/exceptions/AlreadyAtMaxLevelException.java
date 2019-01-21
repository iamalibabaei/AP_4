package models.exceptions;

import java.io.IOException;

public class AlreadyAtMaxLevelException extends IOException
{
    public AlreadyAtMaxLevelException()
    {
        super("this object reached to its maximum level");
    }

}
