/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : 
 *************************************************************************/

import java.awt.*;

public class Platform extends Rectangle
{
    // instance variables
    private int red;
    private int grn;
    private int blu;

    // constructor for platforms
    public Platform(int x, int y, int w, int h, int r, int g, int b)
    {
        super(x, y, w, h); // call parent constructor
        red = r; // set red value
        grn = g; // set green value
        blu = b; // set blue value
    }
    // draw method
    public void draw(Graphics g)
    {
        g.setColor(new Color(red, grn, blu)); // set platform color
        g.fillRect(getLeft(), getTop(), getWidth(), getHeight()); // draw platform
    }
}

