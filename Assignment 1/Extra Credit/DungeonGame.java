// Name        : Joe Turner
// Username    : jeturner
// Description : ties together all the other classes so we can play the dungeon game

public class DungeonGame
{
    public static void main(String [] args)
    {
        final int TILE_SIZE = 32;
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        int keys = StdIn.readInt();
        int gems = StdIn.readInt();
        Dungeon dungeon = new Dungeon(width, height, TILE_SIZE);
        for (int y = height - 1; y > -1; y--)
        {
            for (int x = 0; x < width; x++)
            {
                String str = StdIn.readString();
                if (str.equals("W")) dungeon.set(x, y, "W");
                else if (str.equals("S")) dungeon.set(x, y, "S");
                else if (str.equals("D")) dungeon.set(x, y, "D");
                else if (str.equals("-")) dungeon.set(x, y, "-");
                else if (str.equals("R")) dungeon.set(x, y, "R");
                else if (str.equals("Y")) dungeon.set(x, y, "Y");
                else if (str.equals("B")) dungeon.set(x, y, "B");
                else if (str.equals("U")) dungeon.set(x, y, "U");

            }
        }
        for (int k = 0; k < keys; k++) dungeon.addKey();
        for (int g = 0; g < gems; g++) dungeon.addGem();
        Hero hero = new Hero(1, 1, TILE_SIZE, 100, "girl.png");
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);        
        StdDraw.setXscale(0, width * TILE_SIZE);
        StdDraw.setYscale(0, height * TILE_SIZE);
        dungeon.draw();
        hero.draw();
        StdDraw.show(10);
        hero.itsame.play();        
        while (true)
        {
            if (StdDraw.hasNextKeyTyped())
            {
                char ch = StdDraw.nextKeyTyped();
                if (ch == 'w')
                {
                    hero.move(ch, dungeon);
                }
                if (ch == 'a')
                {
                    hero.move(ch, dungeon);
                }
                if (ch == 's')
                {
                    hero.move(ch, dungeon);
                }
                if (ch == 'd')
                {
                    hero.move(ch, dungeon);
                }
            }            
            dungeon.draw();
            hero.draw();
            StdDraw.show(10);
            if (hero.getNumGems() == gems || hero.getHp() <= 0) break;
                        
        }
        if (hero.getNumGems() == gems)
        {
            hero.win.play();
            System.out.println("Coungratulations! YOU WIN!");            
        }
        else
        {       
            hero.diesound.playBlocking();
            System.out.println("Our hero has died!");
            hero.gameover.play();
            System.out.println("GAME OVER");
        }
    }	
}
