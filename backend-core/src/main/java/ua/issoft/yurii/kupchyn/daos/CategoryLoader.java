package ua.issoft.yurii.kupchyn.daos;

import ua.issoft.yurii.kupchyn.entities.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CategoryLoader {
    Logger LOGGER = Logger.getLogger(CategoryLoader.class.getName());

    public List<Category> load(String packageName) throws CategoryLoaderException {
        try (BufferedReader reader = createPackageReader(packageName)) {
            return reader
                    .lines()
                    .filter(file -> file.endsWith(".class"))
                    .map(file -> loadClass(packageName, file))
                    .filter(cls -> !cls.isInterface())
                    .filter(cls -> !Modifier.isAbstract(cls.getModifiers()))
                    .filter(Category.class::isAssignableFrom)
                    .map(Category.class.getClass()::cast)
                    .map(this::createCategory)
                    .collect(Collectors.toList());
        } catch (IOException | CategoryLoaderInnerException e) {
            LOGGER.log(Level.SEVERE, "Can't load categories from package " + packageName, e);
            throw new CategoryLoaderException("Can't load categories from package " + packageName, e);
        }
    }

    private BufferedReader createPackageReader(String packageName) {
        return new BufferedReader(new InputStreamReader(createPackageStream(packageName)));
    }

    private InputStream createPackageStream(String packageName) {
        return Optional.ofNullable(ClassLoader
                        .getSystemClassLoader()
                        .getResourceAsStream(packageName.replaceAll("[.]", "/")))
                .orElseThrow(() -> new CategoryLoaderInnerException("Can't load categories from package " + packageName));
    }

    private Class<?> loadClass(String packageName, String className) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new CategoryLoaderInnerException("Class not found:" + className, e);
        }
    }

    private Category createCategory(Class<? extends Category> categoryClass) {
        try {
            return categoryClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new CategoryLoaderInnerException("Failed to create class category instance:" + categoryClass, e);
        }
    }

    private static class CategoryLoaderInnerException extends RuntimeException {
        public CategoryLoaderInnerException(String message) {
            super(message);
        }

        public CategoryLoaderInnerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}