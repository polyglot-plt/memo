
package compiler.symbols_table;

import compiler.architecture_base.TokenKindBase;

public class VariableSymbol<K extends Enum & TokenKindBase<K>> extends SymbolInfo<K> {
    public VariableSymbol(String lexeme, K kind) {
        super(lexeme, kind);
    }
}