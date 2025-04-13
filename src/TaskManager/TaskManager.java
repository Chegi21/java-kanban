package TaskManager;


import Task.Epic;
import Task.Task;
import Task.Status;
import Task.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicsMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasksMap = new HashMap<>();
    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    public void addTask(Task task) {
        if (task != null) {
            task.setId(generateId());
            tasksMap.put(task.getId(), task);
        }
    }

    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(generateId());
            epicsMap.put(epic.getId(), epic);
        }
    }

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

    public void deleteAllTasks() {
        tasksMap.clear();
        epicsMap.clear();
        subTasksMap.clear();
    }

    public void deleteTaskById(Task task) {
        if (task != null) {
            tasksMap.remove(task.getId());
        }
    }

    public void deleteEpicByID(Epic epic) {
        if (epic != null) {
            deleteAllSubTasksOfEpic(epic);
            epicsMap.remove(epic.getId());
        }

    }

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

    public Task getTaskById(int id) {
        return tasksMap.get(id);
    }

    public Epic getEpicById(int id) {
        return epicsMap.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subTasksMap.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasksMap.values());
    }

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

    public void updateTask(Task oldTask, Task newTask) {
        newTask.setId(oldTask.getId());
        tasksMap.put(oldTask.getId(), newTask);
    }


    public void updateEpic(Epic oldEpic, Epic newEpic) {
        if (oldEpic != null && newEpic != null) {
            newEpic.setId(oldEpic.getId());
            newEpic.setSubTaskListOfEpic(oldEpic.getSubTaskListOfEpic());
            epicsMap.put(oldEpic.getId(), newEpic);
            updateStatus(newEpic);
        }
    }

    public void updateSubTask(Subtask oldSubtaskTask, Subtask newSubtaskTask) {
        if (oldSubtaskTask != null && newSubtaskTask != null){
            Epic epic = epicsMap.get(oldSubtaskTask.getEpicId());
            newSubtaskTask.setId(oldSubtaskTask.getId());
            subTasksMap.put(oldSubtaskTask.getId(), newSubtaskTask);
            updateStatus(epic);
        }
    }

    private void updateStatus(Epic epic) {
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

