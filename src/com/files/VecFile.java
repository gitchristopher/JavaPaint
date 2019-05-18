package com.files;

import com.shapes.McShape;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

public class VecFile
{
    //PrintWriter shapeOutput = createFile("/Users/derekbanas/Documents/workspace3/Java Code/src/customers.txt");
    public VecFile()
    {

    }

    public static void OpenFileDlg()
    {
        System.out.println("The file open method ran");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.addChoosableFileFilter(new VecFileFilter("vec","Vec Images (*.vec)"));
        jFileChooser.setAcceptAllFileFilterUsed(false);

        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }
    public static void SaveFileDlg(ArrayList<McShape> McShapeList)
    {
        System.out.println("The file save method ran");
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

    private static void OpenFile()
    {

    }

    private static void SaveFile(ArrayList<McShape> McShapeList, File aNewFile)
    {
        System.out.println("Selected file: " + aNewFile.getAbsolutePath());
        try
        {
            PrintWriter infoToWrite = new PrintWriter( new BufferedWriter( new FileWriter(aNewFile)));
            writeData(McShapeList, infoToWrite);
        }catch (IOException e)
        {
            System.out.println("An I/O Error Occurred");
            System.exit(0);
        }
    }

    private static void writeData(ArrayList<McShape> McShapeList, PrintWriter infoToWrite)
    {
        List<String> commandList = new ArrayList<>();

        Color edge = Color.BLACK;
        Color fill = Color.BLACK;
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
                String hex = "#"+Integer.toHexString(fill.getRGB()).substring(2).toUpperCase();
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
}


