package it.brunasti.engine.inferential;

import it.brunasti.engine.inferential.exceptions.RuleParsingException;
import it.brunasti.engine.inferential.utils.RuleEngineUtils;
import it.brunasti.engine.inferential.utils.RuleUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;

/**
 * Unit test for simple App.
 */
public class RuleEngineTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RuleEngineTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RuleEngineTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCreateRuleEngine()
    {

        RuleEngine ruleEngine = new RuleEngine();
//        ruleEngine.setDebug(true);

        try {
            String ruleDef = "Rule1:Prova di rule#A=B";
            Rule r = RuleUtils.buildRule(ruleDef);
            ruleEngine.getRulesBase().registerRule(r);
            assertEquals(1, ruleEngine.rulesNumber());
            ruleEngine.getRulesBase().registerRule(r);
            assertEquals(1, ruleEngine.rulesNumber());
        } catch (RuleParsingException rpe) {
            rpe.printStackTrace();
            fail("Exception : "+rpe.getMessage());
        }

        try {
            String ruleDef = "C=( A + B ) * 0.5";
            Rule r = RuleUtils.buildRule(ruleDef);
            ruleEngine.getRulesBase().registerRule(r);
            assertEquals(2, ruleEngine.rulesNumber());
        } catch (RuleParsingException rpe) {
            rpe.printStackTrace();
            fail("Exception : "+rpe.getMessage());
        }

        try {
            String ruleDef = "B=0.5";
            Rule r = RuleUtils.buildRule(ruleDef);
            ruleEngine.getRulesBase().registerRule(r);
            assertEquals(3, ruleEngine.rulesNumber());
        } catch (RuleParsingException rpe) {
            rpe.printStackTrace();
            fail("Exception : "+rpe.getMessage());
        }

        RuleEngineUtils.normalizeKnowledgeBase(ruleEngine);

        assertEquals(3, ruleEngine.factsNumber());

        try {
            String fileName = "./src/test/java/it/brunasti/engine/inferential/testRuleEngine.json";
            RuleEngineUtils.writeToJsonFile(ruleEngine,fileName);
            RuleEngine newEngine = RuleEngineUtils.readFromJsonFile(fileName);
            System.out.println(ruleEngine);
            System.out.println(newEngine);

            assertEquals(ruleEngine.toString(), newEngine.toString());

        } catch (IOException ioe) {
            ioe.printStackTrace();
            fail("Exception : "+ioe.getMessage());
        }

    }


}
