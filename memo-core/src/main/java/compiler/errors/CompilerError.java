package compiler.errors;

/**
 * @author tomaso
 */

public class CompilerError {

    private int line;
    private String text;

    public CompilerError(int line, String text) {
        this.line = line;
        this.text = text;
    }

    @Override
    public String toString() {
        return text + " in line " + line;
    }
}
