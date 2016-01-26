package memo_lang.compiler.abstract_syntax_tree;

public abstract class ASTExpressionBinary extends ASTExpression {

    public ASTExpression izq;
    public ASTExpression der;

    public ASTExpressionBinary(ASTExpression izq, ASTExpression der, int line) {
        super(line);
        this.izq = izq;
        this.der = der;
    }

}
