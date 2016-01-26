/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p/>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package memo_lang.compiler;

import compiler.CompilerBase;
import compiler.errors.ErrorReporter;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.stream.SourceStream;
import compiler.syntax_analyzer.SyntaxAnalyzer;
import memo_lang.compiler.lexical_analyzer.Lexer;
import memo_lang.compiler.syntax_analyzer.Parser;

public class MemoCompiler extends CompilerBase<TokenKind> {

    @Override
    public LexicalAnalyzer<TokenKind> newLexicalAnalyzer(SourceStream in) {
        return new Lexer(in, errorReporter);
    }

    @Override
    public SyntaxAnalyzer<TokenKind> newSyntaxAnalyzer(ErrorReporter er) {
        return new Parser(scanner, er);
    }

}
