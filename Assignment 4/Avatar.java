/*************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : Every Ultima needs an Avatar 
 *************************************************************************/
public class Avatar 
{
    int x;
    int y;
    double radius = 4.0;
    final double INCREMENT = 0.5;
    final double MIN = 2.0;
    Avatar(int x, int y)      // Create a new Avatar at index position (x,y)
    {
        this.x = x;
        this.y = y;
    }
    int getX()                    // Get the current x-position of the Avatar
    {
        return x;
    }
    int getY()                    // Get the current y-position of the Avatar
    {
        return y;
    }
    void setLocation(int x, int y) // Update the position of the Avatar to index (x,y)
    {
        this.x = x;
        this.y = y;
    }
    double getTorchRadius()          // Get the current torch radius
    {
        return radius;
    }
    void increaseTorch()           // Increase torch radius by 0.5
    {
        radius += INCREMENT; 
    }
    void decreaseTorch()           // Decrease torch radius by 0.5, minimum is 2.0
    {
        if (radius - 0.5 < MIN) radius = MIN;
        else radius -= 0.5;
    }
    void draw()                    // Draw at the current position
    {
        StdDraw.picture((x * 16) + 8, (y * 16) + 8, "avatar.gif");
    }
    // Test method
    public static void main(String [] args)
    {
        Avatar avatar = new Avatar(5, 5);
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
