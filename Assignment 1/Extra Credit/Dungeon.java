// Name        : Joe Turner
// Username    : jeturner
// Description : Creates a dungeon and populates it with keys and gems

public class Dungeon
{
    int tileSize;
    int width;
    int height;
    int x;
    int y;
    Cell [][] map;
    Cell cell = null;
    /**
     * Construct a new dungeon of the specified size.
     * The dungeon starts out completely empty.
     * Creator must later call set() to initialize each Cell.
     * 
     * @param width     How many cells wide the dungeon is
     * @param height    How many cells tall the dungeon is
     * @param tileSize  Size of tiles in pixels
     */
    public Dungeon(int width, int height, int tileSize)
    {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        map = new Cell[width][height];
    }
    /**
     * Setup a given grid location based on a string
     * that represents the type of thing at location.
     * 
     * "W" = wall, image "dirt.png"
     * "S" = secret passage, image "stone.png"
     * "D" = door, image "door_closed.png", "door_open.png"
     * "-" = dirt floor, image "stone.png"
     * 
     * @param cellX  x index of the cell 
     * @param cellY  y index of the cell
     * @param str    letter code from the dungeon file
     */
    public void set(int cellX, int cellY, String str)
    {
        if (str == "W") cell = new Cell("dirt.png", false);
        else if (str == "S") cell = new Cell("dirt.png", true);
        else if (str == "D") cell = new CellDoor("door_closed.png", "door_open.png");
        else if (str == "-") cell = new Cell("stone.png", true);
        else if (str == "R") cell = new CellLava("lava.png", true);
        else if (str == "B") cell = new Cell("dirt0.png", true);
        else if (str == "Y") cell = new Cell("rock.png", false);
        else if (str == "U") cell = new CellWater("water.png", true);
        map[cellX][cellY] = cell;
    }	
    /**
     * Add a key to a random location in the dungeon.
     * The location must be valid, that it it should not
     * be a wall, a door, already have a key or gem, or
     * be the hero's starting location.
     */
    public void addKey()
    {
        do {
            x = StdRandom.uniform(0, width);
            y = StdRandom.uniform(0, height);
            cell = map[x][y];
        } while ((!cell.passable || !cell.empty) || cell == map[1][1]);
        cell.addKey();
    }	
    /**
     * Add a gem to a random location in the dungeon.
     * The location must be valid, that it it should not
     * be a wall, a door, already have a key or gem, or
     * be the hero's starting location.
     */
    public void addGem()
    {
        do {
            x = StdRandom.uniform(0, width);
            y = StdRandom.uniform(0, height);
            cell = map[x][y];
        } while ((!cell.passable || !cell.empty) || cell == map[1][1]);
        cell.addGem();	
    }
    /**
     * Attempt to move the hero to the given cell location.
     * 
     * @param cellX  Proposed new cell x-location of hero 
     * @param cellY  Proposed new cell y-location of hero
     * @param hero   Reference to the Hero object
     * @return       true if move worked, false otherwise
     */
    public boolean attemptMove(int cellX, int cellY, Hero hero)
    {
        cell = map[cellX][cellY];
        if (cell.attemptMove(hero)) return true;
        else return false;
    }	
    /**
     * Draw all the cell tiles that make up the dungeon.
     */
    public void draw()
    {		
        int x = tileSize / 2;
        int y = tileSize / 2;
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                cell = map[j][i];
                cell.draw(x, y);
                x += tileSize;
            }
            x = tileSize / 2;
            y += tileSize;
        }
    }	
    /**
     * Test out some of the methods in the Dungeon class
     */
    public static void main(String [] args)
    {
        // Setup a fake 4 x 3 dungeon with 32 pixel tiles
        final int TILE_SIZE = 32;		
        Dungeon dungeon = new Dungeon(4, 3, TILE_SIZE);
        StdDraw.setCanvasSize(4 * TILE_SIZE, 3 * TILE_SIZE);
        StdDraw.setXscale(0, 4 * TILE_SIZE);
        StdDraw.setYscale(0, 3 * TILE_SIZE);

        // Create a dungeon like:
        //  W--W
        //  W---
        //  WDW-
        dungeon.set(0, 0, "W");
        dungeon.set(1, 0, "D");
        dungeon.set(2, 0, "W");
        dungeon.set(3, 0, "-");

        dungeon.set(0, 1, "W");
        dungeon.set(1, 1, "-");
        dungeon.set(2, 1, "-");
        dungeon.set(3, 1, "-");

        dungeon.set(0, 2, "W");
        dungeon.set(1, 2, "-");
        dungeon.set(2, 2, "-");
        dungeon.set(3, 2, "W");

        // Add one key and one gem in a random location
        dungeon.addKey();
        dungeon.addGem();

        // Draw the sample dungeon
        dungeon.draw();
    }	
}
