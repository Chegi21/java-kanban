import Managers.Manager;
import Task.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private static Epic epic;
    private static Epic epic1;
    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Эпик 1", "Описание 1");
        epic1 = new Epic("Эпик 2", "Описание 2");
    }

    @Test
    void testEqualsEpicId(){
        epic.setId(1);
        epic1.setId(1);

        assertEquals(epic, epic1);
    }

    @Test
    void testNotEqualsEpicId(){
        epic.setId(1);
        epic1.setId(2);

        assertNotEquals(epic, epic1);
    }

}
