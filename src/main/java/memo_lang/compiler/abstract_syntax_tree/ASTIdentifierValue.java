package memo_lang.compiler.abstract_syntax_tree;

import compiler.symbols_table.SymbolInfo;
import memo_lang.compiler.TokenKind;

public class ASTIdentifierValue extends ASTIdentifier {

    public ASTIdentifierValue(SymbolInfo<TokenKind> entry, int line) {
        super(entry, line);
    }

}
