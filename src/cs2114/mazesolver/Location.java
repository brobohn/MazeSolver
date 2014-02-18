package cs2114.mazesolver;

// -------------------------------------------------------------------------
/**
 * This class represents a location in the Maze; an x coordinate and a y
 * coordinate. It implements the ILocation interface.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 */

public class Location
    implements ILocation
{
    private int x;
    private int y;


    /**
     * Constructor for a location
     *
     * @param xPos
     *            the x coordinate
     * @param yPos
     *            the y coordinate
     */
    public Location(int xPos, int yPos)
    {
        x = xPos;
        y = yPos;
    }


    /**
     * Returns the Location ahead of the explorer
     *
     * @param exp
     *            the direction of the explorer
     * @return the location in the direction the explorer is facing
     */
    public ILocation ahead(Explorer exp)
    {
        if (exp == Explorer.NORTH)
        {
            return north();
        }
        else if (exp == Explorer.EAST)
        {
            return east();
        }
        else if (exp == Explorer.SOUTH)
        {
            return south();
        }
        else
        {
            return west();
        }
    }


    /**
     * Returns the Location to the right of the explorer
     *
     * @param exp
     *            the direction of the explorer
     * @return the location in the direction the explorer is facing
     */
    public ILocation right(Explorer exp)
    {
        if (exp == Explorer.NORTH)
        {
            return east();
        }
        else if (exp == Explorer.EAST)
        {
            return south();
        }
        else if (exp == Explorer.SOUTH)
        {
            return west();
        }
        else
        {
            return north();
        }
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        else if (obj instanceof Location)
        {
            return (x == ((Location)obj).x() && y == ((Location)obj).y());
        }
        return false;
    }


    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x(), y());
    }


    // ----------------------------------------------------------
    @Override
    public ILocation east()
    {
        return new Location(x + 1, y);
    }


    // ----------------------------------------------------------
    @Override
    public ILocation north()
    {
        return new Location(x, y - 1);
    }


    // ----------------------------------------------------------
    @Override
    public ILocation south()
    {
        return new Location(x, y + 1);
    }


    // ----------------------------------------------------------
    @Override
    public ILocation west()
    {
        return new Location(x - 1, y);
    }


    // ----------------------------------------------------------
    @Override
    public int x()
    {
        return x;
    }


    // ----------------------------------------------------------
    @Override
    public int y()
    {
        return y;
    }

}
