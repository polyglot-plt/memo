package memo_lang.compiler.abstract_syntax_tree;

public abstract class ASTExpression extends ASTVisitor {

    public ASTExpression(int line) {
        super(line);
    }
}
