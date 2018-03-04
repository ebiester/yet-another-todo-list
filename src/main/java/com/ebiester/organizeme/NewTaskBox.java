package com.ebiester.organizeme;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NewTaskBox extends HBox {
    public NewTaskBox(TaskList taskList) {
        TextField addTodoTextField = new TextField();
        addTodoTextField.setMaxWidth(Double.MAX_VALUE);

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

        getChildren().add(addTodoTextField);
        getChildren().add(addButton);
    }
}
