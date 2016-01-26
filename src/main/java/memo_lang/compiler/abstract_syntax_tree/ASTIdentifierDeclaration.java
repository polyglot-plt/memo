package memo_lang.compiler.abstract_syntax_tree;

public class ASTIdentifierDeclaration extends ASTIdentifier {

    public ASTIdentifierDeclaration(int line) {
        super(line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
