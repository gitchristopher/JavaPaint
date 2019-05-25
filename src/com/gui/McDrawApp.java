package com.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

import com.dwg.McCanvas;
import com.shapes.McShape;
import static com.files.VecFile.OpenFileDlg;
import static com.files.VecFile.SaveFileDlg;

/**
 * @author Moarie Hawas & Christopher Linnan
 * @version 1.0
 */
public class McDrawApp extends JFrame
{

    // Monitors which paint action was last selected by the user
    public static int currentPaintingAction = 0;

    // Lets the app know if the user is still drawing, used when drawing polygons
    public static boolean currentlyDrawing = false;

    // Lets the app know if the polygon is yet to be closed, polygons only become shapes after being closed
    public static boolean isPolyOpen = false;

    // Defaults the colours to black edges and no colour fill colour
    public static Color edgeColour = Color.BLACK, fillColour = null;

    // The array temporarily list hold shapes when a drawing is loaded
    public static ArrayList<McShape> LoadedData = new ArrayList<McShape>();

    // CONSTANT used for ctrl + z so the app knows whats in focus
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    // The canvas drawn on by the user
    private static McCanvas _theCanvas;

    //UI Elements
    JButton btnSelectMarker, btnSelectLine, btnSelectRectangle, btnSelectEllipse, btnSelectPolygon, btnUndo, btnSelectEdgeColour, btnSelectFillColour;


    /**
     * The Entry point of the paint app
     * @param args
     */
    public static void main(String [] args)
    {
        new McDrawApp();
    }

    /**
     * Creates a new instance of the paint app and all the GUI elements
     */
    public McDrawApp(){

        // Set the default values for the applications GUI
        this.setTitle("McDrawApp - Vector Design Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setMinimumSize(new Dimension(640, 740));
        this.setFocusable(true);

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

        newOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });
        openOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        saveOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        // Button Menu Bar
        // Creating the panel at LEFT side and adding components
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        buttonPanel.setBackground(Color.decode("#e3e3e3"));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Create shape painting buttons
        btnSelectMarker = MakePaintToolButton(1,"","Click to plot a marker", "./Images/MARKER.png");
        btnSelectLine = MakePaintToolButton(2,"","Click and drag to draw lines", "./Images/PEN.png");
        btnSelectRectangle = MakePaintToolButton(3,"","Click and drag to draw rectangles", "./Images/RECTANGLE.png");
        btnSelectEllipse = MakePaintToolButton(4,"","Click and drag to draw Ellipses", "./Images/ELLIPSE.png");
        btnSelectPolygon = MakePaintToolButton(5,"","Left click and drag to plot points, click near where you started to auto complete", "./Images/POLYGON.png");

        // Create functional buttons
        btnUndo = MakeFunctionalButton("","Undo last shape","./Images/UNDO.png",2);
        btnSelectEdgeColour = MakeFunctionalButton("","Select an edge colour","./Images/SWATCH.png",3);
        btnSelectFillColour = MakeFunctionalButton("","Select a fill colour","./Images/FILL.png",4);

        // Add buttons to UI
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
        buttonPanel.add(btnUndo);

        // This is so the part under the buttons is the same colour as the easel panel when the window is stretched
        JPanel p = new JPanel();
        p.setBackground(Color.decode("#dcdcdc"));

        buttonPanel.add(p);


        // This is the panel which will hold the canvas to be painted on
        JPanel easelPanel = new JPanel();
        easelPanel.setLocation(5, 5);
        easelPanel.setSize(500, 500);
        easelPanel.setBackground(Color.decode("#dcdcdc"));
        easelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));


        // Bottom status panel with zoom slider
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JSlider slider = new JSlider(10,400,100);
        setPreferredSize(new Dimension(300, 30));
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                McDrawApp._theCanvas.zoom(((JSlider) e.getSource()).getValue());
            }
        }
        );

        JLabel statusLabel = new JLabel("Zoom:  ");
        JLabel zoomRestLabel = new JLabel("Rest");
        zoomRestLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                slider.setValue(100);
            }
        });

        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        statusPanel.add(slider);
        statusPanel.add(zoomRestLabel);


        // The actual paint area
        _theCanvas = new McCanvas(500, 500);
        _theCanvas.setLayout(null);
        easelPanel.add(_theCanvas);

        JScrollPane myScrollyPanel = new JScrollPane(easelPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Adding Components to the frame.
        this.add(BorderLayout.SOUTH, statusPanel);
        this.add(BorderLayout.WEST, buttonPanel);
        this.add(BorderLayout.NORTH, menuBar);
        this.add(BorderLayout.CENTER, myScrollyPanel);
        this.setVisible(true);
    }


    // METHODS

    /**
     * Makes a paint tool button for different shapes by setting various parameters and adding an action listener
     * @param paintingActionNumber The number represents what tool is being used, each number is unique
     * @param buttonText The text that would appear on the button face
     * @param buttonToolTip The helpful tooltip
     * @param iconLocation The location in the file structure for an icon for the button
     * @return a new button
     */
    private JButton MakePaintToolButton(final int paintingActionNumber, String buttonText, String buttonToolTip, String iconLocation){
        JButton myButton = new JButton();
        myButton.setText(buttonText);
        myButton.setToolTipText(buttonToolTip);
        myButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myButton.setMinimumSize(new Dimension(80, 60));
        myButton.setIcon(new ImageIcon(iconLocation));

        if (paintingActionNumber >= 0){
            myButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentPaintingAction = paintingActionNumber;
                }
            });
        }

        return myButton;
    }


    //Makes a new button adding in the button text and tooltip,
    //the button ID determines what action listener function gets added to the button

    /**
     * Makes a functional button for different utilities by setting various parameters and adding an action listener
     * @param buttonText The text that would appear on the button face
     * @param buttonToolTip The helpful tooltip
     * @param iconLocation The location in the file structure for an icon for the button
     * @param buttonId An internal switch statement chooses what action should be performed based on this number
     * @return a new button
     */
    private JButton MakeFunctionalButton(String buttonText, String buttonToolTip, String iconLocation,final int buttonId){
        JButton myButton = new JButton();
        myButton.setText(buttonText);
        myButton.setToolTipText(buttonToolTip);
        myButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myButton.setMinimumSize(new Dimension(80, 60));
        myButton.setIcon(new ImageIcon(iconLocation));

        switch(buttonId) {
            case 1:
                //TODO: Space for another function
                //code block
                break;
            case 2: // Undo button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        McDrawApp._theCanvas.removeLastShape();
                    }
                });
                break;
            case 3: // Edge colour picker button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        edgeColour = JColorChooser.showDialog(null,  "Select an edge colour", Color.BLACK);
                    }
                });
                break;
            case 4: // Fill colour picker button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fillColour = JColorChooser.showDialog(null,  "Select a fill colour", Color.BLACK);
                    }
                });
                break;
            case 5: // Save file button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });
            case 6: // New file button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();
                    }
                });
            case 8: // Open file button
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        openFile();
                    }
                });
            default:
                //TODO: do something better for the default switch of the button maker function
                //code block
        }

        return myButton;
    }

    /**
     * Getter for the current painting action which represents which shape is being drawn
     * @return an integer representing which shape is being drawn
     */
    public int getCurrentPaintAction(){
        return currentPaintingAction;
    }

    /**
     * Open the save file dialogue filtered to the VEC file format while passing the list of shapes
     */
    private void saveFile()
    {
        ArrayList<McShape> tempListOfMcShapes = this._theCanvas.getMcShapesList();
        SaveFileDlg(tempListOfMcShapes);
    }

    /**
     * Opens the open file dialogue filtered to the VEC file format
     */
    private void openFile()
    {
        boolean opened = OpenFileDlg();
        if (opened)
        {
            newFile();
            sendDataToCanvas();
        }
    }

    /**
     * Sends data that was just loaded, using the openFile function, to the canvas
     */
    private void sendDataToCanvas()
    {
        this._theCanvas.setMcShapesList(LoadedData);
        LoadedData.clear();
    }

    /**
     * Clears the canvas of all shapes
     */
    private void newFile()
    {
        _theCanvas.resetCanvas();
        edgeColour = Color.BLACK;
        fillColour = null;
        isPolyOpen = false;
        currentlyDrawing = false;
    }

}