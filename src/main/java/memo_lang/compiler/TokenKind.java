package memo_lang.compiler;

import compiler.architecture_base.TokenKindBase;

public enum TokenKind implements TokenKindBase<TokenKind> {

    Int,

    Float,

    Write,

    Id,

    FloatLiteral,

    IntLiteral,

    SemiColon,

    LeftParen,

    RightParen,

    Sum,

    Multiplication,

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
