package com.ebiester.organizeme;

import com.ebiester.organizeme.db.TaskStorerToDatabase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jetbrains.exodus.entitystore.EntityId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.ebiester.organizeme.TaskStatus.READY;

public class Task {
    private String taskName;
    private TaskList parentList;
    private ObjectProperty<TaskStatus> status;
    private LocalDateTime createdTime;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    //Does this belong here? Get this out or move storage in? Think about proper factoring.
    private EntityId entityId;

    public Task(String taskName, TaskList taskList) {
        this.taskName = taskName;
        this.parentList = taskList;
        status = new SimpleObjectProperty<>(READY);
        this.createdTime = LocalDateTime.now();
        new TaskStorerToDatabase().store(this);
    }

    public Task(EntityId entityId,
                String taskName,
                TaskStatus taskStatus,
                LocalDateTime createdTime,
                Optional<LocalDateTime> startedTime,
                Optional<LocalDateTime> endedTime) {
        this.entityId = entityId;
        this.taskName = taskName;
        this.status = new SimpleObjectProperty<>(taskStatus);
        this.createdTime = createdTime;
        startedTime.ifPresent(localDateTime -> this.startedTime = localDateTime);
        endedTime.ifPresent(localDateTime -> this.endedTime = localDateTime);

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
        endedTime = LocalDateTime.now();
        status.setValue(TaskStatus.CONTINUE_LATER);
        parentList.add(this.taskName);
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
                break;
            case CONTINUE_LATER:
                append = " - worked on until " + endedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                break;
        }

        return taskName + append;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskStatus getStatus() {
        return status.get();
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getStartedTime() {
        return startedTime;
    }

    public LocalDateTime getEndedTime() {
        return endedTime;
    }

    public EntityId getEntityId() {
        return entityId;
    }

    public void setEntityId(EntityId entityId) {
        this.entityId = entityId;
    }

    public void setParentList(TaskList parentList) {
        this.parentList = parentList;
    }

}