package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList {
    private ArrayList<Task> tasks;
    private ObservableList<TaskCell> visibleTasks;
    private int currentPageNumber;

    public TaskList() {
        tasks = new ArrayList<>();
        visibleTasks = FXCollections.observableArrayList(new ArrayList<>());
        currentPageNumber = 0;
    }

    public void add(String task) {
        Task item = new Task(task);
        tasks.add(item);

        if (visibleTasks.size() < 25) {
            visibleTasks.add(new TaskCell(item));
        }
    }


    public ObservableList<TaskCell> getFirstPage() {
        int itemsToReturn = Math.min(25, tasks.size());
        List<Task> newTasks = tasks.subList(0, itemsToReturn);
        visibleTasks.clear();

        newTasks.stream()
                .map((task) -> visibleTasks.add(new TaskCell(task)));

        return visibleTasks;
    }

}