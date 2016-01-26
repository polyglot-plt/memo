
package compiler.symbols_table;

import compiler.architecture_base.TokenKindBase;

public class LliteralSymbol<K extends Enum & TokenKindBase<K>> extends SymbolInfo<K> implements Kind {
    public LliteralSymbol(String lexeme, K kind) {
        super(lexeme, kind);
    }
}