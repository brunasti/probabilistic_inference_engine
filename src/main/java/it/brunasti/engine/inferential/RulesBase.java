package it.brunasti.engine.inferential;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ToString
@NoArgsConstructor
@Getter
public class RulesBase implements Serializable {
    Map<String,Rule> rules = new HashMap<>();

    public String dump() {
        String result = "";
        String separator = "  ";
        for (Rule rule : rules.values()) {
            result = result + separator + rule.getName() + " : " + rule.getFact().getName() + " = " + rule.getFormula().getBody();
            separator = "\n  ";
        }

        return result;
    }

    public Rule registerRule(Rule rule) {
        if (!rules.containsKey(rule.getName())) {
            rules.put(rule.getName(), rule);
        } else {
            System.err.println("Warning: rule ["+rule.getName()+"] already defined");
            System.err.println("Warning: old fact ["+rules.get(rule.getName())+"]");
            System.err.println("Warning: new fact ["+rule+"]");
        }
        return rules.get(rule.getName());
    }

}
