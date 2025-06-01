package Task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
        this.status = Status.NEW;
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.taskType = TaskType.EPIC;
    }

    public Epic(String name, String description, Status status, TaskType taskType) {
        super(name, description, status, taskType);
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
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", taskType=" + taskType +
                ", subTaskList=" + subTasks +
                "} ";
    }
}

