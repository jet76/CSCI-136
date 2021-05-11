import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/*************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : This class learns how to make word predictions based on
 *               being shown training data. It can learn from all the words
 *               in a file (via the train() method) or from a single
 *               individual word (via the trainWord() method). After new
 *               training data is added, the build() method must be called
 *               so the class can recompute the most likely word for all
 *               possible prefixes. 
 *************************************************************************/

public class WordPredictor 
{
	// The counts we use to train the probabilities in the unigram model
	private HashMap<String, Integer> wordToCount = new HashMap<String, Integer>();
	
	// A word prefix to dictionary entry map, lets us make the predictions
	private HashMap<String, DictEntry> prefixToEntry = new HashMap<String, DictEntry>(); 
	
	// Total count of all the words we've been trained on 
	private long total = 0;
	
	// Train on the specified file, this method can be called multiple
	// times in order to train on a set of files.  
	public void train(String trainingFile)
	{
	    try
	    {
	        Scanner scan = new Scanner(new File(trainingFile)); // create a scanner to read the input file 
	        while (scan.hasNext()) trainWord(scan.next());
	        scan.close();
	    }
	    catch (Exception ex)
	    {
	        System.out.println("Could not open training file: " + trainingFile);
	    }
	}
	
	// Train on an instance of the given word.
	public void trainWord(String word)
	{
	    String w = word.toLowerCase().replaceAll("[^a-z']", "");
	    if (!wordToCount.containsKey(w)) wordToCount.put(w, 1);
        else
        {
            int c = wordToCount.get(w) + 1;
            wordToCount.remove(w);
            wordToCount.put(w, c);
        }
        total++;
	}

	// Find out how many total words of training data we've seen
	public long getTrainingCount()
	{
	    return total;
	}
	
	// Return the number of times we've seen a particular word.
	// Returns 0 if we've never seen this word.
	public int getWordCount(String word)
	{
	    if (!wordToCount.containsKey(word)) return 0;
	    else return wordToCount.get(word);
	}

	// Prepare the object for prefix to word mapping.
	// This should be called AFTER all the training.
	// It MUST be called before getBest() will work.	
	public void build()
	{
	    prefixToEntry.clear();
	    for (String key : wordToCount.keySet())
	    {
	        double p = (double) wordToCount.get(key) / total;
	        DictEntry e = new DictEntry(key, p);
	        for (int i = 1; i < key.length() + 1; i++)
	        {
	            String s = key.substring(0, i);
	            if (!prefixToEntry.containsKey(s))
	                {
	                prefixToEntry.put(s, e);
	                }
	            else if (p > prefixToEntry.get(s).getProb())
	            {
	                prefixToEntry.remove(s);
	                prefixToEntry.put(s, e);
	            }
	        }
	    }
	}
	
	// Return the best matching DictEntry object based on a prefix.
	// If the prefix doesn't match any known word, returns null.
	public DictEntry getBest(String prefix)
	{
	    DictEntry best = null;
	    if (prefixToEntry.containsKey(prefix)) best = prefixToEntry.get(prefix);
	    return best;
	}
			
	// Test main method
	public static void main(String [] args)
	{		
		// Train a model on the first bit of Moby Dick
		WordPredictor wp = new WordPredictor();		
		System.out.println("bad1 = " + wp.getBest("the"));		
		wp.train("moby_start.txt");
		System.out.println("training words = " + wp.getTrainingCount());
		
		// Try and crash things on bad input
		System.out.println("bad2 = " + wp.getBest("the"));		
		wp.train("thisfiledoesnotexist.txt");
		System.out.println("training words = " + wp.getTrainingCount() + "\n");

		String [] words = {"the", "me", "zebra", "ishmael", "savage"};
		for (String s: words)			
			System.out.println("count, " + s + " = " + wp.getWordCount(s));
		System.out.println();
		
		wp.train("moby_end.txt");
		
		// Check the counts again after training on the end of the book
		for (String s: words)			
			System.out.println("count, " + s + " = " + wp.getWordCount(s));
		System.out.println();

		// Get the object ready to start looking things up
		wp.build();

		// Do some prefix lookups
		String [] test = {"a", "ab", "b", "be", "t", "th", "archang"};
		for (String prefix : test)
			System.out.println(prefix + " -> " + wp.getBest(prefix));
		System.out.println("training words = " + wp.getTrainingCount() + "\n");
	
		// Add two individual words to the training data
		wp.trainWord("beefeater");
		wp.trainWord("BEEFEATER!");
		wp.trainWord("Pneumonoultramicroscopicsilicovolcanoconiosis");
		
		// The change should have no effect for prefix lookup until we build()
		System.out.println("before, b -> " + wp.getBest("b"));
		System.out.println("before, pn -> " + wp.getBest("pn"));
		wp.build();
		System.out.println("after, b -> " + wp.getBest("b"));
		System.out.println("after, pn -> " + wp.getBest("pn"));
		System.out.println("training words = " + wp.getTrainingCount() + "\n");
		
		// Test out training on a big file, timing the training as well
		Stats stats1 = new Stats();
		wp.train("mobydick.txt");
		wp.build();
		for (String prefix : test)
			System.out.println(prefix + " -> " + wp.getBest(prefix));
		System.out.println("training words = " + wp.getTrainingCount());
		System.out.println(stats1);
		
		// Test lookup using random prefixes between 1-6 characters
		System.out.println("\nRandom load test:");
		Stats stats2 = new Stats();
		final String VALID = "abcdefghijklmnopqrstuvwxyz'";
		final long TEST_NUM = 10000000; 
		long hits = 0;
		for (long i = 0; i < TEST_NUM; i++)
		{
			String prefix = "";
			for (int j = 0; j <= (int) (Math.random() * 6); j++)
				prefix += VALID.charAt((int) (Math.random() * VALID.length()));
			DictEntry entry = wp.getBest(prefix);
			if (entry != null)
				hits++;
		}
		System.out.println(stats2);
		System.out.println("Hit % = " + ((double) hits / TEST_NUM * 100.0));
	} 
}

