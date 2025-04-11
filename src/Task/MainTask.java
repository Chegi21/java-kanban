package Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainTask {
    protected Integer id;
    protected final String name;
    protected final String description;
    protected Status status = Status.NEW;
    protected List<Integer> epicListID = new ArrayList<>();

    public MainTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public MainTask(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void saveEpicID(int epicID) {
        epicListID.add(epicID);
    }

    public void removeFromListEpicID(int epicID) {
        epicListID.remove(Integer.valueOf(epicID));
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getEpicListID() {
        return epicListID;
    }

    public void setEpicListID(List<Integer> epicListID) {
        this.epicListID = epicListID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainTask)) return false;
        MainTask mainTask = (MainTask) o;
        return id == mainTask.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MainTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicListID=" + epicListID +
                '}';
    }
}

