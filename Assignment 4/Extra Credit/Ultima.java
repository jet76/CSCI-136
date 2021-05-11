/*************************************************************************
 * Name        : Keith Vertanen 
 * Username    : kvertanen
 * Description : Ultima 0, the main game loop 
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
		World world = new World(args[0]);
				
		StdDraw.show(0);
		world.draw();
		while (true)
		{
			if (StdDraw.hasNextKeyTyped())
            {				
                char ch = StdDraw.nextKeyTyped();
                world.handleKey(ch);
                world.draw();
            }
			else
				StdDraw.show(100);
		}		
	}
}
