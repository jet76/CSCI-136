/*************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : Learns to make predictions by text given at startup and
 *               by things the user types into the program. As users type,
 *               the program predicts the most likely word given the
 *               currently entered word prefix.
 *************************************************************************/

import java.awt.Color;
import java.awt.Font;

public class PredictiveKeyboard 
{	
    public static void main(String [] args)
    {
        String word = "";
        String sentence = "";
        String prediction = "";
        String keyboard = "abcdefghijklmnopqrstuvwxyz'";
        WordPredictor wp = new WordPredictor();
        for (String a : args) wp.train(a);
        StdDraw.setCanvasSize(1024, 256);
        while (true)
        {
            wp.build();
            if (StdDraw.hasNextKeyTyped())
            {
                char ch = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(ch);
                if (ch == ' ' && word != null)
                {                    
                    sentence += word + " ";
                    wp.trainWord(word);
                    word = "";
                }
                else if (ch =='\b')
                {
                    if (word.length() == 1) word = "";
                    else if (word.length() > 1)
                    {
                        word = word.substring(0, word.length() - 1);
                    }
                }
                else if (ch == '\n' && prediction != "")
                {
                    sentence += prediction + " ";
                    wp.trainWord(prediction);
                    word = "";
                }
                if (index >= 0 && index < keyboard.length()) word += ch;
                StdDraw.clear();
                if (word != "")
                {
                    DictEntry e = wp.getBest(word);
                    if (e != null) prediction = e.getWord();
                    else prediction = "";
                    StdDraw.setFont(new Font("Consolas", Font.PLAIN, 24));
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.text(0.5, 0.75, word);
                }
                else prediction = "";
                if (prediction != "")
                {
                    StdDraw.setFont(new Font("Consolas", Font.PLAIN, 24));
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.text(0.5, 0.5, prediction);
                }
                if (sentence != "")
                {
                    StdDraw.setFont(new Font("Consolas", Font.PLAIN, 16));
                    StdDraw.setPenColor(Color.black);
                    StdDraw.textRight(1, 0.25, sentence);
                }
            }
        }
    }
}
