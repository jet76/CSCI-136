/*************************************************************************
 *  YOU DO NOT NEED TO MODIFY THIS FILE
 *
 *  Compilation:  javac TSPTimer.java
 *  Execution:    java TSPTimer N
 *  Dependencies: Tour.java Point.java
 *
 *  Time the two heuristics by generated random instances of size N.
 *
 *  % java TSPTimer N
 *
 *************************************************************************/
// I modified this file anyhow
public class TSPLooper 
{
    public static void main(String[] args) 
    {
        final int SIZE = 600;
        final int MODE = Integer.parseInt(args[0]);
        int N = 1000;
        double elapsed1;
        double elapsed2;
        if (MODE == 0 )
        {
            do
            {
                System.out.println(N);
                // generate data and run nearest insertion heuristic
                Stopwatch timer1 = new Stopwatch();
                Tour tour1 = new Tour();
                for (int i = 0; i < N; i++) 
                {
                    double x = Math.random() * SIZE;
                    double y = Math.random() * SIZE;
                    Point p = new Point(x, y);
                    tour1.insertNearest(p);
                }
                elapsed1 = timer1.elapsedTime();
                System.out.println("Tour distance = " + tour1.distance());
                System.out.println("Nearest insertion:  " + elapsed1 + " seconds");
                System.out.println();
                N *= 2;
            } while (elapsed1 < 100); 
        }
        else if (MODE == 1)
        {
            do {
                System.out.println(N);
                // generate data and run smallest insertion heuristic
                Stopwatch timer2 = new Stopwatch();
                Tour tour2 = new Tour();
                for (int i = 0; i < N; i++) 
                {
                    double x = Math.random() * SIZE;
                    double y = Math.random() * SIZE;
                    Point p = new Point(x, y);
                    tour2.insertSmallest(p);
                }
                elapsed2 = timer2.elapsedTime();
                System.out.println("Tour distance = " + tour2.distance());
                System.out.println("Smallest insertion:  " + elapsed2 + " seconds");
                System.out.println();
                N *= 2;
            } while (elapsed2 < 100); 
        }
    }
}
