// Name        : Joe Turner
// Username    : jeturner
// Description : Creates water cells and will be used by dungeon.java to create the dungeon map.

public class CellWater extends Cell
{
    /**
     * Create a new Cell object that represents water.
     */
    public CellWater(String image, boolean passable)
    {
        super(image, passable);
    }
    /**
     * Attempt to move the hero to this lava location.
     * Taking damage in the process.
     * 
     * @return  true if Hero made it through, false otherwise
     */
    public boolean attemptMove(Hero hero)
    {
        if (this.passable)
        {
            hero.watersound.play();
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
            return false;
        }
    }
    /**
     * Test out some of the methods in the CellLava class
     */
    public static void main(String [] args)
    {
        // Set a small window
        StdDraw.setCanvasSize(256, 64);

        // Draw a wall 
        CellWater lava = new CellWater("water.png", false);		
        lava.draw(0.1, 0.5);
        lava.draw(0.3, 0.5);
    }
}
