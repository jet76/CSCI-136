// Name        : Joe Turner
// Username    : jeturner
// Description : Creates lava cells that burn the hero and will be used by dungeon.java to create the dungeon map.

public class CellLava extends Cell
{
    /**
     * Create a new Cell object that represents lava.
     * The lava will deal damage to the hero when 
     * the hero enters the cell
     */
    public CellLava(String image, boolean passable)
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
            hero.takeDmg(5);
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
        CellLava lava = new CellLava("lava.png", false);		
        lava.draw(0.1, 0.5);
        lava.draw(0.3, 0.5);
    }
}
