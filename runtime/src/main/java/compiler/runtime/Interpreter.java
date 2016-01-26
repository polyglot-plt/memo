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

package compiler.runtime;

import compiler.runtime.operators.RuntimeOperator;
import ui.io.Out;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    final Context context;

    public Interpreter(List<String> bytecode, Loader loader) {
        final List<RuntimeOperator> code = new ArrayList<RuntimeOperator>();
        context = new Context(code);

        final Integer count = Integer.parseInt(bytecode.get(0));

        context.mkVars(count);

        for (int i = count * 3 + 1; i < bytecode.size(); i++)
            code.add(loader.createOperation(bytecode.get(i)));

        for (int i = 0; i < count; i++) {
            Object[] data = loader
                    .createConstantValue(
                            bytecode.get(i * 3 + 1),
                            bytecode.get(i * 3 + 2),
                            bytecode.get(i * 3 + 3));

            context.memory.assign((Integer) data[0], data[1]);
        }
    }

    public void execute() {
//        Out.writeLine("********** Memo Interpreter (Java Version 3.1) **********");
        Out.writeLine("Program is running...");
        while (!context.halt) {
            RuntimeOperator rtOp = context.code.get(context.cursor);
            rtOp.execute(context);
            context.cursor++;
        }
        if (context.stack.size() != 1)
            Out.writeLine("Program has halted with operands on the stack.");
        else if ((Integer) context.stack.peek() != 0)
            Out.writeLine("Program has halted with exit code %d.", context.stack.peek());
        else
            Out.writeLine("Program has halted normally.");
    }
}
