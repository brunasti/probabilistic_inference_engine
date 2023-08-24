package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.*;
import it.brunasti.engine.inferential.exceptions.RuleParsingException;

public class RuleUtils implements Constants {

    static public Rule buildRule(String definition) throws RuleParsingException {
        if (definition == null) {
            throw new RuleParsingException("Rule not defined");
        }

        String name = null;
        String description = "";
        Fact fact;
        Formula formula;

        int headerDelim = definition.indexOf(RULE_MID_SEPARATOR);
        if (headerDelim >= 0) {
            String header = definition.substring(0,headerDelim).trim();
            int nameDelim = header.indexOf(RULE_FIRST_SEPARATOR);
            if (nameDelim <= 0) {
                name = header;
                description = header;
            } else {
                name = header.substring(0,nameDelim).trim();
                description = header.substring(nameDelim+1).trim();
            }
        }

        if (name == null) {
            name = Utils.createName("RULE");
        }

        String sFormula = definition.substring(headerDelim+1).trim();
        int delimiter = sFormula.indexOf(RULE_FORMULA_INITIATOR);
        if (delimiter <= 0) {
            throw new RuleParsingException("Rule with out '"+RULE_FORMULA_INITIATOR+"' sign ["+definition+"]");
        }

        String sFact = sFormula.substring(0,delimiter).trim();
        String body = sFormula.substring(delimiter+1).trim();
        String nameFormula = Utils.createName("FORMULA");

        fact = Fact.builder().name(sFact).description("Fact "+sFact+" defined in rule "+name).value(-1.0).build();
        formula = Formula.builder().name(nameFormula).body(body).description("Formula ["+sFormula+"] defined in rule "+name).build();

        return Rule.builder().name(name).description(description).fact(fact).formula(formula).build();
    }

}
