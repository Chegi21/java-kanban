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

public class HttpServerEpicsTest {
    private TaskManager manager;
    private HttpTaskServer taskServer;
    private final Gson gson  = new GsonBuilder().
            registerTypeAdapter(Duration.class, new DurationTypeAdapter()).
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).
            create();

    @BeforeEach
    public void setUp() throws IOException {
        manager = new InMemoryTaskManager(Manager.getDefaultHistory());
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test Epic", "Description",
                Status.NEW, LocalDateTime.now(), Duration.ofMinutes(15));
        String json = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).
                POST(HttpRequest.BodyPublishers.ofString(json)).
                header("Content-Type", "application/json").
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Должен вернуться статус 201 CREATED");

        List<Epic> epics = manager.getAllEpics();
        assertEquals(1, epics.size());
        assertEquals("Test Epic", epics.get(0).getName());
    }

    @Test
    public void testGetTasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Desc", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(10));
        manager.addEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/epics")).
                GET().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Epic"));
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Epic epic = new Epic("ToDelete", "Desc", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        manager.addEpic(epic);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/epics?" + epic.getId())).
                DELETE().
                build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(manager.getAllEpics().isEmpty());
    }
}
