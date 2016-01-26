/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.runtime.values;

/**
 * @author frodo
 */
public class IntValue extends RuntimeValue<Integer> {

    public IntValue() {
        super(0);
    }

    public IntValue(Integer value) {
        super(value);
    }

}
