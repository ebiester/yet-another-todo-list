package com.ebiester.organizeme;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AppLayoutVBox extends VBox{
    public AppLayoutVBox(TaskList taskList) {
        ObservableList<Node> children = getChildren();
        children.add( new NewTaskBox(taskList));
        children.add(new TaskListView(taskList));
        children.add(new PageManipulationBox(taskList));
    }
}
