// Name        : Joe Turner
// Username    : jeturner
// Description : Reads an input file then creates a list of blocks, manages details about the list. Used by BlockGame.
import java.io.*;
import java.util.Scanner;
public class Blocks {
    private class Node // Let there be nodes
    {
        private Block block; // let the nodes contain blocks
        private Node next; // let the nodes link to each other
    }
    Node first = null; // let the first node be null
    public Blocks(String filename)                    // Create a new collection of blocks based on a text file
    {
        try
        {
            Scanner scanner = new Scanner(new File(filename)); // create a scanner to read the input file         
            while (scanner.hasNext()) // while the file has stuff to read
            {
                Block block = new Block(scanner.nextDouble(), //create a block from input
                        scanner.nextDouble(), 
                        scanner.nextDouble(), 
                        scanner.nextDouble(), 
                        scanner.nextInt(), 
                        scanner.nextInt(), 
                        scanner.nextInt());
                add(block); // add the block to the list
            }
        }
        catch (Exception ex)
        {
            System.out.println("Could not find blocks file: " + filename); // error message for unfound input file
        }
    }
    public void draw()                                // Draw all the blocks in the collection
    {
        Node current = first; // let the current node be the first in the list
        while (current != null) // while we haven't reached the list's end
        {
            current.block.draw(true); // draw current block
            current = current.next; // select next node
        }
    }
    public int size()                                 // Find out how many blocks are in the collection
    {
        Node current = first; // let the current node be the first in the list
        int size = 0; // keep track of the size of the
        while (current != null) // while we haven't reached the list's end
        {
            size++; // increment the count
            current = current.next; // select next node
        }
        return size; // return the size
    }
    public Block removeAtLocation(double x, double y) // Return block at (x,y) removing from collection, returns null if no block at (x,y)
    {
        Node current = first; // let the current node be the first in the list
        Block block = null;
        int count = 0; // to keep track of position in list
        int link = 0; // keep track of link to be removed
        while (current != null) // while we haven't reached the list's end
        {
            count++; // increment the count
            if (current.block.inside(x, y)) // if the mouse coordinates are inside the current block then 
            { 
                link = count; // make note of the blocks position in the list
            }
            current = current.next; // select the next node
        }
        if (link > 0) // if we found a block
        {
            if (link == 1) // if it's the first block
            {
                current = first; // select the first node
                block = current.block; // get the block
                first = current.next; // set the next node to the first node               
            }
            else
            {
                current = first; // select the first node
                count = 0; // keep track of list position
                while (current != null) // loop through list
                {
                    count ++; // increment count
                    if (count == link - 1) // when we reach the link before the link to be removed
                    {
                        block = current.next.block; // get the block to be removed
                        if (current.next.next != null) current.next = current.next.next; // link the block before to the block after (if there is one)
                        else current.next = null; // or set the next node to null;
                    }
                    current = current.next; // select next node
                }
            }
        }
        return block; // return the block
    }
    public void add(Block block)                      // Add the given block to the collection
    {
        Node node = new Node(); // create new node
        node.block = block; // add new block to node
        node.next = first; // link new node to first node in list
        first = node; // set new node as first in list
    }
    public double height()                            // Return the height of the topmost block in the collection
    {
        Node current = first; // get first node
        double highest = 0.0; // counter for highest block in stack
        while (current != null) // looop through list
        {
            if (current.block.getTop() > highest) highest = current.block.getTop(); // keep track of the highest block
            current = current.next; // select next node
        }
        return highest; // return the highest block found
    }
    public boolean validLocation(Block block)         // Check if a Block's location is valid (doesn't intersect other blocks or go outside unit box)
    {
        Node current = first; // select first node
        boolean valid = true; // keep track of whether or not location is valid
        while (current != null) // loop through blocks in list
        {
            if (current.block.intersects(block) || block.outsideUnitBox()) valid = false; // if location is not valid set variable to false
            current = current.next; // select next node
        }
        return valid; // return the result
    }
    public static void main(String [] args)
    {
        Blocks blocks = new Blocks("bogusfilename.txt");
        blocks.add(new Block(0.5, 0.5, 0.1, 0.1, 255, 0, 0));
        blocks.add(new Block(0.2, 0.2, 0.2, 0.3, 0, 0, 255));
        blocks.add(new Block(0.7, 0.7, 0.05, 0.4, 0, 255, 0));

        blocks.draw();
        System.out.println("size   = " + blocks.size());
        System.out.println("height = " + blocks.height());

        Block b1 = new Block(0.4, 0.45, 0.15, 0.05, 0, 0, 0);
        Block b2 = new Block(0.2, 0.45, 0.15, 0.05, 0, 0, 0);
        b1.draw(true);
        b2.draw(true);
        System.out.println("b1 valid = " + blocks.validLocation(b1));
        System.out.println("b2 valid = " + blocks.validLocation(b2));

        Block current = blocks.removeAtLocation(0.0, 0.0);
        System.out.println("current  = " + current);
        System.out.println("size now = " + blocks.size());

        current = blocks.removeAtLocation(0.5, 0.5);
        System.out.println("current = " + current);     
        System.out.println("size now = " + blocks.size());
    }
}
