package compiler.errors;

/**
 * @author tomaso
 */
public class SyntacticError extends CompilerError {

    public SyntacticError(int line, String text) {
        super(line, text);
    }

    public SyntacticError(int line) {
        super(line, "");
    }
}
