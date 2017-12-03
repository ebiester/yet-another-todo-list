package com.ebiester.organizeme;

import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.Instant;

public class TaskCell extends HBox {
    private Label taskLabel = new Label();
    private Button doButton;
    private Button continueButton;
    private Task task;
    private Insets buttonPadding = new Insets(2, 4, 2, 4);

    TaskCell(Task task) {
        super();

        setSpacing(5);

        EventHandler<ActionEvent> progressEventHandler = event -> task.progress();
        doButton = createButton("Do", progressEventHandler);

        EventHandler<ActionEvent> continueEventHandler = event -> task.continueLater();
        continueButton = createButton("Continue", continueEventHandler);

        this.task = task;
        taskLabel.setText(task.toString());
        taskLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(taskLabel, Priority.ALWAYS);
        task.statusObjectProperty().addListener(getStatusInvalidationListener(task));

        //doButton.setPrefWidth(continueButton.getWidth());

        continueButton.setVisible(false);

        this.getChildren().addAll(taskLabel, continueButton, doButton);

    }

    private InvalidationListener getStatusInvalidationListener(Task task) {
        return listener -> {
                TaskStatus status = task.statusObjectProperty().get();
                switch (status) {
                    case STARTED:
                        continueButton.setVisible(true);
                        doButton.setText("Done!");
                        break;
                    case FINISHED:
                        taskLabel.setText(taskLabel.getText() + " - Done at " + Instant.now().toString());
                        clearButton(doButton);
                        clearButton(continueButton);
                        break;
                    case CONTINUE_LATER:
                        taskLabel.setText(taskLabel.getText() + " - Continue later at " + Instant.now().toString());
                        clearButton(doButton);
                        clearButton(continueButton);
                        break;
                }
            };
    }

    private void clearButton(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }

    private Button createButton(String buttonText, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(buttonText);
        button.setPadding(buttonPadding);

        button.setOnAction(eventHandler);

        return button;
    }

}