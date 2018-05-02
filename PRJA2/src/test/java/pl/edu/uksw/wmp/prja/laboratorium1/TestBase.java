/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uksw.wmp.prja.laboratorium1;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author pkacz_000
 */
public class TestBase {
    
    public TestBase() {
        try {
            Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        } catch (ClassNotFoundException e) {
            fail("Nie utworzono odpowiedniej klasy");
        }        
    }

    protected Object getFieldValue(Object o, String fieldName) {
        try {
            Field f = o.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(o);
        } catch (NoSuchFieldException ex) {
            fail("Nie ma pola " + fieldName);
        } catch (SecurityException ex) {
            fail("" + ex.getCause());
        } catch (IllegalArgumentException ex) {
            fail("" + ex.getCause());
        } catch (IllegalAccessException ex) {
            fail("" + ex.getCause());
        }
        return null;
    }
    
    protected Object invokeMethod(Object o, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException {
        try {
            Class<?>[] types = new Class<?>[args.length];
            for(int i=0; i<args.length; i++) {
                if(types[i] == Integer.class) {
                    types[i] = int.class;
                } else {    
                    types[i] = args[i].getClass();
                }
            }
            Method method = o.getClass().getMethod(methodName, 
                                                   types);
            return method.invoke(o, args);
        } catch (InvocationTargetException ex) {
            System.out.println("Blad podczas wykonywania metody " + methodName + ": " + ex.getCause());
        }
        return null;
    }
    
    protected void checkIfEnumFieldExists(Class enumClass, String fieldName) {
        List<Field> fields = Arrays.asList(enumClass.getDeclaredFields());
        boolean correct = false;
        for (Field f : fields) {
            if (f.isEnumConstant() && f.getName().equals(fieldName)) {
                correct = true;
            }
        }
        assertTrue(fieldName + " nie istnieje", correct);
        
    }
}
