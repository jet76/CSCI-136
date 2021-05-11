
<html>
<head>
<title>Ultima 0 - CSCI 136</title>
<link rel="stylesheet" type="text/css" href="../../style.css" />
<script language="javascript">
function contactEmail(username, display)
{   
    var email = username + '@' + 'mtech.edu';      
    document.write('<a href="mailto:' + display + ' <' + email + '>">' + email + '</a>');    
}
</script>
</head>
<body>

<table width="100%">
<tr>
<td>
<p class="course-title">
CSCI 136 <br />
Fundamentals of Computer Science II<br />
Spring 2014
</p>
</td>

<td width="300">
<p class="course-uni">
<a href="http://www.mtech.edu"><img src="<? echo $root ?>mtech.png" alt="Montana Tech" width="212" height="33" border="0" /></a>
<br /><a href="http://cs.mtech.edu">Computer Science &amp; Software Engineering</a>
</p>
</td>
</tr>

</table>
<hr />

<div class="top-links">
<a href="../../index.php">Schedule</a> &nbsp;|&nbsp;
<a href="../../assignments.php">Assignments</a> &nbsp;|&nbsp;
<a href="../../exams.php">Exams</a> &nbsp;|&nbsp;
<a href="../../resources.php">Resources</a> &nbsp;|&nbsp;
<a href="../../info.php">Course syllabus</a> &nbsp;|&nbsp;
<a href="https://moodlemtech.mrooms3.net/course/view.php?idnumber=34606">Moodle</a> 

</div>
<hr />

<h4>Ultima 0</h4>

<table>
<tr>
<td>
In this assignment, you will be creating the starting point for a tile-based role playing game, similar to the famous <a href="http://en.wikipedia.org/wiki/Ultima_(series)">Ultima series</a>.
You will be creating a recursive algorithm to handle a variable power torch that lights your Avatar's surroundings.
You will reuse the algorithm to make sections of 
</td>
<td>
<iframe width="420" height="315" src="http://www.youtube.com/embed/xxtszY6R9kc" frameborder="0" allowfullscreen></iframe>
</td>
</tr>
</table>
<hr />
<b>Basics.</b>
The game is played on a rectangular grid of tiles. 
The grid of tiles is specified in a flat text file.
Your Avatar moves around by moving in one of the cardinal directions (north, south, east or west).
The Avatar is not allowed to move through certain features (e.g. water or mountains).
It is nighttime, but luckily your Avatar has a variable brightness torch.
The torch cannot see through certain features (e.g. mountains, stone walls).
<br /><br />
<b>Files.</b>
The file <a href="student/ultima.zip">ultima.zip</a> contains the set of graphics tiles we'll be using in this assignment.
We have included a bunch of extra graphics in case you need them for an extra-credit creation. 
It also contains stub versions of the programs <tt>Tile</tt>, <tt>World</tt>, <tt>Avatar</tt>. 
We have given you a fully functional version of the main game program <tt>Ultima.java</tt>.
<br /><br />
<b>Avatar.</b>
A <tt>Avatar</tt> object represents a player in the game.
An Avatar knows things like its current (x,y) position in the world and the current power of the Avatar's torch.
The (x,y) position are indexes, so (0,0) is the lower-left tile, (1, 0) is one tile to east, (0, 1) is one tile to the north, and so on.
An Avatar can do things like get its x/y position, change its location, get its current torch power, increase/decrease its torch power, and draw itself.
An Avatar's torch has a minimum torch radius of 2.0.  The torch power changes in increments of 0.5.  The torch starts with a default radius of 4.0.
<br /><br />
Your <tt>Avatar</tt> data type must implement the following API:
<blockquote><pre>
public class Avatar
----------------------------------------------------------------------------------------
            Avatar(int x, int y)      // Create a new Avatar at index position (x,y)
        int getX()                    // Get the current x-position of the Avatar
        int getY()                    // Get the current y-position of the Avatar
       void setLocation(int x, int y) // Update the position of the Avatar to index (x,y)
     double getTorchRadius()          // Get the current torch radius   
       void increaseTorch()           // Increase torch radius by 0.5
       void decreaseTorch()           // Decrease torch radius by 0.5, minimum is 2.0
       void draw()                    // Draw at the current position
</pre></blockquote>
We have provided a test <tt>main</tt> for <tt>Avatar</tt>.  Here is our output:
<blockquote><pre>
<b>% java Avatar</b>
5 5 4.0
1 4 4.0
1 4 4.5
1 4 4.0
1 4 3.5
1 4 3.0
1 4 2.5
1 4 2.0
1 4 2.0
</pre></blockquote>

<b>Tile.</b>
A <tt>Tile</tt> object represents an individual position in the Ultima world.
All tiles are the same size: 16 by 16 pixels.
Tiles know things like what type of tile it is and whether it is currently lit by the torch.
A tile can do things like tell people about whether it is lit, set whether it is lit or not, 
draw itself, determine if the Avatar can walk through it,
and determine if light passes through it.  Tiles should default to not being lit.
<br /><br />
Here are the details of the types of tiles your program needs to support:
<table border="1" cellspacing="0" cellpadding="3">
<tr><td>Name</td><td>Filename</td><td>Image</td><td>Opaque?</td><td>Passable?</tt><td>String code</td></tr>
<tr><td>Brick floor</td><td><tt>brickfloor.gif</tt></td><td><img src="student/brickfloor.gif" width="16" height="16" /></td><td>No</td><td>Yes</tt></td><td>B</td></tr>
<tr><td>Lava</td><td><tt>lava.gif</tt></td><td><img src="student/lava.gif" width="16" height="16" /></td><td>No</td><td>Yes</tt></td><td>L</td></tr>
<tr><td>Water</td><td><tt>water.gif</tt></td><td><img src="student/water.gif" width="16" height="16" /></td><td>No</td><td>No</tt></td><td>W</td></tr>
<tr><td>Forest</td><td><tt>forest.gif</tt></td><td><img src="student/forest.gif" width="16" height="16" /></td><td>Yes</td><td>Yes</tt></td><td>F</td></tr>
<tr><td>Grasslands </td><td><tt>grasslands.gif</tt></td><td><img src="student/grasslands.gif" width="16" height="16" /></td><td>No</td><td>Yes</tt></td><td>G</td></tr>
<tr><td>Mountains</td><td><tt>mountains.gif</tt></td><td><img src="student/mountains.gif" width="16" height="16" /></td><td>Yes</td><td>No</tt></td><td>M</td></tr>
<tr><td>Stone wall</td><td><tt>stonewall.gif</tt></td><td><img src="student/stonewall.gif" width="16" height="16" /></td><td>Yes</td><td>No</tt></td><td>S</td></tr>
</table>
<br />
If a tile is not lit, you can draw it using the supplied <tt>blank.gif</tt> image.  Here is the API you should implement for the <tt>Tile</tt> class:
<blockquote><pre>
public class Tile
-----------------------------------------------------------------------------------------
            Tile(String code)        // Create a new tile based on a String code
    boolean getLit()                 // Return whether this Tile is lit or not
       void setLit(boolean value)    // Change the lit status of this Tile
       void draw(int x, int y)       // Draw at index position (x, y)
    boolean isOpaque()               // Does this type of Tile block light?
    boolean isPassable()             // Can the Avatar walk on this Tile?
</pre></blockquote>
<table>
<tr>
<td>
We have provided a test <tt>main</tt> for <tt>Tile</tt>.  Here is our output:
</td>
<td>
<blockquote><pre>
<b>% java Tile</b>
0 0 : lit true	opaque false	passable true
0 1 : lit false	opaque false	passable true
1 0 : lit false	opaque false	passable true
1 1 : lit true	opaque false	passable true
2 0 : lit true	opaque false	passable false
2 1 : lit false	opaque false	passable false
3 0 : lit false	opaque true	    passable true
3 1 : lit true	opaque true	    passable true
4 0 : lit true	opaque false	passable true
4 1 : lit false	opaque false	passable true
5 0 : lit false	opaque true	    passable false
5 1 : lit true	opaque true	    passable false
6 0 : lit true	opaque true	    passable false
6 1 : lit false	opaque true	    passable false
</pre></blockquote>
</td>
<td>&nbsp;&nbsp;
<img src="tile_main.png" width="132" height="83" />
</td>
</table>
<br /><br />

<b>World.</b>
The <tt>World</tt> class represents all the tiles in the world as well as the Avatar.
This class is responsible for handling all keystrokes from the main program in <tt>Ultima.java</tt>.
The class knows things like all the <tt>Tile</tt> objects in the world and the <tt>Avatar</tt> object.
The class can do things like handle keystrokes from the user, draw itself, and light a portion of the world.
Your <tt>World</tt> data type must implement the following API:
<blockquote><pre>
public class World
-----------------------------------------------------------------------------------------
      World(String filename)        // Load the tiles and Avatar based on the given filename
 void handleKey(char ch)            // Handle a keypress from the main game program
 void draw()                        // Draw all the tiles and the Avatar
  int light(int x, int y, double r) // Set the tiles that are lit based on an initial position
                                    // (x, y) and a torch radius of r. Returns the number of tiles
                                    // that were lit up by the algorithm.
</pre></blockquote>
The constructor of <tt>World</tt> reads in a file using Java file I/O. 
Here is an example input file:
<pre><blockquote>
10 5
3 1
W W W W W G G G W W 
W F W G W S G G G G 
W F F G S L S G G G 
W F F G G S G G G W 
W W W F G G G G G G 
</pre></blockquote>
The integers on the first line specify the width and height of the world.
The integers on the second line specify the starting x- and y-index of the Avatar.
The remaining letters give the code letters for each tile in the world.
<br /><br />
The <tt>handleKey()</tt> method should handle the following keys:
<ul>
<li><b>w</b>&nbsp;:&nbsp;Move the Avatar north. Nothing should happen if the tile to the north is not passable or is off the map.</li>
<li><b>s</b>&nbsp;:&nbsp;Move the Avatar south. Nothing should happen if the tile to the south is not passable or is off the map.</li>
<li><b>a</b>&nbsp;:&nbsp;Move the Avatar west. Nothing should happen if the tile to the west is not passable or is off the map.</li>
<li><b>d</b>&nbsp;:&nbsp;Move the Avatar east. Nothing should happen if the tile to the east is not passable or is off the map.</li>
<li><b>+</b>&nbsp;:&nbsp;Increase the torch radius by 0.5, there is no maximum radius.</li>
<li><b>-</b>&nbsp;:&nbsp;Decrease the torch radius by 0.5, subject to a minimum radius of 2.0.</li>
</ul>

The lighting algorithm is the crux of the assignment.  
You will need to implement a recursive helper method that gets called by the public <tt>light</tt> method.
For example:<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<tt>private int lightDFS(int x, int y, int currentX, int currentY, double r)</tt><br /><br />
The basic approach is to first set all the Tile objects to being unlit.  
Then start <tt>lightDFS</tt> at the Avatar's current position.
The <tt>lightDFS</tt> method will call itself recursively for the positions to the north, south, east and west (these four directions only, do NOT recur on the diagonals).
This allows the algorithm to spread across the map.
The <tt>lightDFS</tt> method needs to retain the initial (x,y) starting position so it can calculate how far the (currentX, currentY) position is from it.
You must of course be careful to limit the recursion with appropriate base cases:
<ul>
<li><b>Base case 1 </b> - Current position is off the map</li>
<li><b>Base case 2 </b> - Current position has already been visited (the Tile is marked as lit).</li>
<li><b>Base case 3 </b> - Current position is opaque</li>
<li><b>Base case 4 </b> - Current position is outside the torch radius.
A position is considered &quot;outside&quot; if the Euclidean distance between the (x,y) index of the Avatar and the (x2, y2) index of the tile is greater than or equal to the torch radius.
</li>
</ul>
Opaque cells should still be lit, but they should not propagate the search.  
This is what causes certain parts of the map to appear black despite being within the radius of the torch.

<br /><br />
<table>
<tr>
<td>
We have provided a test <tt>main</tt> for <tt>World</tt>.  
We temporarily instrumented our <tt>light</tt> method to show you the input parameters and result value.
Here are a couple runs:
</td>
<td>
<blockquote><pre>
<b>% java World 10x5.txt</b>
light(3, 1, 4.0) = 23
</pre></blockquote>
</td>
<td>&nbsp;&nbsp;
<img src="world_main.png" width="166" height="131" />
</td>
</tr>
<tr>
<td>
</td>
<td>
<blockquote><pre>
<b>% java World 30x20.txt
light(3, 4, 4.0) = 42
</pre></blockquote>
</td>
<td>
<img src="world_main2.png" width="486" height="371" />
</td>
</table>
<br /><br />

    
<hr />

<b>Do I need to follow the prescribed APIs?</b>
Yes.
You may not add public methods to the API; however, you may add private methods 
(which are only accessible in the class in which they are declared).
<br /><br />

<b>I seem to have the number 16 in multiple spots in my code.  Is that okay?</b>
No.  
You should be able to declare the tile size exactly once in the project.
If you do it this way, it will make it easy if in the future you need to change your program to use a different tile size.
<br /><br />

<b>How come the <tt>light()</tt> method is part of the public API?  It is only called from within World.</b>
Yes, this could really be <tt>private</tt>. 
But this way it is easier for us to test your program and find bugs.
Mohahaha. 
<br /><br />

<b>My world is upside down.  What is going on?</b>
The drawing coordinate system has (0,0) in the lower-left.  If you have your 2D array arranged to have [0][0] in the lower-left,
you are going to need to transform the y-index appropriately when you read in the dungeon text file (since the first line of the dungeon is the upper-left cell).
<br /><br />

<b>My tiles are all offset and appear in the borders of the drawing window.</b>
Remember that the <tt>StdDraw.picture()</tt> method expects the <i>center</i> (x,y) position of the picture, not
the lower-left corner.  You need to adjust appropriately to get things to line up nicely.
<br /><br />
<b>Does my program need to print things to the console like in the video?</b>
No.  We did that just to help you debug your program.
<br /><br />

<b>The blank areas of my map have strange lines.  What is going on?</b>
This is caused by drawing black squares or rectangles using <tt>StdDraw</tt>.
Draw a blank tile using the provided <tt>blank.gif</tt> file instead.
<br /><br />
<b>Do I need to modify the size of the drawing window?</b>
Yes. Since each tile is 16 pixels by 16 pixels, you need to size the drawing window to accommodate this by calling <tt>StdDraw.setCanvasSize()</tt>.
Since you only want to do this one time, an appropriate place to do this would be in your <tt>World</tt> constructor since this is only called once in <tt>Ultima</tt>.
<br /><br />
<b>Do I need to modify the <tt>StdDraw</tt> coordinate system?</b>
Not necessarily, but you can if you want.
As with the window size, you'll want to do this in the constructor of <tt>World</tt>.
<br /><br />
<b>Can I send pixel coordinates as the <tt>x</tt> and <tt>y</tt> parameters to methods in <tt>World</tt>, <tt>Tile</tt>, and <tt>Avatar</tt>?</b>
No.  
The public API is that these are the integer index positions of the game tile, not a pixel or other type of coordinate.
<br /><br />

<hr />
<table>
<tr>
<td>
<b>Extra credit level 1.</b> 
Create interesting new level(s).
<br /><br />
<b>Extra credit level 2.</b> 
Enhance your lighting algorithm so that opaque objects block what can be seen.  
Something along the lines of my version shown on the right.
<br /><br />
<b>Extra credit level 3.</b> 
Make it so some tile types can be light sources of a given radius.
On the video on the right, I made lava tiles generate light out to a distance of 2.5.
The light should obey the same occlusion algorithm as for the Avatar's torch.
</td>
<td>
<iframe width="420" height="315" src="http://www.youtube.com/embed/0KBjIa50FbU" frameborder="0" allowfullscreen></iframe><br />
<iframe width="420" height="315" src="http://www.youtube.com/embed/3oMbpTkTYXE" frameborder="0" allowfullscreen></iframe><br />
</td>
</tr>
</table>
<hr />
<b>Submission.</b>
Submit your programs <tt>Avatar.java</tt>, <tt>Tile.java</tt>, and <tt>World.java</tt> using Moodle.
Be sure each submitted source file has the required header with your name, username, and a description of the program.
For extra-credit, please submit a single zip file containing all your programs/graphics/sounds via the special assignment extra-credit drop box.

<br />
<hr />
<p class="updated">Page last updated: December 30, 2014</p>
</body>

</html>

