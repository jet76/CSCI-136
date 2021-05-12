/*************************************************************************
 * Name        : Keith Vertanen
 * Username    : kvertanen
 * Description : Class that can track running time and also provide
 *               information about the amount of memory used.
 *************************************************************************/

import java.lang.management.*;

public class Stats 
{ 
    private long start;

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

    public void reset()
    {
    	start = System.currentTimeMillis();
    }
    
    // Return the used heap memory used in MB
    public double heapMemory() 
    {
    	return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1048576.0;
    }

    // Return the maximum available memory in MB
    public double maxMemory() 
    {
    	return Runtime.getRuntime().maxMemory() / 1048576.0;
    }

    // Return some lines of text with the stats
    public String toString()
    {
    	String result = "";
		result += String.format("elapsed time (s)      : %7.4f\n", elapsedTime());
		result += String.format("heap memory used (MB) : %7.4f\n", heapMemory());
		result += String.format("max memory (MB)       : %7.4f", maxMemory());		
		return result;
    }
} 
