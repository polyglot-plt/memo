package memo_lang.compiler.abstract_syntax_tree;

import compiler.abstract_syntax_tree.AST;

import java.util.LinkedList;
import java.util.List;

public class ASTProgram extends AST {

    public List<ASTInstruction> instructions;

    public ASTProgram(List<ASTInstruction> instructions) {
        super(0);
        this.instructions = new LinkedList<ASTInstruction>(instructions);
    }

}
