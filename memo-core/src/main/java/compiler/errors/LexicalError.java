package compiler.errors;

/**
 * @author tomaso
 */

public class LexicalError extends CompilerError {

    public LexicalError(int line, String text) {
        super(line, text);
    }
}
