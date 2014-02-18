package cs2114.mazesolver;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * This is a test class for the Location class.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 */

public class LocationTest
    extends TestCase
{
    // Fields
    private Location loc0;
    private Location loc1;


    // ----------------------------------------------------------
    /**
     * Tests the toString() method
     */
    public void testToString()
    {
        loc0 = new Location(1, 1);
        assertEquals(loc0.toString(), "(1, 1)");
    }


    /**
     * Tests the equals() method
     */
    public void testEquals()
    {
        loc0 = new Location(1, 1);
        loc1 = new Location(1, 1);
        Location loc2 = new Location(0, 0);
        assertTrue(loc0.equals(loc1));
        assertFalse(loc0.equals(loc2));
        int i = 0;
        assertFalse(loc0.equals(i));
    }


    /**
     * Tests the east() method
     */
    public void testEast()
    {
        loc0 = new Location(1, 1);
        loc1 = new Location(2, 1);
        assertTrue(loc1.equals(loc0.east()));
    }


    /**
     * Tests the north() method
     */
    public void testNorth()
    {
        loc0 = new Location(1, 1);
        loc1 = new Location(1, 0);
        assertTrue(loc1.equals(loc0.north()));
    }


    /**
     * Tests the south() method
     */
    public void testSouth()
    {
        loc0 = new Location(1, 1);
        loc1 = new Location(1, 2);
        assertTrue(loc1.equals(loc0.south()));
    }


    /**
     * Tests the west() method
     */
    public void testWest()
    {
        loc0 = new Location(1, 1);
        loc1 = new Location(0, 1);
        assertTrue(loc1.equals(loc0.west()));
    }
}
