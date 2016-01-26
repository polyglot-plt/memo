package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.values.FloatValue;

public class SumFloatOp implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        FloatValue der = (FloatValue) context.stack.pop();
        FloatValue izq = (FloatValue) context.stack.pop();
        FloatValue rval = new FloatValue(izq.value + der.value);
        context.stack.push(rval);
    }
}
