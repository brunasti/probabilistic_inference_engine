package it.brunasti.engine.inferential.utils;

import java.util.*;

import static it.brunasti.engine.inferential.utils.Associativity.LEFT;
import static it.brunasti.engine.inferential.utils.Associativity.RIGHT;


/**
Infix notation is the common arithmetic and logical formula notation, in which operators are written infix-style between the operands they act on. Unfortunately what seems natural for us, is not as simple to parse by computers as prefix or RPN notations.

RPN also known as the Reverse Polish Notation is mathematical notation wherein every operator (eg. + - * %) follows all of its operands.

In order to parse and convert a given infix mathematical expression to RPN we will use the shunting-yard algorithm ( https://en.wikipedia.org/wiki/Shunting_yard_algorithm ) .

 */

public class InfixToRPNConverter {

// ***

    public final static Map<String, Operator> OPS = new HashMap<>();

    static {
        // We build a map with all the existing Operators by iterating over the existing Enum
        // and filling up the map with:
        // <K,V> = <Character, Operator(Character, Associativity, Precedence)>
        for (Operator operator : Operator.values()) {
            OPS.put(operator.symbol, operator);
        }
    }

    // TODO : Check if this could be a non static....
    public static List<String> infixToRPNConverter(List<String> tokens) {

        List<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        // For all the input tokens [S1] read the next token [S2]
        for (String token : tokens) {
            if (OPS.containsKey(token)) {
                // Token is an operator [S3]
                while (!stack.isEmpty() && OPS.containsKey(stack.peek())) {
                    // While there is an operator (y) at the top of the operators stack and
                    // either (x) is left-associative and its precedence is less or equal to
                    // that of (y), or (x) is right-associative and its precedence
                    // is less than (y)
                    //
                    // [S4]:
                    Operator cOp = OPS.get(token); // Current operator
                    Operator lOp = OPS.get(stack.peek()); // Top operator from the stack
                    if ((cOp.associativity == LEFT && cOp.comparePrecedence(lOp) <= 0) ||
                            (cOp.associativity == RIGHT && cOp.comparePrecedence(lOp) < 0)) {
                        // Pop (y) from the stack S[5]
                        // Add (y) output buffer S[6]
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack S[7]
                stack.push(token);
            } else if ("(".equals(token)) {
                // Else If token is left parenthesis, then push it on the stack S[8]
                stack.push(token);
            } else if (")".equals(token)) {
                // Else If the token is right parenthesis S[9]
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    // Until the top token (from the stack) is left parenthesis, pop from
                    // the stack to the output buffer
                    // S[10]
                    output.add(stack.pop());
                }
                // Also pop the left parenthesis but don't include it in the output
                // buffer S[11]
                stack.pop();
            } else {
                // Else add token to output buffer S[12]
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            // While there are still operator tokens in the stack, pop them to output S[13]
            output.add(stack.pop());
        }

        return output;
    }


}