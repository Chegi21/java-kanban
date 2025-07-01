package httpTaskServer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler {
    protected static final int STATUS_OK = 200;
    protected static final int STATUS_CREATED = 201;
    protected static final int STATUS_TASK_NOT_FOUND = 404;
    protected static final int STATUS_METHOD_NOT_FOUND = 405;
    protected static final int STATUS_CONFLICT = 406;
    protected static final int STATUS_BAD_REQUEST = 500;

    protected void sendText(HttpExchange exchange, String text, int responseCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, resp.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(resp);
        }
        exchange.close();
    }

    protected static Optional<Integer> parseId(String query) {
        try {
            return Optional.of(Integer.parseInt(query));
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }
}

