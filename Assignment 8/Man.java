/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : 
 *************************************************************************/

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Man extends Rectangle
{
    // instance variables
    private BufferedImage img = null;
    private boolean alive = true;
    private int velX, velY;

    // constructor method
    public Man(int x, int y, int w, int h, int velX, int velY, BufferedImage img)
    {
        super(x, y, w, h); // call parent constructor
        this.velX = velX;
        this.velY = velY;
        this.img = img; // set image
    }
    // returns man's alive status
    public boolean isAlive()
    {
        return alive;
    }

    // sets man's alive status
    public void alive(boolean alive)
    {
        this.alive = alive;
    }
    // sets X velocity
    public void setVelX(int velX)
    {
        this.velX = velX;
    }
    // sets Y velocity
    public void setVelY(int velY)
    {
        this.velY = velY;
    }
    // returns x velocity
    public int getVelX()
    {
        return velX;
    }
    // returns y velocity
    public int getVelY()
    {
        return velY;
    }
    // moves man
    public void move(int x, int y)
    {
        setLeft(x);
        setTop(y);
    }
    // draws man
    public void draw(Graphics g)
    {
        g.drawImage(img, getLeft(), getTop(), null); 
    }
}

