// Name        : Joe Turner
// Username    : jeturner
// Description : Creates cells that will be used by dungeon.java to create the dungeon map.

public class Cell
{
    String image;
    boolean passable;
    boolean hasGem;
    boolean hasKey;
    boolean empty;
    /**
     * Default constructor, used by CellDoor
     */
    public Cell()
    {
    }
    /**
     * Create a new cell representing a location in the Dungeon
     * 
     * @param image     Filename containing tile image
     * @param passable  Can the hero walk through this cell?
     *  
     */
    public Cell(String image, boolean passable)
    {
        this.image = image;
        this.passable = passable;
        this.hasGem = false;
        this.hasKey = false;
        this.empty = true;
    }
    /**
     * Draw the image for this cell at the given (x, y) center.
     * If the cell contains a key or gem, draw on top of the 
     * background tile.
     *  
     * @param x  x-position for drawing
     * @param y  y-position for drawing
     */
    public void draw(double x, double y)
    {
        StdDraw.picture(x, y, this.image);
        if (this.hasKey == true) StdDraw.picture(x, y, "key.png");
        if (this.hasGem == true) StdDraw.picture(x, y, "gem.png");
    }
    /**
     * Add a key to this cell 
     */
    public void addKey()
    {
        this.hasKey = true;
        this.empty = false;
    }
    /**
     * Add a gem to this cell 
     */
    public void addGem()
    {	
        this.hasGem = true;
        this.empty = false;
    }
    /**
     * Checks to see if this cell is empty, that is it has
     * the potential to store a key or gem.
     * 
     * @return  true or false
     */
    public boolean empty()
    {
        return this.empty;
    }
    /**
     * Attempt to move the Hero to this location, taking any goodies
     * that are stored at this cell.
     * 
     * @param hero  The Hero object that is trying to move to this cell
     * @return      true if the Hero can validly move here
     */
    public boolean attemptMove(Hero hero)
    {
        if (this.passable)
        {
            hero.walksound.play();
            if (!this.empty)
            {
                if (this.hasKey) 
                {
                    this.hasKey = false;
                    hero.takeKey();
                }
                if (this.hasGem)
                {
                    this.hasGem = false;
                    hero.takeGem();
                }
            }
            return true;
        }
        else
        {
            hero.wallsound.play();
            return false;
        }
    }
    /**
     * Test out some of the methods in the Cell class
     */
    public static void main(String [] args)
    {

        // Draw a wall 
        Cell cell1 = new Cell("dirt.png", false);		
        cell1.draw(0.1, 0.5);

        // Draw an empty passage, then add a gem and draw again
        Cell cell2 = new Cell("stone.png", true);		
        cell2.draw(0.2, 0.5);
        if (cell2.empty())
            cell2.addGem();
        cell2.draw(0.3, 0.5);

        // Create a passage, add a key and then draw
        Cell cell3 = new Cell("stone.png", true);		
        if (cell3.empty())
            cell3.addKey();
        cell3.draw(0.4, 0.5);		
    }
}
