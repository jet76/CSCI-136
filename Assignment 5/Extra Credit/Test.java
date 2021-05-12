
public class Test
{
    final static int SIZE = 16;
    final int HALFSIZE = SIZE / 2;
    
    public static void main(String [] args)
    {
        StdDraw.setCanvasSize(10 * SIZE, 10 * SIZE);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        Moongate g1 = new Moongate(0, 0, 1, 1, 10, 20);
        Moongate g2 = new Moongate(1, 1, 0, 0, 20, 20);
        try
        {
        Thread t1 = new Thread(g1);
        Thread t2 = new Thread(g2);
        t1.start();
        t2.start();
        }
        catch (Exception ex)
        {
            System.out.println("Whoops!");
        }
        while (true)
        {
            g1.draw();
            g2.draw();
            StdDraw.show(10);
        }
    }
}
