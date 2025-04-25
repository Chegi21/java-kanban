import Task.Epic;
import Task.Subtask;
import Task.Task;
import Task.Status;
import Managers.TaskManager;
import Managers.HistoryManager;
import Managers.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Manager.getDefault();
    }
    @Test
    void testEpicNotSubtaskEpic() {

        Epic epic = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", 1);
        taskManager.addSubTask(subtask);

        List<Epic> epics = taskManager.getAllEpics();
        List<Subtask> subtasks = taskManager.getAllSubTasks();

        assertNotEquals(epics.get(0), subtasks.get(0));
    }

    @Test
    void testAddManagersNotNull() {
        HistoryManager historyManager = Manager.getDefaultHistory();

        assertNotNull(taskManager);
        assertNotNull(historyManager);
    }

    @Test
    void testAddAllTasksAndGetById() {

        Task task = new Task("Задача 1", "Описание 1");
        taskManager.addTask(task);

        Epic epic = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", 1);
        taskManager.addSubTask(subtask);

        Task task1 = taskManager.getTaskById(task.getId());
        Epic epic1 = taskManager.getEpicById(epic.getId());
        Subtask subtask1 = taskManager.getSubtaskById(subtask.getId());

        assertNotNull(task1);
        assertNotNull(epic1);
        assertNotNull(subtask1);
    }

    @Test
    void testGenerationAndManualAddIdTask() {
        Task task = new Task("Задача 1", "Описание 1");
        task.setId(1);
        taskManager.addTask(task);

        Epic epic = new Epic("Эпик 1", "Описание 1");
        epic.setId(1);
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание 1", 1);
        subtask.setId(1);
        taskManager.addSubTask(subtask);

        assertNotEquals(task, epic);
        assertNotEquals(task, subtask);
        assertNotEquals(epic, subtask);

    }

    @Test
    void testNoChangeAddTaskAfterPutInManager() {
        Task originalTask = new Task("Задача 1", "Описание 1", Status.NEW);
        originalTask.setId(1);

        Task copyTask = new Task(originalTask.getName(), originalTask.getDescription(), originalTask.getStatus());
        copyTask.setId(originalTask.getId());

        taskManager.addTask(originalTask);

        Task taskFromManager = taskManager.getTaskById(copyTask.getId());

        assertEquals(copyTask.getId(), taskFromManager.getId());
        assertEquals(copyTask.getName(), taskFromManager.getName());
        assertEquals(copyTask.getDescription(), taskFromManager.getDescription());
        assertEquals(copyTask.getStatus(), taskFromManager.getStatus());
        
    }

    @Test
    void testHistoryPutTaskInManager() {
        Task taskOrigin = new Task("Задача 1", "Описание 1");
        for (int i = 0; i < 10; i++) {
            taskManager.addTask(taskOrigin);
        }

        List<Task> taskListOfManager = taskManager.getAllTasks();
        for (Task task : taskListOfManager) {
            taskManager.getTaskById(task.getId());
        }

        List<Task> taskListOfHistory = taskManager.getHistory();
        for (Task taskOfHistory : taskListOfHistory) {
            assertEquals(taskOrigin.getName(), taskOfHistory.getName());
            assertEquals(taskOrigin.getDescription(), taskOfHistory.getDescription());
        }
    }

}
