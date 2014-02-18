package cs2114.mazesolver;

// import android.widget.Button;
// import android.widget.CompoundButton;
import android.widget.TextView;
import sofia.graphics.ShapeView;

// -------------------------------------------------------------------------
/**
 * This is a test class for the MazeSolverScreen
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.10.19
 */
public class MazeSolverScreenTests
    extends student.AndroidTestCase<MazeSolverScreen>
{
    // ~ Fields ................................................................

    private ShapeView shapeView;
    private TextView  infoLabel;

    // This field will store the pixel width/height of a cell in the maze.
    private int       cellSize;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MazeSolverScreenTests()
    {
        super(MazeSolverScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        float viewSize = Math.min(shapeView.getWidth(), shapeView.getHeight());

        cellSize = (int)(viewSize / 8);
    }


    // ----------------------------------------------------------
    /**
     * Tests drawing walls.
     */
    public void testDrawWalls()
    {
        getScreen().drawWallsClicked();
        touchDownCell(4, 4);
        touchMoveCell(5, 4);
        assertEquals(
            getScreen().getMaze().getCell(new Location(4, 4)),
            MazeCell.WALL);
        assertEquals(
            getScreen().getMaze().getCell(new Location(5, 4)),
            MazeCell.WALL);
    }


    /**
     * Tests breaking walls.
     */
    public void testBreakWalls()
    {
        getScreen().drawWallsClicked();
        clickCell(4, 4);
        getScreen().eraseWallsClicked();
        clickCell(4, 4);
        assertEquals(
            getScreen().getMaze().getCell(new Location(4, 4)),
            MazeCell.UNEXPLORED);
    }


    /**
     * Tests setting the start location, and trying to make the start location a
     * wall.
     */
    public void testSetStart()
    {
        getScreen().setStartClicked();
        clickCell(2, 2);
        assertEquals(
            getScreen().getMaze().getCell(new Location(2, 2)),
            getScreen().getMaze().getCell(
                getScreen().getMaze().getStartLocation()));
        getScreen().drawWallsClicked();
        clickCell(2, 2);
        assertEquals(
            getScreen().getMaze().getCell(new Location(2, 2)),
            MazeCell.UNEXPLORED);
    }


    /**
     * Tests setting the goal location.
     */
    public void testSetGoal()
    {
        getScreen().setGoalClicked();
        touchDownCell(5, 5);
        assertEquals(
            getScreen().getMaze().getCell(new Location(5, 5)),
            getScreen().getMaze().getCell(
                getScreen().getMaze().getGoalLocation()));
    }


    /**
     * Tests the text in the infoLabel after the maze is solved with a solution.
     */
    public void testSolve()
    {
        getScreen().getMaze().loadBoardState(
            "OOOOOOOO",
            "OWOOOOOO",
            "OWOOOOOO",
            "WWOOOOOO",
            "OOOOOOOO",
            "OOOOOOOO",
            "OOOOOOOO",
            "OOOOOOWO");
        getScreen().solveClicked();
        assertEquals(getScreen().getSolution(), infoLabel.getText().toString());
    }


    /**
     * Tests the text in the infoLabel after the maze is solved with no
     * solution.
     */
    public void testSolveNull()
    {
        getScreen().getMaze().loadBoardState(
            "OOOOOOOO",
            "OWWWWWWW",
            "OWOOOOOO",
            "WWOOOOOO",
            "OOOOOOOO",
            "OOOOOOOO",
            "OOOOOOOO",
            "OOOOOOOO");
        getScreen().solveClicked();
        assertEquals("No solution was possible.", infoLabel.getText()
            .toString());
    }


    // ~ Private methods .......................................................

    // ----------------------------------------------------------
    /**
     * Simulates touching down on the middle of the specified cell in the maze.
     */
    private void touchDownCell(int x, int y)
    {
        touchDown(shapeView, (x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates moving the finger instantaneously to the middle of the
     * specified cell in the maze.
     */
    private void touchMoveCell(int x, int y)
    {
        touchMove((x + 0.5f) * cellSize, (y + 0.5f) * cellSize);
    }


    // ----------------------------------------------------------
    /**
     * Simulates clicking the middle of the specified cell in the maze. This is
     * equivalent to calling: touchDownCell(x, y); touchUp();
     */
    private void clickCell(int x, int y)
    {
        touchDownCell(x, y);
        touchUp();
    }
}
