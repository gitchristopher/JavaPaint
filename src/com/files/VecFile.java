package com.files;

import com.gui.McDrawApp;
import com.shapes.*;
import com.shapes.Polygon;
import com.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

/**
 * A custom file format for storing information about a drawing made in the McDrawApp painting application
 */
public class VecFile
{

    /**
     * Constructs a new object to give access to the classes methods
     */
    public VecFile()
    {

    }

    /**
     * Opens the open file dialogue for the user to choose a file to open
     * @return false if the user desided to not open any file
     */
    public static boolean OpenFileDlg()
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            OpenFile(selectedFile);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Opens the save dialogue so the user can save the painting
     * @param McShapeList a list of shapes drawn to the canvas
     */
    public static void SaveFileDlg(ArrayList<McShape> McShapeList)
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showSaveDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File myFile = jFileChooser.getSelectedFile();
            File aNewFile = new File(myFile.getName().replaceAll(".vec","")+".vec");
            SaveFile(McShapeList, aNewFile);
        }
    }

    /**
     * Opens the VEC file and reads in the data
     * @param selectedFile the file which the user want to open
     */
    private static void OpenFile(File selectedFile)
    {

        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
            LoadData(br);
            br.close();
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }finally
        {
            //br.close();
        }
    }

    /**
     * Saves the shape data to a VEC file
     * @param McShapeList the list of shapes to save int he VEC file
     * @param aNewFile the new file to create when saving
     */
    private static void SaveFile(ArrayList<McShape> McShapeList, File aNewFile)
    {
        try
        {
            PrintWriter infoToWrite = new PrintWriter( new BufferedWriter( new FileWriter(aNewFile)));
            WriteData(McShapeList, infoToWrite);
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }
    }

    /**
     * Writes the shape data to a file, called while saving
     * @param McShapeList the list of shapes to save in the VEC file
     * @param infoToWrite a PrintWriter used to write the data to the file
     */
    private static void WriteData(ArrayList<McShape> McShapeList, PrintWriter infoToWrite)
    {
        List<String> commandList = new ArrayList<>();

        Color edge = Color.BLACK;
        Color fill = null;
        boolean edgeUpdated = false;
        boolean fillUpdated = false;
        for (McShape shape:McShapeList)
        {
            // get colour
            Color shapeEdgeColour = shape.getEdgeColour();
            Color shapeFillColour = shape.getFillColour();

            // if its different to the current colour update the colour and set boolean to true
            if (shapeEdgeColour != null)
            {
                if (shapeEdgeColour != edge)
                {
                    edge = shapeEdgeColour;
                    edgeUpdated = true;
                }
            }
            if (shapeFillColour != null)
            {
                if (shapeFillColour != fill)
                {
                    fill = shapeFillColour;
                    fillUpdated = true;
                }
            }else
                {
                    if (shapeFillColour != fill)
                    {
                        fill = null;
                        fillUpdated = true;
                    }
                }

            // if boolean is true input PEN or FILL command and set the bool to false
            if (edgeUpdated)
            {
                String hex = "#"+Integer.toHexString(edge.getRGB()).substring(2).toUpperCase();
                commandList.add("PEN "+hex);
                edgeUpdated = false;
            }
            if (fillUpdated)
            {
                String hex;
                if (fill == null)
                {
                    hex = "OFF";
                }else
                    {
                        hex = "#"+Integer.toHexString(fill.getRGB()).substring(2).toUpperCase();
                    }
                commandList.add("FILL "+hex);
                fillUpdated = false;
            }

            // export shape details
            commandList.add(shape.commandExport());
        }

        for (String command:commandList)
        {
            infoToWrite.println(command);
        }

        infoToWrite.close();
    }

    /**
     * Loads the data from the selected file into the application
     * @param br the reader used to read in the data
     * @throws IOException occurs when there is an error reading the file
     */
    private static void LoadData(BufferedReader br) throws IOException
    {
        ArrayList<McShape> importListOfMcShapes = new ArrayList<McShape>();

        try
        {
            Color edge = Color.BLACK;
            Color fill = null;

            String singleCommand = br.readLine();
            while (singleCommand != null) {

                // Separate the first word (the command) from the values after it
                int i = singleCommand.indexOf(' ');
                String command = singleCommand.substring(0, i);
                String value = singleCommand.substring(i);

                // Switch on command

                switch(command) {
                    case "PEN":
                        // Set the edge colour
                        String edgecolour = value.trim();
                        Color commandEdgeColour = Color.decode(edgecolour);
                        if (commandEdgeColour != null)
                        {
                            if (commandEdgeColour != edge)
                            {
                                edge = commandEdgeColour;
                            }
                        }
                        break;
                    case "FILL":
                        // Set the fill colour
                        String fillcolour = value.trim();
                        Color commandFillColour;
                        if (fillcolour.contains("OFF"))
                        {
                            commandFillColour = null;
                        }else
                            {
                                commandFillColour = Color.decode(fillcolour);
                            }

                        if (commandFillColour != fill)
                        {
                            fill = commandFillColour;
                        }

                        break;
                    case "PLOT":
                        String plotValues = value.trim();
                        Plot newPlot = new Plot(plotValues, edge, fill);
                        importListOfMcShapes.add((McShape)newPlot);
                        break;
                    case "LINE":
                        String lineValues = value.trim();
                        Line newLine = new Line(lineValues, edge, fill);
                        importListOfMcShapes.add((McShape)newLine);
                        break;
                    case "RECTANGLE":
                        String rectValues = value.trim();
                        Rectangle newRectangle = new Rectangle(rectValues, edge, fill);
                        importListOfMcShapes.add((McShape)newRectangle);
                        break;
                    case "ELLIPSE":
                        String ellValues = value.trim();
                        Ellipse newEllipse = new Ellipse(ellValues, edge, fill);
                        importListOfMcShapes.add((McShape)newEllipse);
                        break;
                    case "POLYGON":
                        String polyValues = value.trim();
                        Polygon newPolygon = new Polygon(polyValues, edge, fill);
                        importListOfMcShapes.add((McShape)newPolygon);
                        break;
                    default:
                        // code block
                }

                // Get the next line of the saved document
                singleCommand = br.readLine();
            }
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }catch (NullPointerException e)
        {
            System.out.println("An Null Error Occurred");
            System.exit(0);
        }finally
        {
            br.close();
        }
        br.close();
        McDrawApp.LoadedData.clear();
        McDrawApp.LoadedData.addAll(importListOfMcShapes);
    }
}


