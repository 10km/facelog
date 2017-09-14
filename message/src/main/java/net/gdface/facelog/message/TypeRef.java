package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/** 
 * 用于通过创建一个子类在运行获取泛型类型的{@link Type}<br>
 *
 */
public class TypeRef<T> {
    protected final Type type;
    protected TypeRef(){
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    /**
     * Gets underlying {@code Type} instance.
     */
    public Type getType() {
        return type;
    }
}
