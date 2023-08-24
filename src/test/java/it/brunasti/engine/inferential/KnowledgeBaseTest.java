package it.brunasti.engine.inferential;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class KnowledgeBaseTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public KnowledgeBaseTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( KnowledgeBaseTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testCreateKnowledgeBase()
    {
        FactsBase knowledgeBase = new FactsBase();

        Fact fact = Fact.builder().build();
        knowledgeBase.registerFact(fact);
        assertEquals(knowledgeBase.size(), 1);
    }

    public void testAdd_twice()
    {
        FactsBase knowledgeBase = new FactsBase();

        Fact fact = Fact.builder().build();
        knowledgeBase.registerFact(fact);
        knowledgeBase.registerFact(fact);
        assertEquals(knowledgeBase.size(), 1);
    }

    public void testAdd_two_equals_facts()
    {
        FactsBase knowledgeBase = new FactsBase();

        Fact fact = Fact.builder().build();
        knowledgeBase.registerFact(fact);

        fact = Fact.builder().build();
        knowledgeBase.registerFact(fact);

        assertEquals(1, knowledgeBase.size());
    }

    public void testAdd_two_different_facts()
    {
        FactsBase knowledgeBase = new FactsBase();

        Fact fact = Fact.builder().name("A").build();
        knowledgeBase.registerFact(fact);

        fact = Fact.builder().name("B").build();
        knowledgeBase.registerFact(fact);

        assertEquals(2, knowledgeBase.size());
    }


}
