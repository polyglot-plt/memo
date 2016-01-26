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

package memo_lang.compiler;

import compiler.CompilerBase;
import compiler.abstract_syntax_tree.AST;
import compiler.architecture_base.GenPhase;
import compiler.architecture_base.SemanticPhase;
import compiler.errors.ErrorReporter;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.stream.SourceStream;
import compiler.symbols_table.SymbolInfo;
import compiler.symbols_table.SymbolsTable;
import compiler.syntax_analyzer.SyntaxAnalyzer;
import memo_lang.compiler.code_generator.Encoder;
import memo_lang.compiler.lexical_analyzer.Lexer;
import memo_lang.compiler.semantic_analyzer.TypesChecker;
import memo_lang.compiler.syntax_analyzer.Parser;

public class MemoCompiler extends CompilerBase<TokenKind, AST> {

    public SyntaxAnalyzer<TokenKind, AST> newSyntaxAnalyzer(ErrorReporter er) {
        return new Parser(scanner, symbolsTable, er);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SemanticPhase<AST>[] newSemanticPhases(ErrorReporter errorReporter) {
        return new SemanticPhase[]{new TypesChecker(errorReporter)};
    }

    public LexicalAnalyzer<TokenKind> newLexicalAnalyzer(SourceStream in) {
        return new Lexer(in, errorReporter);
    }

    public SymbolsTable<TokenKind, SymbolInfo<TokenKind>> newSymbolsTable() {
        return new SymbolsTable<TokenKind, SymbolInfo<TokenKind>>();
    }

    @Override
    public GenPhase<AST> newGenPhase(SymbolsTable<TokenKind, SymbolInfo<TokenKind>> symbolsTable) {
        return new Encoder<AST>(symbolsTable);
    }

}
