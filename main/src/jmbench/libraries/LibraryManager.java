package jmbench.libraries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Examines the external directory and reads all the libraries.  Then configures them.
 *
 * @author Peter Abeles
 */
public class LibraryManager {

    List<LibraryDescription> all;


    /**
     * Loads all the libraries in the external directory.
     */
    public LibraryManager() {
        all = new ArrayList<LibraryDescription>();

        File dirExternal = new File("external");

        if( !dirExternal.exists() )
            throw new RuntimeException("Can't find external directory");
        if( !dirExternal.isDirectory())
            throw new RuntimeException("external is not a directory!");

        File[] files = dirExternal.listFiles();

        for( File f : files ) {
            if( !f.isDirectory() )
                continue;

            File settings = new File(f,"TestSetInfo.txt");
            if( settings.exists() ) {
                all.addAll(parseDescription(settings));
            }
        }

        assignPlotStuff();
    }

    private void assignPlotStuff() {
        // sort into alphabetical order
    }

    protected List<LibraryDescription> parseDescription(File file) {
        List<LibraryStringInfo> listString = LibraryTools.loadTests(file);

        List<LibraryDescription> out = new ArrayList<LibraryDescription>();

        for( LibraryStringInfo info : listString ) {
            LibraryDescription desc = new LibraryDescription();

            desc.directory = file.getParent();
            desc.info = info;

            out.add( desc );
        }

        return out;
    }

    /**
     * Reads a list of libraries that are to be tested from a file and loads their information
     *
     * @param targetFile
     */
    public List<LibraryDescription> loadTargetFile( String targetFile ) {
        return null;
    }

    public LibraryDescription lookup( String name ) {
        for (int i = 0; i < all.size(); i++) {
            LibraryStringInfo l = all.get(i).info;

            if( l.nameFull.compareToIgnoreCase(name) == 0 )
                return all.get(i);
            else if( l.nameShort.compareToIgnoreCase(name) == 0 )
                return all.get(i);
            else if( l.namePlot.compareToIgnoreCase(name) == 0 )
                return all.get(i);
        }
        return null;
    }

    public void printAllNames() {
        for (int i = 0; i < all.size(); i++) {
            LibraryStringInfo l = all.get(i).info;

            System.out.println("Library "+l.nameFull+"  in directory "+all.get(i).directory);
        }
    }
}