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

        ListView<TaskList> taskView = setupTaskListListView(taskList);

        VBox tilePane = new VBox();
        primaryStage.setTitle("Yet Another Todo List");
        primaryStage.setScene(new Scene(tilePane, 300, 275));
        ObservableList<Node> children = tilePane.getChildren();
        children.add(setupNewTaskBox(taskList));
        children.add(taskView);
        children.add(setupPageManipulationBox(taskList));

        primaryStage.show();
    }

    private HBox setupPageManipulationBox(TaskList taskList) {
        HBox hBox = new HBox();
        Button nextPageButton = new Button(">");
        nextPageButton.setOnAction((event) -> taskList.getNextPage());

        hBox.getChildren().add(nextPageButton);
        return hBox;
    }

    private HBox setupNewTaskBox(TaskList taskList) {
        HBox hBox = new HBox();
        TextField addTodoTextField = new TextField();

        Button addButton = new Button();
        addButton.setText("Add");

        EventHandler<ActionEvent> actionEvent = (event) -> {
            StringProperty todoText = addTodoTextField.textProperty();
            if (todoText.isNotEmpty().get()) {
                taskList.add(todoText.get());
                todoText.setValue("");
            }
        };

        addTodoTextField.setOnAction(actionEvent);
        addButton.setOnAction(actionEvent);

        hBox.getChildren().add(addTodoTextField);
        hBox.getChildren().add(addButton);
        return hBox;
    }

    private ListView<TaskList> setupTaskListListView(TaskList taskList) {

        ListView<TaskList> taskView = new ListView(taskList.getNextPage());

        taskView.setPrefSize(610.0, 800.0);
        taskView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskView.setEditable(true);
        return taskView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
