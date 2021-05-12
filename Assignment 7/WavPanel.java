/************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : Handles audio files and draws wav forms.
 *************************************************************************/

import java.awt.*;
import javax.swing.*;

public class WavPanel extends JPanel 
{
    // Holds the currently loaded audio data
    private double [] audio = null;

    // Present playback position, -1 if not currently playing
    private int currentIndex = -1;

    // Attempts to load the audio in the given filename.
    // Returns true on success, false otherwise.
    public boolean load(String filename)
    {
        boolean load = true;
        try
        {
            audio = StdAudio.read(filename);
        }
        catch (Exception ex)
        {
            load = false;
        }
        return load;
    }

    // Return the number of samples in the currently loaded audio.
    // Returns 0 if no audio loaded.
    public int getNumSamples()
    {
        int numSamples = 0;
        if (audio != null) numSamples = audio.length;
        return numSamples;
    }

    // Get the index of the next audio sample that will be played.
    // Returns -1 if playback isn't active.
    public int getCurrentIndex()
    {	
        return currentIndex;
    }

    // Sets the index of the next audio sample to be played.
    // Set to -1 when playback is not active.
    public void setCurrentIndex(int i)
    {
        currentIndex = i;
    }

    // Play a single audio sample based on the current index.
    // Advance the index one position.
    // Returns the panel x-coordinate of the played sample.
    // Returns -1 if playback failed (no audio or index out of range).
    public int playAndAdvance()
    {
        try
        {
            StdAudio.play(audio[currentIndex]);
            currentIndex++;
        }
        catch (Exception ex)
        {
            currentIndex = -1;
        }
        return 0;
    }

    // Draw the waveform and the current playing position (if any)
    // This method shouldn't be called directly.  The player
    // should call repaint() whenever you need to update the 
    // waveform visualization.
    public void paintComponent(Graphics g) 
    {
        int scaleX = this.getWidth();
        int scaleY = this.getHeight();
        int increment = (int) getNumSamples() / scaleX;
        int posX = 0;
        for (int i = 0; i < getNumSamples(); i += increment)
        {
            double sample = Math.abs(audio[i]);
            int height = (int) (sample * scaleY);
            if (height < 1) height = 1;
            int offset = (scaleY - height) / 2;
            g.setColor(Color.blue);
            g.drawLine(posX, 0 + offset, posX, scaleY - offset);
            posX++;
        }
        if (getCurrentIndex() > -1)
        {
            int x = getCurrentIndex() / increment;
            g.setColor(Color.red);
            g.drawLine(x, 0, x, scaleY);
        }
    }

}
