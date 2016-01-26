package memo_lang.compiler.abstract_syntax_tree;

public class ASTFloatValue extends ASTSymbol {

    public ASTFloatValue(int line) {
        super(line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
