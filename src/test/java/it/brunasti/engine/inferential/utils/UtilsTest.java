package it.brunasti.engine.inferential.utils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Utils functions.
 */
public class UtilsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UtilsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UtilsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCreateName()
    {
        assertNotSame(Utils.createName("A"), Utils.createName("A"));
        assertNotSame(Utils.createName("A"), Utils.createName("B"));
    }

    public void testDump()
    {
        String t = "Title";
        String[] arr = {"A", "B"};
        Utils.dump(t, arr);
    }

    public void testDump_errors()
    {
        String t = "Title";
        String[] arr = {"A", "B"};
        Utils.dump(null, arr);
        Utils.dump(t, null);
    }


}
