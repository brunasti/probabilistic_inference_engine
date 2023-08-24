package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.RuleEngine;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RuleEngineUtilsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RuleEngineUtilsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RuleEngineUtilsTest.class );
    }

    public void test_readRulesFromNonJsonFile()
    {
        String knowledgeBaseFile = "./knowledge_base.json";
        String rulesFile = "./rules.kbr";

        try {
            RuleEngine ruleEngine = RuleEngineUtils.readFromJsonFile(knowledgeBaseFile);
            assertNotNull(ruleEngine);

            RuleEngineUtils.readRulesFromNonJsonFile(ruleEngine,rulesFile);
            assertNotNull(ruleEngine);

            System.out.println(ruleEngine.getRulesBase().dump());

            assertEquals(5,ruleEngine.getRulesBase().getRules().size());
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
            ex.printStackTrace();
        }

    }


    public void test_readFactsFromNonJsonFile()
    {
        String knowledgeBaseFile = "./knowledge_base.json";
        String rulesFile = "./facts.kbf";

        try {
            RuleEngine ruleEngine = RuleEngineUtils.readFromJsonFile(knowledgeBaseFile);
            assertNotNull(ruleEngine);

            RuleEngineUtils.readFactsFromNonJsonFile(ruleEngine,rulesFile);
            assertNotNull(ruleEngine);

            System.out.println(ruleEngine.getFactsBase().dump());

            assertEquals(4,ruleEngine.getFactsBase().getFacts().size());
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
            ex.printStackTrace();
        }

    }

}
