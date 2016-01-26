package compiler.runtime.operators;

import compiler.runtime.Context;
import ui.io.Out;

public class WriteOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        Object value = context.stack.pop();
        Out.writeLine(value.toString());
    }
}
