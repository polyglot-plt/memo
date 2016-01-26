
package compiler.symbols_table;

import compiler.architecture_base.TokenKindBase;

public class BuiltInTypeSymbol<K extends Enum & TokenKindBase<K>> extends SymbolInfo<K> implements Kind {
    public BuiltInTypeSymbol(String lexeme, K kind) {
        super(lexeme, kind);
    }
}