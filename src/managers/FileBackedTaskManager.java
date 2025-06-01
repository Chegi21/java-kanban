package managers;

import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskType;
import task.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exception.ManagerSaveException;
import exception.ManagerLoadException;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(HistoryManager historyManager, File file) {
        if (!file.exists()) {
            return new FileBackedTaskManager(historyManager, file);
        }
        FileBackedTaskManager manager = new FileBackedTaskManager(historyManager, file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();
            boolean isHistorySection = false;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    isHistorySection = true;
                    continue;
                }

                if (!isHistorySection) {
                    Task task = fromString(line);
                    if (task != null) {
                        int id = task.getId();

                        switch (task.getTaskType()) {
                            case TASK:
                                manager.tasksMap.put(id, task);
                                break;
                            case EPIC:
                                manager.epicsMap.put(id, (Epic) task);
                                break;
                            case SUBTASK:
                                manager.subTasksMap.put(id, (Subtask) task);
                                Epic epic = manager.epicsMap.get(((Subtask) task).getEpicId());
                                if (epic != null) {
                                    epic.saveSubTaskOfEpic((Subtask) task);
                                }
                                break;
                        }
                    }

                } else {
                    for (Task task : fromHistoryString(line, manager)) {
                        manager.historyManager.add(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при загрузке из файла: " + file.getName());
        }

        return manager;
    }

    public void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic\n");

            List<Task> tackList = super.getAllTasks();
            for (Task task : tackList) {
                bufferedWriter.write(toString(task.getId(), task.getTaskType(), task.getName(), task.getStatus(), task.getDescription(), "") + "\n");
            }

            List<Epic> epicList = super.getAllEpics();
            for (Epic epic : epicList) {
                bufferedWriter.write(toString(epic.getId(), epic.getTaskType(), epic.getName(), epic.getStatus(), epic.getDescription(), "") + "\n");
            }

            List<Subtask> subtaskList = super.getAllSubTasks();
            for (Subtask subtask : subtaskList) {
                bufferedWriter.write(toString(subtask.getId(), subtask.getTaskType(), subtask.getName(), subtask.getStatus(), subtask.getDescription(), String.valueOf(subtask.getEpicId())) + "\n");
            }

            bufferedWriter.write("\n");
            bufferedWriter.write(historyToString(this.historyManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных в файл: " + file.getName());
        }
    }

    private String toString(int id, TaskType taskType, String name, Status status, String description, String epicId) {
        return String.format("%s,%s,%s,%s,%s,%s", id, taskType, name, status, description, epicId);
    }

    private String historyToString(HistoryManager manager) {
        List<String> taskId = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            taskId.add(String.valueOf(task.getId()));
        }
        Collections.reverse(taskId);
        return String.join(",", taskId);
    }

    public static Task fromString(String line) {
        String[] stringTask = line.split(",");
        int id;
        TaskType taskType;
        String name;
        Status status;
        String description;
        String epicId = "";

        if (stringTask.length < 6) {
            id = Integer.parseInt(stringTask[0]);
            taskType = TaskType.valueOf(stringTask[1]);
            name = stringTask[2];
            status = Status.valueOf(stringTask[3]);
            description = stringTask[4];
        } else {
            id = Integer.parseInt(stringTask[0]);
            taskType = TaskType.valueOf(stringTask[1]);
            name = stringTask[2];
            status = Status.valueOf(stringTask[3]);
            description = stringTask[4];
            epicId = stringTask[5];
        }


        switch (taskType) {
            case TASK:
                Task task = new Task(name, description, status, taskType);
                task.setId(id);
                return task;

            case EPIC:
                Epic epic = new Epic(name, description, status, taskType);
                epic.setId(id);
                return epic;

            case SUBTASK:
                Subtask subtask = new Subtask(name, description, status, taskType, Integer.parseInt(epicId));
                subtask.setId(id);
                return subtask;
        }

        return null;
    }

    public static List<Task> fromHistoryString(String line, FileBackedTaskManager manager) {
        List<Task> historyTasks = new ArrayList<>();
        String[] historyIds = line.split(",");

        for (String idStr : historyIds) {
            int id = Integer.parseInt(idStr);

            Task task = manager.tasksMap.get(id);
            if (task == null) {
                task = manager.epicsMap.get(id);
            }
            if (task == null) {
                task = manager.subTasksMap.get(id);
            }

            if (task != null) {
                historyTasks.add(task);
            }
        }

        return historyTasks;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTaskById(Task task) {
        super.deleteTaskById(task);
        save();
    }

    @Override
    public void deleteEpicById(Epic epic) {
        super.deleteEpicById(epic);
        save();
    }

    @Override
    public void deleteAllSubTasksOfEpic(Epic epic) {
        super.deleteAllSubTasksOfEpic(epic);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void deleteSubtaskById(Subtask subtask) {
        super.deleteSubtaskById(subtask);
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return super.getAllSubTasks();
    }

    @Override
    public List<Subtask> getSubTasksOfEpic(int epicID) {
        return super.getSubTasksOfEpic(epicID);
    }

    @Override
    public void updateTask(Task oldTask, Task newTask) {
        super.updateTask(oldTask, newTask);
        save();
    }

    @Override
    public void updateEpic(Epic oldEpic, Epic newEpic) {
        super.updateEpic(oldEpic, newEpic);
        save();
    }

    @Override
    public void updateSubTask(Subtask oldSubtaskTask, Subtask newSubtaskTask) {
        super.updateSubTask(oldSubtaskTask, newSubtaskTask);
        save();
    }

    @Override
    public void updateStatus(Epic epic) {
        super.updateStatus(epic);
        save();
    }
}
