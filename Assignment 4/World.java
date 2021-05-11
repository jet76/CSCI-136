import java.io.File;
import java.util.Scanner;

/*************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : Creates the legendary world of Sosaria 
 *************************************************************************/

public class World
{
    final static int SIZE = 16;
    static int width;
    static int height;
    int count;
    Avatar avatar;
    Tile [][] map;
    Tile tile;
    World(String filename)        // Load the tiles and Avatar based on the given filename
    {
        try
        {
            Scanner scan = new Scanner(new File(filename));
            width = scan.nextInt();
            height = scan.nextInt();
            map = new Tile[width][height];
            avatar = new Avatar(scan.nextInt(), scan.nextInt());
            StdDraw.setCanvasSize(width * SIZE, height * SIZE);
            StdDraw.setXscale(0.0, width * SIZE);
            StdDraw.setYscale(0.0, height * SIZE);
            for (int i = height - 1; i > -1; i--)
            {
                for (int j = 0; j < width; j++)
                {
                    Tile tile = new Tile(scan.next());
                    map[j][i] = tile;
                }
            }
            scan.close();
        }
        catch (Exception ex)
        {
            System.out.println();
        }
    }
    void handleKey(char ch)            // Handle a keypress from the main game program
    {
        int x = avatar.getX();
        int y = avatar.getY();
        switch (ch)
        {
            case 'w':
                y++;
                if (y != height && map[x][y].passable) avatar.setLocation(x, y);
                break;
            case 'a':
                x--;
                if (x != -1 && map[x][y].passable) avatar.setLocation(x, y);
                break;        
            case 's':
                y--;
                if (y != -1 && map[x][y].passable) avatar.setLocation(x, y);            
                break;        
            case 'd':
                x++;
                if (x != width && map[x][y].passable) avatar.setLocation(x, y);
                break;        
            case '+':
                avatar.increaseTorch();
                break; 
            case '-': 
                avatar.decreaseTorch();
                break;        
        }        
    }
    void draw()                        // Draw all the tiles and the Avatar
    {
        light(avatar.getX(), avatar.getY(), avatar.getTorchRadius());
        for (int i = height - 1; i > -1; i--)
            for (int j = 0; j < width; j++)
                map[j][i].draw(j, i);
        avatar.draw();
    }
    int light(int x, int y, double r) // Set the tiles that are lit based on an initial position
    // (x, y) and a torch radius of r. Returns the number of tiles
    // that were lit up by the algorithm.
    {
        for (int i = height - 1; i > -1; i--)
            for (int j = 0; j < width; j++)
                map[j][i].setLit(false);
        count = 0;
        count = lightDFS(x, y, x, y, r);
        return count;
    }
    private int lightDFS(int x, int y, int currentX, int currentY, double r)
    {
        if (currentX < 0 || currentX > width - 1 || currentY < 0 || currentY > height - 1) return count;
        else tile = map[currentX][currentY];
        if (tile.getLit()) return count;
        else if (tile.isOpaque())
        {            
            if (tile.getLit() != true) tile.setLit(true);
            count ++;
            return count;
        }
        else if (Math.sqrt(Math.pow(currentX - x, 2) + Math.pow(currentY - y, 2)) >= r)
        {
            if (tile.getLit()) tile.setLit(false);
            return count;
        }
        tile.setLit(true);
        count ++;
        lightDFS(x, y, currentX, currentY + 1, r);
        lightDFS(x, y, currentX, currentY - 1, r);
        lightDFS(x, y, currentX + 1, currentY, r);
        lightDFS(x, y, currentX - 1, currentY, r);
        return count;
    }
    // Test method	
    public static void main(String [] args)
    {        
        World world = new World(args[0]);
        world.draw();		
    }	
}
