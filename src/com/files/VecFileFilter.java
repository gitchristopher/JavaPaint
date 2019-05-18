package com.files;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class VecFileFilter extends FileFilter
{

    String _fileDescription = "";
    String _fileExtension = "";

    public VecFileFilter(String extension, String description) {
        this._fileExtension = extension;
        this._fileDescription = description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return (f.getName().toLowerCase().endsWith(this._fileExtension));
    }

    @Override
    public String getDescription() {
        return this._fileDescription;
    }
}
