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

package memo_lang.compiler.utils;

import compiler.symbols_table.SymbolInfo;
import memo_lang.compiler.MemoTypes;
import memo_lang.compiler.TokenKind;

public class SymbolInfoGetters {

    protected boolean declared(SymbolInfo<TokenKind> symbol) {
        return (Boolean) symbol.getOrDefault("declared", false);
    }

    protected MemoTypes type(SymbolInfo<TokenKind> symbol) {
        return (MemoTypes) symbol.getOrDefault("type", MemoTypes.Undefined);
    }

    protected boolean init(SymbolInfo<TokenKind> symbol) {
        return (Boolean) symbol.getOrDefault("init", false);
    }

    protected Integer address(SymbolInfo<TokenKind> symbol) {
        return (Integer) symbol.getOrDefault("address", -1);
    }
}
