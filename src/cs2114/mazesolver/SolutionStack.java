package cs2114.mazesolver;

import java.util.ArrayList;
import java.util.EmptyStackException;

// -------------------------------------------------------------------------
/**
 * This class is the stack class used for the Maze solver.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 * @param <T>
 *            the type of data we want to contain in the stack
 */
public class SolutionStack<T>
{
    private ArrayList<ILocation> stack; // the ArrayList holding the stack
    private int                  top;  // the index of the top element of the
// Stack


    // ----------------------------------------------------------
    /**
     * Create a new SolutionStack object.
     */
    public SolutionStack()
    {
        stack = new ArrayList<ILocation>(0);
        top = 0;
    }


    // ----------------------------------------------------------
    /**
     * Adds an item to the stack
     *
     * @param item
     *            the item
     */
    public void push(ILocation item)
    {
        stack.add(top, item);
        top++;
    }


    /**
     * Removes the top item from stack. Throws an exception if the stack is
     * empty.
     */
    public void pop()
    {
        EmptyStackException ex = new EmptyStackException();
        if (top <= 0)
        {
            throw ex;
        }
        else
        {
            stack.remove(top - 1);
            top--;
        }
    }


    /**
     * returns the top element
     *
     * @return the top element
     */
    public ILocation top()
    {
        EmptyStackException ex = new EmptyStackException();
        if (top <= 0)
        {
            throw ex;
        }
        else
        {
            return stack.get(top - 1);
        }
    }


    /**
     * returns the size
     *
     * @return the size
     */
    public int size()
    {
        return top;
    }


    /**
     * checks if the array list is empty
     *
     * @return the value of emptiness
     */
    public boolean isEmpty()
    {
        return stack.size() == 0;
    }
}
