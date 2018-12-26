package models.exceptions;

public class MaxlevelException extends Exception {
    public MaxlevelException() {
        super("this object reached to its maximum level");
    }
}
