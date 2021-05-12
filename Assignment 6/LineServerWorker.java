// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : This is the server-side workhorse. The worker needs to have access to the Lines object so it can add new lines or clear all the lines if a client requests it. The class also keeps track of the total number of clients. The worker waits for a single line of text from the client and always responds with a single line.

import java.io.*;
import java.net.*;
import java.util.*;

public class LineServerWorker implements Runnable
{
    // Keeps track of the number of clients
    static int clients = 0;

    // Instance variables for 
    private Socket socket = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    private static Lines lines = new Lines();    

    public LineServerWorker(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("HELLO: " + socket.toString());
            clients ++;
            System.out.println("CONNECTIONS: " + clients);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while (true)
            {
                String line = reader.readLine();
                if (line == null) break;
                String[] commands = line.split("\\s+");
                String result = "ERROR";
                String code = commands[0].toUpperCase();
                if (code.equals("GET"))
                {
                    result = lines.size() + " " + clients;
                    if (lines.size() > 0) result += " " + lines.toString();
                }
                else if (code.equals("CLEAR"))
                {
                    lines.clear();
                    result = "OK";
                }
                else if (code.equals("ADD"))
                {                  
                    double x1 = Double.parseDouble(commands[1]);
                    double y1 = Double.parseDouble(commands[2]);
                    double x2 = Double.parseDouble(commands[3]);
                    double y2 = Double.parseDouble(commands[4]);
                    int r = Integer.parseInt(commands[5]);
                    int g = Integer.parseInt(commands[6]);
                    int b = Integer.parseInt(commands[7]);
                    Line newLine = new Line(x1, y1, x2, y2, r, g, b);
                    lines.add(newLine);
                    result = "OK";
                }
                else if (code.equals("QUIT"))
                {
                    writer.println("OK");
                    System.out.println("GOODBYE: " + socket.toString());
                    clients --;
                    System.out.println("CONNECTIONS: " + clients);
                    break;
                }
                writer.println(result);
            }
            reader.close();
            writer.close();
            socket.close();
        }
        catch (IOException e)
        {
            System.out.println("HANGUP: " + socket.toString());
            clients --;
            System.out.println("CONNECTIONS: " + clients);
        }
    }

    public static void main(String[] args)
    {

    }
}
