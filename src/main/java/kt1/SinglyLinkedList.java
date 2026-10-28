package kt1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Односвязный список.
 * Хранит ссылки на первый (head) и последний (tail) узлы, поэтому добавление
 * в начало и в конец, а также удаление из начала работают за O(1).
 * Поиск (contains) проходит по всем узлам и работает за O(n).
 */
public class SinglyLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null)
            tail = node;
        size++;
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
    }

    /**
     * Удаляет первый элемент и возвращает его значение.
     *
     * @throws NoSuchElementException если список пуст
     */
    public T removeFirst() {
        if (head == null)
            throw new NoSuchElementException("Список пуст.");

        T value = head.value;
        head = head.next;
        // Удалили последний оставшийся узел: tail тоже должен стать пустым,
        // иначе следующий addLast прицепит узел к уже удалённому.
        if (head == null)
            tail = null;
        size--;
        return value;
    }

    /**
     * Возвращает первый элемент, не удаляя его.
     *
     * @throws NoSuchElementException если список пуст
     */
    public T getFirst() {
        if (head == null)
            throw new NoSuchElementException("Список пуст.");

        return head.value;
    }

    public boolean contains(T value) {
        for (Node<T> current = head; current != null; current = current.next) {
            if (Objects.equals(current.value, value))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
