package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.values.IntValue;

public class LoadDirOp implements RuntimeOperator {

    final IntValue operand;

    public LoadDirOp(IntValue operand) {
        this.operand = operand;
    }

    @Override
    public void execute(Context context) {
        Integer address = operand.value;
        context.stack.push(address);
    }

    @Override
    public String toString() {
        return "LoadDir " + operand;
    }

}
