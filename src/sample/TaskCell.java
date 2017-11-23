package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.time.Instant;

public class TaskCell extends HBox {
    Label taskLabel = new Label();
    Button doButton;
    Button continueButton;
    Task task;
    Insets buttonPadding = new Insets(2, 4, 2, 4);

    TaskCell(Task task) {
        super();

        setSpacing(5);

        doButton = createButton("Do");
        continueButton = createButton("Continue");

        this.task = task;
        taskLabel.setText(task.task);
        taskLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(taskLabel, Priority.ALWAYS);

        task.statusObjectProperty().addListener(listener -> {
            TaskStatus status = task.statusObjectProperty().get();
            if (status == TaskStatus.STARTED) {
                continueButton.setVisible(true);
                doButton.setText("Done!");
            }

            if (status == TaskStatus.FINISHED) {
                Instant now = Instant.now();
                taskLabel.setText(taskLabel.getText() + " - Done at " + now.toString());
                doButton.setVisible(false);
                doButton.setManaged(false);
                continueButton.setVisible(false);
                continueButton.setVisible(false);
            }
        });

        //doButton.setPrefWidth(continueButton.getWidth());

        continueButton.setVisible(false);

        this.getChildren().addAll(taskLabel, continueButton, doButton);

    }

    private Button createButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setPadding(buttonPadding);

        button.setOnAction((event -> {
            task.progress();
        }));

        return button;
    }

}