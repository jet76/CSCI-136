// Name        : Joe Turner
// Username    : jeturner
// Description : creates and manipulates blocks
import java.awt.*;
public class Block {
    double x; // x coordinate for a block
    double y; // y coordinate for a block
    double width; // width of block
    double height; // height of block
    int red; // amount of color
    int green; // amount of color
    int blue; // amount of color
    public Block(double x, double y, double width, double height, int red, int green, int blue) // Create a block at the given (x,y) center position, with the given width, height, and RGB color value
    {
        this.x=x; // set block's x coordinate
        this.y=y; // set block's y coordinate
        this.width=width; // set block's width
        this.height=height; // set block's height
        this.red=red; // set block's red value
        this.green = green; // set block's green value
        this.blue = blue; // set block's blue value
    }             
    public void draw(boolean filled)                  // Draw the block in either filled or outline form
    {
        StdDraw.setPenColor(new Color(this.red, this.green, this.blue)); // set pen color using block's color values
        if (filled) StdDraw.filledRectangle(this.x, this.y, this.width / 2, this.height / 2); // draw the block filled
        else StdDraw.rectangle(this.x, this.y, this.width / 2, this.height / 2); // draw the block hollow
    }
    public double getLeft()                           // Get the leftmost coordinate of the block
    {
        return this.x - (this.width / 2);
    }
    public double getRight()                          // Get the rightmost coordinate of the block
    {
        return this.x + (this.width / 2);
    }
    public double getTop()                            // Get the topmost coordinate of the block
    {
        return this.y + (this.height / 2);
    }
    public double getBottom()                         // Get the bottom-most coordinate of the block
    {
        return this.y - (this.height / 2);
    }
    public boolean inside(double x, double y)         // See if the point (x,y) is inside the box (including on the edge)
    {
        if (x >= this.getLeft() && x <= this.getRight() && y <= this.getTop() && y >= this.getBottom()) return true;
        else return false;
    }
    public boolean outsideUnitBox()                   // Check if the block extends outside the unit box
    {
        if (this.getLeft() < 0.0 || this.getRight() > 1.0 || this.getBottom() < 0.0 || this.getTop() > 1.0) return true;
        else return false;
    }
    public boolean intersects(Block other)            // Check if this block intersects with another block
    {
        if (other.getLeft() > this.getRight() || other.getRight() < this.getLeft() || other.getTop() < this.getBottom() || other.getBottom() > this.getTop()) return false;
        else return true;
    }
    public void move(double x, double y)              // Move the block to a new (x,y) position
    {
        this.x = x;
        this.y = y;
    }
    public void rotate()                              // Rotate the block by 90 degrees
    {
        double oldWidth = this.width;
        this.width = this.height;
        this.height = oldWidth;
    }
    public static void main(String [] args)
    {
        Block b1 = new Block(0.5, 0.5, 0.1, 0.2, 255, 0, 0);
        Block b2 = new Block(0.1, 0.1, 0.05, 0.1, 0, 0, 255);
        Block b3 = new Block(0.5, 0.5, 0.5, 0.3, 0, 255, 0);

        b1.draw(true);
        b2.draw(false);
        System.out.println("b1 " + b1.getLeft() + " " + b1.getTop() + " " + b1.getRight() + " " + b1.getBottom());       

        b2.rotate();
        b2.draw(false);
        b3.draw(false);

        System.out.println("b1 <-> b2 " + b1.intersects(b2) + " " + b2.intersects(b1));
        System.out.println("b1 <-> b3 " + b1.intersects(b3) + " " + b3.intersects(b1));

        System.out.println("b3 outside " + b3.outsideUnitBox());
        System.out.println("(0.6, 0.6) in b3 " + b3.inside(0.6, 0.6));
        System.out.println("(0.8, 0.8) in b3 " + b3.inside(0.8, 0.8));

        b3.move(0.1, 0.7);
        b3.draw(true);
        System.out.println("b3 outside " + b3.outsideUnitBox());         
        System.out.println("b1 <-> b2 " + b1.intersects(b2) + " " + b2.intersects(b1));
        System.out.println("b1 <-> b3 " + b1.intersects(b3) + " " + b3.intersects(b1));
    }

}
