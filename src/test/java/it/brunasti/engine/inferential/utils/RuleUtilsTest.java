package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.Rule;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RuleUtilsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RuleUtilsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RuleUtilsTest.class );
    }


    public void testBuildRule()
    {
        try {
            Rule r = RuleUtils.buildRule("A=B");
            assertNotNull(r);
            assertNotNull(r.getFact());
            assertNotNull(r.getFormula());
            assertEquals("A",r.getFact().getName());
            assertEquals("B",r.getFormula().getBody());

            r = RuleUtils.buildRule("R:D#A=B");
            assertNotNull(r);
            assertNotNull(r.getFact());
            assertNotNull(r.getFormula());
            assertEquals("A",r.getFact().getName());
            assertEquals("B",r.getFormula().getBody());
            assertEquals("R",r.getName());
            assertEquals("D",r.getDescription());

            assertNotNull(RuleUtils.buildRule("X#A=B"));
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage() );
        }
    }

    public void testBuildRule_failure()
    {
        try {
            assertNotNull(RuleUtils.buildRule(null ));
            fail( "Exception NOT thrown : Rule not defined");
        } catch (Exception ex) {
            System.out.println("Exception correctly thrown:"+ex.getMessage());
        }

        try {
            assertNotNull(RuleUtils.buildRule("A"));
            fail( "Exception NOT thrown : Rule with out '=' sign [A]");
        } catch (Exception ex) {
            System.out.println("Exception correctly thrown:"+ex.getMessage());
        }

        try {
            assertNotNull(RuleUtils.buildRule("R:D\nA"));
            fail( "Exception NOT thrown : Rule with out '=' sign [R:D\nA]");
        } catch (Exception ex) {
            System.out.println("Exception correctly thrown:"+ex.getMessage());
        }

    }

}
