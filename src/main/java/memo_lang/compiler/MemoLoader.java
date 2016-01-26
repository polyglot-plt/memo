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

import compiler.runtime.Loader;
import compiler.runtime.operators.*;
import compiler.runtime.values.FloatValue;
import compiler.runtime.values.IntValue;

public class MemoLoader implements Loader {

    @Override
    public RuntimeOperator createOperation(String o) {
        RuntimeOperator res = null;
        String[] parts = o.trim().split("[ ]+");
        switch (Enum.valueOf(OperatorsKind.class, parts[0])) {
            case haltOp:
                res = new HaltOp();
                break;
            case newIntOp:
                res = new NewIntOp();
                break;
            case newFloatOp:
                res = new NewFloatOp();
                break;
            case multFloatOp:
                res = new MultFloatOp();
                break;
            case multIntOp:
                res = new MultIntOperator();
                break;
            case sumIntOp:
                res = new SumIntOp();
                break;
            case sumFloatOp:
                res = new SumFloatOp();
                break;
            case storeOp:
                res = new StoreOp();
                break;
            case writeOp:
                res = new WriteOp();
                break;
            case loadDirOp:
                res = new LoadDirOp(new IntValue(Integer.parseInt(parts[1])));
                break;
            case loadOp:
                res = new LoadOp(new IntValue(Integer.parseInt(parts[1])));
                break;
        }
        return res;
    }

    @Override
    public Object[] createConstantValue(String type, String pos, String value) {
        Object[] res = new Object[2];
        res[0] = Integer.parseInt(pos);

        switch (Enum.valueOf(TokenKind.class, type)) {
            case FloatLiteral:
                res[1] = new FloatValue(Float.parseFloat(value));
                break;
            case IntLiteral:
                res[1] = new IntValue(Integer.parseInt(value));
                break;
        }

        return res;
    }
}
