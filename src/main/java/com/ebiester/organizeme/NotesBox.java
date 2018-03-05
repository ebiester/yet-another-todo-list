package com.ebiester.organizeme;

import javafx.scene.control.TextArea;

public class NotesBox extends TextArea {
    private TaskCell currentTaskCell;
    public NotesBox() {
        setPrefHeight(500);
    }

    public void update(TaskCell taskCell) {
        if (currentTaskCell != null) {
            if (!currentTaskCell.getNotes().equals(getText())) {
                currentTaskCell.setNotes(getText());
            }
        }

        currentTaskCell = taskCell;
        setText(taskCell.getNotes());
    }


    public void update() {
        currentTaskCell.setNotes(getText());
    }
}
