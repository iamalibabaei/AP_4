package models.exceptions;

public class AlreadyAtMaxLevelException extends Exception {
    public AlreadyAtMaxLevelException() {
        super("this object reached to its maximum level");
    }
}
