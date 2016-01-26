package memo_lang.compiler.abstract_syntax_tree;

public class ASTInstructionAssig extends ASTInstruction {

    public ASTIdentifier id;
    public ASTExpression expresion;

    public ASTInstructionAssig(ASTIdentifier id, ASTExpression expresion, int line) {
        super(line);
        this.id = id;
        this.expresion = expresion;
    }

}
