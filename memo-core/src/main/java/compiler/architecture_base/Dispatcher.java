/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p/>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package compiler.architecture_base;


import java.lang.reflect.Method;

public class Dispatcher<T> {

    String methodName;
    Object target;
    T defaultValue;

    public Dispatcher(Object target, String methodName) {
        this(target, methodName, null);
    }

    public Dispatcher(Object target, String methodName, T defaultValue) {
        this.target = target;
        this.methodName = methodName;
        this.defaultValue = defaultValue;
    }

    /**
     * Mecanismo de despacho:
     * - Busco un método de nombre methodName que reciba por parámetro un objeto de tipo o.getClass()
     * Si no lo encuentro busco uno que reciba objetos de algún tipo padre de o.getClass()
     *
     * @param o
     * @return Lo ejecuto si lo encuentro, devuelvo defaultValue en caso contrario.
     */
    @SuppressWarnings("unchecked")
    public T call(Object o) {// ¿Cambios en la semántica de Java?
        Method m = null;
        T res = null;
        Method[] methods = target.getClass().getMethods();
        Class initClass = o.getClass();

        while (m == null && initClass != Object.class) {
            int i = 0;
            while (i < methods.length && m == null) {
                if (methods[i].getParameterCount() == 1 &&
                        methods[i].getParameterTypes()[0].getTypeName().equals(initClass.getName())) {
                    m = methods[i];
                }
                i++;
            }
            initClass = initClass.getSuperclass();
        }
        try {
            if (m != null) {
                res = (T) m.invoke(target, o);
            } else
                res = defaultValue;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
