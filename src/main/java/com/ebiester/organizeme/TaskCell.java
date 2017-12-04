package com.ebiester.organizeme;

import com.ebiester.organizeme.db.TaskStorerToDatabase;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.Instant;

class TaskCell extends HBox {
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
        showCorrectButtons(task);
    }

    private InvalidationListener getStatusInvalidationListener(Task task) {
        return listener -> {
            new TaskStorerToDatabase().store(task);
            showCorrectButtons(task);
            taskLabel.setText(task.toString());

        };
    }

    private void showCorrectButtons(Task task) {
        TaskStatus status = task.statusObjectProperty().get();
        switch (status) {
            case STARTED:
                continueButton.setVisible(true);
                doButton.setText("Done!");
                break;
            case FINISHED:
            case CONTINUE_LATER:
                clearButton(doButton);
                clearButton(continueButton);
        }
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