package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.operators.RuntimeOperator;
import compiler.runtime.values.IntValue;

public class MultIntOperator implements RuntimeOperator {
    @Override
    public void execute(Context context) {
        IntValue der = (IntValue) context.stack.pop();
        IntValue izq = (IntValue) context.stack.pop();
        IntValue rval = new IntValue(izq.value * der.value);
        context.stack.push(rval);
    }
}
