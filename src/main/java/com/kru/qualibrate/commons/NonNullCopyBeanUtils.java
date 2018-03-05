package com.kru.qualibrate.commons;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;



/**
 * A utility to copy properties from one object to another.
 * Irrespective of Type of Objects, it copies all properties with same name.
 * It does not assign NULL properties from source hence target property is not erased.
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * @param <T> target object to copy into
 * @param <S> source object to copy from
 */

public class NonNullCopyBeanUtils<T, S> {
    public T copyNonNullProperties(T target, S in) {
        if (in == null || target == null) return null;

        final BeanWrapper src = new BeanWrapperImpl(in);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for (final Field property : target.getClass().getDeclaredFields()) {
            Object providedObject = src.getPropertyValue(property.getName());
            if (providedObject != null && !(providedObject instanceof Collection<?>)) {
                trg.setPropertyValue(
                        property.getName(),
                        providedObject);
            }
        }
        return target;
    }
}
