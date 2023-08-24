package it.brunasti.engine.inferential.utils;

public enum Operator implements Comparable<Operator> {

    // TODO Add boolean and statistical operators
    ADDITION("+", Associativity.LEFT, 0),
    SUBTRACTION("-", Associativity.LEFT, 0),
    DIVISION("/", Associativity.LEFT, 5),
    MULTIPLICATION("*", Associativity.LEFT, 5),
    MODULUS("%", Associativity.LEFT, 5),
    POWER("^", Associativity.RIGHT, 10),

    // Boolean Operators
    // TODO Check if precedence is correct
    NOT("!", Associativity.LEFT, 15),
    AND("&", Associativity.RIGHT, 10),
    OR("|", Associativity.RIGHT, 10),

    // "Probability" Operators
    // TODO Should we make all of them only uppercase?
    MAX("MAX", Associativity.RIGHT, 5),
    MIN("min", Associativity.RIGHT, 5),
    AVG("Avg", Associativity.RIGHT, 5)

            ;

    final Associativity associativity;
    final int precedence;
    final String symbol;

    Operator(String symbol, Associativity associativity, int precedence) {
        this.symbol = symbol;
        this.associativity = associativity;
        this.precedence = precedence;
    }

    public int comparePrecedence(Operator operator) {
        return this.precedence - operator.precedence;
    }
}