package kt1;

/**
 * Прогон контрольных примеров из задания.
 */
public class Main {

    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        System.out.print("Список после addFirst(1), addFirst(2), addFirst(3):");
        for (int value : list)
            System.out.print(" " + value);
        System.out.println(", size = " + list.size());

        MyStack<String> stack = new MyStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println("Стек: peek = " + stack.peek() + ", pop = " + stack.pop()
                + ", pop = " + stack.pop() + ", size = " + stack.size());

        System.out.println();
        System.out.println("Проверка скобок:");
        String[] bracketExamples = {"(a + b) * [c - d]", "{[()()]}", "([)]", "((a + b)", ""};
        for (String expression : bracketExamples)
            System.out.println("  \"" + expression + "\" -> " + BracketChecker.isBalanced(expression));

        System.out.println();
        System.out.println("Вычисление выражений:");
        String[] calcExamples = {"3 + 4 * 2", "(3 + 4) * 2", "10 / 2 - 3", "2 * (3 + (4 - 1))", "5 / 0"};
        for (String expression : calcExamples) {
            try {
                System.out.println("  " + expression + " = " + Calculator.evaluate(expression));
            } catch (ArithmeticException e) {
                System.out.println("  " + expression + " -> ArithmeticException: " + e.getMessage());
            }
        }
    }
}
