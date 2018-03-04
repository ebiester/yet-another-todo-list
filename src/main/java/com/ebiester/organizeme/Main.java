package com.ebiester.organizeme;

//import com.ebiester.organizeme.db.DBConfigurationHolder;
import com.ebiester.organizeme.db.DBConfigurationHolder;
import com.ebiester.organizeme.db.TaskStorerToDatabase;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jetbrains.exodus.entitystore.PersistentEntityStore;

import java.io.File;
import java.util.List;

/*
Note: a giant Application/controller mashup is not good architecture, but I'm still trying to understand the
needs of my system before committing to an architecture, taking advantage of beginner's mind. I'll move to FXML,
controllers, and proper patterns as it reveals itself. This is a small enough application that I can get away with it.

I've also played with a few basic ideas but haven't committed to one yet.
*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // TODO: Make this user configurable
        String currentUserHomeDir = System.getProperty("user.home");
        String storageDir = currentUserHomeDir + File.separator + ".organizemedev";
        DBConfigurationHolder.setStorageDir(storageDir);
        List<Task> tasksFromDatabase = new TaskStorerToDatabase().getTasksFromDatabase();

        TaskList taskList = new TaskList(tasksFromDatabase);

        VBox tilePane = new VBox();
        primaryStage.setTitle("Yet Another Todo List");
        primaryStage.setScene(new Scene(new AppLayoutVBox(taskList)));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
