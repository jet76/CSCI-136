// name        : Joe Turner
// username    : jeturner
// description : uses two greedy heuristics to solve the traveling salesperson problem.

//  Smallest Insertion                              
//  N   1000    2000    4000    8000    16000   32000   64000   128000
//  Tour distance   15311.1603942984    22142.9944640798    31076.5790917963    43948.2100535659    61815.1066677183    87760.9992818434    123960.4072823890   175498.0852187200
//  Time (secs) 0.015   0.032   0.125   0.484   2.016   8.241   43.636  287.648
//          2.133333333 3.90625 3.872   4.165289256 4.087797619 5.294988472 6.591988267
//  a = 0.015   t=aN^1  t=aN^1  t=aN^2  t=aN^2  t=aN^2  t=aN^2  hmm hmm
//                                  
//  Nearest Insertion                               
//  N   1000    2000    4000    8000    16000   32000   64000   128000
//  Tour distance   26645.0597127296    36268.4757486213    53116.3577148216    74925.8878853959    104806.4389817240   148751.1998602620   209494.3787189160   298515.9729605060
//  Time (secs) 0.016   0.015   0.063   0.266   1.266   5.46    29.968  233.503
//          0.9375  4.2 4.222222222 4.759398496 4.312796209 5.488644689 7.791744527
//  a = 0.016   t=aN^1  t=aN^1  t=aN^2  t=aN^2  t=aN^2  t=aN^2  hmm t=aN^3

public class Tour
{
    double distance = 0.0; // let there be a space between things
    private class Node // Let there be Nodes!
    {
        private Point p; // Let the Nodes have Points!
        private Node next; // Let one Node link to the next!
    }
    private Node first = null; // Create the first Node
    private Node best = null; // Create the best Node
    Tour() // create an empty tour. call it pointless.
    {

    }
    Tour(Point a, Point b, Point c, Point d) // create a 4 point tour a->b->c->d->a. call it pointless.
    {
        Node A = new Node();
        Node B = new Node();
        Node C = new Node();
        Node D = new Node();       
        A.p = a;
        B.p = b;
        C.p = c;
        D.p = d;
        A.next = B;
        B.next = C;
        C.next = D;
        D.next = A;
        first = A;
    }
    void show() // print the tour to standard output
    {
        Node current = first; // start at the first node
        if (current != null) // if the node isn't empty
        {
            do // loop through all nodes
            {
                System.out.println(current.p); // print the Point
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first Node
        }
    }
    void draw() // draw the tour to standard draw
    {
        Node current = first; // start at the first node
        if (current != null) // if the node isn't empty
        {
            do // loop through all nodes
            {
                current.p.draw(); // draw the Point
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first Node
            do // loop through all nodes
            {
                current.p.drawTo(current.next.p); // draw from the current Point to the next Point
                current = current.next; // select the next node.
            } while (current != first); // stop when we get back to the first Node
        }
    }
    int size() // number of points on tour
    {
        int size = 0; // counter for list size
        Node current = first; // start at the first node
        if (current != null) // if the node isn't empty
        {
            do // loop through all nodes
            {
                size += 1; // count the node
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first Node
        }
        return size; // return the size of the list
    }
    double distance() // return the total distance of the tour
    {
        Node current = first; // start at the first node
        distance = 0.0; // reset the distance
        if (current != null) // if the node isn't empty
        {
            do // loop through all nodes
            {           
                distance += current.p.distanceTo(current.next.p); // keep track of the total distance between nodes
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first Node
        }
        return distance; // return the distance of the tour
    }
    void insertNearest(Point p) // insert p using nearest neighbor heuristic
    {
        Node current = first; // start at the first node
        if (current != null) // if the node isn't empty
        {
            double nearest = 0.0; // set the nearest distance
            do // loop through all nodes
            {
                distance = p.distanceTo(current.p); // calculate the distance between the current point and the new point
                if (distance < nearest || nearest == 0.0) // if we have a new nearest distance
                {
                    nearest = distance; // update the nearest distance
                    best = current; // make note of the best node
                }    
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first node
            Node node = new Node(); // create a new node
            node.p = p; // give the Node a Point
            node.next = best.next; // link the new node to the next node
            best.next = node; // link the best node to the new node
        }
        else // if the node is empty
        {
            Node node = new Node(); // create a new node
            first = node; // make the new node the first node
            node.p = p; // give the new node a point
            node.next = first; // link the new node to itself
        }		
    }
    void insertSmallest(Point p) // insert p using smallest increase heuristic
    {
        Node current = first; // select the first node
        if (current != null) // if the node isn't empty
        {
            double smallest = 0.0; // set the smallest distance
            do // loop through all nodes
            {
                distance = p.distanceTo(current.p) + p.distanceTo(current.next.p) - current.p.distanceTo(current.next.p); // calculate the increase in distance at the current node
                if (distance < smallest || smallest == 0.0) // if we have a new smallest increase for distance
                {
                    smallest = distance; // update the smallest distance
                    best = current; // make note of the best node
                }
                current = current.next; // select the next node
            } while (current != first); // stop when we get back to the first Node
            Node node = new Node(); // create a new node
            node.p = p; // give the new node a point
            node.next = best.next; // link the new node to the next node
            best.next = node; // link the best node to the new node
        }
        else // if the node is empty
        {
            Node node = new Node(); // create a new node
            first = node; // make the new node the first node
            node.p = p; // give the new node a point
            node.next = first; // link the new node to itself
        }
    }
}