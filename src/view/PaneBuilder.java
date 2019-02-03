package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class PaneBuilder extends Pane
{
    protected ObservableList<Node> childrenList;

    protected PaneBuilder(double x, double y, double width, double height)
    {
        this(x, y);
        setWidth(width);
        setHeight(height);
    }

    protected PaneBuilder(double x, double y)
    {
        relocate(x, y);
        childrenList = getChildren();
        build();
    }

    protected abstract void build();

}
