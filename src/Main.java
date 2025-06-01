import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import managers.Manager;
import managers.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Manager.getFileBackedManager();

        //Загружаем содержимое файла
        System.out.println();
        System.out.println("Содержимое списка истории из файла сохранения:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        // Создаем обычные задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        System.out.println();
        System.out.println("Список задач:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(taskManager.getTaskById(task.getId()).getName() + ": " + taskManager.getTaskById(task.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после создания обычных задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Обновляем обычную задача
        Task task3 = new Task("Новая Задача 2", "Новое описание задачи 2", Status.IN_PROGRESS);
        taskManager.updateTask(task2, task3);
        System.out.println("\nОбновленный список задач:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(taskManager.getTaskById(task.getId()).getName() + ": " + taskManager.getTaskById(task.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после обновления обычных задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Создаем эпики
        Epic epic1 = new Epic("Эпик 1", "Пустой 1");
        Epic epic2 = new Epic("Эпик 2", "Пустой 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        System.out.println("\nСписок эпиков:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(taskManager.getEpicById(epic.getId()).getName()  + ": " + taskManager.getEpicById(epic.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после создания эпиков:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Обновляем эпик
        Epic epic3 = new Epic("Эпик 1", "С подзадачами 1", Status.IN_PROGRESS);
        Epic epic4 = new Epic("Эпик 2", "С подзадачами 2", Status.IN_PROGRESS);
        taskManager.updateEpic(epic1, epic3);
        taskManager.updateEpic(epic2, epic4);
        System.out.println("\nОбновленный список эпиков:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(taskManager.getEpicById(epic.getId()).getName()  + ": " + taskManager.getEpicById(epic.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после обновления эпиков:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Создаем подзадачи
        Subtask subtask1 = new Subtask("Подзадача 1.1", "Описание 1.1", epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 1.2", "Описание 1.2", epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 2.1", "Описание 2.1", epic2.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        System.out.println("\nСписок подзадач:");
        for (Subtask subtask : taskManager.getAllSubTasks()) {
            System.out.println(taskManager.getSubtaskById(subtask.getId()).getName() + ": " + taskManager.getSubtaskById(subtask.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после создания подзадач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Вывод статусов эпиков
        System.out.println();
        System.out.println("Статус эпиков:");
        System.out.println("Эпик 1 статус: " + taskManager.getEpicById(epic1.getId()).getStatus());
        System.out.println("Эпик 2 статус: " + taskManager.getEpicById(epic2.getId()).getStatus());

        System.out.println();
        System.out.println("История вызовов после вывода статусов эпиков:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Обновление подзадач
        Subtask subtask1_2 = new Subtask("Новая Подзадача 1.1", "Новое Описание 1.1", Status.DONE, epic1.getId());
        Subtask subtask2_2 = new Subtask("Новая Подзадача 1.2", "Новое Описание 1.2", Status.IN_PROGRESS, epic1.getId());
        Subtask subtask3_2 = new Subtask("Новая Подзадача 2.1", "Новое Описание 2.1", Status.DONE, epic2.getId());
        taskManager.updateSubTask(subtask1, subtask1_2);
        taskManager.updateSubTask(subtask2, subtask2_2);
        taskManager.updateSubTask(subtask3, subtask3_2);
        System.out.println("\nОбновленный список подзадач:");
        for (Subtask subtask : taskManager.getAllSubTasks()) {
            System.out.println(taskManager.getSubtaskById(subtask.getId()).getName() + ": " + taskManager.getSubtaskById(subtask.getId()).getDescription());
        }

        System.out.println();
        System.out.println("История вызовов после обновления подзадач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Вывод обновленных статусов
        System.out.println();
        System.out.println("Обновленный статус эпиков:");
        System.out.println("Эпик 1 статус: " + taskManager.getEpicById(epic1.getId()).getStatus());
        System.out.println("Эпик 2 статус: " + taskManager.getEpicById(epic2.getId()).getStatus());

        System.out.println();
        System.out.println("История вызовов после обновления статусов эпиков:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        //Удаление
        taskManager.deleteTaskById(task2);

        System.out.println();
        System.out.println("\nОбновленный список задач после удаления:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(taskManager.getTaskById(task.getId()).getName() + ": " + taskManager.getTaskById(task.getId()).getDescription());
        }
        System.out.println();
        System.out.println("История вызовов после удаления задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        taskManager.deleteEpicById(epic1);

        System.out.println("\nОбновленный список эпиков после удаления:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(taskManager.getEpicById(epic.getId()).getName()  + ": " + taskManager.getEpicById(epic.getId()).getDescription());
        }
        System.out.println();
        System.out.println("История вызовов после удаления эпиков:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }

        System.out.println("\nОбновленный список подзадач после удаления:");
        for (Subtask subtask : taskManager.getAllSubTasks()) {
            System.out.println(taskManager.getSubtaskById(subtask.getId()).getName() + ": " + taskManager.getSubtaskById(subtask.getId()).getDescription());
        }
        System.out.println();
        System.out.println("История вызовов после удаления подзадач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task.toString());
        }
    }
}
