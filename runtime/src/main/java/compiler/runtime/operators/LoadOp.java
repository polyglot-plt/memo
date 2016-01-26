package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.values.IntValue;

public class LoadOp implements RuntimeOperator {

    final IntValue operand;

    public LoadOp(IntValue address) {
        operand = address;
    }

    @Override
    public void execute(Context context) {
        Integer address = operand.value;
        context.stack.push(context.memory.getVal(address));
    }

    @Override
    public String toString() {
        return "Load " + operand;
    }
}
