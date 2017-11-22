package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Item> items;
    private ObservableList<Item> visibleItems;
    private int currentPageNumber;

    public TaskList() {
        items = new ArrayList<>();
        visibleItems = FXCollections.observableArrayList(new ArrayList<>());
        currentPageNumber = 0;
    }

    public void add(String task) {
        Item item = new Item(task);
        items.add(item);

        if (visibleItems.size() < 25) {
            visibleItems.add(item);
        }
    }


    public ObservableList<Item> getFirstPage() {
        int itemsToReturn = Math.min(25, items.size());
        List<Item> newItems = items.subList(0, itemsToReturn);
        visibleItems.setAll(newItems);
        return visibleItems;
    }

}