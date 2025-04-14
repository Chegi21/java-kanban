package Task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.status = Status.NEW;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }


    public List<Subtask> getSubTaskListOfEpic() {
        return subTasks;
    }

    public void setSubTaskListOfEpic(List<Subtask> subTasks) {
        this.subTasks = subTasks;
    }


    public void saveSubTaskOfEpic(Subtask subtaskTaskOfEpic) {
        subTasks.add(subtaskTaskOfEpic);
    }

    public void removeSubTaskOfEpic(Subtask subtaskTaskOfEpic) {
        subTasks.remove(subtaskTaskOfEpic);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "Id=" + id +
                ", subTaskList=" + subTasks +
                "} " + super.toString();
    }
}

