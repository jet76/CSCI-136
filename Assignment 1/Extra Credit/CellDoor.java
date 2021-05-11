// Name        : Joe Turner
// Username    : jeturner
// Description : Creates special cells that are doors and will be used by dungeon.java to create the dungeon map.

public class CellDoor extends Cell
{
    String imageClosed;
    String imageOpen;
    /**
     * Create a new Cell object that represents a door.
     * The doors starts as locked and only becomes open if a
     * Hero attempts to move into it and the Hero has a key.
     * 
     * @param imageClosed  Image of a closed door
     * @param imageOpen    Image of an open door
     */
    public CellDoor(String imageClosed, String imageOpen)
    {
        super();
        this.imageClosed = imageClosed;
        this.imageOpen = imageOpen;
        this.passable = false;
    }
    /**
     * Draw the door, the door shows up as either closed or
     * open depending on its state.
     */
    public void draw(double x, double y)
    {
        if (this.passable == true) StdDraw.picture(x, y, imageOpen);
        else StdDraw.picture(x, y, imageClosed);
    }
    /**
     * Open the door 
     */
    public void open()
    {
        this.passable = true;
    }
    /**
     * Attempt to move the hero to this door location.
     * This will always work if the door is already open.
     * If it is still closed, the hero has to have a key
     * available for use in order to open the door.
     * 
     * @return  true if Hero made it through, false otherwise
     */
    public boolean attemptMove(Hero hero)
    {
        if (this.passable)
        {
            return true;
        }
        else if (hero.getNumKeys() > 0)
        {
            hero.useKey();
            this.open();
            return true;
        }
        else 
        {
            System.out.println("You need a key for the door!");
            return false;
        }
    }
    /**
     * Test out some of the methods in the CellDoor class
     */
    public static void main(String [] args)
    {
        // Set a small window
        StdDraw.setCanvasSize(256, 64);

        // Draw a wall 
        CellDoor door1 = new CellDoor("door_closed.png", "door_open.png");		
        door1.draw(0.1, 0.5);

        // Open the door and draw again
        door1.open();
        door1.draw(0.3, 0.5);
    }
}
