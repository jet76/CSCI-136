// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : This program has only a main() method and takes a single command line argument, the integer port number to listen on. The program's job is the spawn a worker thread to handle an incoming client and then carrying on listening for new clients.

import java.io.*;
import java.net.*;

public class LineServer
{
    public static void main(String[] args)
    {
        // Set the port number
        int port = 5000;
        
        // Check to see if the user has specified a port number
        if (args.length > 0) port = Integer.parseInt(args[0]);
        
        try
        {
            ServerSocket serverSock = new ServerSocket(port);
            while (true)
            {
                Socket sock = serverSock.accept();
                System.out.println("SERVER: " + sock);                 
                Thread t = new Thread(new LineServerWorker(sock));
                t.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();            
        }
    }
}
