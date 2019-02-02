package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class SceneBuilder extends Scene
{
    protected ObservableList<Node> childrenList;

    protected SceneBuilder(double width, double height)
    {
        super(new Group(), width, height);
        childrenList = ((Group) getRoot()).getChildren();
        build();
    }

    protected abstract void build();
}
