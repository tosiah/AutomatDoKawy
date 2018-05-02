package pl.edu.uksw.wmp.prja.laboratorium1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author pkacz_000
 */
public class Punkt3Test extends TestBase {

    @Test(timeout=300)
    public void dodajKubki() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Object o = c.newInstance();
        try {
            Method dodajKubki = c.getMethod("dodajKubki", int.class);
            assertFalse("Metoda powinna byc publiczna", (dodajKubki.getModifiers() & Modifier.PUBLIC) == 0);
            dodajKubki.invoke(o, 100);
            assertEquals(110, getFieldValue(o, "iloscKubkow"));
            dodajKubki.invoke(o, 100);
            assertEquals(210, getFieldValue(o, "iloscKubkow"));
            dodajKubki.invoke(o, 1000);
            assertEquals(1000, getFieldValue(o, "iloscKubkow"));
        } catch (NoSuchMethodException ex) {
            fail("Brak prawidlowej metody dodajKubki");
        } catch (SecurityException ex) {
            fail("" + ex.getCause());
        } catch (IllegalArgumentException ex) {
            fail("Zla metoda");
        } catch (InvocationTargetException ex) {
            fail("Blad podczas wykonania metody: " + ex.getCause());
        }
    }
    
    @Test(timeout=300)
    public void dodajMleko() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Class c = Class.forName("pl.edu.uksw.wmp.prja.laboratorium2.Automat");
        Object o = c.newInstance();
        try {
            Method dodajMleko = c.getMethod("dodajMleko", double.class);
            assertFalse("Metoda powinna byc publiczna", (dodajMleko.getModifiers() & Modifier.PUBLIC) == 0);
            dodajMleko.invoke(o, 90.5);
            assertEquals(190.5, getFieldValue(o, "iloscMleka"));
            dodajMleko.invoke(o, 0.5);
            assertEquals(191.0, getFieldValue(o, "iloscMleka"));
            dodajMleko.invoke(o, 10);
            assertEquals(200.0, getFieldValue(o, "iloscMleka"));
        } catch (NoSuchMethodException ex) {
            fail("Brak prawidlowej metody dodajMleko");
        } catch (SecurityException ex) {
            fail("" + ex.getCause());
        } catch (IllegalArgumentException ex) {
            fail("Zla metoda");
        } catch (InvocationTargetException ex) {
            fail("Blad podczas wykonania metody: " + ex.getCause());
        }
    }
    
}