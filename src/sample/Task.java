package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Task {
    public String task;
    private TaskStatus status;

    private ObjectProperty<TaskStatus> statusObject;

    public Task(String task) {
        this.task = task;
        statusObject = new SimpleObjectProperty<>(TaskStatus.READY);
    }

    public TaskStatus getStatus() {
        return statusObject.get();
    }

    public ObjectProperty<TaskStatus> statusObjectProperty() {
        return statusObject;
    }

    public void progress() {
        if (statusObject.get() == TaskStatus.READY) {
            statusObject.setValue(TaskStatus.STARTED);
        } else {
            statusObject.setValue(TaskStatus.FINISHED);
        }
    }

    public String toString() {
        return task;
    }
}