package sample;

public class Item {
    public String task;
    public Boolean finished;

    public Item(String task) {
        this.task = task;
        this.finished = false;
    }

    public String toString() {
        return task;
    }
}