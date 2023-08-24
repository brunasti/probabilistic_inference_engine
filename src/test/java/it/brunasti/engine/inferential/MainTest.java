package it.brunasti.engine.inferential;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class MainTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MainTest.class );
    }

    public void testMain_empty()
    {
        String[ ] args = {};

        Main.main(args);
    }

    public void testMain_unknown_option()
    {
        String[ ] args = {"-Q"};

        Main.main(args);
    }

    public void testMain_help()
    {
        String[ ] args = {"-h"};

        Main.main(args);
    }

    public void testMain_help_debug()
    {
        String[ ] args = {"-h", "-d"};

        Main.main(args);
    }

    public void testMain_debug_query_not_found()
    {
        String[ ] args = {"X", "-d"};

        Main.main(args);
    }

    public void testMain_debug_query_Rain()
    {
        String[ ] args = {"Rain", "-d"};

        Main.main(args);
    }

    public void testMain_debug_query_Rain_WITH_DUMP()
    {
        String[ ] args = {"Rain", "-d", "-D", "dump_knowledge_base.json"};

        Main.main(args);
    }

    public void testMain_debug_query_Rain_WITH_TRACE()
    {
        String[ ] args = {"Rain", "-d", "-T", "trace.txt"};

        Main.main(args);
    }

    public void testMain_debug_query_Rain_WITH_TRACE_from_subfiles()
    {
        String[ ] args = {"Rain", "-d", "-T", "trace.txt", "-r", "default_rules.json", "-f", "default_facts.json"};

        Main.main(args);
    }

    public void testMain_query_Rain_from_subfiles()
    {
        String[ ] args = {"Rain", "-d", "-T", "trace.txt", "-x", "-r", "rules.kbr", "-f", "facts.kbf"};

        Main.main(args);
    }

    public void testMain_debug_query_Clouds()
    {
        String[ ] args = {"Clouds", "-d"};

        Main.main(args);
    }


}
