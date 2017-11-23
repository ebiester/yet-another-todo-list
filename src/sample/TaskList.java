package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;
    private ObservableList<TaskCell> visibleTasks;
    private int currentPageNumber;

    private static final Integer NUMBER_OF_TASKS_ON_PAGE = 25;

    public TaskList() {
        tasks = new ArrayList<>();
        visibleTasks = FXCollections.observableArrayList(new ArrayList<>());
        currentPageNumber = -1;
    }

    public void add(String task) {
        Task item = new Task(task, this);
        tasks.add(item);

        if (visibleTasks.size() < 25) {
            visibleTasks.add(new TaskCell(item));
        }
    }

    public ObservableList<TaskCell> getNextPage() {
        int nextPageNumber = currentPageNumber + 1;

        int nextPageSkipCount = NUMBER_OF_TASKS_ON_PAGE * (nextPageNumber);
        if (nextPageSkipCount >= tasks.size()) {
            nextPageNumber = 0;
        }

        List<Task> newTasks = getTaskPage(nextPageNumber);
        visibleTasks.clear();

        //simpler than streams in this case.
        for (Task task : newTasks) {
            visibleTasks.add(new TaskCell(task));
        }

        currentPageNumber = nextPageNumber;
        return visibleTasks;
    }

    private List<Task> getTaskPage(int pageNumber) {
        int from = pageNumber * NUMBER_OF_TASKS_ON_PAGE;
        int fullPageTo = (pageNumber + 1) * NUMBER_OF_TASKS_ON_PAGE;
        int to = Math.min(fullPageTo, tasks.size());

        return tasks.subList(from, to);
    }


}