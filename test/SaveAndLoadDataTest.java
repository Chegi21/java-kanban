import Managers.FileBackedTaskManager;
import Managers.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Task.Task;
import Task.Epic;
import Task.Subtask;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveAndLoadDataTest {
    private FileBackedTaskManager fileManager;
    private File getTempFile() throws IOException {
        File tempFile = File.createTempFile("test_tasks", ".csv");
        tempFile.deleteOnExit();
        return tempFile;
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        fileManager = new FileBackedTaskManager(Manager.getDefaultHistory(), getTempFile());
    }


    @Test
    void testEmptyFileSaveAndLoad(){
        assertTrue(fileManager.getAllTasks().isEmpty());
        assertTrue(fileManager.getAllEpics().isEmpty());
        assertTrue(fileManager.getAllSubTasks().isEmpty());
        assertTrue(fileManager.getHistory().isEmpty());
    }

    @Test
    void testSaveMultipleTasks() throws IOException {
        Task task1 = new Task("Task 1", "Desc 1");
        Task task2 = new Task("Task 2", "Desc 2");
        fileManager.addTask(task1);
        fileManager.addTask(task2);

        List<Task> tasks = fileManager.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals(task1.getName(), tasks.get(0).getName());
        assertEquals(task2.getName(), tasks.get(1).getName());
    }

    @Test
    void testSaveAndLoadEpicAndSubtask(){
        Epic epic = new Epic("Epic", "Epic Desc");
        fileManager.addEpic(epic);
        Subtask sub = new Subtask("Sub", "Sub Desc", epic.getId());
        fileManager.addSubtask(sub);

        List<Epic> epics = fileManager.getAllEpics();
        List<Subtask> subtasks = fileManager.getAllSubTasks();

        assertEquals(1, epics.size());
        assertEquals(1, subtasks.size());
        assertEquals(epic.getName(), epics.get(0).getName());
        assertEquals(sub.getName(), subtasks.get(0).getName());
    }
}
