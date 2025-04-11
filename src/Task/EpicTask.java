package Task;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends MainTask {
    private final int taskID;
    private List<Integer> subTaskListID = new ArrayList<>();

    public EpicTask(String name, String description, int taskID) {
        super(name, description);
        this.status = Status.NEW;
        this.taskID = taskID;
    }

    public EpicTask(String name, String description, Status status, int taskID) {
        super(name, description, status);
        this.taskID = taskID;
    }


    public int getTaskID() {
        return taskID;
    }

    public List<Integer> getSubTaskListID() {
        return subTaskListID;
    }

    public void setSubTaskListID(List<Integer> subTaskListID) {
        this.subTaskListID = subTaskListID;
    }

    public void saveSubTaskID(int subTaskID) {
        subTaskListID.add(subTaskID);
    }

    public void removeFromListSubTaskID(int subTaskID) {
        subTaskListID.remove(Integer.valueOf(subTaskID));
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "taskID=" + taskID +
                ", subTaskListID=" + subTaskListID +
                "} " + super.toString();
    }
}

