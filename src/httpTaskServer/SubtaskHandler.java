package httpTaskServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exception.NotFoundException;
import managers.TaskManager;
import task.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class SubtaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public SubtaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();

        try {
            switch (method) {
                case "GET":
                    if (query == null) {
                        sendText(exchange, gson.toJson(manager.getAllSubTasks()), STATUS_OK);
                    } else {
                        Optional<Integer> id = parseId(query);
                        if (id.isPresent()) {
                            Subtask subtask = manager.getSubtaskById(id.get());
                            if (subtask != null) {
                                sendText(exchange, gson.toJson(subtask), STATUS_OK);
                            } else {
                                sendText(exchange, "Подзадача не найдена", STATUS_TASK_NOT_FOUND);
                            }
                        } else {
                            sendText(exchange, "Ошибка при обработке запроса", STATUS_BAD_REQUEST);
                        }
                    }
                    break;

                case "POST":
                    InputStream body = exchange.getRequestBody();
                    String json = new String(body.readAllBytes(), StandardCharsets.UTF_8);
                    Subtask newSubtask = gson.fromJson(json, Subtask.class);

                    if (newSubtask.getId() == 0) {
                        if (manager.isOverlapTask(newSubtask)) {
                            sendText(exchange, "Подзадача пересекается с существующей", STATUS_CONFLICT);
                        } else {
                            manager.addSubtask(newSubtask);
                            sendText(exchange, "Подзадача создана", STATUS_CREATED);
                        }
                    } else {
                        Subtask oldSubtask = manager.getSubtaskById(newSubtask.getId());
                        manager.updateSubTask(oldSubtask, newSubtask);
                        sendText(exchange, "Подзадача обновлёна", STATUS_CREATED);
                    }
                    break;
                case "DELETE":
                    if (query == null) {
                        manager.deleteAllEpics();
                        sendText(exchange, "Все подзадачи удалены", STATUS_OK);
                    } else {
                        Optional<Integer> id = parseId(query);
                        if (id.isPresent()) {
                            Subtask subtask = manager.getSubtaskById(id.get());
                            manager.deleteSubtaskById(subtask);
                            sendText(exchange, "Подзадача удалёна", STATUS_OK);
                        } else {
                            sendText(exchange, "Ошибка при обработке запроса", STATUS_BAD_REQUEST);
                        }
                    }
                    break;
                default:
                    sendText(exchange, "Метод не поддерживается", STATUS_METHOD_NOT_FOUND);
            }
        } catch (NotFoundException e) {
            sendText(exchange, e.getMessage(), STATUS_TASK_NOT_FOUND);
        } catch (Exception e) {
            sendText(exchange, "Ошибка при обработке запроса: " + e.getMessage(), STATUS_BAD_REQUEST);
        }
    }
}
