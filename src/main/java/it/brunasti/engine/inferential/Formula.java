package it.brunasti.engine.inferential;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.brunasti.engine.inferential.exceptions.UnknownElementException;
import it.brunasti.engine.inferential.utils.Constants;
import it.brunasti.engine.inferential.utils.InfixToRPNConverter;
import it.brunasti.engine.inferential.utils.RPNExecutor;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Formula implements Serializable {

    @Getter
    String name;

    @Getter
    String description;

    @Getter
    String body;

// TODO add parsed structure to handle the facts and operators

    // Formula parsed into RPN
    private List<String> formulaRPN;
    private String[] formulaArray;

    public boolean validate() {
        formulaRPN = InfixToRPNConverter.infixToRPNConverter(Arrays.asList(body.split(" ")));
        formulaArray = convertListToArray(formulaRPN);
        return true;
    }

    private String[] convertListToArray(List<String> list) {
        String[] res = new String[list.size()];
        Iterator<String> iterator = list.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            res[index] = iterator.next();
            index = index + 1;
        }
        return res;
    }

    public double execute(RuleEngine ruleEngine) throws UnknownElementException {
        return RPNExecutor.executeRPNStack(formulaArray, ruleEngine);
    }

    public void dumpOut() {
        System.out.println(dump());
    }

    public String dump() {
        return name+Constants.RULE_MID_SEPARATOR+body;
    }

}
