package pl.edu.uksw.wmp.prja.laboratorium1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author pkacz_000
 */
public class Punkt2Test extends TestBase {

    public Punkt2Test() {
        try {
            Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        } catch (ClassNotFoundException e) {
            fail("Nie utworzono odpowiedniej klasy");
        }        
    }
        
    @Test(timeout=300)
    public void konstruktorDomyslny() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Constructor[] constructors = c.getConstructors();
        boolean correct = false;
        for(Constructor cc : constructors) {
            if(cc.getParameterTypes().length == 0) {
                correct = true;
                Object o = c.newInstance();
                Field f = c.getDeclaredField("iloscKubkow");
                f.setAccessible(true);
                int value = ((Integer)f.get(o)).intValue();
                assertEquals(10, value);
                f = c.getDeclaredField("iloscMleka");
                f.setAccessible(true);                
                assertEquals(100.0, ((Double)f.get(o)).doubleValue(), 0.000001);
            }
        }
        assertTrue("Brak konstruktora domyślnego", correct);
    }
    
    @Test(timeout=300)
    public void konstruktorArgumentowy() throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalArgumentException, IllegalAccessException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Constructor[] constructors = c.getConstructors();
        boolean correct = false;
        try {
            for(Constructor cc : constructors) {
                List l = Arrays.asList(cc.getParameterTypes());
                if(l.contains(int.class) && l.contains(double.class)) {
                    correct = true;
                    int a1 = Math.abs(new Random().nextInt());
                    double a2 = Math.abs(new Random().nextDouble());
                    Object o = cc.newInstance(new Integer(a1), new Double(a2));
                    assertEquals(a1, ((Integer)getFieldValue(o, "iloscKubkow")).intValue());
                    assertEquals(a2, ((Double)getFieldValue(o, "iloscMleka")).doubleValue(), 0.000001);
                    o = cc.newInstance(new Integer(-6), new Double(-10.0));
                    assertEquals(0, ((Integer)getFieldValue(o, "iloscKubkow")).intValue());
                    assertEquals(0.0, ((Double)getFieldValue(o, "iloscMleka")).doubleValue(), 0.000001);
                    
                }
            }
        } catch (InvocationTargetException e) {
            fail("Błąd przy wykonywaniu konstruktora: " + e.getCause());
        }
        assertTrue("Brak konstruktora argumentowego", correct);
    }

}