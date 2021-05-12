/*************************************************************************
 * Name        : Joe Turner
 * Username    : jeturner
 * Description : A Java GUI application that loads WAV format audio files,
 *               displays a waveform visual display of the audio and allows
 *               the user to playback the audio. 
 *************************************************************************/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

public class WavPlayer implements ActionListener
{
    // instance variables
    String title = "Wav Player"; // frame title
    JFrame frame; // frame
    WavPanel wav; // panel for WavPanel
    JPanel controls; // panel for controls
    JPanel file; // panel for file controls
    JPanel player; // panel for player controls
    JLabel filelabel; // label for file controls
    JTextField filename; // textfield for file controls
    JButton load; // load button for file controls
    JButton play; // play button for player controls
    JButton stop; // stop button for player controls
    Thread thread; // thread for audio worker

    // Go!
    public void go()
    {
        // frame
        frame = new JFrame(title); // create frame and give it a title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make sure the frame will exit on close
        frame.setSize(500,300); // set the size of the frame

        // wav panel
        wav = new WavPanel(); // create a panel for the wav graphics

        // control panel
        controls = new JPanel(); // create a panel to hold the file and player controls
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS)); // arrange panels widgets horizontally        

        // file panel
        file = new JPanel(); // create a panel to hold the file widgets
        file.setLayout(new BoxLayout(file, BoxLayout.X_AXIS)); // arrange panels widgets vertically
        filelabel = new JLabel("Filename:"); // create a filename label for the file panel
        filename = new JTextField(); // create a filename text-field for the panel
        load = new JButton("Load"); // create a load button for the panel
        // add widgets to the file panel
        file.add(filelabel);
        file.add(filename);
        file.add(load);

        // player panel
        player = new JPanel(); // create a panel to hold the player widgets        
        player.setLayout(new BoxLayout(player, BoxLayout.X_AXIS)); // arrange panels widgets vertically
        play = new JButton("Play"); // create a play button for the panel
        play.setEnabled(false);
        stop = new JButton("Stop"); // create a stop button for the panel
        stop.setEnabled(false);
        // add widgets to the player panel
        player.add(play);
        player.add(stop);

        // event handlers
        load.addActionListener(new loadListener());
        play.addActionListener(new playListener());
        stop.addActionListener(new stopListener());

        // add the file and player to control panel
        controls.add(file); // add file to control panel
        controls.add(player); // add player to control panel

        // add wav and controls to the frame;
        Container pane = frame.getContentPane(); // get the frame's pane
        pane.add(BorderLayout.CENTER, wav); // add the wav panel to the pane and center it
        pane.add(BorderLayout.SOUTH, controls); // add the control panel to the pane and put it at the bottom

        // make the frame visible
        frame.setVisible(true);
    }

    // listener for load button
    class loadListener implements ActionListener
    {
        // event handler for button
        public void actionPerformed(ActionEvent event)
        {
            if (wav.load(filename.getText())) // if a file has loaded
            {
                frame.setTitle(title + " - " + filename.getText()); // update frame title
                play.setEnabled(true); // enable play button
                frame.repaint(); // repaint frame
            }
            else // if file load fails
            {
                frame.setTitle(title); // update frame title
                play.setEnabled(false); // disable play button
            }
        }
    }

    // listener for play button
    class playListener implements ActionListener
    {
        // worker class for playing audio
        class Worker implements Runnable
        {
            // run method for audio thread
            public void run()
            {
                // loop to play audio until end or stop button pressed
                while (wav.getCurrentIndex() < wav.getNumSamples() && stop.isEnabled())
                {
                    wav.playAndAdvance(); // play audio sample and advance the index
                    frame.repaint(); // repaint frame
                }
                wav.setCurrentIndex(-1); // reset the index
                play.setEnabled(true); // enable play button
                stop.setEnabled(false);
                frame.repaint(); // repaint the frame
            }
        }
        public void actionPerformed(ActionEvent event)
        {
            wav.setCurrentIndex(0); // set the index to audio start
            play.setEnabled(false); // disable play button
            stop.setEnabled(true); // enable stop button
            Worker worker = new Worker(); // create the worker
            thread = new Thread(worker); // create thread for worker
            thread.start(); // start worker thread
        }
    }

    // listener for stop button
    class stopListener implements ActionListener
    {
        // event handler
        public void actionPerformed(ActionEvent event)
        {
            stop.setEnabled(false); // disable stop button
            play.setEnabled(true); // enable play button
        }
    }

    public static void main(String [] args)
    {
        WavPlayer player = new WavPlayer();
        player.go();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub
    }

}
