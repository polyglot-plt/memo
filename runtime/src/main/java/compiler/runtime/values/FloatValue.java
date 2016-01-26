package compiler.runtime.values;

/**
 * @author frodo
 */
public class FloatValue extends RuntimeValue<Float> {

    public FloatValue() {
        super(0.0f);
    }

    public FloatValue(Float value) {
        super(value);
    }

}
