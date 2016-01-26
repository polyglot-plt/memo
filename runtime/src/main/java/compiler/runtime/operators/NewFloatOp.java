package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.values.FloatValue;

public class NewFloatOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        context.memory.addVal(new FloatValue());
    }
}
