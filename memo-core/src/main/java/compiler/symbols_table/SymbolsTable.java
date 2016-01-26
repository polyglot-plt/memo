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

import java.util.*;

public class SymbolsTable<K extends Enum & TokenKindBase<K>, T extends SymbolInfo<K>> implements Scope<K, T> {

    Map<String, T> symbols;

    public SymbolsTable() {
        symbols = new HashMap<String, T>();
    }

    public String getScopeName() {
        return "global";
    }

    public Scope getEnclosingScope() {
        return null;
    }

    public void define(T sym) {
        if (!symbols.containsKey(sym.lexeme))
            symbols.put(sym.lexeme, sym);
    }

    public SymbolInfo<K> resolve(String name) {
        return symbols.getOrDefault(name, null);
    }

    public String toString() {
        return getScopeName() + ":" + symbols;
    }

    public ArrayList<T> getAllEntries() {
        ArrayList<T> res = new ArrayList<T>();
        for (String key : symbols.keySet())
            res.add(symbols.get(key));
        return res;
    }

    public ArrayList<T> getAllEntriesOf(K... types2Get) {
        Set<K> types = new HashSet<K>();
        Collections.addAll(types, types2Get);
        ArrayList<T> res = new ArrayList<T>();
        for (String key : symbols.keySet())
            if (types.contains(symbols.get(key).kind))
                res.add(symbols.get(key));
        return res;
    }

    public void fillAddress(K... types2Fill) {
        Set<K> types = new HashSet<K>();
        Collections.addAll(types, types2Fill);
        int mDir = -1;
        for (T info : getAllEntries())
            if (types.contains(info.kind)) {
                mDir++;
                info.put("address", mDir);
            }
    }
}
