package sample;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TaskList taskList = new TaskList();

        ListView<TaskList> taskView = setupTaskListListView(taskList);

        for (int i = 0; i < 5; i++) {
            taskList.add(Integer.toString(i));
        }

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        VBox tilePane = new VBox();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(tilePane, 300, 275));
        tilePane.getChildren().add(setupNewTaskBox(taskList));
        tilePane.getChildren().add(taskView);

        primaryStage.show();
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

        ListView<TaskList> taskView = new ListView(taskList.getFirstPage());

        taskView.setPrefSize(610.0, 800.0);
        taskView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskView.setEditable(true);
        return taskView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
