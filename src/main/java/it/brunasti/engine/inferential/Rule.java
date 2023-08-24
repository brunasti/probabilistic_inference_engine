package it.brunasti.engine.inferential;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rule implements Serializable {

    String name;
    String description;
    Fact fact;
    Formula formula;

    public Fact replaceFact(Fact newFact) {
        Fact oldFact = fact;
        fact = newFact;
        return oldFact;
    }
}