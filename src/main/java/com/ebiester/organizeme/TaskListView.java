package com.ebiester.organizeme;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class TaskListView<T> extends ListView {

    public TaskListView(TaskList taskList) {
        super(taskList.getNextPage());
        //705 conveniently captures 25 columns. There's probably a better way to do this.
        setPrefSize(600, 705);

        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setEditable(true);
    }
}
