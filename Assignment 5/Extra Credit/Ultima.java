import java.awt.Font;

/*************************************************************************
 * Name        : Keith Vertanen 
 * Username    : kvertanen
 * Description : Ultima 0.1, the main game loop 
 *************************************************************************/
public class Ultima
{	
	public static void main(String [] args)
	{
		if (args.length <= 0)
		{
			System.out.println("Must specify a level file!");
			return;
		}
		final int SLEEP_MS = 100;
		
		World world = new World(args[0]);
				
		StdDraw.show(0);
		world.draw();
		
		// Keep looping as long as avatar hasn't died
		while (world.avatarAlive() && world.getNumMonsters() > 0)
		{
			if (StdDraw.hasNextKeyTyped())
            {				
                char ch = StdDraw.nextKeyTyped();
                world.handleKey(ch);
            }
            
			// Redraw everything and then sleep for a bit
			StdDraw.clear();
			world.draw();
			StdDraw.show(SLEEP_MS);			
		}		
		
		if (world.getNumMonsters() == 0)
			System.out.println("You win!");
		else
			System.out.println("You lost!");
	}
}
