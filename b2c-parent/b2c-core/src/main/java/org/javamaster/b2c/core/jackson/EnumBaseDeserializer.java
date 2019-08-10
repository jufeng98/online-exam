package org.javamaster.b2c.core.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.javamaster.b2c.core.enums.EnumBase;

import java.io.IOException;

/**
 * <p>将前端传过来的数字转换为实现了EnumBase接口的枚举类对象</p>
 * Created on 2019/4/20.<br/>
 *
 * @author yudong
 */
public class EnumBaseDeserializer<E extends Enum<?> & EnumBase> extends JsonDeserializer<EnumBase>
        implements ContextualDeserializer {

    private Class<E> targetClass;

    public EnumBaseDeserializer() {
    }

    public EnumBaseDeserializer(Class<E> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return EnumBase.codeOf(targetClass, p.getIntValue());
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        targetClass = (Class<E>) ctxt.getContextualType().getRawClass();
        return new EnumBaseDeserializer(targetClass);
    }
}
