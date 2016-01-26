package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.operators.RuntimeOperator;
import compiler.runtime.values.IntValue;

public class NewIntOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        context.memory.addVal(new IntValue());
    }
}
