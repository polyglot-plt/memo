package memo_lang.compiler;

import compiler.architecture_base.TokenKindBase;
import tools.TkPattern;

public enum TokenKind implements TokenKindBase<TokenKind> {

    @TkPattern("int")
    Int,

    @TkPattern("float")
    Float,

    @TkPattern("write")
    Write,

    @TkPattern("[a-zA-Z][a-zA-Z0-9]*")
    Id,

    @TkPattern("[1-9][0-9]*\\.[0-9]+")
    FloatLiteral,

    @TkPattern("[1-9][0-9]*")
    IntLiteral,

    @TkPattern(";")
    SemiColon,

    @TkPattern("\\(")
    LeftParen,

    @TkPattern("\\)")
    RightParen,

    @TkPattern("\\+")
    Sum,

    @TkPattern("\\*")
    Multiplication,

    @TkPattern("=")
    Assignment,

    Error,
    EOT;

    @Override
    public TokenKind getEot() {
        return EOT;
    }

    @Override
    public TokenKind getError() {
        return Error;
    }
}
