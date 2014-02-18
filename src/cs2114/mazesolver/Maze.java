package cs2114.mazesolver;

// -------------------------------------------------------------------------
/**
 * This class represents the maze. The most important feature is the
 * two-dimensional array that holds MazeCell objects.
 *
 * @author Benjamin Robohn (brobohn)
 * @version 2013.09.13
 */

public class Maze
    implements IMaze
{
    private MazeCell[][]            maz;
    private int                     size;
    private ILocation               start;
    private ILocation               goal;
    private SolutionStack<Location> soln;                       // the
// solution stack
    private boolean                 canBeSolved = true;
    private Explorer                exp         = Explorer.EAST; // represents
// a person moving around
                                                                 // the maze


    // ----------------------------------------------------------
    /**
     * Create a new Maze object.
     *
     * @param num
     *            the side length of the square maze
     */
    public Maze(int num)
    {
        size = num;
        maz = new MazeCell[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                Location loc = new Location(i, j);
                this.setCell(loc, MazeCell.UNEXPLORED);
            }
        }
        Location sta = new Location(0, 0);
        setStartLocation(sta);
        Location goa = new Location(size - 1, size - 1);
        setGoalLocation(goa);
    }


    // ----------------------------------------------------------
    @Override
    public MazeCell getCell(ILocation loc)
    {
        int xPos = loc.x();
        int yPos = loc.y();
        int siz = size();

        if (xPos < 0 || yPos < 0 || xPos >= siz || yPos >= siz)
        {
            return MazeCell.INVALID_CELL;
        }
        return maz[loc.x()][loc.y()];
    }


    // ----------------------------------------------------------
    @Override
    public ILocation getGoalLocation()
    {
        return goal;
    }


    // ----------------------------------------------------------
    @Override
    public ILocation getStartLocation()
    {
        return start;
    }


    // ----------------------------------------------------------
    @Override
    public void setCell(ILocation loc, MazeCell cel)
    {
        if ((!((loc.equals(getStartLocation())
            || loc.equals(getGoalLocation()))
            && cel.equals(MazeCell.WALL)))
            && (getCell(loc) != MazeCell.INVALID_CELL))
        {
            maz[loc.x()][loc.y()] = cel;
        }
    }


    // ----------------------------------------------------------
    @Override
    public void setGoalLocation(ILocation loc)
    {
        if (!getCell(loc).equals(MazeCell.INVALID_CELL))
        {
            goal = new Location(loc.x(), loc.y());
            if (getCell(goal) == MazeCell.WALL)
            {
                setCell(goal, MazeCell.UNEXPLORED);
            }
        }
    }


    // ----------------------------------------------------------
    @Override
    public void setStartLocation(ILocation loc)
    {
        if (!getCell(loc).equals(MazeCell.INVALID_CELL))
        {
            start = new Location(loc.x(), loc.y());
            if (getCell(start) == MazeCell.WALL)
            {
                setCell(start, MazeCell.UNEXPLORED);
            }
        }
    }


    // ----------------------------------------------------------
    @Override
    public int size()
    {
        return size;
    }


    // ----------------------------------------------------------
    @Override
    public String solve()
    {
        soln = new SolutionStack<Location>();
        soln.push(getStartLocation());
        setCell(getStartLocation(), MazeCell.CURRENT_PATH);

        Location loc0 =
            new Location(getStartLocation().x() + 1, getStartLocation().y());
        Location loc1 =
            new Location(getStartLocation().x(), getStartLocation().y() + 1);
        Location loc2 =
            new Location(getStartLocation().x() - 1, getStartLocation().y());
        Location loc3 =
            new Location(getStartLocation().x(), getStartLocation().y() - 1);

        // Checks to make sure the start location is not enclosed.
        canBeSolved =
            (getCell(loc0).equals(MazeCell.UNEXPLORED)
                || getCell(loc1).equals(MazeCell.UNEXPLORED)
                || getCell(loc2).equals(MazeCell.UNEXPLORED)
                || getCell(loc3).equals(MazeCell.UNEXPLORED));

        while (canBeSolved && !soln.top().equals(getGoalLocation()))
        {
            move();
        }

        if (!canBeSolved)
        {
            return null;
        }

        StringBuilder steps = new StringBuilder();
        while (!soln.isEmpty())
        {
            steps.insert(0, soln.top().toString());
            soln.pop();
        }
        return steps.toString().trim();
    }


    /**
     * Sets the explorers direction to the next direction clockwise.
     */
    public void turnRight()
    {
        if (exp.equals(Explorer.NORTH))
        {
            exp = Explorer.EAST;
        }
        else if (exp.equals(Explorer.EAST))
        {
            exp = Explorer.SOUTH;
        }
        else if (exp.equals(Explorer.SOUTH))
        {
            exp = Explorer.WEST;
        }
        else
        {
            exp = Explorer.NORTH;
        }
    }


    /**
     * Sets the explorers direction to the next direction counter-clockwise.
     */
    public void turnLeft()
    {
        turnRight();
        turnRight();
        turnRight();
    }


    /**
     * Finds and moves to the best valid step.
     */
    public void move()
    {
        Location loc = new Location(soln.top().x(), soln.top().y());

        if (getCell(loc.right(exp)).equals(MazeCell.UNEXPLORED))
        /*
         * If the cell to the right is unexplored, it is added to the stack and
         * marked as the current path, and the explorer turns clockwise.
         */
        {
            soln.push(loc.right(exp));
            setCell(soln.top(), MazeCell.CURRENT_PATH);
            turnRight();
        }
        else if (loc.ahead(exp).equals(getStartLocation()))
        /*
         * If the next cell is the start location, the solution has looped, but
         * a solution may still be possible.
         */
        {
            setCell(soln.top(), MazeCell.FAILED_PATH);
            soln.pop();

            /*
             * If none of the cells surrounding the start location are
             * unexplored, then the maze cannot be solved.
             */
            checkForFailure();
        }
        else if (getCell(loc.ahead(exp)).equals(MazeCell.CURRENT_PATH))
        /*
         * If the cell ahead is part of the solution, the explorer is turning
         * back on itself. The top cell is set as a failed path and removed from
         * the solution.
         */
        {
            setCell(soln.top(), MazeCell.FAILED_PATH);
            soln.pop();

            if (getCell(((Location)soln.top()).right(exp)).equals(
                MazeCell.CURRENT_PATH))
            /*
             * If we are coming out of a dead end, we need to follow the trail
             * of current paths. The rest of the loop will do this by itself,
             * unless we need to turn right to get out of the dead end. This
             * statement will decide if we need to turn right to get out, and
             * the rest of the decision tree will continue as normal. Note that
             * the variable loc no longer refers to the top element of the
             * solution stack, but instead to the location that has just been
             * removed, the cell the explorer is now on must be referenced by
             * soln.top()
             */
            {
                turnRight();
            }
        }
        else if (getCell(loc.ahead(exp)).equals(MazeCell.UNEXPLORED))
        /*
         * If the cell ahead of the explorer is unexplored, it is added to the
         * solution stack.
         */
        {
            soln.push(loc.ahead(exp));
            setCell(soln.top(), MazeCell.CURRENT_PATH);
        }
        else
        /*
         * If none of the previous conditions are true (meaning we cannot turn
         * right, the cell directly ahead is either a wall, a failed cell, or is
         * outside the bounds of the maze) then we turn left and restart the
         * decision tree.
         */
        {
            turnLeft();
        }
    }


    /**
     * Sets the field canBeSolved to false if the maze has no solution. The maze
     * cannot be solved if the start location is the only element in the stack
     * and there are no valid moves to make.
     */
    public void checkForFailure()
    {
        Location loc0 =
            new Location(getStartLocation().x() + 1, getStartLocation().y());
        Location loc1 =
            new Location(getStartLocation().x(), getStartLocation().y() + 1);
        Location loc2 =
            new Location(getStartLocation().x() - 1, getStartLocation().y());
        Location loc3 =
            new Location(getStartLocation().x(), getStartLocation().y() - 1);

        if (soln.size() == 1)
        /*
         * If the only element of the stack is the start location, the we must
         * check to see if there are any unexplored cells adjacent to it. If
         * not, then the right-hand rule led the solution back to the start,
         * meaning there is no solution.
         */
        {
            canBeSolved =
                (getCell(loc0).equals(MazeCell.UNEXPLORED)
                    || getCell(loc1).equals(MazeCell.UNEXPLORED)
                    || getCell(loc2).equals(MazeCell.UNEXPLORED)
                    || getCell(loc3).equals(MazeCell.UNEXPLORED));
        }
    }


    /**
     * Loads a board based on the strings given. For testing. O = UNEXPLORED W =
     * WALL
     *
     * @param board
     *            the rows given to build the board
     */
    public void loadBoardState(String... board)
    {
        String[] rows = board;

        for (int i = 0; i < rows.length; i++)
        {
            for (int j = 0; j < rows.length; j++)
            {
                if (rows[j].charAt(i) == 'W')
                {
                    ILocation loc = new Location(i, j);
                    setCell(loc, MazeCell.WALL);
                }
            }
        }
    }
}
