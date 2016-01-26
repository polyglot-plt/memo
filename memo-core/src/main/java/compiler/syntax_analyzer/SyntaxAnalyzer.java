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

package compiler.syntax_analyzer;

import compiler.abstract_syntax_tree.AST;
import compiler.architecture_base.TokenKindBase;
import compiler.errors.ErrorReporter;
import compiler.errors.SyntacticError;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.lexical_analyzer.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class SyntaxAnalyzer<K extends Enum & TokenKindBase<K>, N extends AST> {

    public Token<K> ct; //cursor token
    protected LexicalAnalyzer<K> in; // from were do we get the tokens
    protected ErrorReporter er;

    public SyntaxAnalyzer(LexicalAnalyzer<K> in, ErrorReporter er) {
        this.in = in;
        this.er = er;
    }

    public void consume() {
        ct = in.nextToken();
    }

    public void recuperateUntil(K tk_expected) {
        while (ct.kind != tk_expected && ct.kind != tk_expected.getEot()) {
            consume();
        }
        if (ct.kind == tk_expected)
            consume();
    }

    @SuppressWarnings("unchecked")
    public Set<K> mkFirsts(K... objs) {
        Set<K> result = new HashSet<K>();
        Collections.addAll(result, objs);
        return result;
    }

    public void match(K tk_expected) {
        if (tk_expected == ct.kind) {
            if (ct.kind != tk_expected.getEot())
                consume();
        } else {
            er.add(new SyntacticError(
                    ct.line,
                    "Expected: " + tk_expected + ", found: " + ct.kind));
        }
    }

    public int selectProduction(Set... sets) {
        int result = 0;
        boolean found = false;
        while (result < sets.length && !found) {
            if (sets[result].contains(ct.kind))
                found = true;
            result++;
        }
        if (!found)
            result = 0;
        return result;
    }

    public abstract N parse();
}
