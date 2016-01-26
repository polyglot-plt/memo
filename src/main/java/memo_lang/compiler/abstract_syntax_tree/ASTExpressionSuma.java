package memo_lang.compiler.abstract_syntax_tree;

/**
 * @author
 */
public class ASTExpressionSuma extends ASTExpressionBinary {

    public ASTExpressionSuma(ASTExpression izq, ASTExpression der, int line) {
        super(izq, der, line);
    }

}
