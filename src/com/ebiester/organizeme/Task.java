package com.ebiester.organizeme;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Task {
    public String task;
    private TaskList parentList;
    private ObjectProperty<TaskStatus> status;

    public Task(String task, TaskList taskList) {
        this.task = task;
        this.parentList = taskList;
        status = new SimpleObjectProperty<>(TaskStatus.READY);
    }

    public ObjectProperty<TaskStatus> statusObjectProperty() {
        return status;
    }

    public void progress() {
        if (status.get() == TaskStatus.READY) {
            status.setValue(TaskStatus.STARTED);
        } else {
            status.setValue(TaskStatus.FINISHED);
        }
    }

    public void continueLater() {
        status.setValue(TaskStatus.CONTINUE_LATER);
        parentList.add(this.task);
    }

    public String toString() {
        return task;
    }
}