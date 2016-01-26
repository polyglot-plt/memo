package compiler.runtime.operators;

import compiler.runtime.Context;

public class HaltOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        context.halt = true;
        context.stack.push(0);
    }
}