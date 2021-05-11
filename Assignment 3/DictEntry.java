/*************************************************************************
 * Name        : Keith Vertanen
 * Username    : kvertanen
 * Description : Reference solution for Speaking Dictionary assignment.
 *************************************************************************/
public class DictEntry
{
	private String word				= ""; 		// word for this entry
	private double prob				= 0.0;    	// unigram language model probability
	private String audioFilename 	= "";   	// audio filename (if any)
	
	// Constructor, no audio filename passed in
	public DictEntry(String word, double prob)
	{
		this.word = word;
		this.prob = prob;
	}

	// Constructor, all the information
	public DictEntry(String word, double prob, String audioFilename)
	{
		this.word  			= word;
		this.prob  			= prob;
		this.audioFilename 	= audioFilename;
	}
	
	// Getter for the audio filename
	public String getAudio()
	{
		return audioFilename;
	}
	
	// Getter for the word
	public String getWord()
	{
		return word;
	}
	
	// Getter for the probability
	public double getProb()
	{
		return prob;
	}
	
	// Return a string represenation of this object
	public String toString()
	{
		String result = "";
		result += word;
		result += "\t";
		result += prob;
		result += "\t";
		result += audioFilename;
		
		return result;
	}
	
	// See if the regular expression matches this word
	public boolean matchPattern(String pattern)
	{
		return (word.matches(pattern));		
	}
	
}

