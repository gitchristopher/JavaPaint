package com.gui;
import com.dwg.McCanvas;



import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class McDrawApp extends JFrame
{
    public static boolean lineButton = true;
    public static boolean recButton = false;
    public static boolean markerButton = false;

    public static void main(String [] args)
    {
        new McDrawApp();
    }

    public McDrawApp(){

        //Set the default values for the applications GUI
        this.setTitle("McDrawApp - Vector Design Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        //Top Menu Bar
        //Creating the MenuBar and adding components
        JMenuBar menuBar = new JMenuBar();
        JMenu fileButton = new JMenu("FILE");
        JMenu helpButton = new JMenu("Help");
        menuBar.add(fileButton);
        menuBar.add(helpButton);
        JMenuItem newOption = new JMenuItem("New");
        JMenuItem openOption = new JMenuItem("Open");
        JMenuItem saveOption = new JMenuItem("Save as");
        fileButton.add(newOption);
        fileButton.add(openOption);
        fileButton.add(saveOption);


        //This is the panel which will hold the canvas to be painted on
        JPanel easelPanel = new JPanel();
        //bottomMenuPanel.addMouseListener();
        easelPanel.setLocation(5, 5);
        easelPanel.setSize(570, 500);
        easelPanel.setBackground(Color.lightGray);
        easelPanel.setLayout(new BorderLayout());
        McCanvas theCanvas = new McCanvas();
        theCanvas.setLayout(null);
        easelPanel.add(theCanvas);

        //Bottom Menu Bar
        //Creating the panel at bottom and adding components
        JPanel bottomPanel = new JPanel(); // the panel is not visible in output
        JButton drawLine = new JButton("Draw Lines");
        drawLine.setToolTipText("Click here to draw lines");
        JButton drawRec = new JButton("Draw Rectangles");
        drawRec.setToolTipText("Click here to draw rectangles");
        JButton drawMarker = new JButton("Place a marker");
        drawMarker.setToolTipText("Click here to place a marker");
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts up to 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        reset.setToolTipText("Clears text field");
        bottomPanel.add(drawLine);
        bottomPanel.add(drawRec);
        bottomPanel.add(drawMarker);
        bottomPanel.add(label); // Components Added using Flow Layout
        bottomPanel.add(label); // Components Added using Flow Layout
        bottomPanel.add(tf);
        bottomPanel.add(send);
        bottomPanel.add(reset);


        //Adding Components to the frame.
        this.add(BorderLayout.SOUTH, bottomPanel);
        this.add(BorderLayout.NORTH, menuBar);
        this.add(BorderLayout.CENTER, easelPanel);
        this.setVisible(true);

        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawLine button value true and all else false
                lineButton = true;
                recButton = false;
                markerButton = false;
                easelPanel.repaint();
            }
        });

        drawRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawRec button value true and all else false
                lineButton = false;
                recButton = true;
                markerButton = false;
                easelPanel.repaint();
            }
        });

        drawMarker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawMarker button value true and all else false
                lineButton = false;
                recButton = false;
                markerButton = true;
                easelPanel.repaint();
            }
        });


        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear text from tf
                tf.setText("");
            }
        });
    }
}