package org.aston.learning.stage1.testutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestReflectionUtils {

    public static Object getPrivateField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access field: " + fieldName, e);
        }
    }

    public static void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... args) {
        try {
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = getPrimitiveTypeIfNeeded(args[i].getClass());
            }

            Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke method: " + methodName, e);
        }
    }

    private static Class<?> getPrimitiveTypeIfNeeded(Class<?> clazz) {
        if (clazz == Integer.class) return int.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Character.class) return char.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Short.class) return short.class;
        return clazz;
    }
}