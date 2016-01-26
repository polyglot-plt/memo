package compiler.runtime;

import compiler.runtime.operators.RuntimeOperator;

import java.util.List;
import java.util.Stack;

public class Context {

    public Stack<Object> stack;
    public Memory memory;
    public int cursor;
    public boolean halt;
    public List<RuntimeOperator> code;

    public Context(List<RuntimeOperator> code) {
        this.code = code;
        this.stack = new Stack<Object>();
        this.memory = new Memory();
        cursor = 0;
        halt = false;
    }

    public void mkVars(int cant) {
        for (int i = 0; i < cant; i++)
            memory.store.add(0);
    }
}
