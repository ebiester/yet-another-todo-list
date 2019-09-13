package com.ebiester.organizeme;

import com.ebiester.organizeme.db.DBConfigurationHolder;
import com.ebiester.organizeme.db.TaskStorerToDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/*
Note: a giant Application/controller mashup is not good architecture, but I'm still trying to understand the
needs of my system before committing to an architecture, taking advantage of beginner's mind. I'll move to FXML,
controllers, and proper patterns as it reveals itself. This is a small enough application that I can get away with it.

I've also played with a few basic ideas but haven't committed to one yet.
*/

public class Main extends Application {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{

        Map<String, String> params = getParameters().getNamed();
        String currentUserHomeDir = System.getProperty("user.home");
        String defaultStorageDir = currentUserHomeDir + File.separator + ".organizeme";
        String storageDir = params.getOrDefault("dbpath", defaultStorageDir);

        log.info("Starting in " + storageDir);

        DBConfigurationHolder.setStorageDir(storageDir);
        List<Task> tasksFromDatabase = new TaskStorerToDatabase().getTasksFromDatabase();

        TaskList taskList = new TaskList(tasksFromDatabase);

        primaryStage.setTitle("Yet Another Todo List");
        primaryStage.setScene(new Scene(new AppLayoutVBox(taskList)));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
