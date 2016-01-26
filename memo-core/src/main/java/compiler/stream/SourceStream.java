/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.stream;

import java.io.IOException;
import java.io.Reader;

/**
 * @author DDC Programación, UCI
 */
public abstract class SourceStream {

    protected Reader textReader;
    private int currentLine;
    private int currentPosition;

    public SourceStream() {
        currentLine = 1;
        currentPosition = 0;
    }

    public char read() {
        int charReaded = -1;

        try {
            charReaded = textReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (charReaded == -1) {
            return '\0';
        }
        currentPosition++;
        char c = (char) charReaded;
        if (c == '\n') {
            currentLine++;
        }
        return c;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
