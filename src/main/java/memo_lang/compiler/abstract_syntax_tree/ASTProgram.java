package memo_lang.compiler.abstract_syntax_tree;

import java.util.LinkedList;
import java.util.List;

public class ASTProgram extends ASTVisitor {

    public List<ASTInstruction> instructions;

    public ASTProgram(List<ASTInstruction> instructions) {
        super(0);
        this.instructions = new LinkedList<ASTInstruction>(instructions);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
