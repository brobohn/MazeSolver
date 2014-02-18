package cs2114.mazesolver;

import sofia.graphics.OvalShape;
import sofia.graphics.Color;
import sofia.app.ShapeScreen;
import sofia.graphics.RectangleShape;
import android.widget.TextView;

// -------------------------------------------------------------------------
/**
 * This class is the screen for the Maze Solver.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.10.12
 */
public class MazeSolverScreen
    extends ShapeScreen
{
    // ~ Fields ................................................................
    private Maze               maze;
    private RectangleShape[][] mazeArray  = new RectangleShape[8][8];
    private OvalShape          startLocation;
    private OvalShape          goalLocation;
    private float              side;
    private TextView           infoLabel;
    private String             solution;
    // These booleans keep track of which button on the screen has been pushed.
    private boolean            buildWalls = true;
    private boolean            isStart    = false;
    private boolean            isGoal     = false;


    // ~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up a blank maze and the GUI.
     */
    public void initialize()
    {
        infoLabel.setText("");
        maze = new Maze(8);
        side = Math.min(getWidth(), getHeight());
        side /= 8;
        // side -= 1;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                RectangleShape tile =
                    new RectangleShape(
                        i * side,
                        j * side,
                        (i + 1) * side,
                        (j + 1) * side);
                tile.setColor(Color.white);
                tile.setFillColor(Color.black);
                add(tile);
                mazeArray[i][j] = tile;
            }
        }

        // Create two ovals to represent start and goal locations
        float startX = maze.getStartLocation().x();
        startX += .5;
        float startY = maze.getStartLocation().y();
        startY += .5;
        startLocation = new OvalShape(startX * side, startY * side, (side / 2));
        startLocation.setColor(Color.green);
        startLocation.setFillColor(Color.green);
        add(startLocation);

        float goalX = maze.getGoalLocation().x();
        goalX += .5;
        float goalY = maze.getGoalLocation().y();
        goalY += .5;
        goalLocation = new OvalShape(goalX * side, goalY * side, (side / 2));
        goalLocation.setColor(Color.red);
        goalLocation.setFillColor(Color.red);
        add(goalLocation);
    }


    // ----------------------------------------------------------
    /**
     * Called when the screen is touched. Calls the editCell() method on
     * whichever cell was touched.
     *
     * @param x
     *            the x coordinate of the touch
     * @param y
     *            the y coordinate of the touch
     */
    public void onTouchDown(float x, float y)
    {
        int xPos = (int)(x / side);
        int yPos = (int)(y / side);
        editCell(xPos, yPos);
    }


    /**
     * Called when the screen is swiped across. Calls the editCell() method on
     * whichever cell was swiped.
     *
     * @param x
     *            the x coordinate of the swipe
     * @param y
     *            the y coordinate of the swipe
     */
    public void onTouchMove(float x, float y)
    {
        int xPos = (int)(x / side);
        int yPos = (int)(y / side);
        editCell(xPos, yPos);
    }


    // ----------------------------------------------------------
    /**
     * Performs the appropriate action on the cell: setting start or goal
     * locations, building a wall, or breaking a wall.
     *
     * @param x
     *            the x coordinate in the maze array
     * @param y
     *            the y coordinate in the maze array
     */
    public void editCell(int x, int y)
    {
        if (isStart)
        {
            maze.setStartLocation(new Location(x, y));
            float startX = x;
            startX += .5;
            float startY = y;
            startY += .5;
            startLocation.setPosition(startX * side, startY * side);
        }
        else if (isGoal)
        {
            maze.setGoalLocation(new Location(x, y));
            float goalX = x;
            goalX += .5;
            float goalY = y;
            goalY += .5;
            goalLocation.setPosition(goalX * side, goalY * side);
        }
        else if (buildWalls)
        {
            maze.setCell(new Location(x, y), MazeCell.WALL);
        }
        else
        {
            maze.setCell(new Location(x, y), MazeCell.UNEXPLORED);
        }
        setCellColor(x, y);
    }


    // ----------------------------------------------------------
    /**
     * Sets the color of a newly drawn wall or newly cleared cell.
     *
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public void setCellColor(int x, int y)
    {
        if (maze.getCell(new Location(x, y)).equals(MazeCell.WALL))
        {
            mazeArray[x][y].setFillColor(Color.white);
        }
        if (maze.getCell(new Location(x, y)).equals(MazeCell.UNEXPLORED))
        {
            mazeArray[x][y].setFillColor(Color.black);
        }
    }


    // ----------------------------------------------------------
    /**
     * Puts the screen into wall drawing mode.
     */
    public void drawWallsClicked()
    {
        buildWalls = true;
        isStart = false;
        isGoal = false;
    }


    // ----------------------------------------------------------
    /**
     * Puts the screen into wall breaking mode.
     */
    public void eraseWallsClicked()
    {
        buildWalls = false;
        isStart = false;
        isGoal = false;
    }


    // ----------------------------------------------------------
    /**
     * Puts the screen into start location setting mode.
     */
    public void setStartClicked()
    {
        buildWalls = false;
        isStart = true;
        isGoal = false;
    }


    // ----------------------------------------------------------
    /**
     * Puts the screen into goal location setting mode.
     */
    public void setGoalClicked()
    {
        buildWalls = false;
        isGoal = true;
        isStart = false;

    }


    // ----------------------------------------------------------
    /**
     * Computes a solution and displays it in the infoLabel. After solution is
     * computed, cells are changed to the appropriate colors. If there is no
     * solution, the infoLabel will say "No solution was possible."
     */
    public void solveClicked()
    {
        solution = maze.solve();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Location loc = new Location(i, j);
                if (maze.getCell(loc).equals(MazeCell.FAILED_PATH))
                {
                    mazeArray[i][j].setFillColor(Color.gray);
                }
                else if (maze.getCell(loc).equals(MazeCell.CURRENT_PATH))
                {
                    mazeArray[i][j].setFillColor(Color.blue);
                }
            }
        }
        if (solution == null)
        {
            infoLabel.setText("No solution was possible.");
        }
        else
        {
            infoLabel.setText(solution);
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the maze.
     *
     * @return the maze
     */
    public Maze getMaze()
    {
        return maze;
    }


    // ----------------------------------------------------------
    /**
     * Returns a string containing the solution as calculated by solve()
     *
     * @return the solution
     */
    public String getSolution()
    {
        return solution;
    }
}
