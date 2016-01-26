package memo_lang.compiler.abstract_syntax_tree;

public class ASTIntValue extends ASTSymbol {

    public ASTIntValue(int line) {
        super(line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
