import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private static Task task;
    private static Task task1;
    @BeforeEach
    public void beforeEach() {
        task = new Task("Задача 1", "Описание 1");
        task1 = new Task("Задача 2", "Описание 2");
    }

    @Test
    void testEqualsTaskId(){
        task.setId(1);
        task1.setId(1);

        assertEquals(task, task1);
    }

    @Test
    void testNotEqualsTaskId(){
        task.setId(1);
        task1.setId(2);

        assertNotEquals(task, task1);
    }

}