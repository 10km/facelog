package net.gdface.facelog.simplemq;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/** 
 * 用于通过创建一个子类在运行获取泛型类型的{@link Type}<br>
 *
 */
public class TypeRef<T> {
    protected final Type type;
	protected final Class<?> rawType;
    private static Class<?> getRawClass(Type type){
        if(type instanceof Class<?>){
            return (Class<?>) type;
        } else if(type instanceof ParameterizedType){
            return getRawClass(((ParameterizedType) type).getRawType());
        } else{
            throw new IllegalArgumentException("invalid type");
        }
    }
    protected TypeRef(){
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.rawType = getRawClass(((ParameterizedType) superClass).getActualTypeArguments()[0]);
    }

    /**
     * Gets underlying {@code Type} instance.
     */
    public Type getType() {
        return type;
    }
	public Class<?> getRawType() {
		return rawType;
	}
}
