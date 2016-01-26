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
import compiler.architecture_base.GenPhase;
import compiler.architecture_base.SemanticPhase;
import compiler.architecture_base.TokenKindBase;
import compiler.errors.ErrorReporter;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.lexical_analyzer.Token;
import compiler.stream.SourceStream;
import compiler.symbols_table.SymbolInfo;
import compiler.symbols_table.SymbolsTable;
import compiler.syntax_analyzer.SyntaxAnalyzer;

import java.util.LinkedList;
import java.util.List;

public abstract class CompilerBase<K extends Enum<K> & TokenKindBase<K>, N extends AST> implements CompilerBaseInterface {

    protected LexicalAnalyzer<K> scanner;
    protected ErrorReporter errorReporter;
    protected SymbolsTable<K, SymbolInfo<K>> symbolsTable;
    protected SyntaxAnalyzer<K, N> parser;
    protected SemanticPhase<N>[] semPhases;
    protected GenPhase<N> generator;

    public ErrorReporter newErrorReporter() {
        return new ErrorReporter();
    }

    @Override
    public ErrorReporter getErrorReporter() {
        return errorReporter;
    }

    public abstract SymbolsTable<K, SymbolInfo<K>> newSymbolsTable();

    public abstract LexicalAnalyzer<K> newLexicalAnalyzer(SourceStream source);

    public abstract SyntaxAnalyzer<K, N> newSyntaxAnalyzer(ErrorReporter errorReporter);

    public abstract SemanticPhase<N>[] newSemanticPhases(ErrorReporter errorReporter);

    public abstract GenPhase<N> newGenPhase(SymbolsTable<K, SymbolInfo<K>> symbolsTable);

    private N semanticAnalysis(N tree) {
        int i = 0;
        while (errorReporter.isEmpty() && i < semPhases.length) {
            tree = semPhases[i].apply(tree);
            i++;
        }
        return tree;
    }

    private List<String> codeGeneration(N tree) {
        List<String> result = new LinkedList<String>();
        if (errorReporter.isEmpty()) {
            result = generator.genCode(tree);
        }
        return result;
    }

    @Override
    public List<Token<K>> scanAll(SourceStream source) {
        errorReporter = newErrorReporter();
        scanner = newLexicalAnalyzer(source);
        return scanner.allTokens();
    }

    @Override
    public N syntaxAnalysis(SourceStream source) {
        symbolsTable = newSymbolsTable();
        errorReporter = newErrorReporter();
        scanner = newLexicalAnalyzer(source);
        parser = newSyntaxAnalyzer(errorReporter);
        return parser.parse();
    }

    @Override
    public N semanticAnalysis(SourceStream source) {
        N analysis = syntaxAnalysis(source);
        semPhases = newSemanticPhases(errorReporter);
        return semanticAnalysis(analysis);
    }

    @Override
    public List<String> compile(SourceStream source) {
        N analysis = semanticAnalysis(source);
        generator = newGenPhase(symbolsTable);
        return codeGeneration(analysis);
    }

}
