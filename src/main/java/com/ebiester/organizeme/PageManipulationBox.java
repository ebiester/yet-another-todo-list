package com.ebiester.organizeme;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PageManipulationBox extends HBox {
    public PageManipulationBox(TaskList taskList) {
        Button nextPageButton = new Button(">");
        nextPageButton.setOnAction((event) -> taskList.getNextPage());

        getChildren().add(nextPageButton);
    }
}
