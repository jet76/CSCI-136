// Name        : Joe Turner
// Username    : jeturner
// Description : The program takes 3-5 command line arguments: <color file> <output file> <num blocks> [min size] [max size], then generates a file with data for random blocks and draws the blocks.
import java.awt.Color;
import java.io.*;
import java.util.Scanner;
public class CreateBlocks {	
    static String colorFile;
    static String outputFile;
    static int numBlocks = 0;
    static double min;
    static double max;
    static boolean booMin = false;
    static boolean booMax = false;
    static Color[] color = null;
    static Color [] loadColors(String filename)
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename));
            try
            {
                while (scanner.hasNext())
                {
                    int colors = scanner.nextInt();
                    color = new Color[colors];
                    for (int i = 0; i < colors; i++)
                    {
                        scanner.next();
                        color[i] = new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    }
                }
                scanner.close();
                System.out.println("Read in " + color.length + " colors.");
            }
            catch (Exception ex)
            {
                System.out.println("Could not parse color file: " + filename);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Could not read color file: " + filename);
        }
        return color;
    }
    public static void main(String [] args)
    {
        if (args.length < 3) 
        {
            System.out.println("% java CreateBlocks");
            System.out.println("CreateBlocks <color file> <output file> <num blocks> [min size] [max size]");
        }
        else if (args.length >= 3)
        {              
            if (loadColors(args[0]) != null)
            {
                try
                {
                    numBlocks = Integer.parseInt(args[2]);
                    if (numBlocks > 0)
                    {
                        outputFile = args[1];
                        if (args.length > 3)
                        {
                            try
                            {
                                double minArg = Double.parseDouble(args[3]);
                                if (minArg > 0.0 && minArg < 1.0) 
                                {
                                    min = minArg;
                                    booMin = true;
                                    if (args.length > 4)
                                    {
                                        try
                                        {
                                            double maxArg = Double.parseDouble(args[4]);
                                            if (maxArg > 0.0 && maxArg < 1.0)
                                            {
                                                booMax = true;
                                                max = maxArg;
                                            }
                                            else 
                                            {
                                                System.out.println("Invalid max block size : " + maxArg);
                                            }
                                        }
                                        catch (Exception ex)
                                        {
                                            System.out.println("Invalid max block size: " + args[4]);
                                        }
                                    }
                                    else
                                    {
                                        booMax = true;
                                        max = 0.25;
                                    }
                                }
                                else
                                {
                                    System.out.println("Invalid min block size: " + minArg);
                                }
                            }
                            catch (Exception ex)
                            {
                                System.out.println("Invalid min block size: " + args[3]);
                            }
                        }
                        else 
                        {
                            booMax = true;
                            booMin = true;
                            min = 0.05;
                            max = 0.25;
                        }
                    } 
                    else System.out.println("Invalid number of blocks: " + numBlocks);
                }
                catch (Exception ex)
                {
                    System.out.println("Invalid number of blocks: " + numBlocks);
                }
                finally
                {
                    if (booMin && booMax && numBlocks > 0) 
                    {
                        try
                        {
                            PrintWriter writer = new PrintWriter(outputFile);
                            StdDraw.show();
                            for (int i = 0; i < numBlocks; i ++)
                            {
                                double w = StdRandom.uniform(min, max);
                                double h = StdRandom.uniform(min, max);
                                double x = StdRandom.uniform(w / 2, 1 - w / 2);
                                double y = StdRandom.uniform(h / 2, 1- h / 2);
                                int randomColor = StdRandom.uniform(0, color.length - 1);
                                int r = color[randomColor].getRed();
                                int g = color[randomColor].getGreen();
                                int b = color[randomColor].getBlue();
                                writer.println(x + "\t" + y + "\t " +  w + "\t " + h + "\t " + r + "\t " + g + "\t " + b);
                                StdDraw.setPenColor(new Color(r, g, b));
                                StdDraw.filledRectangle(x, y, w / 2, h / 2);
                            }
                            writer.close();
                            System.out.println("Creating " + numBlocks + " random blocks.");
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Failed to open output file: " + args[1]);
                        }
                    }
                    else if (booMin && booMax && min > max)
                    {
                        System.out.println("Invalid block size range: " + min + " > " + max);
                    }
                }
            }
        }
    }
}








