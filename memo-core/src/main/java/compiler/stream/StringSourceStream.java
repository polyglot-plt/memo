/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.stream;

import java.io.StringReader;

/**
 * @author trujillo
 */
public class StringSourceStream extends SourceStream {

    public StringSourceStream(String sourceCode) {
        super();
        this.textReader = new StringReader(sourceCode);
    }
}
