package compiler.lexical_analyzer;

import compiler.architecture_base.TokenKindBase;
import compiler.errors.ErrorReporter;
import compiler.stream.SourceStream;

import java.util.LinkedList;
import java.util.List;

public abstract class LexicalAnalyzer<K extends Enum & TokenKindBase<K>> {

    protected SourceStream in;
    protected ErrorReporter er;

    public LexicalAnalyzer(SourceStream in, ErrorReporter er) {
        this.in = in;
        this.er = er;
    }

    public abstract Token<K> nextToken();

    public ErrorReporter getErrorList() {
        return er;
    }

    public List<Token<K>> allTokens() {
        List<Token<K>> result = new LinkedList<Token<K>>();
        Token<K> nt = nextToken();
        Token<K> eott = nt;
        while (nt.kind != eott.kind.getEot() && nt.kind != eott.kind.getError()) {
            result.add(nt);
            nt = nextToken();
        }
        return result;
    }
}
