package memo_lang.compiler.abstract_syntax_tree;


public class ASTIdentifierReference extends ASTIdentifier {

    public ASTIdentifierReference(int line) {
        super(line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
