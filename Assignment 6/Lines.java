// Name        : Joe Turner & Elisha Gentry
// Username    : jeturner & epgentry
// Description : The Lines class main job is to store a collection of Line objects. This class is also important as it controls access to the shared data being assessed by the server's worker threads.

import java.util.ArrayList;

public class Lines
{
    private ArrayList<Line> lines = new ArrayList<Line>();

    public synchronized void add(Line line) // Add a new line to the collection 
    {
        lines.add(line);
    }
    
    public synchronized String toString()     // Return a single line containing the data for all the lines
    {
        String data = "";
        for (int i = 0; i < lines.size(); i++) data += lines.get(i).toString() + " ";
        return data;
    }
    
    public synchronized int size()         // Find out how many lines are currently in the collection
    {
        return lines.size();
    }
    
    public synchronized void clear()        // Remove all lines from the collection
    {
        lines.clear();
    }    
}
