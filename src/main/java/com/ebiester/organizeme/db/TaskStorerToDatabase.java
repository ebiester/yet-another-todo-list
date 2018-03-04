package com.ebiester.organizeme.db;

import com.ebiester.organizeme.Task;
import com.ebiester.organizeme.TaskStatus;
import jetbrains.exodus.entitystore.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TaskStorerToDatabase {
    private static final String taskEntityType = "Task";

    public void store(Task task) {
        PersistentEntityStore entityStore = DBConfigurationHolder.getPersistentEntityStore();
        StoreTransaction txn = entityStore.beginTransaction();
        try {
            do {
                Entity taskEntity;
                EntityId taskEntityId = task.getEntityId();

                if (taskEntityId != null) {
                    taskEntity = txn.getEntity(taskEntityId);
                } else {
                    taskEntity = txn.newEntity(taskEntityType);
                    taskEntity.setProperty("taskName", task.getTaskName());
                    taskEntity.setProperty("createdTime",
                            getDatabaseRepresentationOfLocalTime(task.getCreatedTime()));
                }

                taskEntity.setProperty("status", task.getStatus().name());

                if (task.getStartedTime() != null) {
                    taskEntity.setProperty("startedTime",
                            getDatabaseRepresentationOfLocalTime(task.getStartedTime()));
                }
                if (task.getEndedTime() != null) {
                    taskEntity.setProperty("endedTime",
                            getDatabaseRepresentationOfLocalTime(task.getEndedTime()));
                }

                //the current transaction will abort if someone changes underneath you.
                if (txn != entityStore.getCurrentTransaction()) {
                    txn = null;
                    break;
                }

                task.setEntityId(taskEntity.getId());
            } while (!txn.flush());
        } finally {
            // if txn has not already been aborted in execute()
            if (txn != null) {
                txn.abort();
            }

            entityStore.close();
        }
    }

    //this shouldn't be necessary.
    private String getDatabaseRepresentationOfLocalTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public List<Task> getTasksFromDatabase() {
        PersistentEntityStore entityStore = DBConfigurationHolder.getPersistentEntityStore();
        StoreTransaction txn = entityStore.beginReadonlyTransaction();
        LinkedList<Task> tasks = new LinkedList<>();
        try {
            final EntityIterable allTasks = txn.getAll(taskEntityType);
            for (Entity task : allTasks) {
                String taskname = task.getProperty("taskName").toString();
                TaskStatus status = TaskStatus.valueOf(task.getProperty("status").toString());
                LocalDateTime createdTime =
                        getLocalTimeFromDatabaseRepresentation(task.getProperty("createdTime")).get();
                  Optional<LocalDateTime> startedTime =
                        getLocalTimeFromDatabaseRepresentation(task.getProperty("startedTime"));
                  Optional<LocalDateTime> endedTime =
                        getLocalTimeFromDatabaseRepresentation(task.getProperty("endedTime"));


                tasks.add(new Task(task.getId(),
                        taskname,
                        status,
                        createdTime,
                        startedTime,
                        endedTime));
            }
        } finally {
            if (txn != null) {
                txn.abort();
            }
            entityStore.close();
        }

        return tasks;
    }

    private Optional<LocalDateTime> getLocalTimeFromDatabaseRepresentation(Comparable databaseRepresentation) {

        LocalDateTime time = null;
        if (databaseRepresentation != null) {
            time = LocalDateTime.parse(databaseRepresentation.toString());
        }

        return Optional.ofNullable(time);
    }
}
