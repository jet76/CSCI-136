import java.awt.Color;
import java.awt.Font;

/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : Every Ultima needs an Avatar 
 *************************************************************************/
public class Avatar 
{
    final int SIZE = 16;
    final int HALFSIZE = SIZE / 2;
    private int x;
    private int y;
    private double radius;
    private int hp;
    private int damage;
    final double INCREMENT = 0.5;
    final double MIN = 2.0;
    Stats stats;
    Avatar(int x, int y, int hp, int damage, double torch)       // Create a new Avatar at index position (x,y)
    {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.damage = damage;
        this.radius = torch;
    }
    public synchronized int getX()                    // Get the current x-position of the Avatar
    {
        return x;
    }
    public synchronized int getY()                    // Get the current y-position of the Avatar
    {
        return y;
    }
    public synchronized void setLocation(int x, int y) // Update the position of the Avatar to index (x,y)
    {
        this.x = x;
        this.y = y;
    }
    public synchronized double getTorchRadius()          // Get the current torch radius
    {
        return radius;
    }
    public synchronized void increaseTorch()           // Increase torch radius by 0.5
    {
        radius += INCREMENT; 
    }
    public synchronized void decreaseTorch()           // Decrease torch radius by 0.5, minimum is 2.0
    {
        if (radius - 0.5 < MIN) radius = MIN;
        else radius -= 0.5;
    }
    public synchronized void draw()                    // Draw at the current position
    {
        int x = (this.x * SIZE) + HALFSIZE;
        int y = (this.y * SIZE) + HALFSIZE;
        StdDraw.picture(x, y, "avatar.gif");
        if (stats != null && stats.elapsedTime() < 3.0)
        {
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 12));
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(x, y, "" + hp);
        }
    }
    public synchronized int getHitPoints()            // Get the number of hit points left
    {
        return hp;
    }
    public synchronized void incurDamage(int damage)   // Reduce the Avatar's hit points by the amount damage
    {
        if (damage > 0)
        {
            hp -= damage;
            if (stats == null) stats = new Stats();
            else stats.reset();
        }
    }
    public synchronized int getDamage()               // Get the amount of damage the Avatar causes monsters
    {
        return damage;
    }
    // Test method
    public static void main(String [] args)
    {
        Avatar avatar = new Avatar(5, 5, 20, 3, 100.0);
        System.out.printf("%d %d %.1f\n", avatar.getX(), avatar.getY(), avatar.getTorchRadius());		
        avatar.setLocation(1, 4);
        System.out.printf("%d %d %.1f\n", avatar.getX(), avatar.getY(), avatar.getTorchRadius());
        avatar.increaseTorch();
        System.out.printf("%d %d %.1f\n", avatar.getX(), avatar.getY(), avatar.getTorchRadius());
        for (int i = 0; i < 6; i++)
        {
            avatar.decreaseTorch();
            System.out.printf("%d %d %.1f\n", avatar.getX(), avatar.getY(), avatar.getTorchRadius());
        }	
    }
}
