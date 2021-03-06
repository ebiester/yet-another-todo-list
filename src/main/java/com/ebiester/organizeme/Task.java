package com.ebiester.organizeme;

import com.ebiester.organizeme.db.TaskStorerToDatabase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jetbrains.exodus.entitystore.EntityId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.ebiester.organizeme.TaskStatus.*;

public class Task {
    private String taskName;
    private TaskList parentList;
    private ObjectProperty<TaskStatus> status;
    private String notes;
    private LocalDateTime createdTime;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    //Does this belong here? Get this out or move storage in? Think about proper factoring.
    private EntityId entityId;
    private boolean isArchived;

    public Task(String taskName, String notes, TaskList taskList) {
        this.taskName = taskName;
        this.parentList = taskList;
        this.notes = notes;
        status = new SimpleObjectProperty<>(READY);
        this.createdTime = LocalDateTime.now();
        this.isArchived = false;
        new TaskStorerToDatabase().store(this);
    }

    public Task(EntityId entityId,
                String taskName,
                TaskStatus taskStatus,
                String notes,
                boolean isArchived,
                LocalDateTime createdTime,
                Optional<LocalDateTime> startedTime,
                Optional<LocalDateTime> endedTime) {
        this.entityId = entityId;
        this.taskName = taskName;
        this.status = new SimpleObjectProperty<>(taskStatus);
        this.createdTime = createdTime;
        this.isArchived = isArchived;
        if (notes != null) {
            this.notes = notes;
        } else {
            this.notes = "";
        }

        startedTime.ifPresent(localDateTime -> this.startedTime = localDateTime);
        endedTime.ifPresent(localDateTime -> this.endedTime = localDateTime);

    }

    public ObjectProperty<TaskStatus> statusObjectProperty() {
        return status;
    }

    public void progress() {
        if (status.get() == READY) {
            startedTime = LocalDateTime.now();
            status.setValue(STARTED);
        } else {
            endedTime = LocalDateTime.now();
            status.setValue(FINISHED);
        }
    }

    public void continueLater() {
        endedTime = LocalDateTime.now();
        status.setValue(CONTINUE_LATER);
        parentList.add(this.taskName, this.getNotes());
    }

    public void notDone() {
        endedTime = LocalDateTime.now();
        status.setValue(NOT_DONE);
    }

    public void archive() {
        isArchived = true;
        if (endedTime != null) {
            endedTime = LocalDateTime.now();
        }

        if (status.getValue() != FINISHED) {
            status.setValue(NOT_DONE);
        }
    }

    // Not sure I like toString controlling this behavior - will likely move this to a decorator later.
    public String toString() {
        String formattedStatus = "";
        String formattedEndTime = endedTime != null ? endedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "";
        switch (status.get()) {
            case READY:
                break;
            case STARTED:
                formattedStatus = " - started at " + startedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                break;
            case FINISHED:
                formattedStatus = " - finished at " + formattedEndTime;
                break;
            case CONTINUE_LATER:
                formattedStatus = " - worked on until " + formattedEndTime;
                break;
            case NOT_DONE:
                formattedStatus = " - not done on " + formattedEndTime;
                break;
        }

        return taskName + formattedStatus;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isArchived() {
        return this.isArchived;
    }
}