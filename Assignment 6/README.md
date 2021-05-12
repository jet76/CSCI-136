# Shared line drawing  

You will be created a socket client/server program that implements a shared drawing canvas. Clients will connect to a multi-threaded socket server that keeps track of the lines drawn by all the clients. The clients will see (after a short time delay anyway) all the lines drawn by all other clients. Any client can also clear all the lines.	  

---

**Getting started.** You will developing a few simple classes as well as the main programs for the socket client and the multi-threaded socket server. There is no stub code for this assignment. We have given you the API for the two simple classes. The client and server programs you can base off some of the socket examples given in class.  

**Line.** It doesn't get much simpler than this. A Line object represents a two-dimensional line with a given color. It knows things like its end points and its color. It can do things like return a string representation of itself and draw itself. Your Line data type must implement the following API:  

<pre>
public class Line
----------------------------------------------------------------------------------------
       Line(double x0, double y0, double x1, double y1, int r, int g, int b)  // Create line (x0,y0)-(x1,y1) of given RGB color, r/g/b in [0, 255]
String toString()       // String representation of the line, format "x0 y0 x1 y1 r g b"
       void draw()      // Draw the line using StdDraw
</pre>

**Lines.** The Lines class main job is to store a collection of Line objects. This class is also important as it controls access to the shared data being assessed by the server's worker threads. Hint: think carefully about what methods need to be synchronized in the API. In your multi-threaded server, a single Lines object should be used by all the worker threads. Here is the API:  

<pre>
public class Lines
-----------------------------------------------------------------------------------------
  void add(Line line) // Add a new line to the collection 
String toString()     // Return a single line containing the data for all the lines
   int size()         // Find out how many lines are currently in the collection
  void clear()        // Remove all lines from the collection
</pre>

**Communication protocol.** Before discussing the other classes you'll implement, it is important to understand the communication protocol that will be used between the client and the server. The client and server communicate via a simple text-based protocol. The protocol uses a request-response style of communication, similar to protocols such as HTTP. The client always initiates a communication by sending a single line of text to the server. Normally the line is only a command, but in the case of the ADD command, additional parameters specifying the location and color of a line follow the command word. After receiving a command from the client, the server does the appropriate action and sends a single line of text in response. The server always respond with a single line of text, often just simply saying "OK".  

The following tables details each command:  

<table border="1" cellspacing="0" cellpadding="3">
<tr><td>Command</td><td width="45%">Client parameters</td><td width="45%">Server response</td></tr>
<tr><td>GET</td><td>None</td>
<td>
A single line of text containing fields separated by spaces. 
The first field is an integer giving the number of current <tt>Line</tt> objects in the <tt>Lines</tt> collection.
The second field is an integer giving the number of currently connected clients.
Following this are 0 or more 7-tuples giving the details of the lines in the collection.
Each 7-tuple consists of 4 floating-point values for the endpoints of the line (x0 y0 x1 y1).
After this come 3 integer values specifying the RGB color of the line.
The RGB color is specified by three integers in [0, 255].
<br /><br />
For example, the response for a canvas with 2 lines and 3 connected clients:
<pre>2 3 0.65 0.23 0.88 0.5 200 134 89 0.1 0.23 0.33 0.428 12 29 109</pre>
</td>
</tr>
<tr><td>CLEAR</td><td>None</td><td><pre>OK</pre></td></tr>
<tr><td>ADD</td>
<td>
A 7-tuple of the form: &quot;x0 y0 x1 y1 r g b&quot; where (x0, y0) and (x1, y1) are the endpoints of the line and (r, g, b) are integer values in [0, 255] specifying the line's color.
<br /><br />
For example, to add a green line from the lower-left corner to the upper-right corner:
<pre>ADD 0.0 0.0 1.0 1.0 0 0 255</pre>
</td>
<td>
<pre>OK</pre>
</td>
</tr>
<tr><td>QUIT</td><td>None</td><td><pre>OK</pre></td></tr>
</tr>
</table>

**LineClient.** This is the program that does the actual drawing shown in the video. The client program should periodically (approximately every 100ms) execute a GET command to obtain the current set of lines and the number of clients currently connected. The number of lines in the shared canvas should appear as text in the upper-left corner of your window. The current number of clients connected should appear as text just below the count of lines.  

The client program takes two optional command line arguments, the first is the hostname of server. The default hostname should be "localhost". If the hostname is present, a second optional command line argument may also be accepted. This is the port number of the server. The default value of the port should be 5000.  

When a client starts, it chooses a random RGB color. All lines drawn by the same client are always this same color for the lifetime of the client. The start position of a line is specified by the user pressing the mouse button. The end position of that line is specified when the user releases the mouse button. After button release, an ADD command is sent to the server ir order to add the new line to the collection. As shown in the video, you don't need to draw the line until the button is released. New lines drawn by other clients should continue to appear even while a client is busy drawing a line (i.e. the user is holding down the mouse button).  

The client program supports two commands via the keyboard. The 'q' key causes the client to execute the QUIT command. This should cause an orderly closing of the input and output streams as well as the socket. Note that the drawing window in StdDraw remains open even after your client's main() method finishes. Note also that the lines drawn by a client remain in the shared collection even if the client quits. The 'c' key causes the client to execute a CLEAR command. This removes all lines from the shared set stored on the server.  

**LineServer.** This program has only a main() method and takes a single command line argument, the integer port number to listen on. The program's job is the spawn a worker thread to handle an incoming client and then carrying on listening for new clients. It is similar to my [ValueServer.java](ValueServer.java) and [Magic8ServerMulti.java](Magic8ServerMulti.java) examples.  

**LineServerWorker.** This is the server-side workhorse. The worker needs to have access to the Lines object so it can add new lines or clear all the lines if a client requests it. The class also keeps track of the total number of clients. The worker waits for a single line of text from the client and always responds with a single line.  

The worker should print out some information to the console when clients connect or disconnect. When a client connects, print to standard output "HELLO:" followed by the client's Socket object (using the objects build-in toString method). Then print another line "CONNECTIONS:" followed by the total clients connected once the new client joins.  

If the worker receives a QUIT command, it responds with "OK" and then does an orderly closing of the read/write streams and the socket. When the client disconnects, print to standard output "GOODBYE:" followed by the client's Socket object. Then print another line "CONNECTIONS:" followed by the total clients connected once the new client joins.  

The client may be naughty and shutdown without sending a QUIT command. Try this and see what happens to your worker thread. Handle this circumstance so that your worker prints out "HANGUP:" followed by the socket information, updates the client counter, and then displays the number of connections after the impolite client is gone.  

Here is an example of our server's output. Two clients connected to the server, then both disconnected normally. A third client connected and then disconnected abruptly.  

<pre>
% java LineServer 4242
HELLO: Socket[addr=/127.0.0.1,port=51536,localport=4242]
CONNECTIONS: 1
HELLO: Socket[addr=/127.0.0.1,port=51537,localport=4242]
CONNECTIONS: 2
GOODBYE: Socket[addr=/127.0.0.1,port=51537,localport=4242]
CONNECTIONS: 1
GOODBYE: Socket[addr=/127.0.0.1,port=51536,localport=4242]
CONNECTIONS: 0
HELLO: Socket[addr=/127.0.0.1,port=51544,localport=4242]
CONNECTIONS: 1
HANGUP: Socket[addr=/127.0.0.1,port=51544,localport=4242]
CONNECTIONS: 0
</pre>

---

**Why does the server need to send "OK" back for some of the commands?** It wouldn't really be required. But this way all the commands have a client request followed by a server response.  

**What is an easy way to parse out the data from the GET response?** We recommend you use the Scanner class. You can use it just like with a file, but you pass the constructor a String instead of a File object.  

**How should the server keep track of the number of connected clients?** This is a perfect job for a static instance variable in the worker class. If you declare an instance variable static, all instances of that class share the same underlying variable.  

**What type of exception is caused if the client terminates without issuing a QUIT command?** It depends, we've seen several different types of exceptions. This is probably a justifiable situation for simply catching a general exception of type Exception. By doing this, you'll cover all types of problems. Just be careful since this will also catch exceptions caused by bugs in your code.  

**How can I make sure there is only one instance of the Lines class shared amongst all my workers?** If you declare an instance variable as static, only a single variable is created and shared between all objects of the data type.  

---

**Extra credit.** Add additional functionality to the shared drawing program. Some ideas:  
* Allow drawing of different shapes such as lines, circles, points, etc.  
* Allow selection of a color from an onscreen palette of colors.  
* Make it into some sort of game  

---

This README was adapted from a page at Montana Tech: https://katie.cs.mtech.edu/classes/archive/s14/csci136/assign/lines/
