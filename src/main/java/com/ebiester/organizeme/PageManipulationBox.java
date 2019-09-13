package com.ebiester.organizeme;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PageManipulationBox extends HBox {
    //TODO: Create own Toggle Archive Button and allow its behavior to track itself. Also need better name
    boolean currentlyHidingArchived = true;
    private Button toggleArchiveButton = null;

    public PageManipulationBox(TaskList taskList) {
        Button nextPageButton = new Button(">");
        nextPageButton.setOnAction((event) -> taskList.getNextPage());

        Button archiveButton = new Button("Archive");
        archiveButton.setOnAction((event) -> taskList.archivePage());

        toggleArchiveButton = new Button("Show Archive");
        toggleArchiveButton.setOnAction((event) -> toggleArchive(taskList));

        getChildren().add(nextPageButton);
        getChildren().add(archiveButton);
        getChildren().add(toggleArchiveButton);
    }

    //TODO: Create own Toggle Archive Button and allow its behavior to track itself
    //TODO: this is ugly and can be simplified.
    void toggleArchive(TaskList taskList) {
        if (currentlyHidingArchived) {
            taskList.showArchivedPages(true);
            toggleArchiveButton.setText("Hide Archive");
        } else {
            taskList.showArchivedPages(false);
            toggleArchiveButton.setText("Show Archive");
        }

        currentlyHidingArchived = !currentlyHidingArchived;
    }
}
