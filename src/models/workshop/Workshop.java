package models.workshop;

import models.Item;
import models.Warehouse;
import models.interfaces.Upgradable;
import models.map.Cell;

import java.util.HashMap;
public class Workshop implements Upgradable
{

    private int level = 0, workingTime, timeToReturnProduct = 0, numberOfInputs, numberOfOutputs, maxNumOfInput;
    double factorOfOutput;
    private final Item.Type input, output;
    private Warehouse warehouse;
    private boolean isWorking = false;
    private Cell cell;//this is the cell where it puts the result items

    public Workshop(int workingTime, int timeToReturnProduct, int maxNumOfInput, double factorOfOutput,
                    Item.Type input, Item.Type output, Warehouse warehouse, boolean isWorking, Cell cell) {
        this.workingTime = workingTime;
        this.timeToReturnProduct = timeToReturnProduct;
        this.maxNumOfInput = maxNumOfInput;
        this.factorOfOutput = factorOfOutput;
        this.input = input;
        this.output = output;
        this.warehouse = warehouse;
        this.isWorking = isWorking;
        this.cell = cell;
    }

    public void goToWork() throws IsWorkingException, NotINWarehouse{
        if (!isWorking) {
            throw new IsWorkingException();
        }

        if (warehouse.isAvailable(input)) {
            throw new NotINWarehouse();
        }

        isWorking = true;
        timeToReturnProduct = workingTime;
        numberOfInputs = warehouse.getItemToWorkshop(input, maxNumOfInput);
        numberOfOutputs = factorOfOutput * numberOfInputs < 1 ? 1: (int)( factorOfOutput * numberOfInputs);
    }


    public void turn() {
        timeToReturnProduct--;
        if (timeToReturnProduct == 0) {
            isWorking = false;
            returnProduct();

        }
    }

    private void returnProduct() {
        for (int i = numberOfOutputs ; i > 0 ; i --) {
            cell.getEntities().add(Item.Type.TYPE_INDEXED(output.getType()));
        }
    }
        

    @Override
    public void upgrade() {
        level++;
        factorOfOutput = factorOfOutput * 1.5;
        workingTime = (int)(workingTime * 0.8);
        maxNumOfInput += 2;
    }
}

class IsWorkingException extends Exception{
    public IsWorkingException() {
        super("workshop is busy now");
    }
}

class NotINWarehouse extends Exception
{
    public NotINWarehouse() {
        super("there is no source in warehouse");
    }
}