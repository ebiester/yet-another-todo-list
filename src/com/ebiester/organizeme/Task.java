package com.ebiester.organizeme;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ebiester.organizeme.TaskStatus.READY;

public class Task {
    public String task;
    private TaskList parentList;
    private ObjectProperty<TaskStatus> status;
    LocalDateTime createdTime;
    LocalDateTime startedTime;
    LocalDateTime endedTime;

    public Task(String task, TaskList taskList) {
        this.task = task;
        this.parentList = taskList;
        status = new SimpleObjectProperty<>(READY);
        this.createdTime = LocalDateTime.now();
    }

    public ObjectProperty<TaskStatus> statusObjectProperty() {
        return status;
    }

    public void progress() {
        if (status.get() == READY) {
            startedTime = LocalDateTime.now();
            status.setValue(TaskStatus.STARTED);
        } else {
            endedTime = LocalDateTime.now();
            status.setValue(TaskStatus.FINISHED);
        }
    }

    public void continueLater() {
        status.setValue(TaskStatus.CONTINUE_LATER);
        endedTime = LocalDateTime.now();
        parentList.add(this.task);
    }

    // Not sure I like toString controlling this behavior - will likely move this to a decorator later.
    public String toString() {
        String append = null;
        switch (status.get()) {
            case READY:
                append = "";
                break;
            case STARTED:
                append = " - started at " + startedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                break;
            case FINISHED:
                append = " - finished at " + endedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            case CONTINUE_LATER:
                append = " - worked on until " + endedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return task + append;
    }
}