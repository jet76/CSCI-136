import java.util.ArrayList;


public class Moongate implements Runnable
{
    final static int SIZE = 16;
    final static int HALFSIZE = SIZE / 2;
    final int sleepMS = 10;
    private String image = "grasslands.gif";
    private int x;
    private int y;
    private int moveX;
    private int moveY;
    private boolean open = false;
    private boolean spawned = false;
    private double spawnTime;
    private double interval;
    private Stats stats;
    public Moongate(int x, int y, int moveX, int moveY, double spawnTime, double interval)
    {
        this.x = x; 
        this.y = y; 
        this.moveX = moveX; 
        this.moveY = moveY; 
        this.spawnTime = spawnTime; 
        this.interval = interval;
        this.stats = new Stats();
    }

    public boolean isOpen()
    {
        return open;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public int getMoveX()
    {
        return moveX;
    }

    public int getMoveY()
    {
        return moveY;
    }

    public synchronized void draw()
    {        
        int x = (this.x * SIZE) + HALFSIZE;
        int y = (this.y * SIZE) + HALFSIZE;
        StdDraw.picture(x, y, image);
    }

    public void run()
    {
        // TODO Auto-generated method stub
        while (true)
        {
            try
            {
                Thread.sleep(sleepMS);            
            }
            catch (Exception ex)
            {
                System.out.println("Error!");
            }
            if (!spawned && stats.elapsedTime() >= spawnTime)
            {
                spawned = true;
                open = true;
                stats.reset();
            }
            if (spawned && open)
            {
                if (stats.elapsedTime() <= 0.1) image = "moongate1.gif";
                else if (stats.elapsedTime() <= 0.2) image = "moongate2.gif";
                else if (stats.elapsedTime() <= 0.3) image = "moongate3.gif";
                else if (stats.elapsedTime() <= 5.7) image = "moongate4.gif";
                else if (stats.elapsedTime() <= 5.8) image = "moongate3.gif";
                else if (stats.elapsedTime() <= 5.9) image = "moongate2.gif";
                else if (stats.elapsedTime() <= 6.0) image = "moongate1.gif";
                else open = false; 
            }
            else 
            {
                image = "grasslands.gif";
                if (stats.elapsedTime() >= interval && spawned)
                {
                    open = true;
                    stats.reset();
                }
            }
        }
    }
    // Test method
    public static void main(String [] args)
    {
        int columns = 5;
        int rows = 5;
        // int count = columns * rows;
        int width = columns * SIZE;
        int height = rows * SIZE;
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0.0, width);
        StdDraw.setYscale(0.0, height);
        ArrayList<Moongate> moongates = new ArrayList<Moongate>();
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                moongates.add(new Moongate(i, j, j, i, i * 2 + j * 3, 20));
            }
        }
        Thread [] threads = new Thread[moongates.size()];
        try
        {
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new Thread(moongates.get(i));
                threads[i].start();
            }
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: Threading failed!");
        }
        while (true)
        {
            for (int i = 0; i < moongates.size(); i++)
            {
                Moongate gate = moongates.get(i);
                gate.draw();
            }
            StdDraw.show(10);
        }
    }
}
