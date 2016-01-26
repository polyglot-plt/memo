package compiler.runtime.operators;

import compiler.runtime.Context;

public class StoreOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        Object value = context.stack.pop();
        Integer address = (Integer) context.stack.pop();
        context.memory.assign(address, value);
    }
}
