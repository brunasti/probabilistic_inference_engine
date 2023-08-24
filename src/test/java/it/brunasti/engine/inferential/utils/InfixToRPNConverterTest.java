package it.brunasti.engine.inferential.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static it.brunasti.engine.inferential.utils.InfixToRPNConverter.infixToRPNConverter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfixToRPNConverterTest {

    @Test
    public void test_numbers() {

        List<String> given = Arrays.asList("( 1 + 2 ) * ( 3 / 4 ) ^ ( 5 + 6 )".split(" "));
        List<String> expected = List.of("1", "2", "+", "3", "4", "/", "5", "6", "+", "^", "*");
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

    @Test
    public void test_symbols() {
        List<String> given = Arrays.asList("A ^ 2 + 2 * A * B + B ^ 2".split(" "));
        List<String> expected = List.of("A", "2", "^", "2", "A", "*", "B", "*", "+", "B", "2", "^", "+");
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

    @Test
    public void test_boolean() {
        List<String> given = Arrays.asList("! A".split(" "));
        List<String> expected = List.of("A", "!");
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

    @Test
    public void test_boolean_2() {
        List<String> given = Arrays.asList("! ( A & B )".split(" "));
        List<String> expected = List.of("A", "B", "&", "!");
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

    @Test
    public void test_boolean_3() {
        List<String> given = Arrays.asList("! ( A & B ) | C".split(" "));
        List<String> expected = List.of("A", "B", "&", "!", "C", "|");
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

    @Test
    public void test_broken_formula() {
        List<String> given = Arrays.asList("A ! ( A & ! B ) | + + 2 C".split(" "));
        List<String> expected = List.of("A", "A", "B", "!", "&", "!", "|", "+", "2", "C", "+" );
        List<String> computed = infixToRPNConverter(given);

        assertEquals(expected, computed);
    }

}