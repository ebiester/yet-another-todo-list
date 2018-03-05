package com.ebiester.organizeme;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AppLayoutVBox extends VBox{
    public AppLayoutVBox(TaskList taskList) {
        ObservableList<Node> children = getChildren();
        TaskListView<TaskCell> taskListView = new TaskListView<>(taskList);
        NotesBox notesBox = new NotesBox();
        taskListView.setOnMouseClicked((event) -> {
            //FIXME I shouldn't have to cast here.
            TaskCell taskCell = (TaskCell)taskListView.getSelectionModel().getSelectedItem();
            notesBox.update(taskCell);
                }
        );

        notesBox.textProperty().addListener((arg, oldValue, newValue) -> {
            notesBox.update();
        });

        children.add(new NewTaskBox(taskList));
        children.add(taskListView);
        children.add(new PageManipulationBox(taskList));
        children.add(notesBox);
    }
}
