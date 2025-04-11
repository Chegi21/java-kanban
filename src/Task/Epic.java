package Task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Sub> subTasks = new ArrayList<>();

    public Epic(String name, String description, int taskId) {
        super(name, description);
        this.status = Status.NEW;
        this.id = taskId;
    }

    public Epic(String name, String description, Status status, int taskId) {
        super(name, description, status);
        this.id = taskId;
    }


    public List<Sub> getSubTaskListOfEpic() {
        return subTasks;
    }

    public void setSubTaskListOfEpic(List<Sub> subTasks) {
        this.subTasks = subTasks;
    }


    public void saveSubTaskOfEpic(Sub subTaskOfEpic) {
        subTasks.add(subTaskOfEpic);
    }

    public void removeSubTaskOfEpic(Sub subTaskOfEpic) {
        subTasks.remove(subTaskOfEpic);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "Id=" + id +
                ", subTaskList=" + subTasks +
                "} " + super.toString();
    }
}

