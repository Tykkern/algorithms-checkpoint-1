package kt1;

/**
 * Вычисление арифметических выражений с числами (целыми и десятичными),
 * операциями + - * /, унарным минусом и круглыми скобками.
 *
 * Работает в два этапа:
 * 1. tokenize - разбивает строку на токены и проверяет, что выражение
 *    записано корректно (порядок токенов, скобки, допустимые символы);
 * 2. evaluate - считает значение на двух стеках, числа и операторы
 *    (алгоритм Дейкстры, он же "сортировочная станция", только вместо
 *    вывода в обратную польскую запись операция выполняется сразу).
 */
public final class Calculator {
    // Унарный минус хранится отдельным оператором, чтобы не путать его с вычитанием.
    private static final char UNARY_MINUS = '~';

    private Calculator() {
    }

    /**
     * Сложность O(n): каждый токен один раз попадает в стек и один раз из него снимается.
     *
     * @throws ArithmeticException      при делении на ноль
     * @throws IllegalArgumentException если выражение записано некорректно
     */
    public static double evaluate(String expression) {
        MyStack<Double> values = new MyStack<>();
        MyStack<Character> operators = new MyStack<>();

        for (String token : tokenize(expression)) {
            char c = token.charAt(0);
            if (isNumberChar(c)) {
                values.push(Double.parseDouble(token));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                // Досчитываем всё, что накопилось внутри скобок, и убираем саму "(".
                while (operators.peek() != '(')
                    applyTop(operators, values);
                operators.pop();
            } else {
                // Перед тем как положить оператор, выполняем те, что должны идти раньше него.
                while (!operators.isEmpty() && mustApplyBefore(operators.peek(), c))
                    applyTop(operators, values);
                operators.push(c);
            }
        }

        while (!operators.isEmpty())
            applyTop(operators, values);

        return values.pop();
    }

    /**
     * Разбивает строку на токены: числа, операторы, скобки.
     * Заодно проверяет порядок токенов: в начале, после оператора и после "("
     * ожидается операнд (число, "(" или унарный минус), а после числа и ")" -
     * бинарный оператор или ")". Поэтому вычисление получает уже корректное выражение.
     */
    private static SinglyLinkedList<String> tokenize(String expression) {
        SinglyLinkedList<String> tokens = new SinglyLinkedList<>();
        boolean expectOperand = true;
        int depth = 0;
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (isNumberChar(c)) {
                int start = i;
                while (i < expression.length() && isNumberChar(expression.charAt(i)))
                    i++;
                String number = expression.substring(start, i);
                if (!number.matches("\\d+(\\.\\d*)?|\\.\\d+"))
                    throw error("Некорректное число \"" + number + "\"", start);
                if (!expectOperand)
                    throw error("Пропущен оператор перед числом " + number, start);
                tokens.addLast(number);
                expectOperand = false;
                continue;
            }

            switch (c) {
                case '(' -> {
                    if (!expectOperand)
                        throw error("Пропущен оператор перед \"(\"", i);
                    depth++;
                    tokens.addLast("(");
                }
                case ')' -> {
                    if (expectOperand)
                        throw error("Ожидалось число перед \")\"", i);
                    if (depth == 0)
                        throw error("Лишняя закрывающая скобка", i);
                    depth--;
                    tokens.addLast(")");
                }
                case '+', '-', '*', '/' -> {
                    if (expectOperand) {
                        if (c != '-')
                            throw error("Ожидалось число перед \"" + c + "\"", i);
                        tokens.addLast(String.valueOf(UNARY_MINUS));
                    } else {
                        tokens.addLast(String.valueOf(c));
                        expectOperand = true;
                    }
                }
                default -> throw error("Недопустимый символ \"" + c + "\"", i);
            }
            i++;
        }

        if (tokens.isEmpty())
            throw new IllegalArgumentException("Пустое выражение.");
        if (expectOperand)
            throw new IllegalArgumentException("Выражение обрывается: в конце не хватает числа.");
        if (depth > 0)
            throw new IllegalArgumentException("Не закрыта скобка.");

        return tokens;
    }

    /**
     * Нужно ли выполнить оператор с вершины стека раньше, чем положить новый.
     * Да, если у него выше приоритет. При равном приоритете бинарные операторы
     * выполняются слева направо (10 - 4 - 3 = 3), а унарный минус справа налево (- -5 = 5).
     */
    private static boolean mustApplyBefore(char top, char incoming) {
        if (top == '(')
            return false;

        int topPriority = priority(top);
        int incomingPriority = priority(incoming);
        return topPriority > incomingPriority
                || (topPriority == incomingPriority && incoming != UNARY_MINUS);
    }

    private static int priority(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case UNARY_MINUS -> 3;
            default -> throw new IllegalStateException("Неизвестный оператор: " + operator);
        };
    }

    private static void applyTop(MyStack<Character> operators, MyStack<Double> values) {
        char operator = operators.pop();

        if (operator == UNARY_MINUS) {
            values.push(-values.pop());
            return;
        }

        // Правый операнд лежит на вершине, левый под ним.
        double right = values.pop();
        double left = values.pop();
        values.push(switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> {
                if (right == 0)
                    throw new ArithmeticException("Деление на ноль.");
                yield left / right;
            }
            default -> throw new IllegalStateException("Неизвестный оператор: " + operator);
        });
    }

    private static boolean isNumberChar(char c) {
        return (c >= '0' && c <= '9') || c == '.';
    }

    private static IllegalArgumentException error(String message, int index) {
        return new IllegalArgumentException(message + " (позиция " + (index + 1) + ").");
    }
}
