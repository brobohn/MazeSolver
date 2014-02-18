package cs2114.mazesolver;

import student.TestCase;

/**
 * This is a test class for the Maze class.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.30
 */

public class MazeTest
    extends TestCase
{
    // Fields
    private Maze     maze0;
    private Maze     maze;
    private Location loc0;
    private Location loc1;


    /**
     * Creates a new maze of size 10
     */
    public void setUp()
    {
        maze = new Maze(10);
    }

    /**
     * Tests the getCell() method
     */
    public void testGetCell()
    {
        loc0 = new Location(1, 1);
        assertEquals(maze.getCell(loc0), MazeCell.UNEXPLORED);
        loc1 = new Location(-1, 10);
        assertEquals(maze.getCell(loc1), MazeCell.INVALID_CELL);
    }

    // ----------------------------------------------------------
    /**
     * Tests the two setter and getter methods setStartLocation() and
     * getStartLocation()
     */
    public void testStartLocation()
    {
        loc0 = new Location(0, 0);
        maze.setStartLocation(loc0);
        maze.setStartLocation(new Location(100, 100));
        assertEquals(loc0, maze.getStartLocation());
    }

    /**
     * Tests the setCell method on an invalid cell
     */
    public void testSetCell()
    {
        loc0 = new Location(-1, 0);
        maze.setCell(loc0, MazeCell.UNEXPLORED);
        assertEquals(maze.getCell(loc0), (MazeCell.INVALID_CELL));
    }

    /**
     * Tests the two setter and getter methods setGoalLocation() and
     * getGoalLocation()
     */
    public void testGoalLocation()
    {
        loc0 = new Location(9, 9);
        maze.setGoalLocation(loc0);
        maze.setGoalLocation(new Location(100, 100));
        assertEquals(loc0, maze.getGoalLocation());
    }


    /**
     * Tests the loadBoardState() method.
     * Also tests changing a wall to an unexplored cell.
     */
    public void testLoadBoardState()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OWOO",
                             "OOOW",
                             "WWOO",
                             "OOOO");
        loc0 = new Location(1, 0);
        assertEquals(maze0.getCell(loc0), MazeCell.WALL);

        maze0.setStartLocation(loc0);
        loc1 = new Location(1, 2);
        maze0.setGoalLocation(loc1);
        assertEquals(maze0.getCell(loc0), MazeCell.UNEXPLORED);
        assertEquals(maze0.getCell(loc1), MazeCell.UNEXPLORED);


    }


    /**
     * Tests the solve() method
     */
    public void testSolve1()
    {
        maze0 = new Maze(3);
        maze0.loadBoardState("OOO",
                             "OOO",
                             "OOO");
        assertEquals(maze0.solve(), "(0, 0)(0, 1)(0, 2)(1, 2)(2, 2)");
    }


    /**
     * Tests the solve() method
     */
    public void testSolve2()
    {
        maze0 = new Maze(3);
        maze0.loadBoardState("OOO",
                             "WOO",
                             "OOO");
        assertEquals(maze0.solve(), "(0, 0)(1, 0)(1, 1)(1, 2)(2, 2)");
    }


    /**
     * Tests the solve() method
     */
    public void testSolve3()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OOOO",
                             "OOOO",
                             "OWOO",
                             "OWOO");
        assertEquals(
            maze0.solve(),
            "(0, 0)(0, 1)(1, 1)(2, 1)(2, 2)(2, 3)(3, 3)");
    }

    /**
     * Tests the solve() method
     */
    public void testSolve4()
    {
        maze0 = new Maze(5);
        maze0.loadBoardState("OOOWO",
                             "WWOWO",
                             "OOOOO",
                             "OWOWO",
                             "OWOWO");
        assertEquals(
            maze0.solve(),
            "(0, 0)(1, 0)(2, 0)(2, 1)(2, 2)(3, 2)(4, 2)(4, 3)(4, 4)");
    }

    /**
     * Tests the solve() method
     */
    public void testSolve5()
    {
        maze0 = new Maze(8);
        maze0.loadBoardState("OOOWOOOO",
                             "WWOWOWWO",
                             "OOOWOWWO",
                             "OWOWOWWO",
                             "OWOWOWWO",
                             "OWOWOWWO",
                             "OWOOOOWO",
                             "OWOWWWOO");
        assertEquals(
            maze0.solve(),
            "(0, 0)(1, 0)(2, 0)(2, 1)(2, 2)(2, 3)(2, 4)" +
            "(2, 5)(2, 6)(3, 6)(4, 6)(4, 5)(4, 4)(4, 3)" +
            "(4, 2)(4, 1)(4, 0)(5, 0)(6, 0)(7, 0)(7, 1)" +
            "(7, 2)(7, 3)(7, 4)(7, 5)(7, 6)(7, 7)");
    }

    /**
     * Tests the solve() method
     */
    public void testSolve6()
    {
        maze0 = new Maze(6);
        maze0.loadBoardState("OOOOOO",
                             "OWWWOO",
                             "OOOWOO",
                             "OOOWOO",
                             "OOOWOO",
                             "OOOWOO");
        assertEquals(
            maze0.solve(),
            "(0, 0)(1, 0)(2, 0)(3, 0)(4, 0)" +
            "(4, 1)(4, 2)(4, 3)(4, 4)(4, 5)(5, 5)");
    }

    /**
     * Tests the solve() method on an enclosed start location.
     */
    public void testSolveTrapped()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OWOO",
                             "WOOO",
                             "OOOO",
                             "OOOO");
        assertNull(maze0.solve());
    }

    /**
     * Tests the solve() method on an unsolvable maze.
     */
    public void testSolveNull1()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OOOO",
                             "OOOO",
                             "WWWW",
                             "OOOO");
        assertNull(maze0.solve());
    }

    /**
     * Tests the solve() method on an unsolvable maze.
     */
    public void testSolveNull2()
    {
        maze0 = new Maze(5);
        maze0.loadBoardState("OOOOO",
                             "OOOOO",
                             "OOOOO",
                             "OOOWW",
                             "OOOWW");
        assertNull(maze0.solve());
    }

    /**
     * Tests the solve() method on an unsolvable maze.
     */
    public void testSolveNull3()
    {
        maze0 = new Maze(5);
        maze0.loadBoardState("OOOOO",
                             "OOOOO",
                             "OOOOO",
                             "WWOOO",
                             "OWOOO");
        loc0 = new Location(2, 1);
        loc1 = new Location(0, 4);
        maze0.setStartLocation(loc0);
        maze0.setGoalLocation(loc1);
        assertNull(maze0.solve());
    }

    /**
     * Tests the solve() method with a start and goal other than the default
     */
    public void testNonDefaults1()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OOOO",
                             "OOOO",
                             "OOOO",
                             "OOOO");
        loc0 = new Location(1, 1);
        loc1 = new Location(2, 2);
        maze0.setStartLocation(loc0);
        maze0.setGoalLocation(loc1);
        assertEquals(maze0.solve(),
            "(1, 1)(1, 2)(0, 2)(0, 1)(0, 0)(1, 0)(2, 0)(2, 1)(2, 2)");
    }

    /**
     * Tests the solve() method with a start and goal other than the default
     */
    public void testNonDefaults2()
    {
        maze0 = new Maze(4);
        maze0.loadBoardState("OOOO",
                             "OWWO",
                             "OWOO",
                             "OWOO");
        loc0 = new Location(2, 0);
        loc1 = new Location(0, 3);
        maze0.setStartLocation(loc0);
        maze0.setGoalLocation(loc1);
        assertEquals(maze0.solve(), "(2, 0)(1, 0)(0, 0)(0, 1)(0, 2)(0, 3)");
    }
}
