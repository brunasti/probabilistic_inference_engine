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
public class FactsBase implements Serializable {
    Map<String, Fact> facts = new HashMap<>();

    public String dump() {
        String result = "";
        String separator = "  ";
        for (Fact fact : facts.values()) {
            result = result + separator + fact.getName() + " : " + fact.getValue();
            separator = "\n  ";
        }

        return result;
    }

    public Fact registerFact(Fact fact) {
        if (!facts.containsKey(fact.getName())) {
            facts.put(fact.getName(), fact);
        } else {
            System.err.println("Warning: fact ["+fact.getName()+"] already defined");
            System.err.println("Warning: old fact ["+ facts.get(fact.getName())+"]");
            System.err.println("Warning: new fact ["+fact+"]");
        }
        return facts.get(fact.getName());
    }

    public int size() {
        return facts.size();
    }

}
