
package compiler.symbols_table;

import compiler.architecture_base.TokenKindBase;

public interface Scope<K extends Enum & TokenKindBase<K>, T extends SymbolInfo<K>> {

    String getScopeName();

    Scope getEnclosingScope();

    void define(T sym);

    SymbolInfo resolve(String name);
}
