package sample;

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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TaskList taskList = new TaskList();

        ListView<TaskList> taskView = setupTaskListListView(taskList);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        VBox tilePane = new VBox();
        primaryStage.setTitle("Hello World");
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
