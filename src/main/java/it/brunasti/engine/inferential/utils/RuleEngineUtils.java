package it.brunasti.engine.inferential.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.brunasti.engine.inferential.*;
import it.brunasti.engine.inferential.exceptions.FactParsingException;
import it.brunasti.engine.inferential.exceptions.RuleParsingException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RuleEngineUtils implements Constants {

    public static void writeToJsonFile(RuleEngine ruleEngine, String fileName) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(ruleEngine);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(json);

        writer.close();
    }

    public static RuleEngine readFromJsonFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream is = new FileInputStream(fileName);
        RuleEngine ruleEngine = mapper.readValue(is, RuleEngine.class);
        System.out.println("  - RuleEngine loaded \n" + ruleEngine);
        normalizeKnowledgeBase(ruleEngine);
        System.out.println("  - RuleEngine normalized \n" + ruleEngine);
        return ruleEngine;
    }


    public static void readFactsFromJsonFile(RuleEngine ruleEngine, String fileName) throws IOException {
        System.out.println("  - Load KnowledgeBase facts from " + fileName);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream is = new FileInputStream(fileName);
        FactsBase knowledgeBase = mapper.readValue(is, FactsBase.class);
        System.out.println("  - KnowledgeBase loaded \n" + knowledgeBase);
        ruleEngine.loadKnowledgeBase(knowledgeBase);
        normalizeKnowledgeBase(ruleEngine);
        System.out.println("  - RuleEngine normalized \n" + ruleEngine);
    }

    public static void readRulesFromJsonFile(RuleEngine ruleEngine, String fileName) throws IOException {
        System.out.println("  - Load KnowledgeBase rule from " + fileName);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream is = new FileInputStream(fileName);
        RulesBase rules = mapper.readValue(is, RulesBase.class);
        System.out.println("  - Rules loaded \n" + rules);
        ruleEngine.loadRules(rules);
        normalizeKnowledgeBase(ruleEngine);
        System.out.println("  - RuleEngine normalized \n" + ruleEngine);
    }


    public static void normalizeKnowledgeBase(RuleEngine ruleEngine) {
        System.out.println("  - normalizeKnowledgeBase KnowledgeBase");
        Collection<Rule> rules = ruleEngine.getRulesBase().getRules().values();
        Iterator<Rule> rulesIterator = rules.iterator();
        while (rulesIterator.hasNext()) {
            Rule rule = rulesIterator.next();

            Fact fact = rule.getFact();
            Fact kbFact = ruleEngine.getFact( fact );
            if (kbFact != null) {
                rule.replaceFact(kbFact);
            } else {
                ruleEngine.registerFact(fact);
            }

            Formula formula = rule.getFormula();
            formula.validate();
        }
    }


//    public static Rule readRuleFromNonJson(RuleEngine ruleEngine, String line) throws RuleParsingException {
//        System.out.println("readRuleFromNonJson ["+line+"]");
//        String header;
//        String def;
//
//        if (line.indexOf(RULE_MID_SEPARATOR) < 0) {
//            throw new RuleParsingException("Rule Definition with not "+RULE_MID_SEPARATOR+" separator "+line);
//        }
//        if (line.indexOf(RULE_FORMULA_INITIATOR) < 0) {
//            throw new RuleParsingException("Rule Definition with not "+RULE_FORMULA_INITIATOR+" separator "+line);
//        }
//
//        header = line.substring(0,line.indexOf(RULE_MID_SEPARATOR)).trim();
//        def = line.substring(line.indexOf(RULE_MID_SEPARATOR)+1).trim();
//        System.out.println("  - ["+header+"] ["+def+"]");
//
//        String name;
//        String description;
//
//        if (header.indexOf(RULE_FIRST_SEPARATOR) > 0) {
//            name = header.substring( 0, header.indexOf(RULE_FIRST_SEPARATOR)).trim();
//            description = header.substring( header.indexOf(RULE_FIRST_SEPARATOR) + 1).trim();
//        } else {
//            name = header;
//            description = "Description of "+header;
//        }
//
//        String factName;
//        String formulaDef;
//
//        factName = def.substring( 0, header.indexOf(RULE_FORMULA_INITIATOR)).trim();
//        formulaDef = def.substring( header.indexOf(RULE_FORMULA_INITIATOR) + 1).trim();
//
//        Fact fact = ruleEngine.getFact(factName);
//        Rule rule = Rule.builder().name(name).description(description).fact().formula().build();
//
//
//        return null;
//    }

    public static void readRulesFromNonJsonFile(RuleEngine ruleEngine, String fileName) throws IOException {
        System.out.println("readRulesFromNonJsonFile rules from " + fileName);
        File file=new File(fileName);    //creates a new file instance
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        String line;
        List<Rule> rules = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if ((line.length() > 0) && (line.trim().charAt(0) != NON_JSON_COMMENT)) {
                    Rule rule = RuleUtils.buildRule(line);
                    rules.add(rule);
                }
            }

            rules.forEach(rule -> ruleEngine.getRulesBase().registerRule(rule));
            System.out.println("  - loaded readRulesFromNonJsonFile rules from " + fileName);
            System.out.println(ruleEngine);
            normalizeKnowledgeBase(ruleEngine);
        } catch (RuleParsingException rpe) {
            rpe.printStackTrace();
        }
    }

    public static void readFactsFromNonJsonFile(RuleEngine ruleEngine, String fileName) throws IOException {
        System.out.println("readFactsFromNonJsonFile facts from " + fileName);
        File file=new File(fileName);    //creates a new file instance
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        String line;
        List<Fact> facts = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if ((line.length() > 0) && (line.trim().charAt(0) != NON_JSON_COMMENT)) {
                    Fact fact = FactUtils.buildFact(line);
                    facts.add(fact);
                }
            }

            facts.forEach(fact -> ruleEngine.getFactsBase().registerFact(fact));
            System.out.println("  - loaded readFactsFromNonJsonFile facts from " + fileName);
            System.out.println(ruleEngine);
            normalizeKnowledgeBase(ruleEngine);
        } catch (FactParsingException fpe) {
            fpe.printStackTrace();
        }
    }



}
