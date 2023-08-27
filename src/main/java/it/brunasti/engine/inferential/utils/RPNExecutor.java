package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.Fact;
import it.brunasti.engine.inferential.RuleEngine;
import it.brunasti.engine.inferential.exceptions.UnknownElementException;

import java.util.*;

/**
 * Executor for a stack in RPN (Reverse Polish Notation)
 * https://en.wikipedia.org/wiki/Reverse_Polish_notation
 *
 */
public class RPNExecutor {

    static double popupValue(Stack<String> stack, RuleEngine ruleEngine) throws UnknownElementException {
        System.out.println("        popupValue ");

        String element = stack.pop();

        try {
            System.out.println("        -- "+element);
            return Double.parseDouble(element);
//            double res = Double.parseDouble(element);
//            if (res > 1) {
//                res = 1;
//            } else if (res < 0) {
//                res = 0;
//            }
//            return res;
        } catch (NumberFormatException nfe) {
            Fact f = ruleEngine.getFact(element);
            if (f != null) {
                System.out.println("        ---- "+f.getValue());
                ruleEngine.trace("  -> pop : "+element);
                ruleEngine.trace("  ---> : "+f.getValue());
                return f.getValue();
            } else {
                throw new UnknownElementException("Unknown element ["+element+"]");
            }
        }

    }


    // TODO : Make a ArrayList<String> version
    // TODO : Add debug option
    static public double executeRPNStack(String[] tokens, RuleEngine ruleEngine) throws UnknownElementException
    {

        // Initialize the stack and the variable
        Stack<String> stack = new Stack<>();
        double x, y;
        String result;
        String choice;
        double value;
        String p = "";

        // Iterating to the each character
        // in the array of the string
        System.out.println("---------");
        for (int i = 0; i < tokens.length; i++) {

            System.out.println("[" +tokens[i]+ "]");

            // If the character is not the special character
            // ('+', '-' ,'*' , '/')
            // then push the character to the stack
            // TODO : Warning: String values are compared using '!=', not 'equals()'
            if (
                !Operator.ADDITION.symbol.equals(tokens[i]) &&
                !Operator.SUBTRACTION.symbol.equals(tokens[i]) &&
                !Operator.MULTIPLICATION.symbol.equals(tokens[i]) &&
                !Operator.DIVISION.symbol.equals(tokens[i]) &&
                !Operator.MODULUS.symbol.equals(tokens[i]) &&
                !Operator.POWER.symbol.equals(tokens[i]) &&
                !Operator.NOT.symbol.equals(tokens[i]) &&
                !Operator.AND.symbol.equals(tokens[i]) &&
                !Operator.OR.symbol.equals(tokens[i]) &&
                !Operator.MAX.symbol.equals(tokens[i]) &&
                !Operator.MIN.symbol.equals(tokens[i]) &&
                !Operator.AVG.symbol.equals(tokens[i])
            ) {
                stack.push(tokens[i]);
                System.out.println("        stack push[" +tokens[i]+ "]");
                continue;
            }
            else {
                // else if the character is the special
                // character then use the switch method to
                // perform the action
                choice = tokens[i];
                System.out.println("        choice [" +tokens[i]+ "]");
            }

            // Switch-Case
            Operator operator = InfixToRPNConverter.OPS.get(choice);
            switch (operator) {
                case ADDITION:
                    System.out.println(" - exec +");

                    // Performing the "+" operation by popping
                    // put the first two character
                    // and then again store back to the stack
                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = x + y;
                    result = p + value;
                    stack.push(result);
                    break;

                case SUBTRACTION:
                    System.out.println(" - exec -");

                    // Performing the "-" operation by popping
                    // put the first two character
                    // and then again store back to the stack
                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = y - x;
                    result = p + value;
                    stack.push(result);
                    break;

                case MULTIPLICATION:
                    System.out.println(" - exec *");

                    // Performing the "*" operation
                    // by popping put the first two character
                    // and then again store back to the stack

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = x * y;
                    result = p + value;
                    stack.push(result);
                    break;

                case DIVISION:
                    System.out.println(" - exec /");

                    // Performing the "/" operation by popping
                    // put the first two character
                    // and then again store back to the stack

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = y / x;
                    result = p + value;
                    stack.push(result);
                    break;

                case NOT:
                    System.out.println(" - exec ! - NOT");

                    x = popupValue(stack,ruleEngine);
                    value = 1 - x;
                    result = p + value;
                    stack.push(result);
                    break;

                case AND:
                    System.out.println(" - exec & - AND");

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = x + y;
                    if (value > 1) {
                        value = 1;
                    }
                    result = p + value;
                    stack.push(result);
                    break;

                case OR:
                    System.out.println(" - exec | - OR");

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = 1 - (x + y);
                    if (value < 0) {
                        value = 0;
                    }
                    result = p + value;
                    stack.push(result);
                    break;

                case MAX:
                    System.out.println(" - exec MAX");

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = Math.max(x,y);
                    result = p + value;
                    stack.push(result);
                    break;

                case MIN:
                    System.out.println(" - exec MIN");

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value = Math.min(x,y);
                    result = p + value;
                    stack.push(result);
                    break;

                case AVG:
                    System.out.println(" - exec AVG");

                    x = popupValue(stack,ruleEngine);
                    y = popupValue(stack,ruleEngine);
                    value =  (x + y) / 2;
                    result = p + value;
                    stack.push(result);
                    break;

                default:
                    System.out.println("- continue");
                    continue;
            }
            System.out.println("        continue ----");
        }

        // Method to convert the String to integer
        System.out.println("---------");
        return popupValue(stack,ruleEngine);
    }

}

