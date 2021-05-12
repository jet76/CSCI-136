// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : 

import java.io.*;
import java.net.*;
import java.awt.*;

public class LineTest
{
    public static void main(String[] args)
    {
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        boolean firstClick = true;

        while (true)
        {
            if (StdDraw.mousePressed())
            {
                if (firstClick)
                {
                    x1 = StdDraw.mouseX();
                    y1 = StdDraw.mouseY();
                    firstClick = false;
                }
            }
            else if (!StdDraw.mousePressed() && !firstClick)
            {
                
                if (!firstClick)
                {
                    x2 = StdDraw.mouseX();
                    y2 = StdDraw.mouseY();
                    
                    firstClick = true;
                    System.out.println("x1: " + x1);
                    System.out.println("y1: " + y1);
                    System.out.println();
                    System.out.println("x2: " + x2);
                    System.out.println("y2: " + y2);
                    System.out.println();
                }   
            }




            StdDraw.show(3000);
        }

    }
}