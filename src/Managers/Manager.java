package Managers;

import java.io.File;

public class Manager {
    public static TaskManager  getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackedManager() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "tasks.csv");
        return FileBackedTaskManager.loadFromFile(getDefaultHistory(), file);
    }

}
