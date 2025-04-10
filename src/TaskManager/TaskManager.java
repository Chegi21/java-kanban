package TaskManager;


import Task.EpicTask;
import Task.MainTask;
import Task.Status;
import Task.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, MainTask> tasksMap = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicsMap = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasksMap = new HashMap<>();
    private int nextID = 1;

    private int generateID() {
        return nextID++;
    }

    public void addTask(MainTask mainTask) {
        mainTask.setID(generateID());
        tasksMap.put(mainTask.getID(), mainTask);
    }

    public void addEpic(EpicTask epicTask) {
        epicTask.setID(generateID());
        epicsMap.put(epicTask.getID(), epicTask);

        MainTask mainTask = tasksMap.get(epicTask.getTaskID());
        if (mainTask != null) {
            mainTask.saveEpicID(epicTask.getID());
        }
    }

    public void addSubTask(SubTask subTask) {
        subTask.setID(generateID());
        subTasksMap.put(subTask.getID(), subTask);

        EpicTask epicTask = epicsMap.get(subTask.getEpicID());
        if (epicTask != null) {
            epicTask.saveSubTaskID(subTask.getID());
            updateStatus(epicTask);
        }
    }

    public void deleteAllTasks() {
        tasksMap.clear();
        epicsMap.clear();
        subTasksMap.clear();
    }

    public void deleteTaskById(Integer taskID) {
        deleteAllEpicsOfTask(taskID);
        tasksMap.remove(taskID);
    }

    public void deleteAllEpicsOfTask(Integer taskID) {
        MainTask mainTask = tasksMap.get(taskID);

        if (mainTask != null) {
            List<Integer> listID = new ArrayList<>(mainTask.getEpicListID());
            if (!listID.isEmpty()) {
                for (Integer epicID : listID) {
                    deleteEpicByID(epicID);
                }
            }

        }
    }

    public void deleteEpicByID(Integer epicID) {
        EpicTask epicTask = epicsMap.get(epicID);

        if (epicTask != null) {
            MainTask mainTask = tasksMap.get(epicTask.getTaskID());
            if (mainTask != null) {
                mainTask.removeFromListEpicID(epicID);
            }
            deleteAllSubTasksOfEpic(epicID);
        }

        epicsMap.remove(epicID);
    }

    public void deleteAllSubTasksOfEpic(Integer epicID) {
        EpicTask epicTask = epicsMap.get(epicID);

        if (epicTask != null) {
            List<Integer> listID = new ArrayList<>(epicTask.getSubTaskListID());
            if (!listID.isEmpty()) {
                for (Integer subTaskID : listID) {
                    deleteSubTaskByID(subTaskID);
                }
            }
        }
    }

    public void deleteSubTaskByID(Integer subTaskID) {
        SubTask subtask = subTasksMap.get(subTaskID);

        if (subtask != null) {
            EpicTask epicTask = epicsMap.get(subtask.getEpicID());
            if (epicTask != null) {
                epicTask.removeFromListSubTaskID(subTaskID);
                updateStatus(epicTask);
            }
        }

        subTasksMap.remove(subTaskID);
    }

    public MainTask getTaskById(int id) {
        return tasksMap.get(id);
    }

    public EpicTask getEpicById(int id) {
        return epicsMap.get(id);
    }

    public SubTask getSubtaskById(int id) {
        return subTasksMap.get(id);
    }

    public List<MainTask> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    public List<EpicTask> getAllEpics() {
        return new ArrayList<>(epicsMap.values());
    }

    public List<SubTask> getAllSubtasks() {
        return new ArrayList<>(subTasksMap.values());
    }

    public List<SubTask> getSubTasksOfEpic(int epicID) {
        EpicTask epicTask = epicsMap.get(epicID);

        List<SubTask> listSubTask = new ArrayList<>();
        if (epicTask != null) {

            for (int subId : epicTask.getSubTaskListID()) {
                listSubTask.add(subTasksMap.get(subId));
            }
        }

        return listSubTask;
    }

    public void updateTask(MainTask oldMainTask, MainTask newTask) {
        newTask.setID(oldMainTask.getID());
        newTask.setEpicListID(oldMainTask.getEpicListID());
        tasksMap.put(oldMainTask.getID(), newTask);
    }


    public void updateEpic(EpicTask oldEpicTask, EpicTask newEpicTask) {
        newEpicTask.setID(oldEpicTask.getID());
        newEpicTask.setSubTaskListID(oldEpicTask.getSubTaskListID());
        epicsMap.put(oldEpicTask.getID(), newEpicTask);
        updateStatus(newEpicTask);
    }

    public void updateSubTask(SubTask oldSubTask, SubTask newSubTask) {
        EpicTask epicTask = epicsMap.get(oldSubTask.getEpicID());

            if (epicTask != null){
                newSubTask.setID(oldSubTask.getID());
                subTasksMap.put(oldSubTask.getID(), newSubTask);
                updateStatus(epicTask);
            }
    }

    private void updateStatus(EpicTask epicTask) {
        List<Integer> listSubTaskID = new ArrayList<>(epicTask.getSubTaskListID());

        if (listSubTaskID.isEmpty()) {
            epicTask.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subTaskID : listSubTaskID) {
            SubTask subtask = subTasksMap.get(subTaskID);
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
            epicTask.setStatus(Status.DONE);
        } else if (allNew) {
            epicTask.setStatus(Status.NEW);
        } else {
            epicTask.setStatus(Status.IN_PROGRESS);
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

