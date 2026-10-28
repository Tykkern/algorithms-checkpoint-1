package kt1;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void newStackIsEmpty() {
        MyStack<Integer> stack = new MyStack<>();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void popReturnsElementsInReverseOrder() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemoveTop() {
        MyStack<String> stack = new MyStack<>();
        stack.push("a");
        stack.push("b");

        assertEquals("b", stack.peek());
        assertEquals("b", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void singleElement() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(42);

        assertFalse(stack.isEmpty());
        assertEquals(42, stack.peek());
        assertEquals(42, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popAndPeekOnEmptyStackThrow() {
        MyStack<Integer> stack = new MyStack<>();

        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void repeatedPopFromEmptiedStackThrows() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.pop();

        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::pop);
        assertEquals(0, stack.size());
    }

    @Test
    void stackCanBeReusedAfterEmptying() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(1);
        stack.pop();
        stack.push(2);

        assertEquals(2, stack.peek());
        assertEquals(1, stack.size());
    }
}
