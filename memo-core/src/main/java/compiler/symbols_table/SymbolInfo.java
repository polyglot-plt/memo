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

package compiler.symbols_table;

import compiler.architecture_base.TokenKindBase;

import java.util.HashMap;

public abstract class SymbolInfo<K extends Enum & TokenKindBase<K>> extends HashMap<String, Object> {

    public String lexeme;
    public K kind;

    public SymbolInfo(String lexeme, K kind) {
        this.lexeme = lexeme;
        this.kind = kind;
    }

    @Override
    public boolean equals(Object obj) {
        String objLexeme = ((SymbolInfo) obj).lexeme;
        return objLexeme.equals(lexeme);
    }

    @Override
    public int hashCode() {
        return lexeme.hashCode();
    }

    @Override
    public String toString() {
        if (kind != null) return '<' + lexeme + ":" + kind + '>';
        return lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

}
