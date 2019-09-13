package com.ebiester.organizeme;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class MenuBuilder {

    public MenuBar getMenu() {
        final Menu fileMenu = new Menu("File");

        MenuItem importItem = new MenuItem("Import/Replace");
        fileMenu.getItems().addAll(getExportItem(),
                importItem,
                new SeparatorMenuItem(),
                getExitMenuItem());

        final Menu reportMenu = new Menu("Report");
        MenuItem tasksItem = new MenuItem("Tasks");
        reportMenu.getItems().add(tasksItem);

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(fileMenu, reportMenu);
        return menuBar;
    }

    @NotNull
    private MenuItem getExportItem() {
        MenuItem exportItem = new MenuItem("Export");

        Stage stage = new Stage();
        Text text = new Text(10, 40, "Hello World!");
        text.setFont(new Font(40));
        Scene scene = new Scene(new Group(text));

        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        EventHandler<ActionEvent> eventHandler = event -> stage.show();
        exportItem.setOnAction(eventHandler);

        return exportItem ;
    }

    @NotNull
    private MenuItem getExitMenuItem() {
        MenuItem exitItem = new MenuItem("Exit");
        EventHandler<ActionEvent> continueEventHandler = event -> System.exit(0);
        exitItem.setOnAction(continueEventHandler);
        return exitItem;
    }
}
