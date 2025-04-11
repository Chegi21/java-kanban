import Task.EpicTask;
import Task.MainTask;
import Task.Status;
import Task.SubTask;
import TaskManager.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new TaskManager();

        // Создание двух обычных задач
        MainTask task1 = new MainTask("Задача 1", "Описание задачи 1", Status.NEW);
        MainTask task2 = new MainTask("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        System.out.println("Список обычных задач:");
        for (MainTask task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        MainTask task3 = new MainTask("Новая Задача 2", "Новое Описание задачи 2", Status.NEW);
        manager.updateTask(task2, task3);

        System.out.println();
        System.out.println("Список новых задач:");
        for (MainTask task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        EpicTask epic1 = new EpicTask("Эпик 1", "Описание эпика 1", Status.NEW, task1.getID());
        EpicTask epic2 = new EpicTask("Эпик 2", "Описание эпика 2", Status.NEW, task1.getID());
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        System.out.println();
        System.out.println("Список эпиков:");
        for (EpicTask epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        EpicTask epic3 = new EpicTask("Новый Эпик 2", "Новое Описание эпика 2", Status.NEW, task1.getID());
        manager.updateEpic(epic2, epic3);

        System.out.println();
        System.out.println("Новый список эпиков:");
        for (EpicTask epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        SubTask sub1 = new SubTask("Подзадача 1.1", "Описание 1.1", Status.NEW, epic1.getID());
        SubTask sub2 = new SubTask("Подзадача 1.2", "Описание 1.2", Status.NEW, epic1.getID());
        SubTask sub3 = new SubTask("Подзадача 2.1", "Описание 2.1", Status.NEW, epic2.getID());
        manager.addSubTask(sub1);
        manager.addSubTask(sub2);
        manager.addSubTask(sub3);

        System.out.println();
        System.out.println("Список подзадач:");
        for (SubTask sub : manager.getAllSubtasks()) {
            System.out.println(sub.toString());
        }

        SubTask sub1_2 = new SubTask("Новая Подзадача 1.1", "Новое Описание 1.1", Status.DONE, epic1.getID());
        SubTask sub2_2 = new SubTask("Новая Подзадача 1.2", "Новое Описание 1.2", Status.IN_PROGRESS, epic1.getID());
        SubTask sub3_2 = new SubTask("Новая Подзадача 2.1", "Новое Описание 2.1", Status.DONE, epic2.getID());
        manager.updateSubTask(sub1, sub1_2);
        manager.updateSubTask(sub2, sub2_2);
        manager.updateSubTask(sub3, sub3_2);

        System.out.println();
        System.out.println("Новый писок подзадач:");
        for (SubTask sub : manager.getAllSubtasks()) {
            System.out.println(sub.toString());
        }

        System.out.println("\nПосле изменения статусов:");
        System.out.println("Эпик 1 статус: " + manager.getEpicById(epic1.getID()).getStatus()); // должен быть IN_PROGRESS
        System.out.println("Эпик 2 статус: " + manager.getEpicById(epic2.getID()).getStatus()); // должен быть DONE

        // Удаление одной задачи, одного эпика и одной подзадачи
        manager.deleteTaskById(task2.getID());
        manager.deleteEpicByID(epic2.getID());
        manager.deleteSubTaskByID(sub3.getID());

        // Финальный список
        System.out.println("\nПосле удаления:");
        System.out.println("Задачи:");
        for (MainTask task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        System.out.println("\nЭпики:");
        for (EpicTask epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        System.out.println("\nПодзадачи:");
        for (SubTask sub : manager.getAllSubtasks()) {
            System.out.println(sub);
        }



    }
}
