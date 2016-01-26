package compiler.runtime.values;

import compiler.runtime.RuntimeEntity;

public abstract class RuntimeValue<T> implements RuntimeEntity {

    public T value;

    public RuntimeValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
