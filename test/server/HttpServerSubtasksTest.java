package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.InMemoryTaskManager;
import managers.Manager;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpServerSubtasksTest {
    private TaskManager taskManager;
    private HttpTaskServer taskServer;
    private Gson gson = new GsonBuilder().
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
            registerTypeAdapter(Duration.class, new DurationTypeAdapter()).
            create();

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = new InMemoryTaskManager(Manager.getDefaultHistory());
        taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Desc", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(10));
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test Subtask", "Description",
                Status.NEW, LocalDateTime.now(), Duration.ofMinutes(15), epic.getId());
        String json = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).
                POST(HttpRequest.BodyPublishers.ofString(json)).
                header("Content-Type", "application/json").
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Должен вернуться статус 201 CREATED");

        List<Subtask> subtasks = taskManager.getAllSubTasks();
        assertEquals(1, subtasks.size());
        assertEquals("Test Subtask", subtasks.get(0).getName());
    }

    @Test
    public void testGetTasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Desc", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(10));
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, LocalDateTime.now(), Duration.ofMinutes(15), epic.getId());
        taskManager.addSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/subtasks")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Subtask"));
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Desc", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(10));
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, LocalDateTime.now(), Duration.ofMinutes(15), 1);
        taskManager.addSubtask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/subtasks?" + subtask.getId())).
                DELETE().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(taskManager.getAllSubTasks().isEmpty());
    }
}
