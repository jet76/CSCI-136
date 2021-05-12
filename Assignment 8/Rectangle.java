// Abstract base class for rectangular like game objects.

import java.awt.*;
import java.util.ArrayList;

public abstract class Rectangle 
{
	// The top-left (x, y) location of the rectangle
    private int x;
	private int y;
	
	// The size of the rectangle
	private int width;
	private int height;
		
	public Rectangle()
	{
	}
	
	public Rectangle(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public int getLeft()
	{
		return x;
	}
	public int getTop()
	{
		return y;
	}
	
    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setLeft(int left)
    {
        this.x = left;
    }
    
    public void setTop(int top)
    {
        this.y = top;
    }
    
	public int getRight()
	{
		return x + width;
	}
	
	public int getBottom()
	{
		return y + height;
	}
	
	// All subclasses must implement their own draw method.
	// This one doesn't do anything.
	public abstract void draw(Graphics g);
	
	// If we were to move this rectangle by (deltaX, deltaY), return the first
	// rectangle in the ArrayList that we would intersect.
	// Returns null if we wouldn't intersect anything.
	public Rectangle intersectOnMove(int deltaX, int deltaY, ArrayList<Rectangle> rects)
	{
		x += deltaX;
		y += deltaY;
		Rectangle result = null;
		for (Rectangle rect : rects)
		{
			if (intersects(rect))
			{
				result = rect;
				break;
			}
		}	
		x -= deltaX;
		y -= deltaY;
		return result;
	}
	
	// Return a string representation of this rectangle
	public String toString()
	{
		String result = "";
		result += String.format("[%d, %d] - [%d - %d]", getLeft(), getTop(), getRight(), getBottom());
		return result;
	}
	
	// See if this rectangle intersects another one
	public boolean intersects(Rectangle other)
	{
		if (other == null)
			return false;
	
		double p1_x = this.getLeft();
		double p1_y = this.getTop();
		double p2_x = this.getRight();
		double p2_y = this.getBottom();
		
		double p3_x = other.getLeft();
		double p3_y = other.getTop();
		double p4_x = other.getRight();
		double p4_y = other.getBottom();

		return (p3_x < p2_x && p1_x < p4_x && p1_y < p4_y && p3_y < p2_y);
	}	
}
