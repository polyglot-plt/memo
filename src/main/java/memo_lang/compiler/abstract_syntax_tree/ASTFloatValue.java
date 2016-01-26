package memo_lang.compiler.abstract_syntax_tree;

import compiler.symbols_table.SymbolInfo;
import memo_lang.compiler.TokenKind;

public class ASTFloatValue extends ASTSymbol {

    public ASTFloatValue(SymbolInfo<TokenKind> entry, int line) {
        super(entry, line);
    }

    @Override
    public Object visit(Visitor visitor) {
        return visitor.visit(this);
    }

}
