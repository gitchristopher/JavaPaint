package files;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * A custom file format filter for the .VEC file format
 */
public class VecFileFilter extends FileFilter
{

    String _fileDescription = "";
    String _fileExtension = "";

    /**
     * Constructs and initialises a new file format filter
     * @param extension the extension format as a string
     * @param description the description of the file formation in a string
     */
    public VecFileFilter(String extension, String description) {
        this._fileExtension = extension;
        this._fileDescription = description;
    }

    /**
     * Restricts the file format
     * @param f the File to test
     * @return true if the file is to be accepted
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return (f.getName().toLowerCase().endsWith(this._fileExtension));
    }

    /**
     * The description of this file format filter.
     * @return the description of this filter
     */
    @Override
    public String getDescription() {
        return this._fileDescription;
    }
}
