package memo_lang.compiler.abstract_syntax_tree;

import compiler.symbols_table.SymbolInfo;
import memo_lang.compiler.TokenKind;

/**
 * @author
 */
public abstract class ASTSymbol extends ASTExpression {

    public SymbolInfo<TokenKind> entry;

    public ASTSymbol(SymbolInfo<TokenKind> entry, int line) {
        super(line);
        this.entry = entry;
    }

}
