package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public abstract class SceneBuilder extends Scene
{
    protected ObservableList<Node> childrenList;

    protected SceneBuilder(double width, double height)
    {
        super(new Group(), width, height);
        childrenList = ((Group) getRoot()).getChildren();
        build();
    }

    protected SceneBuilder(double width, double height, Paint color)
    {
        this(width, height);
        setFill(color);
    }

    protected abstract void build();
}
