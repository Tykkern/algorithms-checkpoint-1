package kt1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final double EPS = 1e-9;

    // Контрольные примеры из задания
    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "'3 + 4 * 2',         11",
            "'(3 + 4) * 2',       14",
            "'10 / 2 - 3',        2",
            "'2 * (3 + (4 - 1))', 12",
    })
    void controlExamples(String expression, double expected) {
        assertEquals(expected, Calculator.evaluate(expression), EPS);
    }

    @Test
    void divisionByZeroThrowsArithmeticException() {
        ArithmeticException e = assertThrows(ArithmeticException.class, () -> Calculator.evaluate("5 / 0"));
        assertEquals("Деление на ноль.", e.getMessage());
    }

    @Test
    void divisionByZeroInsideParenthesesThrows() {
        assertThrows(ArithmeticException.class, () -> Calculator.evaluate("1 / (2 - 2)"));
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "'42',                42",
            "'  7  ',             7",
            "'1.5 * 2',           3",
            "'7 / 2',             3.5",
            "'.5 + 0.25',         0.75",
            "'0.1 + 0.2',         0.3",
            "'10 - 4 - 3',        3",
            "'100 / 10 / 5',      2",
            "'2 + 3 * 4 - 6 / 2', 11",
            "'((2))',             2",
            "'(1 + 2) * (3 + 4)', 21",
            "'2*(3+4)',           14",
    })
    void additionalCases(String expression, double expected) {
        assertEquals(expected, Calculator.evaluate(expression), EPS);
    }

    @ParameterizedTest(name = "{0} = {1}")
    @CsvSource({
            "'-3 + 5',        2",
            "'2 * -3',        -6",
            "'-2 * 3',        -6",
            "'-(2 + 3) * 2',  -10",
            "'- -4',          4",
            "'10 - -2',       12",
    })
    void unaryMinus(String expression, double expected) {
        assertEquals(expected, Calculator.evaluate(expression), EPS);
    }

    @ParameterizedTest(name = "\"{0}\"")
    @ValueSource(strings = {
            "",
            "   ",
            "2 +",
            "* 2",
            "2 3",
            "()",
            "(2 + 3",
            "2 + 3)",
            "2 (3)",
            "2 + * 3",
            "1.2.3",
            ".",
            "2 $ 3",
            "[2 + 3]",
    })
    void invalidExpressionsThrowIllegalArgument(String expression) {
        assertThrows(IllegalArgumentException.class, () -> Calculator.evaluate(expression));
    }
}
