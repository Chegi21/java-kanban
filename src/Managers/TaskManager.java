package Managers;

import Task.Task;
import Task.Epic;
import Task.Subtask;

import java.util.List;

public interface TaskManager {

    int generateId();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(Subtask subtask);

    void deleteAllTasks();

    void deleteTaskById(Task task);

    void deleteEpicById(Epic epic);

    void deleteSubTaskById(Subtask subtask);

    void deleteAllSubTasksOfEpic(Epic epic);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubTasks();

    List<Subtask> getSubTasksOfEpic(int epicID);

    List<Task> getHistory();

    void updateTask(Task oldTask, Task newTask);

    void updateEpic(Epic oldEpic, Epic newEpic);

    void updateSubTask(Subtask oldSubtaskTask, Subtask newSubtaskTask);

    void updateStatus(Epic epic);

    @Override
    String toString();
}
