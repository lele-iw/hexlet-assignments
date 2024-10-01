package exercise;

import exercise.annotation.Inspect;
import exercise.model.Address;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        for (Method m : address.getClass().getDeclaredMethods()) {
            for (Annotation a : m.getDeclaredAnnotations()) {
                if (a.annotationType() == Inspect.class) {
                    System.out.println("Method " + m.getName()
                            + " returns a value of type " + m.getReturnType().getSimpleName());
                }
            }
        }
    }
}
