package compiler.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frodo
 */
public class Memory {

    public List<Object> store;

    public Memory() {
        store = new ArrayList<Object>();
    }

    public int addVal(Object val) {
        store.add(val);
        return store.size() - 1;
    }

    public void assign(int dir, Object val) {
        store.set(dir, val);
    }

    public Object getVal(int dir) {
        return store.get(dir);
    }

}
