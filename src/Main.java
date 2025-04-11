import Task.Epic;
import Task.Status;
import Task.Sub;
import Task.Task;
import TaskManager.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new TaskManager();

        // Создание двух обычных задач
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task1);
        manager.addTask(task2);
        System.out.println("Список задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        Task task3 = new Task("Новая Задача 2", "Новое описание задачи 2", Status.IN_PROGRESS);
        manager.updateTask(task2, task3);
        System.out.println("\nОбновленный список задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", task1.getId());
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", task1.getId());
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        System.out.println("\nСписок эпиков:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        Epic epic3 = new Epic("Новый Эпик 2", "Новое описание эпика 2", Status.IN_PROGRESS, task1.getId());
        manager.updateEpic(epic2, epic3);
        System.out.println("\nОбновленный список эпиков:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        Sub sub1 = new Sub("Подзадача 1.1", "Описание 1.1", epic1.getId());
        Sub sub2 = new Sub("Подзадача 1.2", "Описание 1.2", epic1.getId());
        Sub sub3 = new Sub("Подзадача 2.1", "Описание 2.1", epic2.getId());
        manager.addSubTask(sub1);
        manager.addSubTask(sub2);
        manager.addSubTask(sub3);
        System.out.println("\nСписок подзадач:");
        for (Sub sub : manager.getAllSubTasks()) {
            System.out.println(sub.toString());
        }
        System.out.println();
        System.out.println("Эпик 1 статус: " + manager.getEpicById(epic1.getId()).getStatus());
        System.out.println("Эпик 2 статус: " + manager.getEpicById(epic2.getId()).getStatus());

        Sub sub1_2 = new Sub("Новая Подзадача 1.1", "Новое Описание 1.1", Status.DONE, epic1.getId());
        Sub sub2_2 = new Sub("Новая Подзадача 1.2", "Новое Описание 1.2", Status.IN_PROGRESS, epic1.getId());
        Sub sub3_2 = new Sub("Новая Подзадача 2.1", "Новое Описание 2.1", Status.DONE, epic2.getId());
        manager.updateSubTask(sub1, sub1_2);
        manager.updateSubTask(sub2, sub2_2);
        manager.updateSubTask(sub3, sub3_2);
        System.out.println("\nОбновленный список подзадач:");
        for (Sub sub : manager.getAllSubTasks()) {
            System.out.println(sub.toString());
        }
        System.out.println();
        System.out.println("Эпик 1 статус: " + manager.getEpicById(epic1.getId()).getStatus());
        System.out.println("Эпик 2 статус: " + manager.getEpicById(epic2.getId()).getStatus());


        manager.deleteTaskById(task2);
        manager.deleteEpicByID(epic2);
        manager.deleteSubTaskBy(sub3);

        // Финальный список
        System.out.println("\nПосле удаления:");
        System.out.println("Список задач:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task.toString());
        }

        System.out.println("\nСписок эпиков:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic.toString());
        }

        System.out.println("\nСписок подзадач:");
        for (Sub sub : manager.getAllSubTasks()) {
            System.out.println(sub);
        }



    }
}
