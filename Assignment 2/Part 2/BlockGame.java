// Name        : Joe Turner
// Username    : jeturner
// Description : creates a game with blocks that must be stacked in a manner which achieves the lowest possible height for the stack.
import java.awt.Color; // import some colors
import java.awt.Font; // import some fonts
public class BlockGame {
    static Block block = null; // create a block that will be used to keep track of which block the user is moving
    public static void main(String [] args)
    {
        if (args.length > 0) // if the user has specified an input file
        {
            Blocks blocks = new Blocks(args[0]); // create a group of blocks from the file            
            while (true) // play the game
            {
                if (StdDraw.mousePressed() && block == null) // if the mouse is pressed and no block is currently being moved
                {
                    block = blocks.removeAtLocation(StdDraw.mouseX(), StdDraw.mouseY()); // attempt to get a block the user is clicking on.
                }
                else if (block != null && blocks.validLocation(block)) // ifd we have a block being moved and mouse is not pressed
                {
                    blocks.add(block); // add the block to the list
                    block = null; //  set the block being moved to null
                }
                StdDraw.clear(); // clear the canvas                
                blocks.draw(); // draw the stationary blocks
                StdDraw.setPenColor(Color.BLACK); // set the pen color
                StdDraw.rectangle(0.5, 0.5, 0.5, 0.5); // draw border for the playing area
                StdDraw.setPenColor(Color.RED); // set the pen color
                StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16)); // set font properties
                StdDraw.textLeft(0.0, 1.015, "Height: " + String.format("%.3f", blocks.height())); // draw the score (height of blocks)
                if (block != null) // if a block is being moved
                {
                    block.move(StdDraw.mouseX(), StdDraw.mouseY()); // center the block on the mouse's X & Y coordinates
                    if (StdDraw.hasNextKeyTyped()) // check for keyboard input
                    {
                        char ch = StdDraw.nextKeyTyped(); // get the key pressed
                        if (ch == ' ') block.rotate(); // if it's the space-bar then rotate the block
                    }
                    if (blocks.validLocation(block)) block.draw(true); // if the moving block is in a vlaid location draw it solid
                    else block.draw(false); // otherwise draw it hollow
                }
                StdDraw.show(10); // wait a brief time before repeating the process
            }
        }
        else // no input file given
        {
            System.out.println("BlockGame <blocks file>"); // inform the user of appropriate use
        }
    }    
}
