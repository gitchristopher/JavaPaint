package com.gui;
import com.dwg.McCanvas;
import com.files.VecFile;
import com.shapes.McShape;


import java.awt.*;
import javax.swing.JFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Keymap;
import javax.swing.undo.UndoManager;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;

import static com.files.VecFile.OpenFileDlg;
import static com.files.VecFile.SaveFileDlg;

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
    public static McCanvas _theCanvas;
    //Default the colours to black and no colour
    public static Color edgeColour = Color.BLACK, fillColour = null;
    public static ArrayList<McShape> LoadedData = new ArrayList<McShape>();
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;



    public static void main(String [] args)
    {
        new McDrawApp();
    }


    public McDrawApp(){

        //Set the default values for the applications GUI
        this.setTitle("McDrawApp - Vector Design Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 678);
        //this.setMinimumSize(new Dimension(700, 678));



        this.setFocusable(true);

        //if(this.requestFocus(true))
        //    System.out.println("requestFocus: true");
        //this.addKeyListener(new KeyListener()
        //{
        //    @Override
        //    public void keyTyped(KeyEvent e)
        //    {
        //        // Not implemented
        //    }
//
        //    @Override
        //    public void keyPressed(KeyEvent e)
        //    {
        //        int keyCode = e.getKeyCode();
        //        //System.out.println(this);
        //        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0))
        //        {
        //            McDrawApp.this.btnUndo.doClick();
        //            System.out.println();
        //            System.out.println("ctrl+z pressed");
        //        }
        //    }
//
        //    @Override
        //    public void keyReleased(KeyEvent e)
        //    {
        //        // Not implemented
        //    }
        //});
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
        buttonPanel.setBackground(Color.decode("#e3e3e3"));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        btnSelectMarker = MakePaintToolButton(1,"","Click to plot a marker", "./Images/MARKER.png");
        btnSelectLine = MakePaintToolButton(2,"","Click and drag to draw lines", "./Images/PEN.png");
        btnSelectRectangle = MakePaintToolButton(3,"","Click and drag to draw rectangles", "./Images/RECTANGLE.png");
        btnSelectEllipse = MakePaintToolButton(4,"","Click and drag to draw Ellipses", "./Images/ELLIPSE.png");
        btnSelectPolygon = MakePaintToolButton(5,"","Left click and drag to plot points, click near where you started to auto complete", "./Images/POLYGON.png");
        //btnSelectFinPolygon = MakePaintToolButton(6,"Finish Polygon","Click here to connect final line of your polygon");

        //btnSend = MakeFunctionalButton("Send","Not currently implemented",1);
        btnUndo = MakeFunctionalButton("","Undo last shape","./Images/UNDO.png",2);
        btnSelectEdgeColour = MakeFunctionalButton("","Select an edge colour","./Images/SWATCH.png",3);
        btnSelectFillColour = MakeFunctionalButton("","Select a fill colour","./Images/FILL.png",4);
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
        //This is so the part under the buttons is the same colour as the easel panel
        JPanel p = new JPanel();
        p.setBackground(Color.decode("#dcdcdc"));
        buttonPanel.add(p);
        //buttonPanel.add(btnSaveVec);
        //Align buttons
        ////btnSelectEdgeColour.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectFillColour.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectMarker.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectEllipse.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnSelectPolygon.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSelectFinPolygon.setAlignmentX(Component.CENTER_ALIGNMENT);
        //label.setAlignmentX(Component.CENTER_ALIGNMENT);
        //tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
        ////btnUndo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //btnSaveVec.setAlignmentX(Component.CENTER_ALIGNMENT);

        //TODO Size buttons
        //TODO uniform buttons
        ////btnSelectLine.setMinimumSize(new Dimension(80, 60));
        ////btnSelectEdgeColour.setMinimumSize(new Dimension(80, 60));
        ////btnSelectFillColour.setMinimumSize(new Dimension(80, 60));
        ////btnSelectMarker.setMinimumSize(new Dimension(80, 60));
        //btnSelectMarker.setMaximumSize(new Dimension(80, 60));
        ////btnSelectRectangle.setMinimumSize(new Dimension(80, 60));
        ////btnSelectEllipse.setMinimumSize(new Dimension(80, 60));
        ////btnSelectPolygon.setMinimumSize(new Dimension(80, 60));
        ////btnUndo.setMinimumSize(new Dimension(80, 60));

        //Adds Icons to buttons
        ////Icon btnLineIcon = new ImageIcon("./Images/PEN.png");
        ////btnSelectLine.setIcon(btnLineIcon);
        ////Icon btnSwatchIcon = new ImageIcon("./Images/SWATCH.png");
        ////btnSelectEdgeColour.setIcon(btnSwatchIcon);
        ////Icon btnFillIcon = new ImageIcon("./Images/FILL.png");
        ////btnSelectFillColour.setIcon(btnFillIcon);
        ////Icon btnMarkerIcon = new ImageIcon("./Images/MARKER.png");
        ////btnSelectMarker.setIcon(btnMarkerIcon);
        ////Icon btnRecIcon = new ImageIcon("./Images/RECTANGLE.png");
        ////btnSelectRectangle.setIcon(btnRecIcon);
        ////Icon btnEllipseIcon = new ImageIcon("./Images/ELLIPSE.png");
        ////btnSelectEllipse.setIcon(btnEllipseIcon);
        ////Icon btnPolygonIcon = new ImageIcon("./Images/POLYGON.png");
        ////btnSelectPolygon.setIcon(btnPolygonIcon);
        ////Icon btnUndoIcon = new ImageIcon("./Images/UNDO.png");
        ////btnUndo.setIcon(btnUndoIcon);
        


        //This is the panel which will hold the canvas to be painted on
        JPanel easelPanel = new JPanel();
        easelPanel.setLocation(5, 5);
        easelPanel.setSize(500, 500);
        easelPanel.setBackground(Color.decode("#dcdcdc"));
        //easelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //easelPanel.setLayout(new BorderLayout());
        easelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //Scroll bars
        //final Scrollbar horizontalScroller = new Scrollbar(Scrollbar.HORIZONTAL);
        //final Scrollbar verticalScroller = new Scrollbar(Scrollbar.VERTICAL);
        //verticalScroller.setOrientation(Scrollbar.VERTICAL);
        //horizontalScroller.setOrientation(Scrollbar.HORIZONTAL);
        //horizontalScroller.setMaximum (1000);
        //horizontalScroller.setMinimum (1);
        //verticalScroller.setMaximum (1000);
        //verticalScroller.setMinimum (1);



        //Status panel with zoom slider
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(this.getWidth(), 30));
        //statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //TODO add slider instead of label
        // create a slider
        JSlider slider = new JSlider(5,800,100);
        setPreferredSize(new Dimension(300, 30));

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int value = ((JSlider) e.getSource()).getValue();
                double scale = value / 100.0;
                //int w = (int) (McDrawApp._theCanvas._width * scale);
                int h = (int) (McDrawApp._theCanvas._height * scale);
                Dimension size = new Dimension(h, h);
                //Dimension size = new Dimension(w, h);
                McDrawApp._theCanvas.setSize(size); // !!
                McDrawApp._theCanvas.setPreferredSize(size); // !!
                McDrawApp._theCanvas.repaint();
                McDrawApp._theCanvas.invalidate();
                McDrawApp._theCanvas.revalidate();
            }
        }
        );



        JLabel statusLabel = new JLabel("Zoom:  ");
        JLabel zoomRestLabel = new JLabel("Rest");
        zoomRestLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Resetting zoom level");
                slider.setValue(100);
            }
        });

        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        statusPanel.add(slider);
        statusPanel.add(zoomRestLabel);


        //Actual paint area
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


    //METHODS
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
    private JButton MakeFunctionalButton(String buttonText, String buttonToolTip, String iconLocation,final int buttonId){
        JButton myButton = new JButton();
        myButton.setText(buttonText);
        myButton.setToolTipText(buttonToolTip);
        myButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        myButton.setMinimumSize(new Dimension(80, 60));
        myButton.setIcon(new ImageIcon(iconLocation));

        switch(buttonId) {
            case 1:
                //TODO: Add listener to send button on bottom menu bar
                //code block
                break;
            case 2:
                myButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        McDrawApp._theCanvas.removeLastShape();
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





    public void saveFile()
    {
        System.out.println("McDrawApp->saveFile method: Opening the save file dialogue");
        ArrayList<McShape> tempListOfMcShapes = this._theCanvas.getMcShapesList();
        SaveFileDlg(tempListOfMcShapes);
    }
    public void openFile()
    {
        System.out.println("McDrawApp->openFile method: Opening the open file dialogue");
        OpenFileDlg();
    }
    public void paintData()
    {
        System.out.println("McDrawApp->paintData method: Setting _theCanvas list of shapes to LoadedData list of imported shapes");
        this._theCanvas.setMcShapesList(LoadedData);
    }

}