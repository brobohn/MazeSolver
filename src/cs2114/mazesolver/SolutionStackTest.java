package cs2114.mazesolver;

import java.util.EmptyStackException;
import student.TestCase;

//-------------------------------------------------------------------------
/**
 * Tests for the SolutionStack class.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 */
public class SolutionStackTest
    extends TestCase
{
    //~ Instance/static variables .............................................

    private SolutionStack<String> stack;


    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Creates a brand new, empty stack for each test method.
     */
    public void setUp()
    {
        stack = new SolutionStack<String>();
    }



    // ----------------------------------------------------------
    /**
     * Tests the push method
     */
    public void testPush()
    {
        ILocation loc = new Location(1, 1);
        stack.push(loc);
        assertEquals(stack.size(), 1);

        ILocation loc1 = new Location(1, 1);
        stack.push(loc1);
        assertEquals(stack.size(), 2);
    }

    /**
     * Tests the pop method
     */
    public void testPop()
    {
        ILocation loc = new Location(1, 1);
        stack.push(loc);
        stack.pop();
        assertEquals(stack.size(), 0);

        Exception thrown = null;
        try
        {
            stack.pop();
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof EmptyStackException);
    }

    /**
     * Tests the top method
     */
    public void testTop()
    {
        Exception thrown = null;
        try
        {
            stack.top();
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof EmptyStackException);

        ILocation loc = new Location(1, 1);
        stack.push(loc);
        assertEquals(stack.top(), loc);
    }

    /**
     * Tests the size method
     */
    public void testSize()
    {
        assertEquals(stack.size(), 0);
        ILocation loc = new Location(1, 1);
        stack.push(loc);
        assertEquals(stack.size(), 1);
    }
}
