package kt1;

/**
 * Проверка сбалансированности скобок (), [], {}.
 */
public final class BracketChecker {

    private BracketChecker() {
    }

    /**
     * Открывающие скобки кладём в стек. Закрывающая скобка должна совпасть
     * по виду с последней открытой, то есть с вершиной стека. В конце стек
     * должен остаться пустым: все открытые скобки закрыты.
     * Остальные символы пропускаются. Сложность O(n) по времени и памяти.
     */
    public static boolean isBalanced(String expression) {
        MyStack<Character> stack = new MyStack<>();

        for (char c : expression.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty())
                    return false;

                // Распаковываем в char, чтобы сравнивать значения, а не ссылки на Character.
                char open = stack.pop();
                if (open != openingFor(c))
                    return false;
            }
        }

        return stack.isEmpty();
    }

    private static char openingFor(char closing) {
        return switch (closing) {
            case ')' -> '(';
            case ']' -> '[';
            default -> '{';
        };
    }
}
