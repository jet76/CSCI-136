/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : Creates monsters to fill the legendary world of Britania 
 *************************************************************************/
import java.awt.Color;
import java.awt.Font;

public class Monster implements Runnable
{
    final int SIZE = 16;
    final int HALFSIZE = SIZE / 2;
    private World world;
    private String image;
    private int x;
    private int y;
    private int damage;
    private int hp;
    private int sleepMs;
    private Stats stats;
    Monster(World world, String code, int x, int y, int hp, int damage, int sleepMs)  
    {
        this.world = world;
        if (code.equals("SK")) image = "skeleton.gif";
        else if (code.equals("OR")) image = "orc.gif";
        else if(code.equals("SL")) image = "slime.gif";
        else if (code.equals("BA")) image = "bat.gif";
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.sleepMs = sleepMs;
    }
    public synchronized void incurDamage(int damage)      // Reduce this monster's hit points by the amount damage
    {
        if (damage > 0)
        {
            hp -= damage;
            if (stats == null) stats = new Stats();
            else stats.reset();
        }
    }
    public synchronized void draw()                       // Draw the monster
    {
        int x = (this.x * SIZE) + HALFSIZE;
        int y = (this.y * SIZE) + HALFSIZE;
        StdDraw.picture(x, y, image);
        if (stats != null && stats.elapsedTime() < 3.0)
        {
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 12));
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(x, y, "" + hp);
        }
    }
    public synchronized int getHitPoints()               // Get the number of remaining hit points of this monster
    {
        return hp;
    }
    public synchronized int getDamage()                  // Get how much damage this monster causes
    {
        return damage;
    }
    public synchronized int getX()                       // Get current x-position of this monster
    {
        return x;
    }
    public synchronized int getY()                       // Get current y-position of this monster
    {
        return y;
    }
    public synchronized void setLocation(int x, int y)    // Move this monster to a new location
    {
        this.x = x;
        this.y = y;
    }
    public void run()                        // Worker thread that periodically moves this monster
    {
        while (hp > 0) 
        {
            //System.out.println(hp);
            try
            {
                Thread.sleep(sleepMs);
               // System.out.println("Sleeping");
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int direction = (int) (Math.random() * 4);
            int newX = this.x;
            int newY = this.y;
            if (direction == 0) newY += 1;
            else if (direction == 1) newX +=1;
            else if (direction == 2) newY -=1;
            else if (direction == 3) newX -=1;
            //System.out.println(this + " from (" + this.x + ", " + this.y + ") to (" + newX + ", " + newY + ") " + this.sleepMs);
            world.monsterMove(newX, newY, this);            
        }
    }
}
