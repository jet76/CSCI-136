// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : client-side app that allows user to draw lines, and sends info to the server

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;

public class LineClient
{

    private static int numLines = 0;
    private static int numClients = 0;


    public static void main(String[] args)
    {
        // Set host and port
        String host = "localhost";
        int port = 5000;

        // Check to see if user has specified a host
        if (args.length > 0) host = args[0];

        // Check to see if user has specified a port
        if (args.length > 1) port = Integer.parseInt(args[1]);

        // Generate random RGB values for color
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);

        double x1 = 0;
        double y1 = 0;

        double x2 = 0;
        double y2 = 0;

        boolean firstClick = true;

        try
        {
            Socket socket = new Socket(host, port);
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            while (true)
            {
                StdDraw.clear();
                if (StdDraw.hasNextKeyTyped())
                {
                    char c = StdDraw.nextKeyTyped();
                    if (c == 'q')
                    {
                        writer.println("QUIT");
                        reader.close();
                        writer.close();
                        socket.close();
                        // break;
                        System.exit(0);
                    }
                    if (c == 'c') 
                    {
                        writer.println("CLEAR"); 
                    }
                }
                else if (StdDraw.mousePressed() && firstClick)
                {                  
                    x1 = StdDraw.mouseX();
                    y1 = StdDraw.mouseY();
                    firstClick = false;
                }
                else if (!StdDraw.mousePressed() && !firstClick)
                {
                    x2 = StdDraw.mouseX();
                    y2 = StdDraw.mouseY();
                    firstClick = true;   
                    writer.println("ADD " + x1 + " " + y1 + " " + x2 + " " +  y2 + " " + r + " " +  g + " " +  b);
                }
                writer.println("GET");
                String message = reader.readLine();
                if (!message.equals("OK"))
                {
                    Scanner scanner = new Scanner(message);
                    // System.out.println(message);
                    numLines = scanner.nextInt();
                    numClients = scanner.nextInt();
                    if (numLines > 0)
                    {
                        for (int i = 0; i < numLines; i++)
                        {
                            double x0 = scanner.nextDouble();
                            double y0 = scanner.nextDouble();

                            double xX = scanner.nextDouble();
                            double yY = scanner.nextDouble();   

                            int r1 = scanner.nextInt();
                            int g1 = (int) scanner.nextInt();
                            int b1 = (int) scanner.nextInt();

                            StdDraw.setPenColor(new Color(r1, g1, b1));
                            StdDraw.line(x0, y0, xX, yY);
                        }
                    }
                }
                StdDraw.show(100);
            }          
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
