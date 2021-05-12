/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : The world of Britania will be composed of these tiles 
 *************************************************************************/
public class Tile 
{
    final int SIZE = 16;
    final int HALFSIZE = SIZE / 2;
    private String pic;
    private boolean lit;
    private boolean opaque;
    private boolean passable;
    private int damage = 0;
    public Tile(String code)        // Create a new tile based on a String code
    {
        char ch = code.charAt(0);
        opaque = false;
        passable = false;
        lit = false;
        switch (ch)
        {
            case 'B':
                pic = "brickfloor.gif";
                passable = true;
                break;
            case 'L':
                pic = "lava.gif";
                passable = true;
                damage = 1;
                break;
            case 'W':
                pic = "water.gif";
                break;
            case 'F':
                pic = "forest.gif";
                opaque = true;
                passable = true;
                break;
            case 'G':
                pic = "grasslands.gif";
                passable = true;
                break;
            case 'M':
                pic = "mountains.gif";
                opaque = true;
                break;
            case 'S':
                pic = "stonewall.gif";
                opaque = true;
                break;
        }
    }
    public boolean getLit()                 // Return whether this Tile is lit or not
    {
        return lit;
    }
    public void setLit(boolean value)    // Change the lit status of this Tile
    {
        lit = value;
    }
    public void draw(int x, int y)       // Draw at index position (x, y)
    {
        int drawX = (x * SIZE) + HALFSIZE;
        int drawY = (y * SIZE) + HALFSIZE;
        if (getLit()) StdDraw.picture(drawX, drawY, pic);
        else StdDraw.picture(drawX, drawY, "blank.gif");
    }
    public boolean isOpaque()               // Does this type of Tile block light?
    {
        return opaque;
    }
    public boolean isPassable()             // Can the Avatar walk on this Tile?
    {
        return passable;
    }
    public int getDamage()              // Return the damage caused by this type of Tile
    {
        return damage;        
    }
    // Test method
    public static void main(String [] args)
    {
        final int SIZE 		= 16;
        final int WIDTH 	= 7;
        final int HEIGHT 	= 2;

        StdDraw.setCanvasSize(WIDTH * SIZE, HEIGHT * SIZE);
        StdDraw.setXscale(0.0, WIDTH * SIZE);
        StdDraw.setYscale(0.0, HEIGHT * SIZE);

        String [] codes = {"B", "L", "W", "F", "G", "M", "S"};
        for (int i = 0; i < WIDTH; i++)
        {
            for (int j = 0; j < HEIGHT; j++)
            {
                Tile tile = new Tile(codes[i]);
                if ((i + j) % 2 == 0)
                    tile.setLit(true);
                System.out.printf("%d %d : lit %s\topaque %s\tpassable %s\n", 
                        i, j, tile.getLit(), tile.isOpaque(), tile.isPassable()); 
                tile.draw(i, j);
            }
        }		
    }	
}
