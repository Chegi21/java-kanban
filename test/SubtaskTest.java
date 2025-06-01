
import task.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SubtaskTest {
    private static Subtask subtask;
    private static Subtask subtask1;
    @BeforeEach
    public void beforeEach() {
        subtask = new Subtask("Подзадача 1", "Описание 1", 1);
        subtask1 = new Subtask("Подзадача 2", "Описание 2", 1);
    }
    @Test
    void testEqualSubtaskId(){
        subtask.setId(1);
        subtask1.setId(1);

        assertEquals(subtask, subtask1);
    }

    @Test
    void testNotEqualSubtaskId(){
        subtask.setId(1);
        subtask1.setId(2);

        assertNotEquals(subtask, subtask1);
    }
}
