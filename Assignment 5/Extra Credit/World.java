import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : Creates the legendary world of Britania 
 *************************************************************************/

public class World
{
    final static int SIZE = 16;
    static int width;
    static int height;
    private int count;
    private Avatar avatar;
    Tile [][] map;
    Tile tile;
    ArrayList<Monster> monsters = new ArrayList<Monster>();
    ArrayList<Moongate> moongates = new ArrayList<Moongate>();
    Thread [] threads;
    Thread [] gates;
    public World(String filename)        // Load the tiles and Avatar based on the given filename
    {
        try
        {
            Scanner scan = new Scanner(new File(filename));
            width = scan.nextInt();
            height = scan.nextInt();
            map = new Tile[width][height];
            avatar = new Avatar(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextDouble());
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
            // Read moongates
            int numGates = scan.nextInt();            
            for (int i = 0; i < numGates; i++)
            {
                moongates.add(new Moongate(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextDouble(), scan.nextDouble()));
            }
            while (scan.hasNext())
            {
                Monster monster = new Monster(this, scan.next(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
                monsters.add(monster);
            }
            scan.close();
            gates = new Thread[moongates.size()];
            for (int i = 0; i < gates.length; i++)
            {
                gates[i] = new Thread(moongates.get(i));
                gates[i].start();
            }
            threads = new Thread[monsters.size()];
            for (int i = 0; i < monsters.size(); i++)
            {
                threads[i] = new Thread(monsters.get(i));
                threads[i].start();
            }            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public synchronized void handleKey(char ch)            // Handle a keypress from the main game program
    {
        int x = avatar.getX();
        int y = avatar.getY();
        switch (ch)
        {
            case 'w':
                y++;
                avatarMove(x, y);
                break;
            case 'a':
                x--;
                avatarMove(x, y);
                break;        
            case 's':
                y--;
                avatarMove(x, y);            
                break;        
            case 'd':
                x++;
                avatarMove(x, y);
                break;        
            case '+':
                avatar.increaseTorch();
                break; 
            case '-': 
                avatar.decreaseTorch();
                break;        
        }        
    }
    public synchronized void draw()                        // Draw all the tiles and the Avatar
    {
        light(avatar.getX(), avatar.getY(), avatar.getTorchRadius());
        for (int i = height - 1; i > -1; i--)
            for (int j = 0; j < width; j++)
                map[j][i].draw(j, i);
        for (int i = 0; i < moongates.size(); i++)
        {
            Moongate moongate = moongates.get(i);
            tile = map[moongate.getX()][moongate.getY()];
            if (tile.getLit()) moongate.draw();
        }
        for (int i = 0; i < monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            tile = map[monster.getX()][monster.getY()];
            if (tile.getLit()) monster.draw();
        }
        avatar.draw();
    }
    private synchronized int light(int x, int y, double r) // Set the tiles that are lit based on an initial position
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
    private synchronized int lightDFS(int x, int y, int currentX, int currentY, double r)
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
    public synchronized boolean avatarAlive()                               // Is the Avatar still alive?
    {
        if (avatar.getHitPoints() < 1) return false;
        else return true;
    }
    public synchronized void monsterMove(int x, int y, Monster monster)  // Attempt to move given monster to (x, y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            tile = map[x][y];
            if (tile.isPassable())
            {
                if (x == avatar.getX() && y == avatar.getY())
                {
                    avatar.incurDamage(monster.getDamage());
                }
                else
                {
                    boolean move = true;
                    for (int i = 0; i < monsters.size(); i++)
                    {
                        if (x == monsters.get(i).getX() && y == monsters.get(i).getY()) move = false;
                    }
                    if (move) 
                    {
                        monster.setLocation(x, y);
                        monster.incurDamage(tile.getDamage());
                        if (monster.getHitPoints() < 1) 
                        {
                            monsters.remove(monster);
                        }
                    }
                }
            }
        }
    }
    public synchronized void avatarMove(int x, int y) // Attempt to move Avatar to (x, y)
    {
        if (x >= 0 && x < width && y >= 0 & y < height)
        {
            tile = map[x][y];
            if (tile.isPassable())
            {                
                boolean move = true;
                for (int i = 0; i < moongates.size(); i++)
                {
                    Moongate moongate = moongates.get(i);
                    if (moongate.isOpen() && moongate.getX() == x && moongate.getY() == y)
                    {
                        x = moongate.getMoveX();
                        y = moongate.getMoveY();
                    }
                }
                for (int i = 0; i < monsters.size(); i++)
                {
                    if (x == monsters.get(i).getX() && y == monsters.get(i).getY())
                    {
                        move = false;
                        monsters.get(i).incurDamage(avatar.getDamage());
                        if (monsters.get(i).getHitPoints() < 1)
                        {
                            monsters.remove(monsters.get(i));
                            //System.out.println(getNumMonsters());
                        }
                    }
                }
                if (move)
                {                    
                    avatar.setLocation(x, y);
                    avatar.incurDamage(tile.getDamage());
                }
            }
        }
    }
    public synchronized int getNumMonsters() // Return number of alive monsters
    {
        return monsters.size();
    }
    // Test method	
    public static void main(String [] args)
    {        
        World world = new World(args[0]);
        world.draw();		
    }	
}
