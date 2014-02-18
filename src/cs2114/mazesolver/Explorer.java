package cs2114.mazesolver;

// -------------------------------------------------------------------------
/**
 * This enum class loosely represents an explorer moving through the maze. This
 * explorer does not know where she is in the maze; she only knows what
 * direction she is facing, one of the four compass direction. Keeping this enum
 * type through the maze makes using the right-hand-rule much easier.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 */
public enum Explorer
{
    /**
     * Facing North
     */
    NORTH,
    /**
     * Facing South
     */
    SOUTH,
    /**
     * Facing East
     */
    EAST,
    /**
     * Facing West
     */
    WEST;
}
