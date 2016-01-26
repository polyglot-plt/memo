package memo_lang.compiler.abstract_syntax_tree;

import compiler.abstract_syntax_tree.AST;

public abstract class ASTInstruction extends AST {

    public ASTInstruction(int line) {
        super(line);
    }
}
