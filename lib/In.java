package stdlib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Simplified version of Princeton's In class.
 * This class provides methods for reading input data from files.
 */
public final class In {
    
    private Scanner scanner;

    /**
     * Initializes an input stream from a filename.
     *
     * @param  filename the name of the file
     */
    public In(String filename) {
        if (filename == null) throw new IllegalArgumentException("filename is null");
        try {
            // first try to read file from local file system
            File file = new File(filename);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis));
                return;
            }
            
            // resource relative to the class loader
            InputStream is = In.class.getResourceAsStream(filename);
            if (is == null) {
                is = In.class.getResourceAsStream("/" + filename);
            }
            if (is == null) {
                is = In.class.getClassLoader().getResourceAsStream(filename);
            }
            if (is == null) {
                is = In.class.getClassLoader().getResourceAsStream("/" + filename);
            }
            if (is == null) {
                throw new IllegalArgumentException("Could not open " + filename);
            }
            scanner = new Scanner(new BufferedInputStream(is));
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + filename, ioe);
        }
    }

    /**
     * Returns true if this input stream has a next line.
     * Use this method to know whether the
     * next call to {@link #readLine()} will succeed.
     *
     * @return {@code true} if this input stream has more input (including whitespace);
     *         {@code false} otherwise
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Returns true if this input stream is empty (except possibly for whitespace).
     * Use this method to know whether the next call to {@link #readInt()},
     * {@link #readDouble()}, etc. will succeed.
     *
     * @return {@code true} if this input stream is empty (except possibly
     *         for whitespace); {@code false} otherwise
     */
    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    /**
     * Reads and returns the next line in this input stream.
     *
     * @return the next line in this input stream; {@code null} if no such line
     */
    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (Exception e) {
            line = null;
        }
        return line;
    }

    /**
     * Reads and returns the next integer in this input stream.
     *
     * @return the next integer in this input stream
     */
    public int readInt() {
        return scanner.nextInt();
    }

    /**
     * Reads and returns the next double in this input stream.
     *
     * @return the next double in this input stream
     */
    public double readDouble() {
        return scanner.nextDouble();
    }

    /**
     * Reads and returns the next boolean in this input stream.
     *
     * @return the next boolean in this input stream
     */
    public boolean readBoolean() {
        return scanner.nextBoolean();
    }

    /**
     * Closes this input stream.
     */
    public void close() {
        scanner.close();
    }
} 