package compiler.runtime.operators;

import compiler.runtime.Context;
import compiler.runtime.RuntimeEntity;

//@FunctionalInterface
public interface RuntimeOperator extends RuntimeEntity {
    void execute(Context context);
}
