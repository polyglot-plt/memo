package memo_lang.compiler.lexical_analyzer;

import compiler.errors.ErrorReporter;
import compiler.errors.LexicalError;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.lexical_analyzer.Token;
import compiler.stream.SourceStream;
import memo_lang.compiler.TokenKind;

import java.util.HashMap;

public class Lexer extends LexicalAnalyzer<TokenKind> {

    private char c; //currentChar
    private HashMap<String, TokenKind> kws;

    public Lexer(SourceStream in, ErrorReporter er) {
        super(in, er);
        c = in.read();
        kws = new HashMap<String, TokenKind>();
        fillKeywordtable();
    }

    private void fillKeywordtable() {
        kws.put("write", TokenKind.Write);
        kws.put("float", TokenKind.Float);
        kws.put("int", TokenKind.Int);
    }

    boolean isLetter() {
        return Character.isLetter(c) || c == '_';
    }

    boolean isDigit() {
        return Character.isDigit(c);
    }

    void consume() {
        c = in.read();
    }

    void WS() {
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
            consume();
        }
    }

    Token<TokenKind> ID() {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isLetter() || isDigit());
        String lexeme = buf.toString();
        TokenKind key = kws.get(lexeme);
        if (key != null) {
            return new Token<TokenKind>(key, lexeme, in.getCurrentLine());
        }
        return new Token<TokenKind>(TokenKind.Id, lexeme, in.getCurrentLine());
    }

    Token<TokenKind> Literal() {
        StringBuilder buf = new StringBuilder();
        TokenKind kind = TokenKind.IntLiteral;
        do {
            buf.append(c);
            consume();
        } while (isDigit());
        if (c == '.') {
            do {
                buf.append(c);
                consume();
            } while (isDigit());
            kind = TokenKind.FloatLiteral;
        }
        return new Token<TokenKind>(kind, buf.toString(), in.getCurrentLine());
    }

    @Override
    public Token<TokenKind> nextToken() {
        while (c != '\0') {
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                WS(); // skip
            }
            switch (c) { // which token approaches?
                case '+':
                    consume();
                    return new Token<TokenKind>(TokenKind.Sum, "+", in.getCurrentLine());
                case '*':
                    consume();
                    return new Token<TokenKind>(TokenKind.Multiplication, "*", in.getCurrentLine());
                case '(':
                    consume();
                    return new Token<TokenKind>(TokenKind.LeftParen, "(", in.getCurrentLine());
                case ')':
                    consume();
                    return new Token<TokenKind>(TokenKind.RightParen, ")", in.getCurrentLine());
                case ';':
                    consume();
                    return new Token<TokenKind>(TokenKind.SemiColon, ";", in.getCurrentLine());
                case '=':
                    consume();
                    return new Token<TokenKind>(TokenKind.Assignment, "=", in.getCurrentLine());
                case '\0':
                    break;
                default: {
                    if (isLetter()) {
                        return ID(); // match ID
                    } else {
                        if (isDigit()) {
                            return Literal(); //match Literal
                        } else {
                            er.add(new LexicalError(
                                    in.getCurrentLine(), "Lexical error: unexpected character " + c));
                            Token<TokenKind> tk_error = new Token<TokenKind>(TokenKind.Error, String.valueOf(c), in.getCurrentLine());
                            consume();
                            return tk_error;
                        }
                    }
                }
            }
        }
        return new Token<TokenKind>(TokenKind.EOT, "\0", in.getCurrentLine());
    }

}