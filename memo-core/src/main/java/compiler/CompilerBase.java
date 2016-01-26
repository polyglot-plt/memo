/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package compiler;

import compiler.abstract_syntax_tree.AST;
import compiler.architecture_base.TokenKindBase;
import compiler.errors.ErrorReporter;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.lexical_analyzer.Token;
import compiler.stream.SourceStream;
import compiler.syntax_analyzer.SyntaxAnalyzer;

import java.util.List;

public abstract class CompilerBase<K extends Enum<K> & TokenKindBase<K>, N extends AST> implements CompilerBaseInterface {

    protected LexicalAnalyzer<K> scanner;
    protected ErrorReporter errorReporter;
    protected SyntaxAnalyzer<K, N> parser;

    public ErrorReporter newErrorReporter() {
        return new ErrorReporter();
    }

    @Override
    public ErrorReporter getErrorReporter() {
        return errorReporter;
    }

    public abstract LexicalAnalyzer<K> newLexicalAnalyzer(SourceStream source);

    @Override
    public List<Token<K>> scanAll(SourceStream source) {
        errorReporter = newErrorReporter();
        scanner = newLexicalAnalyzer(source);
        return scanner.allTokens();
    }

    public abstract SyntaxAnalyzer<K, N> newSyntaxAnalyzer(ErrorReporter errorReporter);

    @Override
    public N syntaxAnalysis(SourceStream source) {
        errorReporter = newErrorReporter();
        scanner = newLexicalAnalyzer(source);
        parser = newSyntaxAnalyzer(errorReporter);
        return parser.parse();
    }

}
