package TaskManager;


import Task.Epic;
import Task.Task;
import Task.Status;
import Task.Sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicsMap = new HashMap<>();
    private final HashMap<Integer, Sub> subTasksMap = new HashMap<>();
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

    public void addSubTask(Sub sub) {
        if (sub != null) {
            sub.setId(generateId());
            subTasksMap.put(sub.getId(), sub);

            Epic epic = epicsMap.get(sub.getEpicId());
            if (epic != null) {
                epic.saveSubTaskOfEpic(sub);
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

    public void deleteSubTaskBy(Sub sub) {
        if (sub != null) {
            Epic epic = epicsMap.get(sub.getEpicId());
            if (epic != null) {
                epic.removeSubTaskOfEpic(sub);
                updateStatus(epic);
            }
            subTasksMap.remove(sub.getId());
        }
    }

    public void deleteAllSubTasksOfEpic(Epic epic) {
        if (epic != null) {
            List<Sub> listSub = new ArrayList<>(epic.getSubTaskListOfEpic());
            if (!listSub.isEmpty()) {
                for (Sub sub : listSub) {
                    deleteSubTaskBy(sub);
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

    public Sub getSubtaskById(int id) {
        return subTasksMap.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    public List<Sub> getAllSubTasks() {
        return new ArrayList<>(subTasksMap.values());
    }

    public List<Sub> getSubTasksOfEpic(int epicID) {
        Epic epic = epicsMap.get(epicID);

        List<Sub> listSubTask = new ArrayList<>();
        if (epic != null) {
            for (Sub sub : epic.getSubTaskListOfEpic()) {
                listSubTask.add(subTasksMap.get(sub.getId()));
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

    public void updateSubTask(Sub oldSubTask, Sub newSubTask) {
        if (oldSubTask != null && newSubTask != null){
            Epic epic = epicsMap.get(oldSubTask.getEpicId());
            newSubTask.setId(oldSubTask.getId());
            subTasksMap.put(oldSubTask.getId(), newSubTask);
            updateStatus(epic);
        }
    }

    private void updateStatus(Epic epic) {
        if (epic != null) {
            List<Sub> listSubTaskOfEpic = new ArrayList<>(epic.getSubTaskListOfEpic());

            if (listSubTaskOfEpic.isEmpty()) {
                epic.setStatus(Status.NEW);
                return;
            }

            boolean allNew = true;
            boolean allDone = true;

            for (Sub subTaskID : listSubTaskOfEpic) {
                Sub subtask = subTasksMap.get(subTaskID.getId());
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

