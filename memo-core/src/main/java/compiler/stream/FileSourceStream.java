/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * @author trujillo
 */
public class FileSourceStream extends SourceStream {

    public FileSourceStream(String fileName) throws FileNotFoundException {
        super();
        FileInputStream in = new FileInputStream(fileName);
        this.textReader = new InputStreamReader(in);
    }
}
