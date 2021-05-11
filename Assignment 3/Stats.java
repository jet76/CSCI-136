// Simple class that tracks elapsed running time and
// also can estimate the heap memory used.

import java.lang.management.*;

public class Stats 
{ 
    private final long start;

    public Stats() 
    {
        start = System.currentTimeMillis();
    } 

    // Return elapsed time in seconds since the object was created
    public double elapsedTime() 
    {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    // Return the heap memory used in kilobytes
    public long heapMemory() 
    {
    	return Math.round(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1024.0);
    }

    // Return some lines of text with the stats
    public String toString()
    {
    	String result = "";
		result += "elapsed (s)           : " + elapsedTime();
		result += "\n";
		result += "heap memory used (KB) : " + heapMemory();
		return result;
    }
} 
