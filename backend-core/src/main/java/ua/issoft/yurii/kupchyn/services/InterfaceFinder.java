package ua.issoft.yurii.kupchyn.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterfaceFinder {
    public static Class<?>[] getAllInterfaces(Class<?> cls) {
        Set<Class<?>> interfaceList = new HashSet<>();
        if (cls.isInterface()) {
            interfaceList.add(cls);
        } else {
            do {
                interfaceList.addAll(List.of(cls.getInterfaces()));
                cls = cls.getSuperclass();
            } while (cls != null);
        }
        return interfaceList.toArray(new Class[0]);
    }
}
