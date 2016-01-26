package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.values.RuntimeValue;

public class PushOp implements RuntimeOperator {

    final RuntimeValue operand;

    public PushOp(RuntimeValue operand) {
        this.operand = operand;
    }

    @Override
    public void execute(Context context) {
        context.stack.push(operand);
    }

    @Override
    public String toString() {
        return "Push " + operand;
    }
}
