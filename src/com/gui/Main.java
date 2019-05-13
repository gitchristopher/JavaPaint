package com.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public static boolean lineButton = true;
    public static boolean recButton = false;
    public static boolean markerButton = false;

    public static void main(String[] args) {

        //Creating the Frame
        JFrame mainFrame = new JFrame("Vector Design Tool");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);


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

        //new drawing panel
        JPanel drawPanel = new JPanel();
        //drawPanel.addMouseListener();
        drawPanel.setLocation(5, 5);
        drawPanel.setSize(570, 500);
        drawPanel.setBackground(Color.lightGray);
        drawPanel.setLayout(new BorderLayout());
        drawPanel.add(new drawing());


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
        mainFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        mainFrame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        mainFrame.setVisible(true);

        drawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawLine button value true and all else false
                lineButton = true;
                recButton = false;
                markerButton = false;
                drawPanel.repaint();
            }
        });

        drawRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawRec button value true and all else false
                lineButton = false;
                recButton = true;
                markerButton = false;
                drawPanel.repaint();
            }
        });

        drawMarker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Makes drawMarker button value true and all else false
                lineButton = false;
                recButton = false;
                markerButton = true;
                drawPanel.repaint();
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