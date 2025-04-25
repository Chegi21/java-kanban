package Managers;


import Task.Epic;
import Task.Task;
import Task.Status;
import Task.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicsMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasksMap = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    private int nextId = 1;


    @Override
    public int generateId() {
        return nextId++;
    }

    @Override
    public void addTask(Task task) {
        if (task != null) {
            task.setId(generateId());
            tasksMap.put(task.getId(), task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(generateId());
            epicsMap.put(epic.getId(), epic);
        }
    }

    @Override
    public void addSubTask(Subtask subtask) {
        if (subtask != null) {
            subtask.setId(generateId());
            subTasksMap.put(subtask.getId(), subtask);

            Epic epic = epicsMap.get(subtask.getEpicId());
            if (epic != null) {
                epic.saveSubTaskOfEpic(subtask);
                updateStatus(epic);
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        tasksMap.clear();
        epicsMap.clear();
        subTasksMap.clear();
    }

    @Override
    public void deleteTaskById(Task task) {
        if (task != null) {
            tasksMap.remove(task.getId());
        }
    }

    @Override
    public void deleteEpicByID(Epic epic) {
        if (epic != null) {
            deleteAllSubTasksOfEpic(epic);
            epicsMap.remove(epic.getId());
        }

    }

    @Override
    public void deleteSubTaskBy(Subtask subtask) {
        if (subtask != null) {
            Epic epic = epicsMap.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubTaskOfEpic(subtask);
                updateStatus(epic);
            }
            subTasksMap.remove(subtask.getId());
        }
    }

    @Override
    public void deleteAllSubTasksOfEpic(Epic epic) {
        if (epic != null) {
            List<Subtask> listSub = new ArrayList<>(epic.getSubTaskListOfEpic());
            if (!listSub.isEmpty()) {
                for (Subtask subtask : listSub) {
                    deleteSubTaskBy(subtask);
                }
            }
        }
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasksMap.get(id));
        return tasksMap.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epicsMap.get(id));
        return epicsMap.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subTasksMap.get(id));
        return subTasksMap.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasksMap.values());
    }

    @Override
    public List<Subtask> getSubTasksOfEpic(int epicID) {
        Epic epic = epicsMap.get(epicID);

        List<Subtask> listSubTask = new ArrayList<>();
        if (epic != null) {
            for (Subtask subtask : epic.getSubTaskListOfEpic()) {
                listSubTask.add(subTasksMap.get(subtask.getId()));
            }
        }

        return listSubTask;
    }

    @Override
    public void updateTask(Task oldTask, Task newTask) {
        newTask.setId(oldTask.getId());
        tasksMap.put(oldTask.getId(), newTask);
    }

    @Override
    public void updateEpic(Epic oldEpic, Epic newEpic) {
        if (oldEpic != null && newEpic != null) {
            newEpic.setId(oldEpic.getId());
            newEpic.setSubTaskListOfEpic(oldEpic.getSubTaskListOfEpic());
            epicsMap.put(oldEpic.getId(), newEpic);
            updateStatus(newEpic);
        }
    }

    @Override
    public void updateSubTask(Subtask oldSubtaskTask, Subtask newSubtaskTask) {
        if (oldSubtaskTask != null && newSubtaskTask != null){
            Epic epic = epicsMap.get(oldSubtaskTask.getEpicId());
            newSubtaskTask.setId(oldSubtaskTask.getId());
            subTasksMap.put(oldSubtaskTask.getId(), newSubtaskTask);
            updateStatus(epic);
        }
    }

    @Override
    public void updateStatus(Epic epic) {
        if (epic != null) {
            List<Subtask> listSubTaskOfEpic = new ArrayList<>(epic.getSubTaskListOfEpic());

            if (listSubTaskOfEpic.isEmpty()) {
                epic.setStatus(Status.NEW);
                return;
            }

            boolean allNew = true;
            boolean allDone = true;

            for (Subtask subtaskTaskID : listSubTaskOfEpic) {
                Subtask subtask = subTasksMap.get(subtaskTaskID.getId());
                if (subtask != null) {
                    if (subtask.getStatus() != Status.NEW) {
                        allNew = false;
                    }
                    if (subtask.getStatus() != Status.DONE) {
                        allDone = false;
                    }
                }
            }

            if (allDone) {
                epic.setStatus(Status.DONE);
            } else if (allNew) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }

    }

    @Override
    public String toString() {
        return "TaskManager.TaskManager{" +
                "tasks=" + tasksMap +
                ", subtasks=" + subTasksMap +
                ", epics=" + epicsMap +
                '}';
    }

}

