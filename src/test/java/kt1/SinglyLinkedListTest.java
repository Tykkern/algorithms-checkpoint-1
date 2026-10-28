package kt1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    private static <T> List<T> toList(SinglyLinkedList<T> list) {
        List<T> result = new ArrayList<>();
        for (T value : list)
            result.add(value);
        return result;
    }

    @Test
    void newListIsEmpty() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertFalse(list.iterator().hasNext());
    }

    @Test
    void addFirstPutsLastAddedElementFirst() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);

        assertEquals(List.of(3, 2, 1), toList(list));
        assertEquals(3, list.size());
    }

    @Test
    void addLastKeepsInsertionOrder() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(List.of(1, 2, 3), toList(list));
        assertEquals(3, list.size());
    }

    @Test
    void addFirstAndAddLastTogether() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(2);
        list.addLast(3);
        list.addFirst(1);
        list.addLast(4);

        assertEquals(List.of(1, 2, 3, 4), toList(list));
    }

    @Test
    void removeFirstReturnsAndRemovesHead() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("a");
        list.addLast("b");

        assertEquals("a", list.removeFirst());
        assertEquals(List.of("b"), toList(list));
        assertEquals(1, list.size());
    }

    @Test
    void removeFirstOnEmptyListThrows() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        assertThrows(NoSuchElementException.class, list::removeFirst);
    }

    @Test
    void removeFirstAfterListBecameEmptyThrows() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.removeFirst();

        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertEquals(0, list.size());
    }

    @Test
    void addLastWorksAfterRemovingSingleElement() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.removeFirst();
        list.addLast(2);
        list.addLast(3);

        assertEquals(List.of(2, 3), toList(list));
        assertEquals(2, list.size());
    }

    @Test
    void getFirstDoesNotRemove() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(7);

        assertEquals(7, list.getFirst());
        assertEquals(1, list.size());
        assertThrows(NoSuchElementException.class, new SinglyLinkedList<Integer>()::getFirst);
    }

    @Test
    void containsFindsExistingValues() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("a");
        list.addLast("b");
        list.addLast(null);

        assertTrue(list.contains("a"));
        assertTrue(list.contains("b"));
        assertTrue(list.contains(null));
        assertFalse(list.contains("c"));
        assertFalse(new SinglyLinkedList<String>().contains("a"));
    }

    @Test
    void containsComparesByEqualsNotByReference() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast(new String("text"));

        assertTrue(list.contains(new String("text")));
    }

    @Test
    void iteratorThrowsAfterLastElement() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
