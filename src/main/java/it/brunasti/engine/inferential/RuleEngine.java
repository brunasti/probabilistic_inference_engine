package it.brunasti.engine.inferential;

import it.brunasti.engine.inferential.exceptions.UnknownElementException;
import it.brunasti.engine.inferential.utils.ExecutionTracer;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

@ToString
@NoArgsConstructor
public class RuleEngine implements Serializable {
    @Getter
    private RulesBase rulesBase = new RulesBase();
    @Getter
    private FactsBase factsBase = new FactsBase();


    @Setter
    private transient boolean debug = false;
    @Setter
    private transient boolean trace = false;
    private transient int depth = 0;

    private transient ExecutionTracer executeTracer;

    public void trace(String string) {
        if (trace) executeTracer.trace(string);
    }

    public void loadRules(RulesBase rules) {
        this.rulesBase = rules;
    }

    public void loadKnowledgeBase(FactsBase knowledgeBase) {
        this.factsBase = knowledgeBase;
    }

    public Fact registerFact(Fact fact) {
        return factsBase.registerFact(fact);
    }

    public Fact getFact(Fact fact) {
        if (fact == null) {
            return null;
        }
        return getFact(fact.name);
    }

    public Fact getFact(String factName) {
        return factsBase.getFacts().get(factName);
    }

    public int rulesNumber() {
        return rulesBase.rules.size();
    }

    public int factsNumber() {
        return factsBase.facts.size();
    }

    public double evaluateFormula(Formula formula) throws UnknownElementException {
        if (debug) System.out.println("      Evaluate formula : "+formula+" ("+trace+")");
        if (debug) formula.dumpOut();

//        if (trace) executeTracer.trace("  - "+formula.getBody());
//
        double res = formula.execute(this);
        if (debug) System.out.println("      <- : "+res);

        return res;
    }

    public int evaluateKnowledge() {
        if (debug) System.out.println(" --- evaluateKnowledge : "+depth);
        if (debug) System.out.println(this);

        int executed = 0;

        Collection<Rule> rules = getRulesBase().getRules().values();
        Iterator<Rule> rulesIterator = rules.iterator();
        while (rulesIterator.hasNext()) {
            Rule rule = rulesIterator.next();
            if (debug) System.out.println("  rule : "+rule);
            if (debug) System.out.println("  - fact : "+rule.fact);
            if (rule.fact.value < 0) {
                if (debug) System.out.println("    execute : "+rule);
                // Evaluate the formula
                try {
                    if (trace) executeTracer.trace("Rule "+rule.getName()+" ? "+rule.fact.getName()+" = "+rule.getFormula().getBody());
                    double res = evaluateFormula(rule.formula);
                    if (debug) System.out.println("    <-> : " + res);
                    if (res >= 0) {
                        if (debug)
                            System.out.println("    =-> : update fact (" + getFact(rule.getFact()) + ") to " + res);
                        getFact(rule.getFact()).value = res;
                        if (trace) executeTracer.trace("   "+rule.fact.getName()+" <- "+res);
                        if (trace) executeTracer.trace("");
                        executed = executed + 1;
                    }
                } catch (UnknownElementException uee) {
                    uee.printStackTrace();
                }
            }
        }

        if (debug) System.out.println("  executed : "+executed);

        return executed;
    }

    public double evaluateQuery(String query) {
        return evaluateQuery(query,false,null);
    }

    public double evaluateQuery(String query, boolean trace, String traceFileName) {
        if (debug) System.out.println("evaluateQuery : "+query+" ("+trace+"-"+traceFileName+")");
        if (trace) {
            this.trace = trace;
            executeTracer = new ExecutionTracer(traceFileName, "Execution Trace : "+query, trace, debug);
            executeTracer.setDebug(debug);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            executeTracer.trace(new String[]{
                    "",
                    "Facts : ",
                    factsBase.dump(),
                    "",
                    "Rules : ",
                    rulesBase.dump(),
                    "",
                    ExecutionTracer.ROW_SEPARATOR,
                    "Query : "+query,
                    "",
                    " - Start : "+timestamp,
                    ExecutionTracer.ROW_SEPARATOR
            });
        }

        double result = evaluateQueryLoop(query);


        if (trace) {
            Fact fact = getFact(query);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            executeTracer.trace(new String[]{
                    ExecutionTracer.ROW_SEPARATOR,
                    "Result : ",
                    "   "+query+" ("+fact.getDescription()+")",
                    "     = "+result,
                    "",
                    " - End   : "+timestamp,
                    ExecutionTracer.ROW_SEPARATOR,
                    ""});
        }

        return result;
    }

    public double evaluateQueryLoop(String query) {
        if (debug) System.out.println("Depth ------- evaluateQuery : "+depth);

        Fact resFact = getFact(query);

        if (resFact == null) {
            if (debug) System.out.println("queried fact not defined : "+query);
            return -2;
        }

        if (resFact.value < 0) {
            if (debug) System.out.println("queried fact value unknown : MUST EVALUATE Knowledge");

            int res = evaluateKnowledge();
            if (res > 0) {
                depth=depth+1;
                return evaluateQuery(query);
            }

            return -1;
        }

        if (resFact.value >= 0) {
            if (debug) System.out.println("Fact found : "+resFact);
            return resFact.value;
        }

        if (debug) System.out.println("-----why here?");
        return 0;
    }
}