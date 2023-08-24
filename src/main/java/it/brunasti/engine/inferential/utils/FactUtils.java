package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.Fact;
import it.brunasti.engine.inferential.exceptions.FactParsingException;

public class FactUtils implements Constants {

    static public Fact buildFact(String definition) throws FactParsingException {
        if (definition == null) {
            throw new FactParsingException("Fact not defined");
        }

        int headerDelim = definition.indexOf(FACT_VALUE_INITIATOR);
        if (headerDelim < 0) {
            throw new FactParsingException("Fact with out '"+FACT_VALUE_INITIATOR+"' sign ["+definition+"]");
        }

        String name;
        String description;
        Fact fact;

        String header = definition.substring(0,headerDelim).trim();
        int nameDelim = header.indexOf(FACT_FIRST_SEPARATOR);
        if (nameDelim <= 0) {
            name = header;
            description = header;
        } else {
            name = header.substring(0,nameDelim).trim();
            description = header.substring(nameDelim+1).trim();
        }

        String value = definition.substring(headerDelim+1).trim();

        fact = Fact.builder().name(name).description(description).value(Double.parseDouble(value)).build();

        return fact;
    }

}
