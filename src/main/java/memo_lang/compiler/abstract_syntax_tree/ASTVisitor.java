package memo_lang.compiler.abstract_syntax_tree;

import compiler.abstract_syntax_tree.AST;

public abstract class ASTVisitor extends AST {

    public ASTVisitor(int line) {
        super(line);
    }

    public abstract Object visit(Visitor visitor);
}
