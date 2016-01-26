package compiler.lexical_analyzer;

import compiler.architecture_base.TokenKindBase;

public class Token<T extends Enum & TokenKindBase<T>> {

    public T kind;
    public String lexeme;
    public int line;

    /**
     * Constructor para los tokens cuyos lexemas deben ser incluidos en la tabla de símbolos.
     *
     * @param kind
     * @param lexeme
     * @param line
     */
    public Token(T kind, String lexeme, int line) {
        this.kind = kind;
        this.lexeme = lexeme;
        this.line = line;
    }

    @Override
    public String toString() {
        return "<" + lexeme + " " + kind + ">";
    }
}
