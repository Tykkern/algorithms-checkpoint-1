package kt1;

/**
 * Узел односвязного списка: значение и ссылка на следующий узел.
 */
class Node<T> {
    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }
}
