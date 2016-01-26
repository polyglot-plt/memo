package memo_lang.compiler.abstract_syntax_tree;

public interface Visitor<T> {

    T visit(ASTProgram o);

    T visit(ASTInstructionDeclaration o);

    T visit(ASTInstructionAssig o);

    T visit(ASTInstructionWrite o);

    T visit(ASTExpressionSuma o);

    T visit(ASTExpressionMult o);

    T visit(ASTIdentifierDeclaration o);

    T visit(ASTIdentifierReference o);

    T visit(ASTIdentifierValue o);

    T visit(ASTFloatValue o);

    T visit(ASTIntValue o);
}
