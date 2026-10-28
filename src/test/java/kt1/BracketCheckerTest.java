package kt1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BracketCheckerTest {

    // Контрольные примеры из задания
    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'(a + b) * [c - d]', true",
            "'{[()()]}',          true",
            "'([)]',              false",
            "'((a + b)',          false",
            "'',                  true",
    })
    void controlExamples(String expression, boolean expected) {
        assertEquals(expected, BracketChecker.isBalanced(expression));
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'a + b',          true",
            "'{[]}(())[{}]',   true",
            "')',              false",
            "'(',              false",
            "')(',             false",
            "'(]',             false",
            "'{[}]',           false",
            "'(a + b))',       false",
    })
    void additionalCases(String expression, boolean expected) {
        assertEquals(expected, BracketChecker.isBalanced(expression));
    }
}
