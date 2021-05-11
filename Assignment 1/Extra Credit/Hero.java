// Name        : Joe Turner
// Username    : jeturner
// Description : creates a hero that can explore the dungeon

public class Hero
{
    int cellX;
    int cellY;
    int tileSize;
    int keys = 0;
    int gems = 0;
    int hp;
    String image;
    AudioFile keysound = new AudioFile("key.wav");
    AudioFile gemsound = new AudioFile("gem.wav");
    AudioFile walksound = new AudioFile("walk.wav");
    AudioFile wallsound = new AudioFile("wall.wav");
    AudioFile doorsound = new AudioFile("door.wav");
    AudioFile ouchsound = new AudioFile("ouch.wav");
    AudioFile watersound = new AudioFile("splash.wav");
    AudioFile diesound = new AudioFile("die.wav");
    AudioFile gameover = new AudioFile("gameover.wav");
    AudioFile win = new AudioFile("stage.wav");
    AudioFile itsame = new AudioFile("itsamemario.wav");
    /**
     * Create a new Hero object
     * 
     * @param cellX     Starting cell x-position
     * @param cellY     Starting cell y-position
     * @param tileSize  Size of image tiles in pixels
     * @param image     Image filename for the Hero
     */
    public Hero(int cellX, int cellY, int tileSize, int hp, String image)
    {
        this.cellX = cellX;
        this.cellY = cellY;
        this.tileSize = tileSize;
        this.hp = hp;
        this.image = image;
        this.keys = 0;
        this.gems = 0;
    }
    /**
     * Draw the Hero at its current location  
     */
    public void draw()
    {
        StdDraw.picture(this.tileSize / 2 + this.cellX * this.tileSize,this.tileSize / 2 +  this.cellY * this.tileSize, image);
    }
    /**
     * Called when Hero uses a key to open a door 
     */
    public void useKey()
    {
        keys --;
        System.out.println("Used key to open door, keys left = " + this.keys);
        doorsound.play();
    }
    /**
     * Find out how many keys the Hero has left
     * 
     * @return  Number of keys
     */
    public int getNumKeys()
    {
        return this.keys;
    }
    /**
     * Called when the Hero takes a key 
     */
    public void takeKey()
    {
        this.keys ++;
        keysound.play();
        System.out.println("You found a key, total keys = " + this.keys);
    }
    /**
     * Find out how many gems the Hero has
     * 
     * @return  Number of gems
     */
    public int getNumGems()
    {
        return this.gems;
    }
    /**
     * Called when the Hero takes a gem 
     */
    public void takeGem() 
    {
        this.gems ++;
        gemsound.play();
        System.out.println("You found a gem, total gems = " + this.gems);
    }
    /**
     * Find out how many health points the Hero has left
     * 
     * @return  Health points
     */
    public int getHp()
    {
        return this.hp;
    }
    /**
     * Called when the Hero takes damage 
     */
    public void takeDmg(int dmg) 
    {
        this.hp -= dmg;
        ouchsound.play();
        System.out.println("Our hero took " + dmg + " damage, total hp = " + this.hp);
    }
    /**
     * Move the Hero based on the key hit by the user:
     * 'w' = move north
     * 's' = move south
     * 'a' = move west
     * 'd' = move east
     * 
     * @param ch       Character typed by the player
     * @param dungeon  Reference to the Dungeon object
     */
    public void move(char ch, Dungeon dungeon)
    {
        String str = String.valueOf(ch);
        if (str.equals("w")) 
        {
            if (dungeon.attemptMove(this.cellX, this.cellY + 1, Hero.this))
            {
                this.cellY += 1;
                System.out.println("Our hero walks North!");               
            }
            else 
            {
                System.out.println("Can't go North!");               
            }
        }
        else if (str.equals("a"))
        {
            if (dungeon.attemptMove(this.cellX - 1, this.cellY, Hero.this))
            {
                this.cellX -= 1;
                System.out.println("Our hero walks West!");             
            }
            else 
            {
                System.out.println("Can't go West!");
            }
        }
        else if (str.equals("s"))
        {
            if (dungeon.attemptMove(this.cellX, this.cellY - 1, Hero.this))
            {                
                this.cellY -=1;
                System.out.println("Our hero walks South!");
            }
            else
            {
                System.out.println("Can't go South!");
            }
        }
        else if (str.equals("d"))
        {
            if (dungeon.attemptMove(this.cellX + 1, this.cellY, Hero.this))
            {
                this.cellX += 1;
                System.out.println("Our hero walks East!");
            }
            else
            {
                System.out.println("Can't go East!");
            }
        }
    }
    /**
     * Test out some of the methods in the Hero class
     */
    public static void main(String [] args)
    {
        // Fake a dungeon that is 6 x 3, assume 32 pixel tiles
        final int TILE_SIZE = 32;		
        StdDraw.setCanvasSize(6 * TILE_SIZE, 3 * TILE_SIZE);
        StdDraw.setXscale(0, 6 * TILE_SIZE);
        StdDraw.setYscale(0, 3 * TILE_SIZE);

        // Create a boy hero at (1, 1)
        Hero hero1 = new Hero(1, 1, TILE_SIZE, 100, "boy.png");
        hero1.draw();
        hero1.takeGem();
        hero1.takeGem();
        hero1.takeKey();
        hero1.useKey();

        // Create a girl hero at (5, 2)
        Hero hero2 = new Hero(5, 2, TILE_SIZE, 100, "girl.png");
        hero2.draw();
        hero2.takeDmg(10);
    }
}
