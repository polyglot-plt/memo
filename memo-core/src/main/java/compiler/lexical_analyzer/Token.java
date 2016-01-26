package compiler.lexical_analyzer;

import compiler.architecture_base.TokenKindBase;
import compiler.symbols_table.SymbolInfo;

public class Token<T extends Enum & TokenKindBase<T>> {

    public T kind;
    public String lexeme;
    public SymbolInfo<T> entry;
    public int line;

    /**
     * Constructor para los tokens cuyos lexemas deben ser incluidos en la tabla de símbolos.
     *
     * @param kind
     * @param lexeme
     * @param line
     * @param entry
     */
    public Token(T kind, String lexeme, int line, SymbolInfo<T> entry) {
        this.kind = kind;
        this.lexeme = lexeme;
        this.entry = entry;
        this.line = line;
    }

    /**
     * Constructor para los tokens cuyos lexemas no deben ser incluidos  en la tabla de símbolos.
     *
     * @param kind
     * @param lexeme
     * @param line
     */
    public Token(T kind, String lexeme, int line) {
        this(kind, lexeme, line, null);
    }

    @Override
    public String toString() {
        return "<" + lexeme + " " + kind + ">";
    }
}
