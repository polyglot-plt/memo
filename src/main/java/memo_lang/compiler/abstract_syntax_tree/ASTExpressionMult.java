package memo_lang.compiler.abstract_syntax_tree;

/**
 * @author
 */
public class ASTExpressionMult extends ASTExpressionBinary {

    public ASTExpressionMult(ASTExpression izq, ASTExpression der, int line) {
        super(izq, der, line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
