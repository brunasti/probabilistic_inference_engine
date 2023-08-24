package it.brunasti.engine.inferential.utils;

import it.brunasti.engine.inferential.RuleEngine;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RPNExecutorTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RPNExecutorTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RPNExecutorTest.class );
    }

    public void testExecution_complex()
    {
        try {
            // String Input
            String[] s
                    = { "10", "6", "9",  "3", "+", "-11", "*",
                    "/",  "*", "17", "+", "5", "+" };

            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(21.545454545454547, result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }

    public void testExecution_simple_double()
    {
        try {
            // String Input
            String[] s = { "1.1", "2.3", "+" };

            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(3.4, result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }

    public void testExecution_zero_division()
    {
        try {
            // String Input
            String[] s = { "1.1", "0", "/" };

            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(Double.POSITIVE_INFINITY,result);

            s = new String[]{ "2", "0", "/" };

            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(Double.POSITIVE_INFINITY,result);

            s = new String[]{ "-2", "0", "/" };

            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(Double.NEGATIVE_INFINITY,result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }


    public void testExecution_subtraction()
    {
        try {

            String[] s = { "1.1", "0", "-" };
            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(1.1,result);

            s = new String[]{ "2", "1", "-" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(1.0,result);

            s = new String[]{ "-2", "1", "-" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(-3.0,result);

            s = new String[]{ "0", "3.3", "-" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(-3.3,result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }
    public void testExecution_simple()
    {
        try {
            // String Input
            String[] s = { "1", "2", "+" };

            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(3.0, result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }

    public void testExecution_less_simple()
    {
        try {
            // String Input
            String[] s = { "1", "2", "+", "3", "-" };

            double result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(0.0, result);
        } catch (Exception ex) {
            fail( "Exception thrown : "+ex.getMessage());
        }
    }

    public void testExecution_not_operator()
    {
        try {
            // String Input
            String[] s;
            double result;

            s = new String[]{ "1", "!" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(0.0, result);

            s = new String[]{ "0.0", "!" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(1.0, result);

            s = new String[]{ "0.5", "!" };
            result = RPNExecutor.executeRPNStack(s, null);
            assertEquals(0.5, result);

        } catch (Exception ex) {
            ex.printStackTrace();
            fail( "Exception thrown : "+ex.getMessage());
        }
    }
    public void testExecution_undef_operator()
    {
        try {
            // String Input
            String[] s = { "1", "ND" };
            String fileName = "./src/test/java/it/brunasti/engine/inferential/testRuleEngine.json";
            RuleEngine ruleEngine = RuleEngineUtils.readFromJsonFile(fileName);

            RPNExecutor.executeRPNStack(s, ruleEngine);
            fail( "Exception not thrown for UNKNOWN Element : "+s.toString());
        } catch (Exception ex) {
            System.out.println("Failed as expected : "+ex.getMessage());
        }
    }

    public void testExecution_fact()
    {
        try {
            // String Input
            String[] s = { "A" };
            String fileName = "./src/test/java/it/brunasti/engine/inferential/testRuleEngine.json";
            RuleEngine ruleEngine = RuleEngineUtils.readFromJsonFile(fileName);

            double result = RPNExecutor.executeRPNStack(s, ruleEngine);
            assertEquals(-1.0, result);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail( "Exception thrown : "+ex.getMessage());
        }
    }


}
