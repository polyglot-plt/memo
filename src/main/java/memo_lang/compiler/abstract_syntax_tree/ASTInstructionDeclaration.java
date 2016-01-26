package memo_lang.compiler.abstract_syntax_tree;

import memo_lang.compiler.MemoTypes;

public class ASTInstructionDeclaration extends ASTInstruction {

    public MemoTypes type;
    public ASTIdentifierDeclaration id;

    public ASTInstructionDeclaration(MemoTypes type, ASTIdentifierDeclaration id, int line) {
        super(line);
        this.type = type;
        this.id = id;
    }

    public ASTInstructionDeclaration() {
        super(0);
    }

}
