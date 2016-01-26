package memo_lang.compiler.abstract_syntax_tree;

public class ASTInstructionWrite extends ASTInstruction {

    public ASTExpression expresion;

    public ASTInstructionWrite(ASTExpression expresion, int line) {
        super(line);
        this.expresion = expresion;
    }

}
