package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TaskList taskList = new TaskList();
        for (int i = 0; i < 5; i++ ) {
            taskList.add(new Integer(i).toString());
        }

        ListView<TaskList> taskView = setupTaskListListView(taskList);

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
        addButton.setText("add");

        EventHandler<ActionEvent> actionEvent = (event) -> {
            StringProperty todoText = addTodoTextField.textProperty();
            taskList.add(todoText.get());
            todoText.setValue("");
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
