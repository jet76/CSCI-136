// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : A Line object represents a two-dimensional line with a given color. It knows things like its end points and its color. It can do things like return a string representation of itself and draw itself.

import java.awt.Color;

public class Line
{
    // Instance variables for a line
    private double x0;
    private double y0;
    private double x1;
    private double y1;
    private int r;
    private int g;
    private int b;
    
    public Line(double x0, double y0, double x1, double y1, int r, int g, int b)  // Create a line (x0,y0)-(x1,y1) with RGB(r,g,b)
    {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public String toString()  // String representation of the line, format "x0 y0 x1 y1 r g b"
    {
        return x0 + " " + y0 + " " + x1 + " " + y1 + " " + r + " " + g + " " + b;
    }
    
    public void draw()      // Draw the line using StdDraw
    {
        StdDraw.setPenColor(new Color(r, g, b));
        StdDraw.line(x0, y0, x1, y1);
    }
}
