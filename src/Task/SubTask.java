package Task;

public class SubTask extends MainTask {
    private final int epicID;

    public SubTask(String name, String description, int epicID) {
        super(name, description);
        this.status = Status.NEW;
        this.epicID = epicID;
    }

    public SubTask(String name, String description, Status status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicID=" + epicID +
                "} " + super.toString();
    }
}

