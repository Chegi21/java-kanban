package Managers;

import Task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskList = new ArrayList<>();
    private static final int MAX_SIZE = 10;

    @Override
    public void add(Task task) {
        if (taskList.size() == MAX_SIZE) {
            taskList.remove(0);
        }
        taskList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskList);
    }
}
