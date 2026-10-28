package kt1;

import java.util.NoSuchElementException;

/**
 * Стек поверх односвязного списка.
 * Вершина стека - голова списка: addFirst и removeFirst меняют только ссылку
 * на голову, поэтому push и pop работают за O(1). Если бы вершиной был конец
 * списка, pop пришлось бы делать за O(n): в односвязном списке нет ссылки
 * на предпоследний узел.
 */
public class MyStack<T> {
    private final SinglyLinkedList<T> items = new SinglyLinkedList<>();

    public void push(T value) {
        items.addFirst(value);
    }

    /**
     * @throws NoSuchElementException если стек пуст
     */
    public T pop() {
        if (isEmpty())
            throw new NoSuchElementException("Стек пуст.");

        return items.removeFirst();
    }

    /**
     * @throws NoSuchElementException если стек пуст
     */
    public T peek() {
        if (isEmpty())
            throw new NoSuchElementException("Стек пуст.");

        return items.getFirst();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}
