/*************************************************************************
 * Name        : Joe Turner & Elisha Gentry
 * Username    : jeturner & epgentry
 * Description : 
 *************************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class StickMan
{
    // instance variables
    private static String filename;
    private int width, height, delay, velX, velY, startX, startY;
    private int moveX, moveY;
    private Man man;
    private ArrayList<Rectangle> platforms = new ArrayList<Rectangle>();
    Stats stats = new Stats();

    // Inner class
    public class gPanel extends JPanel
    {
        // inner class handles mouse events 
        private class mouseHandler implements MouseMotionListener
        {
            // event handler for mouse drag
            public void mouseDragged(MouseEvent e)
            {
                // not used, yet?
            }

            // event handler for mouse move
            public void mouseMoved(MouseEvent e)
            {
                int x = e.getX(); // mouse X coordinate
                int y = e.getY(); // mouse Y coordinate
                int t = man.getTop(); // stickman top coordinate
                int b = man.getBottom(); // stickman bottom coordinate
                int l = man.getLeft(); // stickman left coordinate
                int r = man.getRight(); // stickman right coordinate
                // if mouse inside stickman bounds set X velocity to 0
                if (x >= l && x <= r && y >= t && y <= b) man.setVelX(0);
                // if mouse left of stickman set X velocity to move left
                else if (x < l) man.setVelX(-velX);
                // if mouse right of stickman set X velocity to move right
                else if (x > r) man.setVelX(velX);
            }
        }

        // constructor for graphics panel
        public gPanel()
        {
            addMouseMotionListener(new mouseHandler()); // add mouse listener
        }

        // paint method for graphics panel
        public void paintComponent(Graphics g) 
        {
            // loop and draw platforms
            for (int p = 0; p < platforms.size(); p++)
            {
                Rectangle platform = platforms.get(p); // get current platform
                platform.draw(g); // draw platform
            }
            if (man.isAlive()) man.draw(g); // draw stickman if alive
            else // otherwise draw game over
            {
                g.setColor(Color.RED); // set graphics color
                g.setFont(new Font("SansSerif", Font.BOLD, 48)); //set font properties
                g.drawString("GAME OVER", width / 2 - 3 * 48, height / 2 + 24); // draw game over
            }
        }
    }

    // go!
    public void go()
    {
        try
        {
            // create a scanner for the file
            Scanner scan = new Scanner(new File(filename));

            // scan the width and height of the playing area
            width = scan.nextInt();
            height = scan.nextInt();

            // scan details for man
            startX = scan.nextInt();
            startY = scan.nextInt();
            BufferedImage img = ImageIO.read(new File(scan.next()));
            int manW = img.getWidth();
            int manH = img.getHeight();
            
            // scan timing and velocity
            delay = scan.nextInt();
            velX = scan.nextInt();
            velY = scan.nextInt();

            // create the man
            man = new Man(startX, startY, manW, manH, 0, velY, img);
            moveX = man.getLeft();
            moveY = man.getTop();
            // loop to read platform data
            while (scan.hasNext())
            {
                Platform p = new Platform(scan.nextInt(), scan.nextInt(), scan.nextInt(), 
                        scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
                platforms.add(p);
            }            

            // create statistics panel and its contents
            JPanel top = new JPanel();
            top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
            JLabel time = new JLabel("Time: ");  
            top.add(time);

            // create graphics panel
            gPanel mid = new gPanel();
            mid.setSize(width, height);
            mid.setPreferredSize(new Dimension(width, height));

            // create a control panel and its contents
            JPanel bottom = new JPanel();                 
            JButton restart = new JButton("Restart");
            restart.addActionListener(new restartListener());
            bottom.add(restart);

            // create the frame, add contents, customize, show
            JFrame frame = new JFrame();                    
            frame.add(BorderLayout.NORTH, top);
            frame.add(BorderLayout.CENTER, mid);
            frame.add(BorderLayout.SOUTH, bottom);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            frame.setResizable(false);
            frame.setVisible(true);          

            while (true) // loop forever
            {
                if (man.isAlive()) // if stickman is alive
                {                    
                    int mVelX = man.getVelX();                    
                    if (mVelX != 0)
                    {
                        int mTop = man.getTop();
                        moveX = mTop + mVelX; // determine stickman's next horizontal move
                        Rectangle xIntersect = man.intersectOnMove(mVelX, 0, platforms); // check for objects in stickman's vertical path
                        if (xIntersect != null) // if there's an object in stickman's vertical path
                        {
                            // if stickman collides with object on right
                            if (mVelX > 0 && man.getRight() + mVelX >= xIntersect.getLeft())
                            {
                                moveX = xIntersect.getLeft() - man.getWidth(); // stop stickman at collision point
                            }
                            // if stickman collides with object on left
                            else if (mVelX < 0 && man.getLeft() + mVelX <= xIntersect.getRight())
                            {
                                moveX = xIntersect.getRight(); // stop stickman at collision point
                            }
                        }
                    }
                    int mVelY = man.getVelY();
                    if (man.getVelY() != 0)
                    {
                        int mLeft = man.getLeft();
                        moveY = mLeft + mVelY;
                        Rectangle yIntersect = man.intersectOnMove(0, mVelY, platforms); // check for objects in stickman's horizontal path
                        if (yIntersect != null) // if there's an object in stickman's horizontal path
                        {
                            // if stickman collides with an object below
                            if (mVelY > 0 && man.getBottom() + mVelY >= yIntersect.getTop())
                            {
                                moveY = yIntersect.getTop() - man.getHeight(); // stop stickman at collision point
                            }
                            // if stickman collides with an object above
                            else if (mVelY < 0 && man.getTop() + mVelY <= yIntersect.getBottom())
                            {
                                moveY = yIntersect.getBottom(); // stop stickman at collision point
                            }                    
                        }
                    }
                    // move stickman horizontally within graphics bounds
                    if (moveX > -1 && moveX <= width - man.getWidth()) man.setLeft(moveX);
                    // move stickman vertically within graphics bounds
                    if (moveY > -1) man.setTop(moveY);
                    // display ellapsed time
                    time.setText("Time: " + String.format("%.1f", stats.elapsedTime()));
                    // repaint the frame
                    frame.repaint();
                    // check to see if stickman has fallen into a pit and died
                    if (moveY > height) man.alive(false);  
                    // wait a few milliseconds before repeating
                }
                Thread.sleep(delay);  
            }             
        }
        catch (Exception ex)
        {          
            System.out.println("Failed to open " + filename);
        }
    }

    // class for restart button
    private class restartListener implements ActionListener
    {
        // event handler for restart button
        @Override
        public void actionPerformed(ActionEvent event)
        {
            // reset stickman's location to start position       
            man.setTop(startY);
            man.setLeft(startX);
            // if stickman is dead, reincarnate him
            if (!man.isAlive())man.alive(true);
            // reset timer
            stats.reset();
        }
    }

    // main method
    public static void main(String[] args)
    {
        // if no level file specified
        if (args.length < 1)
        {
            System.out.println("Must specify a level file!"); // display error message
            return; // exit the method
        }
        // otherwise
        filename = args[0]; // get filename
        StickMan stickman = new StickMan(); // create a new stickman
        stickman.go(); // start the game
    }
}