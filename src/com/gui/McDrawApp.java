package com.gui;
import com.dwg.McCanvas;
import com.files.VecFile;
import com.shapes.McShape;


import java.awt.*;
import javax.swing.JFrame;

import javax.swing.*;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class McDrawApp extends JFrame
{
    //TODO: change the paintingActionNumber to a ENUM like superpower enum from tutorial?
    //Monitors which paint action was last selected by the user
    public static int currentPaintingAction = 0;
    public static boolean currentlyDrawing = false;
    public static boolean isPolyOpen = false;
    //TODO: rename tf, im not sure what its for
    //UI Elements
    JButton btnSelectMarker, btnSelectLine, btnSelectRectangle, btnSelectEllipse, btnSelectPolygon, btnSelectFinPolygon, btnSend, btnUndo, btnSelectEdgeColour, btnSelectFillColour, btnSaveVec;
    JTextField tf;
    JPanel easelPanel;
    McCanvas _theCanvas;
    //Default the colours to black and no colour
    public static Color edgeColour = Color.BLACK, fillColour = null;
    public static ArrayList<McShape> LoadedData = new ArrayList<McShape>();

    public static void main(String [] args)
    {
        new McDrawApp();
    }

    public McDrawApp(){

        //Set the default values for the applications GUI
        this.setTitle("McDrawApp - Vector Design Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 650);

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

        openOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
                paintData();
            }
        });
        saveOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        //Button Menu Bar
        //Creating the panel at LEFT side and adding components
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        btnSelectMarker = MakePaintToolButton(1,"","Click to plot a marker");
        btnSelectLine = MakePaintToolButton(2,"","Click and drag to draw lines");
        btnSelectRectangle = MakePaintToolButton(3,"","Click and drag to draw rectangles");
        btnSelectEllipse = MakePaintToolButton(4,"","Click and drag to draw Ellipses");
        btnSelectPolygon = MakePaintToolButton(5,"","Left click and drag to plot points, click near where you started to auto complete");
        //btnSelectFinPolygon = MakePaintToolButton(6,"Finish Polygon","Click here to connect final line of your polygon");

        //btnSend = MakeFunctionalButton("Send","Not currently implemented",1);
        btnUndo = MakeFunctionalButton("","Undo last shape",2);
        btnSelectEdgeColour = MakeFunctionalButton("","Select an edge colour",3);
        btnSelectFillColour = MakeFunctionalButton("","Select a fill colour",4);
        //btnSaveVec = MakeFunctionalButton("Save File","Save your drawing",5);

        //JLabel label = new JLabel("Enter Text");
        //tf = new JTextField(10); // accepts up to 10 characters

        //add buttons
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectEdgeColour);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectFillColour);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectMarker);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectLine);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectRectangle);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectEllipse);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(btnSelectPolygon);
        buttonPanel.add(Box.createVerticalStrut(5));
        //buttonPanel.add(btnSelectFinPolygon);
        //buttonPanel.add(label); // Components Added using Flow Layout
        //buttonPanel.add(tf);
        //buttonPanel.add(btnSend);
        buttonPanel.add(btnUndo);
        //buttonPanel.add(btnSaveVec);
        //Align buttons
        btnSelectEdgeColour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectFillColour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectMarker.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectEllipse.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSelectPolygon.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSelectFinPolygon.setAlignmentX(Component.CENTER_ALIGNMENT);
        //label.setAlignmentX(Component.CENTER_ALIGNMENT);
        //tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUndo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSaveVec.setAlignmentX(Component.CENTER_ALIGNMENT);

        //TODO Size buttons
        //TODO uniform buttons
        btnSelectLine.setMinimumSize(new Dimension(80, 60));
        btnSelectEdgeColour.setMinimumSize(new Dimension(80, 60));
        btnSelectFillColour.setMinimumSize(new Dimension(80, 60));
        btnSelectMarker.setMinimumSize(new Dimension(80, 60));
        //btnSelectMarker.setMaximumSize(new Dimension(80, 60));
        btnSelectRectangle.setMinimumSize(new Dimension(80, 60));
        btnSelectEllipse.setMinimumSize(new Dimension(80, 60));
        btnSelectPolygon.setMinimumSize(new Dimension(80, 60));
        btnUndo.setMinimumSize(new Dimension(80, 60));

        //Adds Icons to buttons
        Icon btnLineIcon = new ImageIcon("./Images/PEN.png");
        btnSelectLine.setIcon(btnLineIcon);
        Icon btnSwatchIcon = new ImageIcon("./Images/SWATCH.png");
        btnSelectEdgeColour.setIcon(btnSwatchIcon);
        Icon btnFillIcon = new ImageIcon("./Images/FILL.png");
        btnSelectFillColour.setIcon(btnFillIcon);
        Icon btnMarkerIcon = new ImageIcon("./Images/MARKER.png");
        btnSelectMarker.setIcon(btnMarkerIcon);
        Icon btnRecIcon = new ImageIcon("./Images/RECTANGLE.png");
        btnSelectRectangle.setIcon(btnRecIcon);
        Icon btnEllipseIcon = new ImageIcon("./Images/ELLIPSE.png");
        btnSelectEllipse.setIcon(btnEllipseIcon);
        Icon btnPolygonIcon = new ImageIcon("./Images/POLYGON.png");
        btnSelectPolygon.setIcon(btnPolygonIcon);
        Icon btnUndoIcon = new ImageIcon("./Images/UNDO.png");
        btnUndo.setIcon(btnUndoIcon);
        


        //This is the panel which will hold the canvas to be painted on
        JPanel easelPanel = new JPanel();
        easelPanel.setLocation(5, 5);
        easelPanel.setSize(500, 500);
        easelPanel.setBackground(Color.lightGray);
        easelPanel.setLayout(new BorderLayout());
        _theCanvas = new McCanvas();
        _theCanvas.setLayout(null);
        easelPanel.add(_theCanvas);


        //Adding Components to the frame.
        this.add(BorderLayout.WEST, buttonPanel);
        this.add(BorderLayout.NORTH, menuBar);
        this.add(BorderLayout.CENTER, easelPanel);
        this.setVisible(true);
    }


    //METHODS
    private JButton MakePaintToolButton(final int paintingActionNumber, String buttonText, String buttonToolTip){
        JButton myButton = new JButton();
        myButton.setText(buttonText);
        myButton.setToolTipText(buttonToolTip);

        if (paintingActionNumber >= 0){
            myButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentPaintingAction = paintingActionNumber;
                    //TODO: Not sure if this action listener should repaint or if it should be done elsewhere
                    //easelPanel.repaint();
                }
            });
        }

        return myButton;
    }

    //TODO: Change name of MakeFunctionalButton, im not sure what these buttons are meant to do with the text field
    //Makes a new button adding in the button text and tooltip,
    //the button ID determines what action listener function gets added to the button
    private JButton MakeFunctionalButton(String buttonText, String buttonToolTip, final int buttonId){
        JButton myButton = new JButton();
        myButton.setText(buttonText);
        myButton.setToolTipText(buttonToolTip);

        switch(buttonId) {
            case 1:
                //TODO: Add listener to send button on bottom menu bar
                //code block
                break;
            case 2:
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //clear text from tf //TODO: update the comment once tf has been renamed
                        tf.setText("");
                    }
                });
                break;
            case 3:
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        edgeColour = JColorChooser.showDialog(null,  "Select an edge colour", Color.BLACK);
                    }
                });
                break;
            case 4:
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fillColour = JColorChooser.showDialog(null,  "Select a fill colour", Color.BLACK);
                    }
                });
                break;
            case 5:
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });
            default:
                //TODO: do something better for the default switch of the button maker function
                //code block
        }

        return myButton;
    }

    public int getCurrentPaintAction(){
        return currentPaintingAction;
    }





    public void saveFile(){

        ArrayList<McShape> tempListOfMcShapes = this._theCanvas.getMcShapesList();

        for (McShape s:tempListOfMcShapes)
        {
            System.out.println(s);
        }

        System.out.println("Save file");
        VecFile x = new VecFile();
        x.SaveFileDlg(tempListOfMcShapes);

    }
    public void openFile(){
        //Component c = this.easelPanel.getComponent(0);

        //Canvas can = (Canvas)c;
        //System.out.println(can);
        //ArrayList<McShape> tempListOfMcShapes = can.getMcShapesList();


        //for (McShape s:tempListOfMcShapes)
        //{
        //    System.out.println(s);
        //}
        System.out.println("Open file");
        VecFile x = new VecFile();
        x.OpenFileDlg();
    }
    public void paintData(){


        this._theCanvas.setMcShapesList(LoadedData);
    }
}