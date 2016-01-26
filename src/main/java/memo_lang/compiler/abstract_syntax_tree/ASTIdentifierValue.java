package memo_lang.compiler.abstract_syntax_tree;

public class ASTIdentifierValue extends ASTIdentifier {

    public ASTIdentifierValue(int line) {
        super(line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
