package memo_lang.compiler.abstract_syntax_tree;

public abstract class ASTInstruction extends ASTVisitor {

    public ASTInstruction(int line) {
        super(line);
    }
}
