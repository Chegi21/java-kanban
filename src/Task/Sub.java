package Task;

public class Sub extends Task {
    private final int epicId;

    public Sub(String name, String description, int epicId) {
        super(name, description);
        this.status = Status.NEW;
        this.epicId = epicId;
    }

    public Sub(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Sub{" +
                "epicID=" + epicId +
                "} " + super.toString();
    }
}

