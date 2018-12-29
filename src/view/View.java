package view;

import controller.Controller;

import java.util.Scanner;
import java.util.regex.Pattern;

public class View
{
    private static View view = new View();
    Scanner scanner;
    private Controller controller;

    private View()
    {
        this.controller = Controller.getInstance();
        scanner = new Scanner(System.in);
    }

    public static View getInstance()
    {
        return view;
    }

    public void handleAlreadyAtMaxLevelException()
    {
        System.out.println("Can't upgrade beyond maxLevel.");
    }

    public void handleInsufficientResourcesException()
    {
        System.out.println("You don't have enough resources");
    }

    public void handleInvalidArgumentException()
    {
        System.out.println("Unknown paratemer; To see all command and their arguments type 'print info'.");
    }

    public void handleIsWorkingException(String name)
    {
        System.out.println(name + " is busy now.");
    }

    public void handleItemNotInWarehouseException()
    {
    }

    public void handleNotEnoughSpaceException()
    {

    }

    public void nextLine()
    {
        String s = scanner.nextLine();
        Pattern p = Pattern.compile(s);
    }

    public void printMap()
    {
    }

    public void printInfo()
    {

    }

    public void printWarehouse()
    {

    }

    public void printWell()
    {
    }

    public void printTruck()
    {

    }

    public void printHelicopter()
    {

    }

    public void printLevel()
    {
    }


}
